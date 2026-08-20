package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Role;

public interface RoleService {
	Role save(Role role);

	Optional<Role> findById(Long roleId);

	Optional<Role> findByName(String roleName);

	Role update(Role role);

	void delete(Long roleId);

	List<Role> findRolesByUserId(Long userId);

	boolean hasRole(Long userId);

	List<Role> findAll();
}