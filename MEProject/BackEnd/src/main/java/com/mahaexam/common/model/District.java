package com.mahaexam.common.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class District {
	private Integer id;
	private String districtName;
	private Integer districtCode;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
	private String deleted; // '0' or '1'
	private Long tenantId;
	private Integer stateId;
	private Integer zoneId;
	private Integer divisionId;
}
