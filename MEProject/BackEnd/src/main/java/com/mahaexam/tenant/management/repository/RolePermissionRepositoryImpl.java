package com.mahaexam.tenant.management.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionRepositoryImpl implements RolePermissionRepository {
    private final JdbcTemplate jdbcTemplate;

    public RolePermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void assignPermission(Long roleId, Long permissionId) {
        String sql = "INSERT INTO role_permission (role_id, permission_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, roleId, permissionId);
    }

    @Override
    public void unassignPermission(Long roleId, Long permissionId) {
        String sql = "DELETE FROM role_permission WHERE role_id = ? AND permission_id = ?";
        jdbcTemplate.update(sql, roleId, permissionId);
    }

    @Override
    public List<Long> findPermissionIdsByRoleId(Long roleId) {
        String sql = "SELECT permission_id FROM role_permission WHERE role_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("permission_id"), roleId);
    }
}
