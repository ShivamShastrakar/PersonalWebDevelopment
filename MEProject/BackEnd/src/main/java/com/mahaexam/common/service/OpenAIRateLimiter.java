package com.mahaexam.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Shared rate limiter for all OpenAI API calls.
 *
 * <p>Enforces two constraints:
 * <ol>
 *   <li><b>Minimum inter-request gap</b> — derived from {@code openai.rateLimit.rpm} (default 500 RPM
 *       → 120 ms between requests). This prevents bursts that would immediately exhaust the quota.</li>
 *   <li><b>Concurrency cap</b> — at most {@code openai.rateLimit.maxConcurrent} (default 5) calls
 *       in-flight simultaneously, so parallel image-generation tasks cannot all hammer the API
 *       at the same instant.</li>
 * </ol>
 *
 * <p>Both {@link com.mahaexam.openai.service.OpenAIServiceImpl} (question generation) and
 * {@link DiagramGeneratorService} (diagram/image generation) acquire a permit from this limiter
 * before each API call.
 */
@Component
public class OpenAIRateLimiter {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIRateLimiter.class);

    /** Maximum requests per minute allowed by the OpenAI tier. */
    @Value("${openai.rateLimit.rpm:500}")
    private int requestsPerMinute;

    /** Maximum simultaneous in-flight requests (prevents burst even within the RPM budget). */
    @Value("${openai.rateLimit.maxConcurrent:5}")
    private int maxConcurrent;

    /**
     * How long to wait on a 429 before the first retry (ms).
     * Doubles on each subsequent 429 up to {@code openai.rateLimit.maxBackoffMs}.
     */
    @Value("${openai.rateLimit.initialBackoffMs:2000}")
    private long initialBackoffMs;

    /** Hard ceiling for exponential back-off wait (ms). Default 60 s. */
    @Value("${openai.rateLimit.maxBackoffMs:60000}")
    private long maxBackoffMs;

    /** Maximum number of 429-triggered retries before giving up. */
    @Value("${openai.rateLimit.maxRetries:5}")
    private int maxRetries;

    // ── internal state ────────────────────────────────────────────────────────

    /** Semaphore enforcing the concurrency cap. Initialised lazily on first use. */
    private volatile Semaphore concurrencySemaphore;

    /** Timestamp (epoch ms) of the last request that was allowed through. */
    private final AtomicLong lastRequestTimeMs = new AtomicLong(0);

    /** Minimum gap between consecutive requests (derived from RPM). */
    private volatile long minGapMs = 120; // default for 500 RPM

    private volatile boolean initialized = false;

    // ── public API ────────────────────────────────────────────────────────────

    /**
     * Acquire the right to make one OpenAI API call.
     *
     * <p>Blocks until:
     * <ul>
     *   <li>a concurrency slot is available, AND</li>
     *   <li>enough time has elapsed since the last call to respect the RPM limit.</li>
     * </ul>
     *
     * <p>Always pair with {@link #release()} in a finally block.
     *
     * @throws InterruptedException if the calling thread is interrupted while waiting
     */
    public void acquire() throws InterruptedException {
        ensureInitialized();
        concurrencySemaphore.acquire();
        throttle();
    }

    /**
     * Release the concurrency slot acquired by {@link #acquire()}.
     * Must be called in a finally block after every {@link #acquire()}.
     */
    public void release() {
        ensureInitialized();
        concurrencySemaphore.release();
    }

    /**
     * Execute a single OpenAI API call with automatic rate-limiting and
     * exponential back-off on 429 responses.
     *
     * @param <T>      return type of the supplier
     * @param supplier the actual HTTP call — must throw {@link RateLimitException}
     *                 when the upstream API returns HTTP 429
     * @return the result of {@code supplier}
     * @throws RateLimitException if all retries are exhausted
     * @throws InterruptedException if interrupted while sleeping between retries
     */
    public <T> T executeWithRetry(RateLimitedSupplier<T> supplier)
            throws InterruptedException, RateLimitException {

        long backoffMs = initialBackoffMs;

        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                acquire();
                try {
                    return supplier.get();
                } finally {
                    release();
                }
            } catch (RateLimitException e) {
                if (attempt == maxRetries) {
                    logger.error("❌ OpenAI 429 rate limit — exhausted all {} retries. Giving up.", maxRetries);
                    throw e;
                }
                logger.warn("⏳ OpenAI 429 rate limit hit (attempt {}/{}). Backing off {}ms before retry…",
                        attempt + 1, maxRetries, backoffMs);
                Thread.sleep(backoffMs);
                backoffMs = Math.min(backoffMs * 2, maxBackoffMs);
            }
        }
        // unreachable — loop above always either returns or throws
        throw new RateLimitException("Retry loop exhausted");
    }

    // ── internals ─────────────────────────────────────────────────────────────

    private void ensureInitialized() {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    minGapMs = requestsPerMinute > 0
                            ? (long) Math.ceil(60_000.0 / requestsPerMinute)
                            : 120;
                    concurrencySemaphore = new Semaphore(Math.max(1, maxConcurrent), true);
                    logger.info("OpenAIRateLimiter initialised: rpm={}, minGap={}ms, maxConcurrent={}, " +
                                    "initialBackoff={}ms, maxBackoff={}ms, maxRetries={}",
                            requestsPerMinute, minGapMs, maxConcurrent,
                            initialBackoffMs, maxBackoffMs, maxRetries);
                    initialized = true;
                }
            }
        }
    }

    /**
     * Spin-wait until the minimum inter-request gap has elapsed, then record
     * the current time as the new "last request" timestamp.
     */
    private void throttle() throws InterruptedException {
        while (true) {
            long now = System.currentTimeMillis();
            long last = lastRequestTimeMs.get();
            long elapsed = now - last;
            if (elapsed >= minGapMs) {
                // Try to atomically claim this slot
                if (lastRequestTimeMs.compareAndSet(last, now)) {
                    return; // we own this request slot
                }
                // Another thread won the CAS — loop again
            } else {
                long waitMs = minGapMs - elapsed;
                logger.debug("Rate limiter: sleeping {}ms to respect {}RPM limit", waitMs, requestsPerMinute);
                TimeUnit.MILLISECONDS.sleep(waitMs);
            }
        }
    }

    // ── helper types ──────────────────────────────────────────────────────────

    /** Supplier that can throw {@link RateLimitException}. */
    @FunctionalInterface
    public interface RateLimitedSupplier<T> {
        T get() throws RateLimitException, InterruptedException;
    }

    /** Thrown when the upstream API returns HTTP 429. */
    public static class RateLimitException extends RuntimeException {
        public RateLimitException(String message) { super(message); }
        public RateLimitException(String message, Throwable cause) { super(message, cause); }
    }
}

