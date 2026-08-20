package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.UserTenant;

public class UserTenantRowMapper implements RowMapper<UserTenant> {
    @Override
    public UserTenant mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserTenant userTenant = new UserTenant();
        userTenant.setId(rs.getLong("id"));
        userTenant.setUserId(rs.getLong("user_id"));
        userTenant.setTenantId(rs.getLong("tenant_id"));
        userTenant.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return userTenant;
    }
}