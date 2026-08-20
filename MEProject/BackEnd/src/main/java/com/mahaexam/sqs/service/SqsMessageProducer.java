package com.mahaexam.sqs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.sqs.model.SqsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

// @Service - Disabled since Spring Cloud AWS SQS auto-configuration is excluded
// @ConditionalOnProperty(name = "spring.cloud.aws.sqs.enabled", havingValue = "true")
@Slf4j
public class SqsMessageProducer {

    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;

    public SqsMessageProducer(SqsAsyncClient sqsAsyncClient, ObjectMapper objectMapper) {
        this.sqsAsyncClient = sqsAsyncClient;
        this.objectMapper = objectMapper;
        log.info("SqsMessageProducer created with SqsAsyncClient: {}", sqsAsyncClient.getClass().getSimpleName());
    }

    /**
     * Send a message to SQS queue
     *
     * @param queueUrl The SQS queue URL
     * @param messageType Type of message (e.g., "EXAM_CREATED", "QUESTION_GENERATED")
     * @param payload The actual message payload
     * @param tenantId Tenant ID
     * @param userId User ID
     * @return CompletableFuture with message ID
     */
    public CompletableFuture<String> sendMessage(String queueUrl, String messageType, Object payload, Long tenantId, Long userId) {
        try {
            SqsMessage sqsMessage = SqsMessage.builder()
                    .messageId(UUID.randomUUID().toString())
                    .messageType(messageType)
                    .payload(payload)
                    .timestamp(LocalDateTime.now())
                    .tenantId(tenantId)
                    .userId(userId)
                    .correlationId(UUID.randomUUID().toString())
                    .build();

            String messageBody = objectMapper.writeValueAsString(sqsMessage);

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();

            return sqsAsyncClient.sendMessage(sendMessageRequest)
                    .thenApply(SendMessageResponse::messageId)
                    .whenComplete((messageId, exception) -> {
                        if (exception != null) {
                            log.error("Failed to send message to SQS: {}", exception.getMessage(), exception);
                        } else {
                            log.info("Message sent successfully to SQS. MessageId: {}, Type: {}", messageId, messageType);
                        }
                    });

        } catch (Exception e) {
            log.error("Error preparing SQS message: {}", e.getMessage(), e);
            return CompletableFuture.failedFuture(e);
        }
    }

    /**
     * Send a simple message without tenant/user context
     */
    public CompletableFuture<String> sendMessage(String queueUrl, String messageType, Object payload) {
        return sendMessage(queueUrl, messageType, payload, null, null);
    }
}
