package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.CourseSubjectGroupMapping;

public class CourseSubjectGroupMappingRowMapper implements RowMapper<CourseSubjectGroupMapping> {
    @Override
    public CourseSubjectGroupMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        CourseSubjectGroupMapping mapping = new CourseSubjectGroupMapping();
        mapping.setId(rs.getInt("id"));
        mapping.setCourseId(rs.getInt("course_id"));
        mapping.setSubjectGroupId(rs.getInt("subject_group_id"));
        mapping.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        mapping.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        mapping.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        mapping.setDeleted(rs.getString("deleted"));
        return mapping;
    }
}
