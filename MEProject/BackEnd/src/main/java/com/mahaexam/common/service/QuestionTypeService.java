package com.mahaexam.common.service;

import com.mahaexam.common.model.QuestionType;
import java.util.List;
import java.util.Optional;

public interface QuestionTypeService {
    List<QuestionType> getAll(Long tenantId);
    Optional<QuestionType> getById(int id);
    Optional<QuestionType> getByCode(String code, Long tenantId);
    List<QuestionType> getByBoardAndSubject(int boardId, int subjectId, Long tenantId);
    int create(QuestionType questionType);
    int update(QuestionType questionType);
    int delete(int id, Long tenantId);
}
