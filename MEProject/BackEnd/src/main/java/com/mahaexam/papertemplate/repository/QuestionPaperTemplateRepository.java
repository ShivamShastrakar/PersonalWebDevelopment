package com.mahaexam.papertemplate.repository;
import java.util.List;

import com.mahaexam.papertemplate.model.QuestionPaperTemplate;

public interface QuestionPaperTemplateRepository {

    void save(Long questionPaperId, Long paperTemplateId, Integer sequence, Long tenantId);

    void saveBatch(Long questionPaperId, List<Long> paperTemplateIds, Long tenantId);

    void deleteByQuestionPaperId(Long questionPaperId);

    List<QuestionPaperTemplate> findAllByTenantId(Long tenantId);

    List<QuestionPaperTemplate> findByQuestionPaperId(Long questionPaperId);
}
