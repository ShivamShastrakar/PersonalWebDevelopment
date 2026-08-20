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
 * Exam results for a student grouped by package.
 * Each entry represents one package the student is enrolled in,
 * with the list of all question papers in that package and the
 * student's result for each paper (null result fields = not yet attempted).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PackageExamResultDTO {

    private Integer packageId;
    private String  packageName;
    private LocalDate packageStartDate;
    private LocalDate packageEndDate;

    private Integer classId;
    private String  className;
    private String  medium;

    /** Total papers in this package matching the student's class + medium */
    private Integer totalExams;
    /** How many of those papers the student has already attempted */
    private Integer takenCount;

    /** Per-exam breakdown inside this package */
    private List<ExamResult> exams;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ExamResult {
        private Long    questionPaperId;
        private String  questionPaperName;
        private Integer totalDuration;       // minutes
        private Integer totalMarks;

        /** null when the student has not attempted this paper yet */
        private Long    summaryId;
        private Boolean isTaken;
        private BigDecimal marksObtained;
        private BigDecimal maxMarks;
        private BigDecimal scorePercent;     // (marksObtained / maxMarks) * 100
        private Integer    correct;
        private Integer    wrong;
        private Integer    notAnswered;
        private Integer    totalQuestions;
        private LocalDateTime attemptedAt;
    }
}

