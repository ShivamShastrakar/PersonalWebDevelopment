package com.mahaexam.common.repo;

import com.mahaexam.common.model.QuestionType;
import java.util.List;
import java.util.Optional;

public interface QuestionTypeRepository {
    List<QuestionType> findAll(Long tenantId);
    Optional<QuestionType> findById(int id);
    Optional<QuestionType> findByCode(String code, Long tenantId);
    List<QuestionType> findByBoardAndSubject(int boardId, int subjectId, Long tenantId);
    int save(QuestionType questionType);
    int update(QuestionType questionType);
    int deleteById(int id, Long tenantId);
    boolean existsByCode(String code, Long tenantId);
    boolean existsByCodeExceptId(String code, int excludeId, Long tenantId);
}
