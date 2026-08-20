package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void assignRole(Long userId, Long roleId) {
        String sql = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
    }

    @Override
    public void unassignRole(Long userId, Long roleId) {
        String sql = "DELETE FROM user_role WHERE user_id = ? AND role_id = ?";
        jdbcTemplate.update(sql, userId, roleId);
    }

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        String sql = "SELECT role_id FROM user_role WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("role_id"), userId);
    }
    
    
    @Override
    public void assignRoles(Long userId, List<Long> roleIds) {
    	// Check if roleIds is null or empty to avoid unnecessary database operations
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";
        
        // Prepare batch parameters
        List<Object[]> batchArgs = roleIds.stream()
                                          .map(roleId -> new Object[]{userId, roleId})
                                          .collect(Collectors.toList());

        // Execute batch insert
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}