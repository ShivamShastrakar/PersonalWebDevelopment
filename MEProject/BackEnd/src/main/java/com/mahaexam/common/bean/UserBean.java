package com.mahaexam.common.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahaexam.tenant.management.model.ApplicationUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBean {
    private Long userId;
    private Long tenantId;
    private String userName;
    @JsonIgnore
    private String password;
    private Boolean isActive;
    private Boolean isSalt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ApplicationUser applicationUser;
}