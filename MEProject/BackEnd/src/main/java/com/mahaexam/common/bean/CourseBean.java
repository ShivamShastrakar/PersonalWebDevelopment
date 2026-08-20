package com.mahaexam.common.bean;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Component
@Schema(description = "Course Bean")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseBean {
	@Schema(description = "Course Name", example = "Engineering")
	private String courseName;

	@Schema(description = "Course Details", example = "Detailed curriculum")
	private String courseDetails;

	@Schema(description = "Tenant ID", example = "1001")
	private Long tenantId;

	@Schema(description = "Updated By", example = "1")
	private Long updatedBy;

	@Schema(description = "Deleted status", example = "0")
	private String deleted;

    @NotEmpty(message = "subjectGroupIds is required and must not be empty")
    @Size(min = 1, message = "subjectGroupIds must contain at least 1 element")
    private List<Long> subjectGroupIds;

    // List of class IDs associated with this course
    private List<Integer> classIds;

	private String subjectName;

	private Integer packageId;
}
