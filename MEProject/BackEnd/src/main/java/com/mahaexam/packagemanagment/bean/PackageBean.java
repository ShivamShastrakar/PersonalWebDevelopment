package com.mahaexam.packagemanagment.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mahaexam.common.bean.ClassBean;
import com.mahaexam.common.bean.CourseBean;
import com.mahaexam.packagemanagment.bean.ServiceBean;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PackageBean {
	private Integer id;

	@NotBlank(message = "Package Name is required")
	private String packageName;

	@NotBlank(message = "Package Details is required")
	private String packageDetails;

	@DecimalMin(value = "0.00", message = "Amount must be non-negative")
	@Digits(integer = 8, fraction = 2, message = "Amount must have up to 8 integer digits and 2 decimal places")
	@DecimalMax(value = "99999999.99", message = "Amount must not exceed 99999999.99")
	private BigDecimal amount;

	@NotBlank(message = "Package For is required")
	private String packageFor;

    @NotBlank(message = "Package Type For is required")
    @Pattern(regexp = "^(Prepare|Practice|Evaluate)$",
            message = "Package Type must be one of: Prepare, Practice, or Evaluate")
	private String packageType;

	@NotBlank(message = "Package Target Student is required")
	private String packageTargetStudents; // Enum: New, Existing

	private String packageMode; // Enum: ONLINE, OFFLINE, CBT, CBT_WITH_COURSE
	private Boolean flag;

	private String packageTypeName;

	private Integer pkgExamGroup;
	private Integer isArchived;
	private Integer archivedBy;

	@NotNull(message = "Start date is required")
	private LocalDate startDate;
	@NotNull(message = "End date is required")
	private LocalDate endDate;

	@NotNull(message = "Show Strike Price is required")
	private Integer showStrikePrice;
	private BigDecimal strikePrice;
	
	private Integer isTestingPackage;
	
	private Integer updatedBy;
	private String packageImgUrl;
	private Long tenantId;

	@NotNull(message = "Class is required")
	private Integer classId;
	
	@NotNull(message = "Course is required")
	private Integer courseId;
	
	@NotNull(message = "Service is required")
	private Integer serviceId;
	
	@NotBlank(message = "Target Year is required")
	private String targetYear;
	
	private String packageImg; // base64 encoded image string
	
	private ClassBean classBean;
	private CourseBean courseBean;
	private List<ServiceBean> serviceBeans;

    private Long studentId;
    private String subscriptiontype;
    
    private Integer no_of_mock_exams;
    private Integer no_of_pactice_exams;
    private Integer no_of_bonus_exams;
    
    private Integer packageCategoryId;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}