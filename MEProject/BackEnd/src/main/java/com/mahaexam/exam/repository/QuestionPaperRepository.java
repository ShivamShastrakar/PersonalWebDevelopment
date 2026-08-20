package com.mahaexam.exam.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.papertemplate.model.QuestionPaper;

public interface QuestionPaperRepository {
    QuestionPaper save(QuestionPaper questionPaper);
    Optional<QuestionPaper> findById(Long id);
    List<QuestionPaper> findAll();
    List<QuestionPaper> findByClassId(Integer classId);
    List<QuestionPaper> findAllByTenantId(Long tenantId);
    List<QuestionPaper> findAllByTenantIdAndFilter(Long tenantId, Long boardId, Integer classId);
    void updateStatus(Long id, Boolean active);
    boolean existsByName(String questionPaperName);
    boolean existsByNameAndTenantId(String questionPaperName, Long tenantId);

    /**
     * Returns all ACTIVE question papers accessible to a student through their
     * active packages, filtered by the student's own class and medium.
     */
    List<QuestionPaper> findExamsByStudentPackageAndMedium(Long studentUserId, Long tenantId);
}
