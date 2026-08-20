package com.mahaexam.tenant.management.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Menu {
	Integer menuId;
    Integer parentId;
    String name;
    String path;
    String icon;
    Integer orderIndex;
    Boolean isActive;
    
    private List<Permission> permissions; // New field to store permissions
    
    
    @Data
    @Builder
    public static class Permission {
        private Long permissionId;
        private String permissionName;
        private String permissionType;
    }
}
