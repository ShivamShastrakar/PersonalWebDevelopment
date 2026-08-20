package com.mahaexam.tenant.management.service;

import java.util.List;

import com.mahaexam.tenant.management.model.Permission;

public interface RolePermissionService {
    void assignPermission(Long roleId, Long permissionId);
    void unassignPermission(Long roleId, Long permissionId);
    List<Permission> findPermissionsByRoleId(Long roleId);
}
