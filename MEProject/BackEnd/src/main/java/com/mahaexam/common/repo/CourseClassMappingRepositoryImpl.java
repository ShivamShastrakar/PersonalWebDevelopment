package com.mahaexam.common.repo;

import com.mahaexam.common.model.CourseClassMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourseClassMappingRepositoryImpl implements CourseClassMappingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseClassMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveMappingsForCourse(Integer courseId, List<Integer> classIds) {
        deleteMappingsForCourse(courseId);
        if (classIds != null && !classIds.isEmpty()) {
            String sql = "INSERT INTO course_class_mapping (course_id, class_id) VALUES (?, ?)";
            List<Object[]> batchArgs = classIds.stream().map(classId -> new Object[]{courseId, classId}).collect(Collectors.toList());
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
    }

    @Override
    public void deleteMappingsForCourse(Integer courseId) {
        String sql = "DELETE FROM course_class_mapping WHERE course_id = ?";
        jdbcTemplate.update(sql, courseId);
    }

    @Override
    public List<Integer> findClassIdsByCourseId(Integer courseId) {
        String sql = "SELECT class_id FROM course_class_mapping WHERE course_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, courseId);
    }

    @Override
    public List<Integer> findCourseIdsByClassId(Integer classId) {
        String sql = "SELECT course_id FROM course_class_mapping WHERE class_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, classId);
    }
}

