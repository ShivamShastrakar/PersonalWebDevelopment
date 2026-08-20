package com.mahaexam.exam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.common.bean.PackageExamResultDTO;
import com.mahaexam.common.bean.PackageSummaryResponse;
import com.mahaexam.common.bean.RecentResultResponse;
import com.mahaexam.common.bean.StudentDashboardDTO;
import com.mahaexam.common.bean.UpcomingExamResponse;
import com.mahaexam.exam.service.ExamTokenService;
import com.mahaexam.common.bean.StudentExamSummaryDTO;
import com.mahaexam.common.bean.StudentQuestionAttemptRequest;
import com.mahaexam.common.bean.StudentQuestionRequest;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.bean.QuestionPaperHierarchyResponseDTO;
import com.mahaexam.common.bean.PartQuestionsDTO;
import com.mahaexam.common.bean.SectionQuestionsDTO;
import com.mahaexam.model.StudentQuestionAttempt;
import com.mahaexam.model.StudentSubjectSummary;
import com.mahaexam.question.model.QuestionEntity;
import com.mahaexam.question.service.StudentQuestionAttemptService;
import com.mahaexam.question.service.StudentSubjectSummaryService;
import com.mahaexam.exam.service.QuestionPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for capturing student exam / question-paper submissions.
 *
 * Base path: /api/exam-submissions
 *
 * Endpoints:
 *   POST   /api/exam-submissions/submit-exam   – Submit all answers for a question paper in one call
 *   POST   /api/exam-submissions               – Submit a single question attempt
 *   GET    /api/exam-submissions/{id}          – Fetch one attempt by ID
 *   GET    /api/exam-submissions              – List all attempts (admin / debug)
 *   DELETE /api/exam-submissions/{id}          – Delete an attempt by ID
 */
