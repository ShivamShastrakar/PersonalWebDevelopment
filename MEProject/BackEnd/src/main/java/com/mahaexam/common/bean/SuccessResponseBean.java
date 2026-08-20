package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SuccessResponseBean {
	private String status;
	private String message;
}
