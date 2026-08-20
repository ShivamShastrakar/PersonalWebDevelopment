package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.StudentCourse;

@Repository
public class StudentCourseRepositoryImpl implements StudentCourseRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentCourseRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 2000;

    public StudentCourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StudentCourse save(StudentCourse studentCourse) {
    	String sql = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";

    	KeyHolder keyHolder = new GeneratedKeyHolder();

    	jdbcTemplate.update(connection -> {
    	    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    	    ps.setLong(1, studentCourse.getStudentId());
    	    ps.setLong(2, studentCourse.getCourseId());
    	    return ps;
    	}, keyHolder);

    	// Retrieve and set the generated ID
    	Number key = keyHolder.getKey();
    	if (key != null) {
    	    studentCourse.setId(key.longValue());
    	}

    	return studentCourse;

    }
    
    @Override
    public void save(Long studentId, List<Long> courseIds) {
        if (studentId == null) {
            logger.warn("studentId is null, skipping save");
            return;
        }
        if (courseIds == null || courseIds.isEmpty()) {
            logger.warn("courseIds is null or empty, skipping save");
            return;
        }

        String sql = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
        List<Object[]> batchArgs = courseIds.stream()
                .map(courseId -> new Object[]{studentId, courseId})
                .collect(Collectors.toList());

        try {
            int[] rowsAffected = jdbcTemplate.batchUpdate(sql, batchArgs);
            logger.info("Inserted {} student_course records for studentId: {}", rowsAffected.length, studentId);
        } catch (Exception e) {
            logger.error("Failed to insert student_course records for studentId: {}", studentId, e);
            throw new RuntimeException("Failed to save student courses: " + e.getMessage(), e);
        }
    }
    @Override
    public Optional<StudentCourse> findById(Long id) {
        String sql = "SELECT * FROM student_course WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudentCourseRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<StudentCourse> findAll() {
        String sql = "SELECT * FROM student_course";
        return jdbcTemplate.query(sql, new StudentCourseRowMapper());
    }

    @Override
    public StudentCourse update(StudentCourse studentCourse) {
        String sql = "UPDATE student_course SET student_id = ?, course_id = ? WHERE id = ?";
        
        jdbcTemplate.update(sql,
            studentCourse.getStudentId(),
            studentCourse.getCourseId(),
            studentCourse.getId()
        );
        return studentCourse;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM student_course WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public void deleteStudentId(Long studentId) {
        String sql = "DELETE FROM student_course WHERE student_id = ?";
        jdbcTemplate.update(sql, studentId);
    }
    
    
    @Override
    public List<StudentCourse> findByStudentId(Long studentId) {
        String sql = """
                SELECT sc.id, sc.student_id, sc.course_id, c.course_name, c.course_details
                FROM student_course sc
                INNER JOIN course c ON sc.course_id = c.id
                WHERE sc.student_id  = ?
                """;;
        return jdbcTemplate.query(sql, new StudentCourseRowMapper(), studentId);
    }
    
    @Override
    public List<StudentCourse> findByStudentIds(List<Long> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            logger.warn("studentIds is null or empty, returning empty list");
            return Collections.emptyList();
        }

        List<Long> validStudentIds = studentIds.stream()
                .filter(id -> id != null)
                .distinct()
                .toList();

        if (validStudentIds.isEmpty()) {
            logger.warn("No valid student IDs provided, returning empty list");
            return Collections.emptyList();
        }

        String sqlTemplate = """
                SELECT sc.id, sc.student_id, sc.course_id, c.course_name, c.course_details
                FROM student_course sc
                INNER JOIN course c ON sc.course_id = c.id
                WHERE student_id IN (%s)
                """;

        List<StudentCourse> allStudentCourses = new ArrayList<>();
        List<List<Long>> batches = IntStream.range(0, (validStudentIds.size() + BATCH_SIZE - 1) / BATCH_SIZE)
                .mapToObj(i -> validStudentIds.subList(
                        i * BATCH_SIZE,
                        Math.min((i + 1) * BATCH_SIZE, validStudentIds.size())))
                .toList();

        for (int i = 0; i < batches.size(); i++) {
            List<Long> batchIds = batches.get(i);
            String placeholders = String.join(",", Collections.nCopies(batchIds.size(), "?"));
            String batchSql = sqlTemplate.formatted(placeholders);

            try {
                List<StudentCourse> batchResults = jdbcTemplate.query(
                        batchSql,
                        new StudentCourseRowMapper(),
                        batchIds.toArray()
                );
                allStudentCourses.addAll(batchResults);
                logger.info("Fetched {} student_course records for batch {}/{} ({} studentIds)",
                        batchResults.size(), i + 1, batches.size(), batchIds.size());
            } catch (Exception e) {
                logger.error("Failed to fetch student_course records for batch {}: {}", i + 1, e.getMessage(), e);
                throw new RuntimeException("Failed to fetch student courses for batch " + (i + 1), e);
            }
        }

        logger.info("Total fetched {} student_course records for {} studentIds",
                allStudentCourses.size(), validStudentIds.size());
        return allStudentCourses;
    }
}