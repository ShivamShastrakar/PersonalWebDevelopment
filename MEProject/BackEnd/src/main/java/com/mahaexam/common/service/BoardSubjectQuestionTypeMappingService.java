package com.mahaexam.common.service;

import com.mahaexam.common.model.BoardSubjectQuestionTypeMapping;
import java.util.List;
import java.util.Optional;

public interface BoardSubjectQuestionTypeMappingService {
    List<BoardSubjectQuestionTypeMapping> getAll(Long tenantId);
    Optional<BoardSubjectQuestionTypeMapping> getById(int id);
    List<BoardSubjectQuestionTypeMapping> getByBoardId(int boardId, Long tenantId);
    List<BoardSubjectQuestionTypeMapping> getByBoardAndSubject(int boardId, int subjectId, Long tenantId);
    int create(BoardSubjectQuestionTypeMapping mapping);
    int delete(int id);
}
