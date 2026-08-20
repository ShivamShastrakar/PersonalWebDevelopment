package com.mahaexam.common.repo;

import com.mahaexam.common.model.BoardSubjectQuestionTypeMapping;
import java.util.List;
import java.util.Optional;

public interface BoardSubjectQuestionTypeMappingRepository {
    List<BoardSubjectQuestionTypeMapping> findAll(Long tenantId);
    Optional<BoardSubjectQuestionTypeMapping> findById(int id);
    List<BoardSubjectQuestionTypeMapping> findByBoardId(int boardId, Long tenantId);
    List<BoardSubjectQuestionTypeMapping> findByBoardAndSubject(int boardId, int subjectId, Long tenantId);
    List<BoardSubjectQuestionTypeMapping> findQuestionTypesByBoardAndSubject(int boardId, int subjectId, Long tenantId);
    int save(BoardSubjectQuestionTypeMapping mapping);
    int softDelete(int id);
    boolean existsByBoardSubjectAndQuestionType(int boardId, int subjectId, int questionTypeId, Long tenantId);
}
