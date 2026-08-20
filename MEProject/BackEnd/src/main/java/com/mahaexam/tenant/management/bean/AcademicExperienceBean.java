package com.mahaexam.tenant.management.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademicExperienceBean {
   
	private Long academicId;
	
	 private Long userId; 
	
	@NotNull(message = "Class is required")
	private Integer classId;

    @NotNull(message = "Subject is required")
    private Integer subjectId;

    @NotNull(message = "Board is required")
    private Integer boardId;

    @NotNull(message = "Chapter is required")
    private String chapters;
}
