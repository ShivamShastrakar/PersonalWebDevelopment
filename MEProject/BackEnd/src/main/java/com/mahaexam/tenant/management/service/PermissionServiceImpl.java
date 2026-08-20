package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.tenant.management.model.Permission;
import com.mahaexam.tenant.management.repository.PermissionRepository;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'permission_' + #permission.name")
    public Permission save(Permission permission) {
        validatePermission(permission);
        return permissionRepository.save(permission);
    }

    @Override
    @Cacheable(value = "configCache", key = "'permission_' + #permissionId")
    public Optional<Permission> findById(Long permissionId) {
        if (permissionId == null) {
            throw new IllegalArgumentException("Permission ID is required");
        }
        return permissionRepository.findById(permissionId);
    }

    @Override
    @Cacheable(value = "configCache", key = "'permission_' + #name")
    public Optional<Permission> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Permission name is required");
        }
        return permissionRepository.findByName(name);
    }

    @Override
    @Cacheable(value = "configCache", key = "'permissions_all'")
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'permission_' + #permission.name")
    public Permission update(Permission permission) {
        validatePermission(permission);
        if (!permissionRepository.findById(permission.getPermissionId()).isPresent()) {
            throw new IllegalArgumentException("Permission with ID " + permission.getPermissionId() + " does not exist");
        }
        return permissionRepository.update(permission);
    }

    @Override
    @Transactional
    @CacheEvict(value = "configCache", key = "'permission_' + #permissionId")
    public void delete(Long permissionId) {
        if (permissionId == null) {
            throw new IllegalArgumentException("Permission ID is required");
        }
        if (!permissionRepository.findById(permissionId).isPresent()) {
            throw new IllegalArgumentException("Permission with ID " + permissionId + " does not exist");
        }
        permissionRepository.delete(permissionId);
    }

    private void validatePermission(Permission permission) {
        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }
        if (permission.getName() == null || permission.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Permission name is required");
        }
    }
    
    @Override
    @Cacheable(value = "configCache", key = "'role_permissions_' + #roleId")
    public List<Permission> findPermissionsByRoleId(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID is required");
        }
//        if (!roleRepository.findById(roleId).isPresent()) {
//            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist");
//        }
        return permissionRepository.findPermissionsByRoleId(roleId);
    }
}