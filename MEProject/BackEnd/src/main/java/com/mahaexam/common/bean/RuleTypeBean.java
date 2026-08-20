package com.mahaexam.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleTypeBean {
	private Integer id;
	private String ruleType;
	private Integer createdBy;
	private Integer updatedBy;
	private Long tenantId;
}
