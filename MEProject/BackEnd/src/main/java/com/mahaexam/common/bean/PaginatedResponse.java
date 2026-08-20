package com.mahaexam.common.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class PaginatedResponse<T> {

	@Schema(description = "List of items in the current page", example = "[{...}, {...}]")
	private List<T> content;

	@Schema(description = "Current page number (0-based)", example = "0")
	private int page;

	@Schema(description = "Number of items per page", example = "10")
	private int size;

	@Schema(description = "Total number of items across all pages", example = "100")
	private long totalElements;

	@Schema(description = "Total number of pages", example = "10")
	private int totalPages;
	
}