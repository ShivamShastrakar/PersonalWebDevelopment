package com.mahaexam.common.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Zone {
	private Integer id;
	private String zoneName;
	private Integer stateId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer tenantId;
}
