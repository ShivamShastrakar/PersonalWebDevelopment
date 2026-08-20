package com.mahaexam.packagemanagment.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudentPackageSelectionSummaryBean {
    private Long selectionSummaryId;
    @NotNull
    private Long studentId;
    private BigDecimal totalAmount;
    private LocalDateTime selectedAt;
    private String status;
    @NotNull
    @Size(min = 1)
    private List<StudentPackageSelectionBean> packageSelectionBeans;
}
