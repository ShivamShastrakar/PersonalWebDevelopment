package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.tenant.management.model.Role;
import com.mahaexam.tenant.management.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'role_' + #role.roleId")
    public Role save(Role role) {
        validateRole(role);
        if (role.getIsActive() == null) role.setIsActive(true);
        if (role.getIsAssignable() == null) role.setIsAssignable(true);
        return roleRepository.save(role);
    }

    @Override
    @Cacheable(value = "configCache", key = "'role_' + #roleId")
    public Optional<Role> findById(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID is required");
        }
        return roleRepository.findById(roleId);
    }


    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'role_' + #role.roleId")
    public Role update(Role role) {
        validateRole(role);
        if (!roleRepository.findById(role.getRoleId()).isPresent()) {
            throw new IllegalArgumentException("Role with ID " + role.getRoleId() + " does not exist");
        }
        return roleRepository.update(role);
    }

    @Override
    @Transactional
    @CacheEvict(value = "configCache", key = "'role_' + #roleId")
    public void delete(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID is required");
        }
        if (!roleRepository.findById(roleId).isPresent()) {
            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist");
        }
        roleRepository.delete(roleId);
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if (role.getName() == null || role.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Role name is required");
        }
        Optional<Role> existingRole = roleRepository.findByName(role.getName().trim());

        if (role.getRoleId() == null) {
            // On create
            if (existingRole.isPresent()) {
                throw new ValidationException("Role name '" + role.getName() + "' already exists");
            }
        } else {
            // On update
            if (existingRole.isPresent() && !existingRole.get().getRoleId().equals(role.getRoleId())) {
                throw new ValidationException("Role name '" + role.getName() + "' is already used by another role");
            }
        }
    }
    public List<Role> findRolesByUserId(Long userId){
    	List<Role> roles = roleRepository.findRolesByUserId(userId);
    	
    	return roles;
    }

	@Override
	public Optional<Role> findByName(String roleName) {
		return roleRepository.findByName(roleName);
	}

	@Override
	public boolean hasRole(Long userId) {
		return roleRepository.hasRole(userId);
	}
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
}