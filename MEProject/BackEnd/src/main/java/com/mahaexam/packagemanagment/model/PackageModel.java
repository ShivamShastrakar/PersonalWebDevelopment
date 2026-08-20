package com.mahaexam.packagemanagment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageModel {
	private Integer id;
	private String packageName;
	private String packageDetails;
	private BigDecimal amount;
	private String packageFor;
	private String packageType;
	private String packageTargetStudents; // Enum: New, Existing
	private String packageMode; // Enum: ONLINE, OFFLINE, CBT, with_course
	private Boolean flag;
	private String packageTypeName;
	private Integer pkgExamGroup;
	private Integer isArchived;
	private Integer archivedBy;
	private LocalDate startDate;
	private LocalDate endDate;
	private String targetYear;
	private Integer showStrikePrice;
	private BigDecimal strikePrice;
	private Integer isTestingPackage;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
	private String deleted; // Enum: 1, 0
	private Integer updatedBy;
	private String packageImgUrl;
	private Long tenantId;

	private Integer classId;

	private Integer courseId;

	private Integer serviceId;

    private Long studentId;
    private String subscriptiontype;
    private String packageImg; // base64 encoded image string
    
    private Integer no_of_mock_exams;
    private Integer no_of_pactice_exams;
    private Integer no_of_bonus_exams;
    
    private Integer packageCategoryId;
}