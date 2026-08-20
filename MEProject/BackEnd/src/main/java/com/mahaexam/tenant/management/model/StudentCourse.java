package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class StudentCourse {
	private Long id;
	private Long studentId;
	private Long courseId;
	private String courseName;
    private String courseDetails;
}
