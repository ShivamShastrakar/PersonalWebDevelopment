package com.mahaexam.common.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.CourseSubjectGroupMapping;

@Repository
public class CourseSubjectGroupMappingRepositoryImpl implements CourseSubjectGroupMappingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseSubjectGroupMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CourseSubjectGroupMapping mapping) {
        String sql = "INSERT INTO course_subject_group_mapping (course_id, subject_group_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, mapping.getCourseId(), mapping.getSubjectGroupId());
    }

    @Override
    public int deleteByCourseId(int courseId) {
        String sql = "UPDATE course_subject_group_mapping SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE course_id = ?";
        return jdbcTemplate.update(sql, courseId);
    }

    @Override
    public List<CourseSubjectGroupMapping> findByCourseId(int courseId) {
        String sql = "SELECT * FROM course_subject_group_mapping WHERE course_id = ? AND deleted = '0'";
        return jdbcTemplate.query(sql, new CourseSubjectGroupMappingRowMapper(), courseId);
    }
}