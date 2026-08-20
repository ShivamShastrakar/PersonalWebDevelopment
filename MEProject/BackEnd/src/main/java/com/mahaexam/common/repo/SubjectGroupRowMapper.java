package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.SubjectGroup;

public class SubjectGroupRowMapper implements RowMapper<SubjectGroup> {
    @Override
    public SubjectGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubjectGroup group = new SubjectGroup();
        group.setGroupId(rs.getInt("group_id"));
        group.setTenantId(rs.getLong("tenant_id"));
        group.setGroupName(rs.getString("group_name"));
        group.setDescription(rs.getString("description"));
        group.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        group.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        group.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        group.setDeleted(rs.getString("deleted"));
        return group;
    }
}

