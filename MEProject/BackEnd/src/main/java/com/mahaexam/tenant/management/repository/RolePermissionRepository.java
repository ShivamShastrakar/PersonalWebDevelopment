package com.mahaexam.tenant.management.repository;

import java.util.List;

public interface RolePermissionRepository {
    void assignPermission(Long roleId, Long permissionId);
    void unassignPermission(Long roleId, Long permissionId);
    List<Long> findPermissionIdsByRoleId(Long roleId);
}