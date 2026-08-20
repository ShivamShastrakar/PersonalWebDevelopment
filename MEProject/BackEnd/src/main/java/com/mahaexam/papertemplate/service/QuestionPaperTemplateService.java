package com.mahaexam.papertemplate.service;

import java.util.List;

import com.mahaexam.papertemplate.model.QuestionPaperTemplate;

public interface QuestionPaperTemplateService {

    void addPaperTemplate(
            Long questionPaperId,
            Long paperTemplateId,
            Integer sequence,
            Long tenantId
    );

    void removeAllByQuestionPaperId(Long questionPaperId);

    List<QuestionPaperTemplate> getAllByTenantId(Long tenantId);

    List<QuestionPaperTemplate> getByQuestionPaperId(Long questionPaperId);
}
