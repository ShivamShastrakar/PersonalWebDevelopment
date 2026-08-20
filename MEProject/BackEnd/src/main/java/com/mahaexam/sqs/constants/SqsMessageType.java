package com.mahaexam.sqs.constants;

public final class SqsMessageType {

    private SqsMessageType() {
        // Private constructor to prevent instantiation
    }

    // Exam related events
    public static final String EXAM_CREATED = "EXAM_CREATED";
    public static final String EXAM_UPDATED = "EXAM_UPDATED";
    public static final String EXAM_DELETED = "EXAM_DELETED";

    // Question related events
    public static final String QUESTION_GENERATED = "QUESTION_GENERATED";
    public static final String QUESTION_UPDATED = "QUESTION_UPDATED";
    public static final String QUESTION_DELETED = "QUESTION_DELETED";

    // Paper template related events
    public static final String PAPER_TEMPLATE_CREATED = "PAPER_TEMPLATE_CREATED";
    public static final String PAPER_TEMPLATE_UPDATED = "PAPER_TEMPLATE_UPDATED";
    public static final String PAPER_TEMPLATE_DELETED = "PAPER_TEMPLATE_DELETED";

    // Notification events
    public static final String EMAIL_NOTIFICATION = "EMAIL_NOTIFICATION";
    public static final String SMS_NOTIFICATION = "SMS_NOTIFICATION";

    // Report generation events
    public static final String REPORT_GENERATION_REQUESTED = "REPORT_GENERATION_REQUESTED";
    public static final String REPORT_GENERATION_COMPLETED = "REPORT_GENERATION_COMPLETED";

    // Batch processing events
    public static final String BULK_UPLOAD_STARTED = "BULK_UPLOAD_STARTED";
    public static final String BULK_UPLOAD_COMPLETED = "BULK_UPLOAD_COMPLETED";
}
