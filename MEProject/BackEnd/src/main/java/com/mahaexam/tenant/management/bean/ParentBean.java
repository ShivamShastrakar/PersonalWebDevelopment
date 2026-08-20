package com.mahaexam.tenant.management.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentBean {
	private Long parentId;
    private String fatherName;
    private String fatherMobileNumber;
    private String fatherOccupation;
    private String motherName;
    private String motherMobileNumber;
    private String motherOccupation;
    private Integer numberOfSiblings;
    private String firstSiblingName;
    private String firstSiblingStd;
    private String secondSiblingName;
    private String secondSiblingStd;
    private String parentsYearlyIncome;
    private java.time.LocalDateTime createdAt;
}
