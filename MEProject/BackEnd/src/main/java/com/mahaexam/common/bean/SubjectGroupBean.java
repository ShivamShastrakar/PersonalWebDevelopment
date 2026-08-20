package com.mahaexam.common.bean;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Component
@Schema(description = "Subject Bean")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectGroupBean {
	@Schema(description = "Group Name", example = "PCM")
	private String groupName;

	@Schema(description = "Description", example = "Physics, Chemistry, Maths")
	private String description;

	@Schema(description = "Tenant ID", example = "1001")
	private Long tenantId;

	@Schema(description = "Deleted status", example = "0")
	private String deleted;
}
