package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Permission;

public interface PermissionRepository {
    Permission save(Permission permission);
    Optional<Permission> findById(Long permissionId);
    Optional<Permission> findByName(String name);
    List<Permission> findAll();
    Permission update(Permission permission);
    void delete(Long permissionId);
    List<Permission> findPermissionsByRoleId(Long roleId);
}