package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleHierarchyLevelMappingRowMapper implements RowMapper<UserRoleHierarchyLevelMapping> {
    @Override
    public UserRoleHierarchyLevelMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserRoleHierarchyLevelMapping.builder()
                .id(rs.getInt("id"))
                .userRoleId(rs.getLong("role_id"))
                .userHierarchyLevelId(rs.getInt("user_hierarchy_level_id"))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                .build();
    }
}
