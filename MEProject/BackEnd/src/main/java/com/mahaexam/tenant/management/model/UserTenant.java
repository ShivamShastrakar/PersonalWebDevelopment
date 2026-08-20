package com.mahaexam.tenant.management.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserTenant {
    private Long id;
    private Long userId;
    private Long tenantId;
    private LocalDateTime createdAt;
}
