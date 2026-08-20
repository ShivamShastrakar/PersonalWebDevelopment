package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.Role;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = Role.builder().build();
        role.setRoleId(rs.getLong("role_id"));
        role.setTenantId(rs.getObject("tenant_id", Long.class));
        role.setName(rs.getString("name"));
        role.setDescription(rs.getString("description"));
        role.setIsActive(rs.getBoolean("is_active"));
        role.setIsAssignable(rs.getBoolean("is_assignable"));
        role.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        role.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        return role;
    }
}
