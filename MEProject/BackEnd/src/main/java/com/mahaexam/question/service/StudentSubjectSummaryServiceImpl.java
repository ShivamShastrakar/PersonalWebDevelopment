package com.mahaexam.question.service;

import com.mahaexam.common.bean.PackageExamResultDTO;
import com.mahaexam.common.bean.PackageSummaryResponse;
import com.mahaexam.common.bean.RecentResultResponse;
import com.mahaexam.common.bean.StudentDashboardDTO;
import com.mahaexam.common.bean.StudentExamSummaryDTO;
import com.mahaexam.common.bean.UpcomingExamResponse;
import com.mahaexam.model.StudentSubjectSummary;
import com.mahaexam.question.repository.StudentSubjectSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSubjectSummaryServiceImpl implements StudentSubjectSummaryService {
    @Autowired
    private StudentSubjectSummaryRepository repository;

    public int save(StudentSubjectSummary summary) {
        return repository.save(summary);
    }

    public int deleteById(Long id) {
        return repository.deleteById(id);
    }

    public StudentSubjectSummary findByIdAndTenantId(Long id, Long tenantId) {
        return repository.findByIdAndTenantId(id, tenantId);
    }

    public List<StudentSubjectSummary> findAllByTenantId(Long tenantId) {
        return repository.findAllByTenantId(tenantId);
    }

    public StudentSubjectSummary findByPaperStudentSubjectAndTenantId(Long questionPaperId, Long studentUserId, Integer subjectId, Long tenantId) {
        return repository.findByPaperStudentSubjectAndTenantId(questionPaperId, studentUserId, subjectId, tenantId);
    }

    @Override
    public List<StudentExamSummaryDTO> findStudentExamSummary(Long studentUserId, Long tenantId) {
        return repository.findStudentExamSummary(studentUserId, tenantId);
    }

    @Override
    public List<PackageExamResultDTO> findExamResultsByPackage(Long studentUserId, Long tenantId) {
        return repository.findExamResultsByPackage(studentUserId, tenantId);
    }

    @Override
    public StudentDashboardDTO findStudentDashboard(Long studentUserId, Long tenantId) {
        return repository.findStudentDashboard(studentUserId, tenantId);
    }

    @Override
    public PackageSummaryResponse findDashboardPackages(Long studentUserId) {
        return repository.findDashboardPackages(studentUserId);
    }

    @Override
    public UpcomingExamResponse findDashboardUpcomingExam(Long studentUserId, Long tenantId) {
        return repository.findDashboardUpcomingExam(studentUserId, tenantId);
    }

    @Override
    public List<RecentResultResponse> findDashboardRecentResults(Long studentUserId, Long tenantId) {
        return repository.findDashboardRecentResults(studentUserId, tenantId);
    }
}
