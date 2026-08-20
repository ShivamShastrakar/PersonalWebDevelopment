package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.Course;
import com.mahaexam.common.util.RepoUtil;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setCourseName(rs.getString("course_name"));
        course.setCourseDetails(rs.getString("course_details"));
        course.setTenantId(rs.getLong("tenant_id"));
        course.setUpdatedBy(rs.getLong("updated_by"));
        course.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        course.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        course.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        course.setDeleted(rs.getString("deleted"));
        
        course.setPackageId(RepoUtil.getOptionalInteger(rs, "package_id"));
        return course;
    }
}