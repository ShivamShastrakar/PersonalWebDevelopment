package com.mahaexam.tenant.management.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MyEarningStats {
    private Long id;
    private Long userId;
    private Long levelOrderId;
    private LocalDate earningPeriodEndDt;
    private Integer totalDirectStudentCount;
    private BigDecimal totalDirectEarningAmt;
    private Integer totalIndirectStudentCount;
    private BigDecimal totalIndirectEarningAmt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
