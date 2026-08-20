package com.mahaexam.sqs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.sqs.model.SqsMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "spring.cloud.aws.sqs.listener.auto-startup", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class SqsMessageConsumer {

    private final ObjectMapper objectMapper;

    /**
     * Listen to SQS messages from DEV queue
     * This method will be triggered automatically when a message arrives in the queue
     */
    @SqsListener(
            value = "${app.aws.sqs.exam-queue}",
            acknowledgementMode = SqsListenerAcknowledgementMode.ON_SUCCESS
    )
    public void receiveDevMessage(String message) {
        processMessage(message, "DEV");
    }

    private void processMessage(String message, String environment) {
        try {
            log.info("🚀 [{}] Received SQS message: {}", environment, message);

            SqsMessage sqsMessage = objectMapper.readValue(message, SqsMessage.class);

            log.info("📋 [{}] Processing message - Type: {}, MessageId: {}, TenantId: {}, UserId: {}",
                    environment,
                    sqsMessage.getMessageType(),
                    sqsMessage.getMessageId(),
                    sqsMessage.getTenantId(),
                    sqsMessage.getUserId());

            // Route message based on type
            routeMessage(sqsMessage, environment);

            log.info("✅ [{}] Successfully processed message: {}", environment, sqsMessage.getMessageId());

        } catch (Exception e) {
            log.error("❌ [{}] Error processing SQS message: {}", environment, e.getMessage(), e);
            log.error("❌ [{}] Failed message content: {}", environment, message);
            // Don't throw exception to prevent message reprocessing - message will be deleted
        }
    }

    /**
     * Process the message based on its type
     */
    private void routeMessage(SqsMessage sqsMessage, String environment) {
        String messageType = sqsMessage.getMessageType();

        switch (messageType) {
            case "EXAM_CREATED":
                handleExamCreated(sqsMessage, environment);
                break;
            case "QUESTION_GENERATED":
                handleQuestionGenerated(sqsMessage, environment);
                break;
            case "PAPER_TEMPLATE_CREATED":
                handlePaperTemplateCreated(sqsMessage, environment);
                break;
            case "EMAIL_NOTIFICATION":
                handleEmailNotification(sqsMessage, environment);
                break;
            default:
                log.warn("[{}] Unknown message type: {}", environment, messageType);
        }
    }

    private void handleExamCreated(SqsMessage message, String environment) {
        log.info("[{}] Handling EXAM_CREATED event: {}", environment, message.getPayload());

        //TODO
        // Add your exam creation logic here

        //Need to get the examId from the payload and then fetch the exam details from the database and then perform the necessary operations like generating question paper.
    }

    private void handleQuestionGenerated(SqsMessage message, String environment) {
        log.info("[{}] Handling QUESTION_GENERATED event: {}", environment, message.getPayload());
        // Add your question generation logic here
    }

    private void handlePaperTemplateCreated(SqsMessage message, String environment) {
        log.info("[{}] Handling PAPER_TEMPLATE_CREATED event: {}", environment, message.getPayload());
        // Add your paper template creation logic here
    }

    private void handleEmailNotification(SqsMessage message, String environment) {
        log.info("[{}] Handling EMAIL_NOTIFICATION event: {}", environment, message.getPayload());
        // Add your email notification logic here
    }
}
