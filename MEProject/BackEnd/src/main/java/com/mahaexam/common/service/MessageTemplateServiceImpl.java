package com.mahaexam.common.service;

import com.mahaexam.common.model.MessageTemplate;
import com.mahaexam.common.repo.MessageTemplateRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageTemplateServiceImpl implements MessageTemplateService {

    private final MessageTemplateRepository repository;

    public MessageTemplateServiceImpl(MessageTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "messageTemplates", key = "#templateId")
    public MessageTemplate getTemplateById(int templateId) {
        return repository.findById(templateId);
    }

    @Override
    @Cacheable(value = "messageTemplates", key = "T(String).valueOf(#templateName).concat('-').concat(#templateType)")
    public MessageTemplate getTemplateByNameAndType(String templateName, String templateType) {
        return repository.getTemplateByNameAndType(templateName,templateType);
    }


    @Override
    @Cacheable(value = "allMessageTemplates")
    public List<MessageTemplate> getAllTemplates() {
        return repository.findAll();
    }

    @Override
    @CachePut(value = "messageTemplates", key = "#result.templateId")
    @CacheEvict(value = "allMessageTemplates", allEntries = true)
    public MessageTemplate createTemplate(MessageTemplate template) {
        repository.save(template);
        return template;  // Assume save sets the generated ID back to template object
    }

    @Override
    @CachePut(value = "messageTemplates", key = "#template.templateId")
    @CacheEvict(value = "allMessageTemplates", allEntries = true)
    public MessageTemplate updateTemplate(MessageTemplate template) {
        repository.update(template);
        return template;
    }

    @Override
    @CacheEvict(value = {"messageTemplates", "allMessageTemplates"}, key = "#templateId", allEntries = true)
    public void deleteTemplate(int templateId) {
        repository.delete(templateId);
    }


}
