package com.mahaexam.tenant.management.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.tenant.management.model.Permission;
import com.mahaexam.tenant.management.repository.PermissionRepository;
import com.mahaexam.tenant.management.repository.RolePermissionRepository;
import com.mahaexam.tenant.management.repository.RoleRepository;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "configCache", key = "'role_permissions_' + #roleId")
    public void assignPermission(Long roleId, Long permissionId) {
        validateRoleAndPermission(roleId, permissionId);
        rolePermissionRepository.assignPermission(roleId, permissionId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "configCache", key = "'role_permissions_' + #roleId")
    public void unassignPermission(Long roleId, Long permissionId) {
        validateRoleAndPermission(roleId, permissionId);
        rolePermissionRepository.unassignPermission(roleId, permissionId);
    }

    @Override
    @Cacheable(value = "configCache", key = "'role_permissions_' + #roleId")
    public List<Permission> findPermissionsByRoleId(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID is required");
        }
        if (!roleRepository.findById(roleId).isPresent()) {
            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist");
        }
        return permissionRepository.findPermissionsByRoleId(roleId);
    }

    private void validateRoleAndPermission(Long roleId, Long permissionId) {
        if (roleId == null || permissionId == null) {
            throw new IllegalArgumentException("Role ID and Permission ID are required");
        }
        if (!roleRepository.findById(roleId).isPresent()) {
            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist");
        }
        if (!permissionRepository.findById(permissionId).isPresent()) {
            throw new IllegalArgumentException("Permission with ID " + permissionId + " does not exist");
        }
    }
}