package com.mahaexam.packagemanagment.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StudentPackageSelectionBean {
    private Long selectionId;
    private Integer packageId;
    private String packageName;
    private Long studentId;
    private Long selectionSummaryId;
    private BigDecimal amount;
    private LocalDateTime selectedAt;
}
