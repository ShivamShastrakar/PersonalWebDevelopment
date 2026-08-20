package com.mahaexam.tenant.management.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserRoleHierarchyLevelMapping {
    private Integer id;
    private Long userRoleId;
    private Integer userHierarchyLevelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
