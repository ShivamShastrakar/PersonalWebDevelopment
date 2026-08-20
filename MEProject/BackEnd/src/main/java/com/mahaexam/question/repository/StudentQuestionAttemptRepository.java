package com.mahaexam.question.repository;

import com.mahaexam.model.StudentQuestionAttempt;
import java.util.List;

public interface StudentQuestionAttemptRepository {
    List<StudentQuestionAttempt> findAllByTenantId(Long tenantId);
    StudentQuestionAttempt findById(Long id);
    int save(StudentQuestionAttempt attempt);
    int deleteById(Long id);
    int[] batchSave(List<StudentQuestionAttempt> attempts);
    List<StudentQuestionAttempt> findByQuestionPaperIdAndStudentUserIdAndTenantId(Long questionPaperId, Long studentUserId, Long tenantId);
    List<StudentQuestionAttempt> findBySummaryIdAndTenantId(Long summaryId, Long tenantId);
}
