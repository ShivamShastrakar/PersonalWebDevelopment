package com.mahaexam.tenant.management.repository;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.tenant.management.bean.*;
import com.mahaexam.tenant.management.model.Student;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);

    Optional<Student> findById(Long studentId);

    List<Student> findAll();

    Student update(Student student);

    void delete(Long studentId);

    Optional<Student> findByUserId(Long userId);

    List<Student> getAllStudentsByRefferalUsreId(Long studentReferralId);

    Integer getStudentCountRefferedByGivenUserId(Long studentRefferalId);

    Integer getAllStudentsCount(UserBean userbean);

    Integer getAllStudentsCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days);

    PaginatedResponse<StudentDetailsBean> search(UserBean user, StudentSerchBean studentSerchBean);

    Optional<StudentDetailsBean> findByIdFull(Long userId);

    boolean hasPackage(Long userId);

    PaginatedResponse<Student> getStudentsWithoutPackage(Pageable pageable);

    Integer getStudentPackageCount(Long userId);

    List<PackageModel> getStudentPackages(Long userId);

    List<StudentDetailsBean> getAllStudentsByChannelPartnerId(Long channelPartnerId);
    
    PaginatedResponse<StudentDetailsBean> getActiveStudentsWithPaidPackage(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable);

    Integer getActiveStudentsWithPaidPackageCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days);

    PaginatedResponse<QuestionPaperResponseDTO> getTotalExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable);
    Integer getTotalExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days);
    Integer getUpcomingExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days);
    PaginatedResponse<QuestionPaperResponseDTO> getUpcomingExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable);
    PaginatedResponse<CompletedExamDetailsBean> getCompletedExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable);
    Integer getCompletedExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days);

    List<EnrollmentByExamBean> getStudentEnrollmentByExam(Long tenantId, Integer academicYearId, Integer boardId, Integer days);

    PaginatedResponse<StudentDetailsBean> getStudentEnrollmentByExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, String examName, Pageable pageable);

    List<StudentJourneyBean> getStudentJourneyStats(Long tenantId, Integer academicYearId, Integer boardId, Integer days);
    PaginatedResponse<CompletedExamDetailsBean> getStudentJourneyDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, String packageType, Pageable pageable);
}
