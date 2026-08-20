package com.mahaexam.papertemplate.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.papertemplate.model.QuestionPaperTemplate;
import com.mahaexam.papertemplate.repository.QuestionPaperTemplateRepository;
import com.mahaexam.papertemplate.service.QuestionPaperTemplateService;

@Service
@Transactional
public class QuestionPaperTemplateServiceImpl
        implements QuestionPaperTemplateService {

    private final QuestionPaperTemplateRepository repository;

    public QuestionPaperTemplateServiceImpl(
            QuestionPaperTemplateRepository repository) {
        this.repository = repository;
    }

    /* -------------------------------------------------
     * ADD PAPER TEMPLATE TO QUESTION PAPER
     * ------------------------------------------------- */
    @Override
    public void addPaperTemplate(
            Long questionPaperId,
            Long paperTemplateId,
            Integer sequence,
            Long tenantId) {

        repository.save(questionPaperId, paperTemplateId, sequence, tenantId);
    }

    /* -------------------------------------------------
     * DELETE ALL BY QUESTION PAPER
     * ------------------------------------------------- */
    @Override
    public void removeAllByQuestionPaperId(Long questionPaperId) {
        repository.deleteByQuestionPaperId(questionPaperId);
    }

    /* -------------------------------------------------
     * GET ALL BY TENANT ID
     * ------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionPaperTemplate> getAllByTenantId(Long tenantId) {
        return repository.findAllByTenantId(tenantId);
    }

    /* -------------------------------------------------
     * GET BY QUESTION PAPER ID
     * ------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionPaperTemplate> getByQuestionPaperId(Long questionPaperId) {
        return repository.findByQuestionPaperId(questionPaperId);
    }
}
