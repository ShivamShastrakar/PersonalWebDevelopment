package com.mahaexam.common.repo;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.Course;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> findAllByTenant(Long tenantId) {
        String sql = "SELECT * FROM course WHERE (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
        return jdbcTemplate.query(sql, new CourseRowMapper(), tenantId);
    }

    @Override
    public Course findById(int id) {
        String sql = "SELECT * FROM course WHERE id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new CourseRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public int save(Course course) {
        String sql = "INSERT INTO course (course_name, course_details, tenant_id, updated_by) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseDetails());
            ps.setLong(3, course.getTenantId());
            ps.setLong(4, course.getUpdatedBy());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : 0;
    }

    @Override
    public int update(Course course) {
        String sql = "UPDATE course SET course_name = ?, course_details = ?, updated_by = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, course.getCourseName(), course.getCourseDetails(), course.getUpdatedBy(), course.getId());
    }

    @Override
    public int softDelete(int id) {
        String sql = "UPDATE course SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByCourseNameAndTenantId(String courseName, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM course WHERE course_name = ? AND (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, courseName, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCourseNameAndTenantIdExceptId(String courseName, Long tenantId, int excludeId) {
        String sql = "SELECT COUNT(*) FROM course WHERE course_name = ? AND (tenant_id = ? OR tenant_id is null) AND id != ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, courseName, tenantId, excludeId);
        return count != null && count > 0;
    }
    
    @Override
    public List<Course> findAllByPackageIds(List<Integer> packageIds) {
        if (packageIds == null || packageIds.isEmpty()) {
            return Collections.emptyList(); // Return empty list for invalid input
        }
        // Create placeholders for each package ID
        String placeholders = String.join(",", Collections.nCopies(packageIds.size(), "?"));
        String sql = "SELECT c.*,pc.package_id FROM course c "
        		+ "inner join package_courses pc on c.id =pc.course_id  "
        		+ "WHERE deleted = '0' and pc.package_id in (" + placeholders + ")";

        // Convert List<Integer> to array for jdbcTemplate
        return jdbcTemplate.query(sql, new CourseRowMapper(), packageIds.toArray());
    }
    @Override
    public Course findByName(String name) {
        String sql = "SELECT * FROM course WHERE course_name = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new CourseRowMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public List<Course> findByNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Collections.emptyList();
        }
        String placeholders = String.join(",", Collections.nCopies(names.size(), "?"));
        String sql = "SELECT * FROM course WHERE course_name IN (" + placeholders + ") AND deleted = '0'";
        return jdbcTemplate.query(sql, new CourseRowMapper(), names.toArray());
    }

    @Override
    public List<Course> findAllByTenantAndClassId(Long tenantId, Integer classId) {
        // Adjust the join and table/column names as per your schema
        String sql = """
                SELECT c.* FROM course c\s
            INNER JOIN course_class_mapping ccm ON c.id = ccm.course_id\s
            WHERE (c.tenant_id = ? OR c.tenant_id IS NULL) AND ccm.class_id = ? AND c.deleted = '0'
            """;
        return jdbcTemplate.query(sql, new CourseRowMapper(), tenantId, classId);
    }
}

