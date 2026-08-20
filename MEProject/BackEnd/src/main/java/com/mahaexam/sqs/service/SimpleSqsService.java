package com.mahaexam.sqs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class SimpleSqsService {

    @Value("${aws.accessKeyId:}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey:}")
    private String secretAccessKey;

    private SqsAsyncClient sqsClient;

    @PostConstruct
    public void initialize() {
        log.info("=== Simple SQS Service Initializing ===");

        try {
            // Get system properties
            String systemAccessKey = System.getProperty("aws.accessKeyId");
            String systemSecretKey = System.getProperty("aws.secretAccessKey");
            String systemAccountId = System.getProperty("aws.accountId");

            log.info("System property aws.accessKeyId: {}", systemAccessKey != null ? "SET" : "NULL");
            log.info("System property aws.secretAccessKey: {}", systemSecretKey != null ? "SET" : "NULL");
            log.info("System property aws.accountId: {}", systemAccountId);

            if (systemAccessKey != null && systemSecretKey != null &&
                !systemAccessKey.trim().isEmpty() && !systemSecretKey.trim().isEmpty()) {

                sqsClient = SqsAsyncClient.builder()
                    .region(Region.AP_SOUTH_1)
                    .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(systemAccessKey, systemSecretKey)))
                    .build();

                log.info("✅ SQS Client created successfully!");
            } else {
                log.warn("❌ AWS credentials not provided via system properties. SQS will not work.");
            }

        } catch (Exception e) {
            log.error("❌ Failed to initialize SQS client: {}", e.getMessage(), e);
        }

        log.info("=========================================");
    }

    public CompletableFuture<String> sendExamCreatedMessage(Long examId, Long tenantId, Long userId) {
        if (sqsClient == null) {
            log.warn("SQS Client not initialized. Cannot send message.");
            return CompletableFuture.completedFuture("SQS_NOT_AVAILABLE");
        }

        try {
            String accountId = System.getProperty("aws.accountId", "805472282650");
            String queueUrl = String.format(
                "https://sqs.ap-south-1.amazonaws.com/%s/mahaexam-exam-dev-queue",
                accountId
            );

            String messageBody = String.format(
                "{\"messageType\":\"EXAM_CREATED\",\"examId\":%d,\"tenantId\":%d,\"userId\":%d,\"timestamp\":\"%s\"}",
                examId,
                tenantId != null ? tenantId : 0,
                userId != null ? userId : 0,
                java.time.LocalDateTime.now().toString()
            );

            SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

            log.info("Sending SQS message for exam ID: {} to queue: {}", examId, queueUrl);

            return sqsClient.sendMessage(request)
                .thenApply(response -> {
                    log.info("✅ SQS message sent successfully! MessageId: {}", response.messageId());
                    return response.messageId();
                })
                .exceptionally(throwable -> {
                    log.error("❌ Failed to send SQS message: {}", throwable.getMessage(), throwable);
                    return "ERROR";
                });

        } catch (Exception e) {
            log.error("❌ Error preparing SQS message: {}", e.getMessage(), e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public boolean isAvailable() {
        return sqsClient != null;
    }
}
