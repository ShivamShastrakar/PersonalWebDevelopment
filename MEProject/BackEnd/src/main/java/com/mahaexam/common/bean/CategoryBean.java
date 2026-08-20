package com.mahaexam.common.bean;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Component
@Schema(description = "Category Bean")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryBean {
	private int id;
	private String categoryName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
	private boolean deleted; // true for '1', false for '0'
	private boolean disabled; // true for 1, false for 0
	private Long tenantId;
}
