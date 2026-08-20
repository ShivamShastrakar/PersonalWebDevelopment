package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class Student {
	private Long studentId;
	private Long userId;
	private Long studentReferenceId;
	private Integer currentClassId;
	private Integer currentSubjectGroupId;
	private Integer targetFinalExamYear;
	private Long parentId;
	
    private String photoImg;
    private String photoUrl;
    private String medium;
    private Integer boardId;
    private String schoolName;
    private String schoolAddress;
    private String category;
    private String instituteName;
    private String parallelReservation;
}
