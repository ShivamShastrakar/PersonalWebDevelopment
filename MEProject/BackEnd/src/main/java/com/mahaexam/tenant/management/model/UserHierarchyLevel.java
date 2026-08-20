package com.mahaexam.tenant.management.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserHierarchyLevel {
    private Integer id;
    private String levelName;
    private String description;
    private Integer levelOrder;
    private Long tenantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
