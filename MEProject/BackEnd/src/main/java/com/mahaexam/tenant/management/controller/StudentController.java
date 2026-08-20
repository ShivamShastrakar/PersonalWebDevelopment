package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.tenant.management.bean.*;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.Student;
import com.mahaexam.tenant.management.service.ApplicationUserProfileService;
import com.mahaexam.tenant.management.service.ApplicationUserService;
import com.mahaexam.tenant.management.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    @Autowired
    private final ApplicationUserProfileService profileService;
    private final ApplicationUserService applicationUserService;

    public StudentController(StudentService studentService, ApplicationUserProfileService profileService,
                             ApplicationUserService applicationUserService) {
        this.studentService = studentService;
        this.profileService = profileService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<StudentDetailsBean> findById(@PathVariable Long userId) {
        if (userId == null) {
            logger.warn("Invalid studentId: null");
            throw new ValidationException("Student not found with ID: " + userId);
        }

        try {
            StudentDetailsBean studentDetails = studentService.findByIdFull(userId);
            if (Objects.nonNull(studentDetails)) {
                logger.info("Found student with ID: {}", userId);
                return ResponseEntity.ok(studentDetails);
            } else {
                logger.warn("Student not found with ID: {}", userId);
                throw new ValidationException("Student not found with ID: " + userId);
            }
        } catch (Exception e) {
            logger.error("Error fetching student with ID: {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{studentUserId}/image")
    public ResponseEntity<StudentDetailsBean> getStudentImageById(@PathVariable Long studentUserId) {
        if (studentUserId == null) {
            logger.warn("Invalid studentId for image: null");
            throw new ValidationException("Student not found with ID: " + studentUserId);
        }

        try {
            Optional<StudentDetailsBean> studentDetails = Optional
                    .ofNullable(studentService.getStudentImageById(studentUserId));
            if (studentDetails.isPresent()) {
                logger.info("Found student image for ID: {}", studentUserId);
                return ResponseEntity.ok(studentDetails.get());
            } else {
                logger.warn("Student image not found for ID: {}", studentUserId);
                throw new ValidationException("Student not found with ID: " + studentUserId);
            }
        } catch (Exception e) {
            logger.error("Error fetching student image for ID: {}: {}", studentUserId, e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse<StudentDetailsBean>> search(
            @Valid @RequestBody StudentSerchBean studentSearchBean) {
        if (studentSearchBean == null) {
            logger.warn("Invalid search request: studentSearchBean is null");
            throw new ValidationException("Search criteria cannot be null");
        }
        UserBean user = getUser();
        try {
            PaginatedResponse<StudentDetailsBean> paginatedResponse = studentService.search(user, studentSearchBean);
            return ResponseEntity.ok(paginatedResponse);
        } catch (Exception e) {
            logger.error("Error searching students: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDetailsBean> update(@PathVariable Long studentId,
                                                     @Valid @RequestBody StudentDetailsBean studentDetailsBean) {
        if (studentId == null || studentDetailsBean == null) {
            logger.warn("Invalid studentId: {} or studentDetailsBean: null", studentId);
            throw new ValidationException("studentId cannot be null");
        }
        if (!studentId.equals(studentDetailsBean.getStudentId())) {
            String message = String.format("Mismatched studentId: path={} vs body={}", studentId,
                    studentDetailsBean.getStudentId());
            logger.warn(message);
            throw new ValidationException(message);
        }

        try {
            StudentDetailsBean updatedStudent = studentService.update(studentDetailsBean);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException e) {
            String message = String.format("Failed to update student with ID: {}: {}", studentId, e.getMessage());
            logger.warn(message,e);
            throw new ValidationException(message);
        } catch (Exception e) {
            String message = String.format("Error updating student with ID: {}: {}", studentId, e.getMessage(), e);
            logger.error(message);
            throw new ValidationException(message);
        }
    }

    @GetMapping("/deletedStudent")
    public ResponseEntity<PaginatedResponse<ApplicationUser>> findDeletedStudent(@RequestParam(defaultValue = "0") int page,  // Optional: defaults to first page (1-indexed)
                                                                                 @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(profileService.findByUserType(user.getTenantId(), AppConstants.USER_TYPE_STUDENT, true, pageable));
    }

    @GetMapping("/without-form-data")
    public ResponseEntity<PaginatedResponse<Student>> withoutFormData(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(studentService.getStudentsWithoutPackage(pageable));
    }

    @PostMapping("/addStudent")
    public ResponseEntity<SuccessResponseBean> addStudent(@Valid @RequestBody StudentRegistrationBean registrationDTO) {
        try {
            UserBean userbean = getUser();
            registrationDTO.setStudentReferenceId(userbean.getUserId());
            studentService.registerStudent(registrationDTO,false,true, true);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                                    "Add student successfully with user ID: " + registrationDTO.getUserId())
                            .build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }


    @GetMapping("/{userId}/package-count")
    public ResponseEntity<CountBean> getStudentPackageCount(@PathVariable Long userId) {
        Integer count = studentService.getStudentPackageCount(userId);
        return ResponseEntity.ok(CountBean.builder().count(count).build());
    }

    @GetMapping("/{userId}/packages")
    public ResponseEntity<List<PackageModel>> getStudentPackages(@PathVariable Long userId) {
        List<PackageModel> packages = studentService.getStudentPackages(userId);
        return ResponseEntity.ok(packages);
    }
        
        @PostMapping("/addNewStudent")
        public ResponseEntity<SuccessResponseBean> adNewdStudent(@Valid @RequestBody StudentDetailsBean studentDetailsBean) {
            try {
                UserBean userbean = getUser();
                studentDetailsBean.setStudentReferenceId(userbean.getUserId());
                studentService.addNewStudent(studentDetailsBean);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(SuccessResponseBean.builder().status("success").message(
                                        "Add student successfully with user ID: " + studentDetailsBean.getUserId())
                                .build());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }

    }

    @GetMapping("/active-with-packages")
    public ResponseEntity<PaginatedResponse<StudentDetailsBean>> getActiveStudentsWithPaidPackage(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getActiveStudentsWithPaidPackage(user.getTenantId(), academicYearId, boardId, days, pageable));
    }

    @GetMapping("/active-with-packages/count")
    public ResponseEntity<Integer> getActiveStudentsWithPaidPackageCount(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days) {
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getActiveStudentsWithPaidPackageCount(user.getTenantId(), academicYearId, boardId, days));
    }

    @GetMapping("/all/count")
    public ResponseEntity<Integer> getAllStudentsCount(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days) {
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getAllStudentsCount(user.getTenantId(), academicYearId, boardId, days));
    }

    @GetMapping("/registered-students")
    public ResponseEntity<PaginatedResponse<StudentDetailsBean>> registeredStudents(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        UserBean userbean = getUser();
        StudentSerchBean studentSearchBean = new StudentSerchBean();
        studentSearchBean.setPage(page);
        studentSearchBean.setSize(size);
        studentSearchBean.setAcademicYearId(academicYearId);
        studentSearchBean.setDays(days);
        PaginatedResponse<StudentDetailsBean> students = studentService.search(userbean, studentSearchBean);

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/total-exams")
    public ResponseEntity<PaginatedResponse<QuestionPaperResponseDTO>> getTotalExamDetails(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getTotalExamDetails(user.getTenantId(), academicYearId, boardId, days, pageable));
    }

    @GetMapping("/total-exams/count")
    public ResponseEntity<Integer> getTotalExamCount(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days) {
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getTotalExamCount(user.getTenantId(), academicYearId, boardId, days));
    }

    @GetMapping("/upcoming-exams/count")
    public ResponseEntity<Integer> getUpcomingExamCount(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days) {
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getUpcomingExamCount(user.getTenantId(), academicYearId, boardId, days));
    }

    @GetMapping("/upcoming-exams")
    public ResponseEntity<PaginatedResponse<QuestionPaperResponseDTO>> getUpcomingExamDetails(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getUpcomingExamDetails(user.getTenantId(), academicYearId, boardId, days, pageable));
    }

    @GetMapping("/completed-exams")
    public ResponseEntity<PaginatedResponse<CompletedExamDetailsBean>> getCompletedExamDetails(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getCompletedExamDetails(user.getTenantId(), academicYearId, boardId, days, pageable));
    }

    @GetMapping("/completed-exams/count")
    public ResponseEntity<Integer> getCompletedExamCount(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days) {
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getCompletedExamCount(user.getTenantId(), academicYearId, boardId, days));
    }

    @GetMapping("/enrollment-by-exam")
    public ResponseEntity<PaginatedResponse<StudentDetailsBean>> getStudentEnrollmentByExamDetails(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days,
            @RequestParam(required = false) String examName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getStudentEnrollmentByExamDetails(user.getTenantId(), academicYearId, boardId, days, examName, pageable));
    }

    @GetMapping("/enrollment-by-exam/count")
    public ResponseEntity<List<EnrollmentByExamBean>> getStudentEnrollmentByExam(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days) {
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getStudentEnrollmentByExam(user.getTenantId(), academicYearId, boardId, days));
    }

    @GetMapping("/journey")
    public ResponseEntity<List<StudentJourneyBean>> getStudentJourneyStats(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days) {
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getStudentJourneyStats(user.getTenantId(), academicYearId, boardId, days));
    }

    @GetMapping("/journey/details")
    public ResponseEntity<PaginatedResponse<CompletedExamDetailsBean>> getStudentJourneyDetails(
            @RequestParam(required = false) Integer academicYearId,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(required = false) Integer days,
            @RequestParam(required = false) String packageType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(studentService.getStudentJourneyDetails(user.getTenantId(), academicYearId, boardId, days, packageType, pageable));
    }
}