package com.mahaexam.tenant.management.bean;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDataLoadBean {
    private String lastName;
    private String firstName;
    private String middleName;
    private String adharNo;
    private String mobileNumber;
    private String email;
    private String className;
    private String examGroup;
    private String courses;
    private String errorMessage;

    private Integer classId;

    private Long courseId;

    private List<Long> courseIds;

    private Integer subjectGroupId;

    private Integer targetFinalExamYear;

    private BigDecimal pacakagePrice;

    private Integer packageId;

    private Long referenceId;

    private Long tenantId;

    private String medium;

    private Long studentId;

    private Long batchId;
}
