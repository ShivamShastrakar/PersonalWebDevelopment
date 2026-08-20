package com.mahaexam.common.repo;

import com.mahaexam.common.model.MessageTemplate;

import java.util.List;

public interface MessageTemplateRepository {
    int save(MessageTemplate template);
    MessageTemplate getTemplateByNameAndType(String templateName, String templateType);
    int update(MessageTemplate template);
    int delete(int templateId);
    MessageTemplate findById(int templateId);
    List<MessageTemplate> findAll();
}
