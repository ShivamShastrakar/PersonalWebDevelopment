package com.mahaexam.common.repo;

import java.util.List;

import com.mahaexam.common.model.Attachment;
import com.mahaexam.common.model.EmailRequest;

public interface EmailRequestRepository {
    void save(EmailRequest emailRequest);
    List<EmailRequest> findByStatus(EmailRequest.EmailStatus status);
    void saveAttachment(Attachment attachment);
    List<Attachment> findAttachmentsByEmailRequestId(Long emailRequestId);
}