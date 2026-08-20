package com.mahaexam.common.service;

import com.mahaexam.common.model.MessageTemplate;

import java.util.List;

public interface MessageTemplateService {
    MessageTemplate getTemplateById(int templateId);
    MessageTemplate getTemplateByNameAndType(String templateName, String templateType);
    List<MessageTemplate> getAllTemplates();
    MessageTemplate createTemplate(MessageTemplate template);
    MessageTemplate updateTemplate(MessageTemplate template);
    void deleteTemplate(int templateId);
}
