package com.mahaexam.tenant.management.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleHierarchyLevelMappingBean {
    private Integer id;
    private Long userRoleId;
    private Integer userHierarchyLevelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
