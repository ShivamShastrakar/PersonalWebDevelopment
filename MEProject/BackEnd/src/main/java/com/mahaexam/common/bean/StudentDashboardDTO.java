package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Single-response DTO for the Student Dashboard screen.
 *
 * Covers all 4 tiles visible in the UI:
 *   1. Header   – greeting, class, institute, last-login, last exam score
 *   2. Packages – total / active / expiring-soon counts
 *   3. Upcoming – next untaken ACTIVE exam
 *   4. Recent   – last N taken exams with score % and date
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDashboardDTO {

    // ── 1. Header ─────────────────────────────────────────────────────────────
    private String displayName;       // "Welcome back, Narendra!"
    private String className;         // "Class 4"
    private String medium;
    private String instituteName;     // "Uptown Public School"
    private String photoUrl;

    // Last Exam badge  ("Last Exam: English Test · 82%")
    private String lastExamName;
    private BigDecimal lastExamScore; // percentage (0-100)
    private LocalDateTime lastExamAttemptedAt;

    // ── 2. My Packages tile ───────────────────────────────────────────────────
    private PackageSummary packages;

    // ── 3. Upcoming Exam tile ─────────────────────────────────────────────────
    private UpcomingExam upcomingExam;

    // ── 4. Recent Results tile ────────────────────────────────────────────────
    private List<RecentResult> recentResults;

    // ─────────────────────────────────────────────────────────────────────────
    // Nested types
    // ─────────────────────────────────────────────────────────────────────────

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PackageSummary {
        private int totalPackages;
        private int activePackages;
        private int expiringSoonPackages; // ending within 30 days
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpcomingExam {
        private Long questionPaperId;
        private String questionPaperName;
        private Integer totalDuration;     // minutes
        private Integer examTotalMarks;
        private Integer examTotalQuestions;
        private LocalDateTime startDate;   // exam window open date
        private LocalDateTime endDate;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RecentResult {
        private Long questionPaperId;
        private Long summaryId;
        private String questionPaperName;
        private BigDecimal marksObtained;
        private BigDecimal maxMarks;
        private BigDecimal scorePercent;   // (marksObtained / maxMarks) * 100
        private LocalDateTime attemptedAt;
    }
}

