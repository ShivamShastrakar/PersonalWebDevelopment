package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Role;

public interface RoleRepository {
	Role save(Role role);

	Optional<Role> findById(Long roleId);

	Optional<Role> findByName(String roleName);

	Role update(Role role);

	void delete(Long roleId);

	List<Role> findRolesByUserId(Long userId);
	
    List<Role> findRolesByUserRoleIds( Long userId, List<Long> roleIds);

	boolean hasRole(Long userId);

	List<Role> findAll();

	boolean existsByNameIgnoreCaseAndTenantId(String roleName, Long tenantId, Long excludeRoleId);

    List<Role> findRolesByNames(List<String> roleNamess);
}