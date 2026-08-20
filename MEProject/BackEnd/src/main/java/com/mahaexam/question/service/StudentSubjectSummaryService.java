package com.mahaexam.question.service;

import com.mahaexam.common.bean.PackageExamResultDTO;
import com.mahaexam.common.bean.PackageSummaryResponse;
import com.mahaexam.common.bean.RecentResultResponse;
import com.mahaexam.common.bean.StudentDashboardDTO;
import com.mahaexam.common.bean.StudentExamSummaryDTO;
import com.mahaexam.common.bean.UpcomingExamResponse;
import com.mahaexam.model.StudentSubjectSummary;
import java.util.List;

public interface StudentSubjectSummaryService {
    int save(StudentSubjectSummary summary);
    StudentSubjectSummary findByIdAndTenantId(Long id, Long tenantId);
    List<StudentSubjectSummary> findAllByTenantId(Long tenantId);
    int deleteById(Long id);
    StudentSubjectSummary findByPaperStudentSubjectAndTenantId(Long questionPaperId, Long studentUserId, Integer subjectId, Long tenantId);

    /** Returns per-package exam summary for the student. */
    List<StudentExamSummaryDTO> findStudentExamSummary(Long studentUserId, Long tenantId);

    /**
     * Returns exam results grouped by package — each package contains the full
     * list of question papers with result details (or not-yet-attempted placeholders).
     */
    List<PackageExamResultDTO> findExamResultsByPackage(Long studentUserId, Long tenantId);

    /** Builds the complete student dashboard data in one call. */
    StudentDashboardDTO findStudentDashboard(Long studentUserId, Long tenantId);

    /** Tile 1 – package counts (total / active / expiring soon) */
    PackageSummaryResponse findDashboardPackages(Long studentUserId);

    /** Tile 2 – next untaken ACTIVE exam matching the student's class + medium */
    UpcomingExamResponse findDashboardUpcomingExam(Long studentUserId, Long tenantId);

    /** Tile 3 – last 5 taken exams with score % */
    List<RecentResultResponse> findDashboardRecentResults(Long studentUserId, Long tenantId);
}
