package com.mahaexam.question.service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.model.StudentQuestionAttempt;
import com.mahaexam.model.StudentSubjectSummary;
import java.util.List;

public interface StudentQuestionAttemptService {
    StudentSubjectSummary save(List<StudentQuestionAttempt> attempts, Integer timeTaken, UserBean user);
    int save(StudentQuestionAttempt attempt, UserBean user);
    StudentQuestionAttempt findById(Long id);
    List<StudentQuestionAttempt> findAllByTenantId(Long tenantId);
    int deleteById(Long id);
    List<StudentQuestionAttempt> findByQuestionPaperIdAndStudentUserIdAndTenantId(Long questionPaperId, Long studentUserId, Long tenantId);
    List<StudentQuestionAttempt> findBySummaryIdAndTenantId(Long summaryId, Long tenantId);
}
