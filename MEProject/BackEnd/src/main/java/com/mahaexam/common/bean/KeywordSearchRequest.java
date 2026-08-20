package com.mahaexam.common.bean;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeywordSearchRequest {
	@NotBlank(message = "Keyword1 is required")
	private String keyword1;
	@NotBlank(message = "Keyword2 is required")
	private String keyword2;
	@NotBlank(message = "Keyword3 is required")
	private String keyword3;
}