@RestController
@RequestMapping("/api/exam-submissions")
public class StudentExamSubmissionController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(StudentExamSubmissionController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final StudentQuestionAttemptService studentQuestionAttemptService;
    private final QuestionPaperService questionPaperService;
    private final StudentSubjectSummaryService studentSubjectSummaryService;
    private final ExamTokenService examTokenService;

    public StudentExamSubmissionController(StudentQuestionAttemptService studentQuestionAttemptService,
                                           QuestionPaperService questionPaperService,
                                           StudentSubjectSummaryService studentSubjectSummaryService,
                                           ExamTokenService examTokenService) {
        this.studentQuestionAttemptService = studentQuestionAttemptService;
        this.questionPaperService = questionPaperService;
        this.studentSubjectSummaryService = studentSubjectSummaryService;
        this.examTokenService = examTokenService;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/exam-submissions/submit-exam
    //
    // Submit all the student's answers for a question paper in a single request.
    // The service auto-grades each attempt (isCorrect flag) and builds the
    // per-subject summary in one transaction.
    //
    // Request body example:
    // {
    //   "questionPaperId": 151,
    //   "studentUserId": 42,
    //   "attempts": [
    //     { "questionId": 229, "subjectId": 36, "answerGiven": {"correctOption": 2} },
    //     { "questionId": 230, "subjectId": 36, "answerGiven": {"correctOption": 3} }
    //   ]
    // }
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/submit-exam")
    public ResponseEntity<?> submitExam(@RequestBody StudentQuestionRequest request) {
        UserBean user = getUser();

        if (request == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Request body must not be null"));
        }
        if (request.getAttempts() == null || request.getAttempts().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Attempts list must not be empty"));
        }
        if (request.getQuestionPaperId() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "questionPaperId is required"));
        }

        // Resolve studentUserId: payload wins, then session fallback
        Long studentUserId = request.getStudentUserId();
        if (studentUserId == null && user != null) {
            studentUserId = user.getUserId();
        }
        final Long resolvedStudentUserId = studentUserId;
        final LocalDateTime now = LocalDateTime.now();

        // Map each StudentQuestionAttemptRequest -> StudentQuestionAttempt (full model)
        List<StudentQuestionAttempt> attempts = new ArrayList<>();
        for (StudentQuestionAttemptRequest attemptReq : request.getAttempts()) {
            // Per-attempt subjectId overrides the top-level subjectId when provided
            Integer subjectId = (attemptReq.getSubjectId() != null)
                    ? attemptReq.getSubjectId()
                    : request.getSubjectId();

            // Serialize answerGiven List<Integer> [2] -> JSON string "[2]" for storage & comparison.
            // null means the student did not answer this question.
            String answerGivenJson = null;
            try {
                if (attemptReq.getAnswerGiven() != null) {
                    answerGivenJson = objectMapper.writeValueAsString(attemptReq.getAnswerGiven());
                }
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                log.error("Failed to serialize answerGiven for questionId {}: {}",
                        attemptReq.getQuestionId(), e.getMessage());
            }

            StudentQuestionAttempt attempt = StudentQuestionAttempt.builder()
                    .questionPaperId(request.getQuestionPaperId())
                    .studentUserId(resolvedStudentUserId)
                    .questionId(attemptReq.getQuestionId())
                    .subjectId(subjectId)
                    .answerGiven(answerGivenJson)
                    .attemptedAt(now)
                    .build();
            attempts.add(attempt);
        }

        log.info("Received exam submission: questionPaperId={}, studentUserId={}, attempts={}",
                request.getQuestionPaperId(), resolvedStudentUserId, attempts.size());

        StudentSubjectSummary summary = studentQuestionAttemptService.save(attempts, request.getTimeTaken(), user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Exam submitted successfully",
                        "summary", summary != null ? summary : "No summary generated"
                ));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/exam-submissions
    //
    // Submit a single question attempt (useful for real-time / per-question save).
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<?> submitSingleAttempt(@RequestBody StudentQuestionAttempt attempt) {
        UserBean user = getUser();

        if (attempt == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Attempt body must not be null"));
        }

        if (user != null && attempt.getStudentUserId() == null) {
            attempt.setStudentUserId(user.getUserId());
        }
        if (attempt.getAttemptedAt() == null) {
            attempt.setAttemptedAt(LocalDateTime.now());
        }

        log.info("Received single exam submission: questionPaperId={}, questionId={}",
                attempt.getQuestionPaperId(), attempt.getQuestionId());

        int saved = studentQuestionAttemptService.save(attempt, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Attempt saved successfully",
                        "savedCount", saved
                ));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/exam-submissions/{id}
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttemptById(@PathVariable Long id) {
        StudentQuestionAttempt attempt = studentQuestionAttemptService.findById(id);
        if (attempt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Attempt not found with id: " + id));
        }
        return ResponseEntity.ok(attempt);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/exam-submissions/summary/{summaryId}
    //
    // Fetch the detailed exam hierarchy (Parts -> Sections -> Questions)
    // and inject the student's submitted answers, correctness, and marks
    // based on a specific submission summary ID.
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/summary/{summaryId}")
    public ResponseEntity<?> getAttemptedPaperHierarchy(@PathVariable Long summaryId) {
        UserBean user = getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }

        if (user.getTenantId() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Tenant context required"));
        }

        // 1. Fetch the summary scoped to the tenant
        StudentSubjectSummary summary = studentSubjectSummaryService.findByIdAndTenantId(summaryId, user.getTenantId());
        if (summary == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Summary not found with id: " + summaryId));
        }

        Long questionPaperId = summary.getQuestionPaperId();

        // 2. Fetch the completely structured question paper
        QuestionPaperHierarchyResponseDTO hierarchy;
        try {
            hierarchy = questionPaperService.getQuestionPaperHierarchyById(questionPaperId, false, user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }

        // 3. Fetch the student's specific attempts for this summary, scoped to tenant
        List<StudentQuestionAttempt> attempts = studentQuestionAttemptService.findBySummaryIdAndTenantId(summaryId, user.getTenantId());

        // 4. Map attempts by questionId for fast lookup
        Map<Long, StudentQuestionAttempt> attemptMap = attempts.stream()
                .collect(java.util.stream.Collectors.toMap(StudentQuestionAttempt::getQuestionId, a -> a, (a1, a2) -> a2));

        // 5. Inject student attempts into the hierarchy's specific questions
        for (PartQuestionsDTO part : hierarchy.getParts()) {
            for (SectionQuestionsDTO section : part.getSections()) {
                if (section.getQuestions() != null) {
                    for (QuestionEntity question : section.getQuestions()) {
                        StudentQuestionAttempt attempt = attemptMap.get(question.getId());
                        if (attempt != null) {
                            question.setAnswerGiven(attempt.getAnswerGiven());
                            question.setIsCorrect(attempt.getIsCorrect());
                            question.setMarksObtained(attempt.getMarksObtained());
                        }
                    }
                }
            }
        }

        // 6. Inject overall time Taken from the StudentSubjectSummary
        hierarchy.setTimeTaken(summary.getTimeTaken());

        return ResponseEntity.ok(hierarchy);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/exam-submissions/start-exam/{questionPaperId}
    //
    // Called when a student clicks "Start Exam".
    // Returns a short-lived examToken valid for:
    //   paper_template.total_duration  +  5-minute submission buffer
    //
    // The client must send this token in the X-Exam-Token header when
    // submitting answers.  The server validates the token at submission
    // time to ensure the student has not exceeded the allowed exam window.
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/start-exam/{questionPaperId}")
    public ResponseEntity<?> startExam(@PathVariable Long questionPaperId) {
        UserBean user = getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }

        // Fetch the exam — this already contains totalDuration from paper_template
        QuestionPaperResponseDTO exam = questionPaperService.getExamsByStudentPackageAndMedium(
                        user.getUserId(), user.getTenantId())
                .stream()
                .filter(e -> questionPaperId.equals(e.getId()))
                .findFirst()
                .orElse(null);

        if (exam == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Exam not found or not available for this student"));
        }

        if (exam.getTotalDuration() == null || exam.getTotalDuration() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Exam duration is not configured. Please contact administrator."));
        }

        String examToken = examTokenService.generateExamToken(
                user.getUserId(), questionPaperId, exam.getTotalDuration());

        return ResponseEntity.ok(Map.of(
                "examToken",       examToken,
                "questionPaperId", questionPaperId,
                "durationMinutes", exam.getTotalDuration(),
                "bufferMinutes",   5,
                "totalWindowMinutes", exam.getTotalDuration() + 5
        ));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DASHBOARD TILE APIs  (call in parallel from FE)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * GET /api/exam-submissions/dashboard/header
     *
     * Header tile: student greeting, class, medium, photo, last exam + score.
     * No DB call — data comes from session + student_subject_summary last exam.
     */
    @GetMapping("/dashboard/header")
    public ResponseEntity<?> getDashboardHeader() {
        UserBean user = getUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "User not authenticated"));

        String displayName = "";
        String photoUrl = null;
        if (user.getApplicationUser() != null) {
            com.mahaexam.tenant.management.model.ApplicationUser au = user.getApplicationUser();
            displayName = ((au.getFirstName() != null ? au.getFirstName() : "")
                    + (au.getLastName() != null ? " " + au.getLastName() : "")).trim();
            photoUrl = au.getPhotoUrl();
        }

        // Last exam pulled from DB (lightweight single-row query via findStudentDashboard header fields)
        StudentDashboardDTO partial = studentSubjectSummaryService
                .findStudentDashboard(user.getUserId(), user.getTenantId());

        return ResponseEntity.ok(StudentDashboardDTO.builder()
                .displayName(displayName)
                .photoUrl(photoUrl)
                .lastExamName(partial.getLastExamName())
                .lastExamScore(partial.getLastExamScore())
                .lastExamAttemptedAt(partial.getLastExamAttemptedAt())
                .build());
    }

    /**
     * GET /api/exam-submissions/dashboard/packages
     *
     * My Packages tile: total / active / expiring-soon counts.
     */
    @GetMapping("/dashboard/packages")
    public ResponseEntity<?> getDashboardPackages() {
        UserBean user = getUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "User not authenticated"));

        PackageSummaryResponse summary =
                studentSubjectSummaryService.findDashboardPackages(user.getUserId());
        return ResponseEntity.ok(summary);
    }

    /**
     * GET /api/exam-submissions/dashboard/upcoming-exam
     *
     * Upcoming Exam tile: next untaken ACTIVE question paper for this student
     * (filtered by class + medium from the student's profile).
     * Returns 204 No Content when no upcoming exam exists.
     */
    @GetMapping("/dashboard/upcoming-exam")
    public ResponseEntity<?> getDashboardUpcomingExam() {
        UserBean user = getUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "User not authenticated"));
        if (user.getTenantId() == null) return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Tenant context required"));

        UpcomingExamResponse upcoming =
                studentSubjectSummaryService.findDashboardUpcomingExam(user.getUserId(), user.getTenantId());
        if (upcoming == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(upcoming);
    }

    /**
     * GET /api/exam-submissions/dashboard/recent-results
     *
     * Recent Results tile: last 5 taken exams with score % and attemptedAt date.
     */
    @GetMapping("/dashboard/recent-results")
    public ResponseEntity<?> getDashboardRecentResults() {
        UserBean user = getUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "User not authenticated"));
        if (user.getTenantId() == null) return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Tenant context required"));

        List<RecentResultResponse> results =
                studentSubjectSummaryService.findDashboardRecentResults(user.getUserId(), user.getTenantId());
        return ResponseEntity.ok(results);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/exam-submissions/my-exams
    //
    // Returns the list of ACTIVE question papers available to the logged-in
    // student, resolved through:
    //   student → student_package_mapping (Active) → packages
    //   → package_question_paper_mapping → question_paper (ACTIVE, tenant)
    //   → question_paper_template → paper_template
    //     (filtered by student's current_class_id + medium)
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/my-exams")
    public ResponseEntity<?> getMyExams() {
        UserBean user = getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }
        if (user.getTenantId() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Tenant context required"));
        }

        List<QuestionPaperResponseDTO> exams =
                questionPaperService.getExamsByStudentPackageAndMedium(user.getUserId(), user.getTenantId());

        return ResponseEntity.ok(exams);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/exam-submissions/my-exam-summary
    // ─────────────────────────────────────────────────────────────────────────
    //   - question paper details (name, status, academicYear)
    //   - medium + className from the linked paper template
    //   - totalExams  : total active papers available for this class+medium
    //   - takenCount  : how many the student has already attempted
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/my-exam-summary")
    public ResponseEntity<?> getMyExamSummary() {
        UserBean user = getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }
        if (user.getTenantId() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Tenant context required"));
        }

        List<StudentExamSummaryDTO> summary =
                studentSubjectSummaryService.findStudentExamSummary(user.getUserId(), user.getTenantId());

        return ResponseEntity.ok(summary);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/exam-submissions/my-exam-results
    //
    // Returns exam results grouped by package.
    // Each package contains the full list of question papers available to the
    // student (matching class + medium), with result details for papers already
    // attempted and isTaken=false placeholders for papers not yet taken.
    //
    // Response shape:
    // [
    //   {
    //     "packageId": 1, "packageName": "SSC Batch A", ...,
    //     "totalExams": 5, "takenCount": 3,
    //     "exams": [
    //       { "questionPaperId": 10, "questionPaperName": "Math Test 1",
    //         "isTaken": true, "marksObtained": 18, "maxMarks": 20,
    //         "scorePercent": 90.00, "correct": 9, "wrong": 1,
    //         "notAnswered": 0, "attemptedAt": "2026-02-10T14:30:00" },
    //       { "questionPaperId": 11, "questionPaperName": "Science Test 1",
    //         "isTaken": false, "totalDuration": 60, "totalMarks": 25 }
    //     ]
    //   }
    // ]
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/my-exam-results")
    public ResponseEntity<?> getMyExamResults() {
        UserBean user = getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }
        if (user.getTenantId() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Tenant context required"));
        }

        List<PackageExamResultDTO> results =
                studentSubjectSummaryService.findExamResultsByPackage(user.getUserId(), user.getTenantId());

        return ResponseEntity.ok(results);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/exam-submissions/student-exams-package-wise
    //
    // Returns all exams of the logged-in student grouped by package.
    // Each package contains the full list of question papers available to the
    // student (matching class + medium), with result details for papers already
    // attempted and isTaken=false placeholders for papers not yet taken.
    //
    // Response shape:
    // [
    //   {
    //     "packageId": 1, "packageName": "SSC Batch A",
    //     "packageStartDate": "2026-01-01", "packageEndDate": "2026-12-31",
    //     "classId": 10, "className": "10", "medium": "English",
    //     "totalExams": 5, "takenCount": 3,
    //     "exams": [
    //       { "questionPaperId": 10, "questionPaperName": "Math Test 1",
    //         "isTaken": true, "marksObtained": 18, "maxMarks": 20,
    //         "scorePercent": 90.00, "correct": 9, "wrong": 1,
    //         "notAnswered": 0, "totalQuestions": 10,
    //         "totalDuration": 60, "totalMarks": 25,
    //         "attemptedAt": "2026-02-10T14:30:00" },
    //       { "questionPaperId": 11, "questionPaperName": "Science Test 1",
    //         "isTaken": false, "totalDuration": 60, "totalMarks": 25 }
    //     ]
    //   }
    // ]
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/student-exams-package-wise")
    public ResponseEntity<?> getStudentExamsPackageWise() {
        UserBean user = getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }
        if (user.getTenantId() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Tenant context required"));
        }

        List<PackageExamResultDTO> results =
                studentSubjectSummaryService.findExamResultsByPackage(user.getUserId(), user.getTenantId());

        return ResponseEntity.ok(results);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET /api/exam-submissions
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<?> getAllAttempts() {
        UserBean user = getUser();
        if (user == null || user.getTenantId() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Tenant context required"));
        }
        return ResponseEntity.ok(studentQuestionAttemptService.findAllByTenantId(user.getTenantId()));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DELETE /api/exam-submissions/{id}
    // ─────────────────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttempt(@PathVariable Long id) {
        int deleted = studentQuestionAttemptService.deleteById(id);
        if (deleted == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Attempt not found with id: " + id));
        }
        return ResponseEntity.noContent().build();
    }

}
