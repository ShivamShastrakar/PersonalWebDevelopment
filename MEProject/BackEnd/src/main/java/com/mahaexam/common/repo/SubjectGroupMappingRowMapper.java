package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.SubjectGroupMapping;

public class SubjectGroupMappingRowMapper implements RowMapper<SubjectGroupMapping> {
    @Override
    public SubjectGroupMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubjectGroupMapping mapping = new SubjectGroupMapping();
        mapping.setMappingId(rs.getInt("mapping_id"));
        mapping.setGroupId(rs.getInt("group_id"));
        mapping.setSubjectId(rs.getInt("subject_id"));
        mapping.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        mapping.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        mapping.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        mapping.setDeleted(rs.getString("deleted"));
        return mapping;
    }
}