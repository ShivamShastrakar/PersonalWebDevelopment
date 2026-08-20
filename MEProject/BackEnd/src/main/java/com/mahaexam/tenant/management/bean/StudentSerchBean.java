package com.mahaexam.tenant.management.bean;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentSerchBean {
	private Integer page = 0;
	private Integer size = 500;
	private Long studentId;
	private Long applicationUserId;
	private Integer classId;
	private Integer subjectGroupId;
	private Integer courseId;
	private Long studentReferenceId;
	private Integer academicYearId;
	private Integer days;
}
