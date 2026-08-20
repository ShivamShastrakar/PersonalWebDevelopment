package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Permission;

public interface PermissionService {
    Permission save(Permission permission);
    Optional<Permission> findById(Long permissionId);
    Optional<Permission> findByName(String name);
    List<Permission> findAll();
    Permission update(Permission permission);
    void delete(Long permissionId);
    List<Permission> findPermissionsByRoleId(Long roleId);
}