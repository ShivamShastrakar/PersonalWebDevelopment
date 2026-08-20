package com.mahaexam.tenant.management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parent {
	private Long parentId;
    @NotBlank(message = "Father's name cannot be blank")
    private String fatherName;
    private String fatherMobileNumber;
    private String fatherOccupation;
    @NotBlank(message = "Mother's name cannot be blank")
    private String motherName;
    private String motherMobileNumber;
    private String motherOccupation;
    @NotNull(message = "Number of siblings cannot be null")
    private Integer numberOfSiblings;
    private String firstSiblingName;
    private String firstSiblingStd;
    private String secondSiblingName;
    private String secondSiblingStd;
    @NotNull(message = "Parents' yearly income cannot be null")
    private String parentsYearlyIncome;
    private LocalDateTime createdAt;
    
    
    private Long studentId;
}
