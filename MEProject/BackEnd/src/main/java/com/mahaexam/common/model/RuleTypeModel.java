package com.mahaexam.common.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleTypeModel {
    private Integer id;
    private String ruleType;
    private LocalDateTime createdAt;
    private Integer createdBy;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted; // Enum: 1, 0
    private Integer updatedBy;
    private Long tenantId;
}