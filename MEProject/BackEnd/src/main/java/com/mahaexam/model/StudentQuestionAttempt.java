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
public class StudentQuestionAttempt {
    private Long id;

    private Long questionPaperId;

    private Long studentUserId;

    private Long questionId;

    private Integer subjectId;

    private String answerGiven;

    private Boolean isCorrect;

    private BigDecimal marksObtained;

    private LocalDateTime attemptedAt;
    
    // Links to the overall summary/attempt-submission group this answer belongs to.
    private Long summaryId;

    private Long tenantId;
}

