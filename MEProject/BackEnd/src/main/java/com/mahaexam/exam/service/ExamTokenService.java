package com.mahaexam.exam.service;

import com.mahaexam.common.config.TokenValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service responsible for issuing and validating short-lived exam tokens.
 *
 * <p>When a student starts an exam the client receives an examToken whose
 * expiry is set to:
 * <pre>
 *   now + (paper_template.total_duration in minutes) + 5 minute buffer
 * </pre>
 * At submission time the backend validates this token to ensure:
 * <ul>
 *   <li>The token belongs to the submitting student.</li>
 *   <li>The token has not expired (i.e. the student finished within the allowed window).</li>
 *   <li>The token is for the correct question paper.</li>
 * </ul>
 */
@Service
public class ExamTokenService {

    private static final Logger log = LoggerFactory.getLogger(ExamTokenService.class);

    /** Extra buffer added on top of the exam duration to account for submission latency. */
    private static final int BUFFER_MINUTES = 30;

    private static final String CLAIM_QUESTION_PAPER_ID = "questionPaperId";
    private static final String CLAIM_USER_ID           = "userId";

    /**
     * Generates a short-lived JWT for the given student and question paper.
     *
     * @param userId           the student's user ID
     * @param questionPaperId  the question paper being started
     * @param durationMinutes  the total exam duration (from paper_template.total_duration)
     * @return signed JWT string
     */
    public String generateExamToken(Long userId, Long questionPaperId, int durationMinutes) {
        long expirationMs = (long) (durationMinutes + BUFFER_MINUTES) * 60 * 1000;

        String token = Jwts.builder()
                .setSubject(userId.toString())
                .claim(CLAIM_USER_ID, userId)
                .claim(CLAIM_QUESTION_PAPER_ID, questionPaperId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, TokenValidator.JWT_SECRET)
                .compact();
//        token = Jwts.builder().setSubject(user.getUserId() + "")
//                .claim("roles", user.getApplicationUser().getUserType())
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
//                .signWith(SignatureAlgorithm.HS256, TokenValidator.JWT_SECRET).compact();
        log.info("Issued exam token for userId={} questionPaperId={} duration={}min (+{}min buffer)",
                userId, questionPaperId, durationMinutes, BUFFER_MINUTES);
        return token;
    }

    /**
     * Validates an exam token at submission time.
     *
     * @param examToken        the token received from the client
     * @param expectedUserId   the student's user ID from the session
     * @param questionPaperId  the question paper being submitted
     * @throws IllegalStateException if the token is invalid, expired, or mismatched
     */
    public void validateExamToken(String examToken, Long expectedUserId, Long questionPaperId) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(TokenValidator.JWT_SECRET)
                    .parseClaimsJws(examToken)
                    .getBody();

            Long tokenUserId = claims.get(CLAIM_USER_ID, Long.class);
            Long tokenPaperId = claims.get(CLAIM_QUESTION_PAPER_ID, Long.class);

            if (!expectedUserId.equals(tokenUserId)) {
                throw new IllegalStateException("Exam token does not belong to the submitting student.");
            }
            if (!questionPaperId.equals(tokenPaperId)) {
                throw new IllegalStateException("Exam token is not valid for this question paper.");
            }

            log.info("Exam token validated successfully for userId={} questionPaperId={}", expectedUserId, questionPaperId);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new IllegalStateException("Exam time has expired. Submission is no longer allowed.");
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Invalid exam token: " + e.getMessage());
        }
    }
}

