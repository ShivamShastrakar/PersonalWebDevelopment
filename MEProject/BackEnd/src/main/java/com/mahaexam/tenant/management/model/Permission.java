package com.mahaexam.tenant.management.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Permission {
    private Long permissionId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String type;

}
