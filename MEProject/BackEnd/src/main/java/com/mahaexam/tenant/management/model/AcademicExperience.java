package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class AcademicExperience {
	private Long academicId;
	
	private Long userId;
	
	private Integer classId;
    
	private Integer subjectId;
    
	private Integer boardId;
    
	private String chapters;

}
