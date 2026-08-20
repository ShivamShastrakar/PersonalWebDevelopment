package com.mahaexam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentSubjectSummary {
    private Long id;

    private Long questionPaperId;

    private Long studentUserId;

    private Integer subjectId;

    private Integer totalQuestions;

    private Integer correct;

    private Integer wrong;

    private Integer notAnswered;

    private BigDecimal marksObtained;

    private BigDecimal maxMarks;

    private Integer timeTaken;

    private LocalDateTime attemptedAt;

    private Long tenantId;
}
