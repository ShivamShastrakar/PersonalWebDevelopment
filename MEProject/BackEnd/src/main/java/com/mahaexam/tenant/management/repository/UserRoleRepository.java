package com.mahaexam.tenant.management.repository;

import java.util.List;

public interface UserRoleRepository {
    void assignRole(Long userId, Long roleId);
    void unassignRole(Long userId, Long roleId);
    List<Long> findRoleIdsByUserId(Long userId);
	void assignRoles(Long userId, List<Long> roleIds);
}