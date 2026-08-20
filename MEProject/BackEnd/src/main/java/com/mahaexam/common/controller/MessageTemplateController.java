package com.mahaexam.common.controller;

import com.mahaexam.common.model.MessageTemplate;
import com.mahaexam.common.service.MessageTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message-templates")
public class MessageTemplateController {

    private final MessageTemplateService messageTemplateService;

    public MessageTemplateController(MessageTemplateService messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageTemplate> getTemplateById(@PathVariable("id") int id) {
        MessageTemplate template = messageTemplateService.getTemplateById(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(template);
    }

    @GetMapping
    public ResponseEntity<List<MessageTemplate>> getAllTemplates() {
        List<MessageTemplate> templates = messageTemplateService.getAllTemplates();
        return ResponseEntity.ok(templates);
    }

    @PostMapping
    public ResponseEntity<MessageTemplate> createTemplate(@RequestBody MessageTemplate template) {
        MessageTemplate created = messageTemplateService.createTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageTemplate> updateTemplate(@PathVariable("id") int id, @RequestBody MessageTemplate template) {
        MessageTemplate existing = messageTemplateService.getTemplateById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        template.setTemplateId(id);
        MessageTemplate updated = messageTemplateService.updateTemplate(template);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable("id") int id) {
        MessageTemplate existing = messageTemplateService.getTemplateById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        messageTemplateService.deleteTemplate(id);
        return ResponseEntity.ok().build();
    }
}
