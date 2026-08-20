package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentResultResponse {
    private Long questionPaperId;
    private Long summaryId;
    private String questionPaperName;
    private BigDecimal marksObtained;
    private BigDecimal maxMarks;
    private BigDecimal scorePercent;   // (marksObtained / maxMarks) * 100
    private LocalDateTime attemptedAt;
}
