package com.mahaexam.packagemanagment.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentPackageSelectionSummary {
    private Long selectionSummaryId;
    private Long studentId;
    private BigDecimal totalAmount;
    private LocalDateTime selectedAt;
    private String status;
    private List<StudentPackageSelection> packageSelections;
}
