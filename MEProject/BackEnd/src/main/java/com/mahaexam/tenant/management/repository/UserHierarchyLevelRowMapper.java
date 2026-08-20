package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHierarchyLevelRowMapper implements RowMapper<UserHierarchyLevel> {
    @Override
    public UserHierarchyLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserHierarchyLevel.builder()
                .id(rs.getInt("id"))
                .levelName(rs.getString("level_name"))
                .description(rs.getString("description"))
                .levelOrder(rs.getInt("level_order"))
                .tenantId(rs.getLong("tenant_id"))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                .build();
    }
}
