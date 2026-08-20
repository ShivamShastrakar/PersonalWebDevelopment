package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.Permission;

public class PermissionRowMapper implements RowMapper<Permission> {
    @Override
    public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
        Permission permission = Permission.builder().build();
        permission.setPermissionId(rs.getLong("permission_id"));
        permission.setName(rs.getString("name"));
        permission.setDescription(rs.getString("description"));
        permission.setType(rs.getString("type"));
        permission.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        return permission;
    }
}