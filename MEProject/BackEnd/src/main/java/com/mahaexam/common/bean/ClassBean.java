package com.mahaexam.common.bean;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Component
@Schema(description = "Subject Bean")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassBean {
	@Schema(description = "Class Name", example = "10th")
	private String className;

	@Schema(description = "Tenant ID", example = "1001")
	private Long tenantId;

	@Schema(description = "Deleted status", example = "0")
	private String deleted;
	
	private Integer packageId;
	private Integer id;

    @Schema(description = "Is Exam Group Required", example = "true")
    private Boolean isExamGroupRequired;
}
