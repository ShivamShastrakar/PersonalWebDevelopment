package com.mahaexam.tenant.management.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    private Long roleId;
    private Long tenantId;
    private String name;
    private String description;
    private Boolean isActive;
    private Boolean isAssignable;
    private Long hierarchyLevelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}