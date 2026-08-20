package com.mahaexam.question.repository;

import com.mahaexam.common.bean.PackageExamResultDTO;
import com.mahaexam.common.bean.PackageSummaryResponse;
import com.mahaexam.common.bean.RecentResultResponse;
import com.mahaexam.common.bean.StudentDashboardDTO;
import com.mahaexam.common.bean.StudentExamSummaryDTO;
import com.mahaexam.common.bean.UpcomingExamResponse;
import com.mahaexam.model.StudentSubjectSummary;
import java.util.List;

public interface StudentSubjectSummaryRepository {
    List<StudentSubjectSummary> findAllByTenantId(Long tenantId);
    StudentSubjectSummary findByIdAndTenantId(Long id, Long tenantId);
    int save(StudentSubjectSummary summary);
    int deleteById(Long id);
    StudentSubjectSummary findByPaperStudentSubjectAndTenantId(Long questionPaperId, Long studentUserId, Integer subjectId, Long tenantId);

    /**
     * Returns per-paper exam summary for a student, filtered by their class and medium.
     */
    List<StudentExamSummaryDTO> findStudentExamSummary(Long studentUserId, Long tenantId);

    /**
     * Returns exam results grouped by package for a student.
     * Each package contains the full list of question papers with result details
     * (marks, correct/wrong, score %, attemptedAt). Papers not yet attempted have
     * isTaken=false and null result fields.
     */
    List<PackageExamResultDTO> findExamResultsByPackage(Long studentUserId, Long tenantId);

    /** Builds the complete student dashboard data in one call. */
    StudentDashboardDTO findStudentDashboard(Long studentUserId, Long tenantId);

    /** Tile 1 - package counts (total / active / expiring soon within 30 days) */
    PackageSummaryResponse findDashboardPackages(Long studentUserId);

    /** Tile 2 - next untaken ACTIVE exam matching the student's class + medium */
    UpcomingExamResponse findDashboardUpcomingExam(Long studentUserId, Long tenantId);

    /** Tile 3 - last 5 taken exams with score % */
    List<RecentResultResponse> findDashboardRecentResults(Long studentUserId, Long tenantId);
}
