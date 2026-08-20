package com.mahaexam.tenant.management.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompletedExamDetailsBean {
    private Long questionPaperId;
    private String questionPaperName;
    private String studentName;
    private BigDecimal marksObtained;
    private BigDecimal maxMarks;
    private BigDecimal scorePercent;
    private LocalDateTime attemptedAt;
}
