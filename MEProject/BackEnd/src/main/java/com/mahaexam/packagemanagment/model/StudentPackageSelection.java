package com.mahaexam.packagemanagment.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StudentPackageSelection {
    private Long selectionId;
    private Integer packageId;
    private Long studentId;
    private Long selectionSummaryId;
    private BigDecimal amount;
    private LocalDateTime selectedAt;
}
