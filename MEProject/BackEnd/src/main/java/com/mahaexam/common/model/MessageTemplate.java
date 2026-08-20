package com.mahaexam.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTemplate {
    private int templateId;
    private String smsTemplateId;
    private String templateName;
    private String templateType; // "email" or "sms"
    private String subject;
    private String content;
    private String status; // "active" or "inactive"
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
