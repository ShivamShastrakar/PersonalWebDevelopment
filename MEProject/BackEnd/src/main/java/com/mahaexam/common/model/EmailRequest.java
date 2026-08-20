package com.mahaexam.common.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {

    private Long id;
    private String toAddresses;
    private String ccAddresses;
    private String bccAddresses;
    private String subject;
    private String body;
    private boolean isHtml;
    private byte[] attachmentData;
    private String attachmentName;
    private EmailStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String errorMessage;
    
    private List<Attachment> attachments;

    public enum EmailStatus {
        PENDING, SENT, FAILED
    }

}
