package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.TempStudent;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class TempStudentRepositoryImpl implements TempStudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public TempStudentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TempStudent save(TempStudent tempStudent) {
        if (tempStudent.getId() == null) {
            return insert(tempStudent);
        } else {
            return update(tempStudent);
        }
    }

    private TempStudent insert(TempStudent tempStudent) {
        String sql = """
            INSERT INTO temp_students 
            (batch_id, last_name, first_name, middle_name, adhar_no, mobile_number, email, 
             class_name, class_id, exam_group, courses, course_ids, subject_group_id, 
             target_final_exam_year, package_id, reference_id, error_message, medium) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, tempStudent.getBatchId());
            ps.setString(2, tempStudent.getLastName());
            ps.setString(3, tempStudent.getFirstName());
            ps.setString(4, tempStudent.getMiddleName());
            ps.setString(5, tempStudent.getAdharNo());
            ps.setString(6, tempStudent.getMobileNumber());
            ps.setString(7, tempStudent.getEmail());
            ps.setString(8, tempStudent.getClassName());
            ps.setObject(9, tempStudent.getClassId());
            ps.setString(10, tempStudent.getExamGroup());
            ps.setString(11, tempStudent.getCourses());
            ps.setString(12, tempStudent.getCourseIds());
            ps.setObject(13, tempStudent.getSubjectGroupId());
            ps.setObject(14, tempStudent.getTargetFinalExamYear());
            ps.setObject(15, tempStudent.getPackageId());
            ps.setObject(16, tempStudent.getReferenceId());
            ps.setString(17, tempStudent.getErrorMessage());
            ps.setString(18, tempStudent.getMedium());
            return ps;
        }, keyHolder);

        tempStudent.setId(keyHolder.getKey().longValue());
        return tempStudent;
    }

    private TempStudent update(TempStudent tempStudent) {
        String sql = """
            UPDATE temp_students SET 
                last_name = ?, first_name = ?, middle_name = ?, adhar_no = ?, 
                mobile_number = ?, email = ?, class_name = ?, class_id = ?, 
                exam_group = ?, courses = ?, course_ids = ?, subject_group_id = ?, 
                target_final_exam_year = ?, package_id = ?, reference_id = ?, 
                error_message = ?, medium = ?, updated_at = ? 
            WHERE id = ?
        """;

        jdbcTemplate.update(sql,
            tempStudent.getLastName(),
            tempStudent.getFirstName(),
            tempStudent.getMiddleName(),
            tempStudent.getAdharNo(),
            tempStudent.getMobileNumber(),
            tempStudent.getEmail(),
            tempStudent.getClassName(),
            tempStudent.getClassId(),
            tempStudent.getExamGroup(),
            tempStudent.getCourses(),
            tempStudent.getCourseIds(),
            tempStudent.getSubjectGroupId(),
            tempStudent.getTargetFinalExamYear(),
            tempStudent.getPackageId(),
            tempStudent.getReferenceId(),
            tempStudent.getErrorMessage(),
            tempStudent.getMedium(),
            Timestamp.valueOf(LocalDateTime.now()),
            tempStudent.getId()
        );

        return tempStudent;
    }

    @Override
    public List<TempStudent> saveAll(List<TempStudent> tempStudents) {
        String sql = """
            INSERT INTO temp_students 
            (batch_id, last_name, first_name, middle_name, adhar_no, mobile_number, email, 
             class_name, class_id, exam_group, courses, course_ids, subject_group_id, 
             target_final_exam_year, package_id, reference_id, error_message, medium) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.batchUpdate(sql, tempStudents, tempStudents.size(), (ps, tempStudent) -> {
            ps.setObject(1, tempStudent.getBatchId());
            ps.setString(2, tempStudent.getLastName());
            ps.setString(3, tempStudent.getFirstName());
            ps.setString(4, tempStudent.getMiddleName());
            ps.setString(5, tempStudent.getAdharNo());
            ps.setString(6, tempStudent.getMobileNumber());
            ps.setString(7, tempStudent.getEmail());
            ps.setString(8, tempStudent.getClassName());
            ps.setObject(9, tempStudent.getClassId());
            ps.setString(10, tempStudent.getExamGroup());
            ps.setString(11, tempStudent.getCourses());
            ps.setString(12, tempStudent.getCourseIds());
            ps.setObject(13, tempStudent.getSubjectGroupId());
            ps.setObject(14, tempStudent.getTargetFinalExamYear());
            ps.setObject(15, tempStudent.getPackageId());
            ps.setObject(16, tempStudent.getReferenceId());
            ps.setString(17, tempStudent.getErrorMessage());
            ps.setString(18, tempStudent.getMedium());
        });

        return tempStudents;
    }

    @Override
    public Optional<TempStudent> findById(Long id) {
        String sql = "SELECT * FROM temp_students WHERE id = ?";

        try {
            TempStudent tempStudent = jdbcTemplate.queryForObject(sql, new TempStudentRowMapper(), id);
            return Optional.of(tempStudent);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<TempStudent> findByReferenceId(Long referenceId) {
        String sql = "SELECT * FROM temp_students WHERE reference_id = ? ORDER BY id";
        return jdbcTemplate.query(sql, new TempStudentRowMapper(), referenceId);
    }

    @Override
    public List<TempStudent> findByPackageId(Integer packageId) {
        String sql = "SELECT * FROM temp_students WHERE package_id = ? ORDER BY id";
        return jdbcTemplate.query(sql, new TempStudentRowMapper(), packageId);
    }

    @Override
    public List<TempStudent> findByBatchId(Long batchId) {
        String sql = "SELECT * FROM temp_students WHERE batch_id = ? ORDER BY id";
        return jdbcTemplate.query(sql, new TempStudentRowMapper(), batchId);
    }

    @Override
    public List<TempStudent> findValidStudents(Long referenceId) {
        String sql = "SELECT * FROM temp_students WHERE reference_id = ? AND (error_message IS NULL OR error_message = '') ORDER BY id";
        return jdbcTemplate.query(sql, new TempStudentRowMapper(), referenceId);
    }

    @Override
    public List<TempStudent> findInvalidStudents(Long referenceId) {
        String sql = "SELECT * FROM temp_students WHERE reference_id = ? AND error_message IS NOT NULL AND error_message != '' ORDER BY id";
        return jdbcTemplate.query(sql, new TempStudentRowMapper(), referenceId);
    }

    @Override
    public List<TempStudent> findValidStudentsByBatchId(Long batchId) {
        String sql = "SELECT * FROM temp_students WHERE batch_id = ? AND (error_message IS NULL OR error_message = '') ORDER BY id";
        return jdbcTemplate.query(sql, new TempStudentRowMapper(), batchId);
    }

    @Override
    public List<TempStudent> findInvalidStudentsByBatchId(Long batchId) {
        String sql = "SELECT * FROM temp_students WHERE batch_id = ? AND error_message IS NOT NULL AND error_message != '' ORDER BY id";
        return jdbcTemplate.query(sql, new TempStudentRowMapper(), batchId);
    }

    @Override
    public void deleteByReferenceId(Long referenceId) {
        String sql = "DELETE FROM temp_students WHERE reference_id = ?";
        jdbcTemplate.update(sql, referenceId);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM temp_students WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteByBatchId(Long batchId) {
        String sql = "DELETE FROM temp_students WHERE batch_id = ?";
        jdbcTemplate.update(sql, batchId);
    }

    @Override
    public int countByReferenceId(Long referenceId) {
        String sql = "SELECT COUNT(*) FROM temp_students WHERE reference_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, referenceId);
    }

    @Override
    public int countValidByReferenceId(Long referenceId) {
        String sql = "SELECT COUNT(*) FROM temp_students WHERE reference_id = ? AND (error_message IS NULL OR error_message = '')";
        return jdbcTemplate.queryForObject(sql, Integer.class, referenceId);
    }

    @Override
    public int countInvalidByReferenceId(Long referenceId) {
        String sql = "SELECT COUNT(*) FROM temp_students WHERE reference_id = ? AND error_message IS NOT NULL AND error_message != ''";
        return jdbcTemplate.queryForObject(sql, Integer.class, referenceId);
    }

    @Override
    public int countByBatchId(Long batchId) {
        String sql = "SELECT COUNT(*) FROM temp_students WHERE batch_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, batchId);
    }

    @Override
    public int countValidByBatchId(Long batchId) {
        String sql = "SELECT COUNT(*) FROM temp_students WHERE batch_id = ? AND (error_message IS NULL OR error_message = '')";
        return jdbcTemplate.queryForObject(sql, Integer.class, batchId);
    }

    @Override
    public int countInvalidByBatchId(Long batchId) {
        String sql = "SELECT COUNT(*) FROM temp_students WHERE batch_id = ? AND error_message IS NOT NULL AND error_message != ''";
        return jdbcTemplate.queryForObject(sql, Integer.class, batchId);
    }

    @Override
    public int[] batchInsert(List<TempStudent> tempStudents) {
        if (tempStudents == null || tempStudents.isEmpty()) {
            return new int[0];
        }

        String sql = """
            INSERT INTO temp_students 
            (batch_id, last_name, first_name, middle_name, adhar_no, mobile_number, email, 
             class_name, class_id, exam_group, courses, course_ids, subject_group_id, 
             target_final_exam_year, package_id, reference_id, error_message, medium) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        // For large datasets, process in chunks to avoid memory issues
        int chunkSize = 1000;
        List<Integer> allResults = new ArrayList<>();

        for (int i = 0; i < tempStudents.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, tempStudents.size());
            List<TempStudent> chunk = tempStudents.subList(i, endIndex);

            // Use the simpler batchUpdate that returns int[]
            int[] chunkResult = jdbcTemplate.batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws java.sql.SQLException {
                    TempStudent tempStudent = chunk.get(i);
                    ps.setObject(1, tempStudent.getBatchId());
                    ps.setString(2, tempStudent.getLastName());
                    ps.setString(3, tempStudent.getFirstName());
                    ps.setString(4, tempStudent.getMiddleName());
                    ps.setString(5, tempStudent.getAdharNo());
                    ps.setString(6, tempStudent.getMobileNumber());
                    ps.setString(7, tempStudent.getEmail());
                    ps.setString(8, tempStudent.getClassName());
                    ps.setObject(9, tempStudent.getClassId());
                    ps.setString(10, tempStudent.getExamGroup());
                    ps.setString(11, tempStudent.getCourses());
                    ps.setString(12, tempStudent.getCourseIds());
                    ps.setObject(13, tempStudent.getSubjectGroupId());
                    ps.setObject(14, tempStudent.getTargetFinalExamYear());
                    ps.setObject(15, tempStudent.getPackageId());
                    ps.setObject(16, tempStudent.getReferenceId());
                    ps.setString(17, tempStudent.getErrorMessage());
                    ps.setString(18, tempStudent.getMedium());
                }

                @Override
                public int getBatchSize() {
                    return chunk.size();
                }
            });

            // Add results to the combined list
            for (int result : chunkResult) {
                allResults.add(result);
            }
        }

        // Convert List<Integer> to int[]
        return allResults.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public int[] batchUpdateStudentId(List<TempStudent> updates) {
        if (updates == null || updates.isEmpty()) {
            return new int[0];
        }

        String sql = """
            UPDATE temp_students 
            SET student_id = ?, updated_at = ? 
            WHERE mobile_number = ? AND email = ? and batch_id =?
        """;

        // For large datasets, process in chunks to avoid memory issues
        int chunkSize = 1000;
        List<Integer> allResults = new ArrayList<>();

        for (int i = 0; i < updates.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, updates.size());
            List<TempStudent> chunk = updates.subList(i, endIndex);

            int[] chunkResult = jdbcTemplate.batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws java.sql.SQLException {
                    TempStudent tempStudent = chunk.get(i);
                    ps.setObject(1, tempStudent.getStudentId());
                    ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(3, tempStudent.getMobileNumber());
                    ps.setString(4, tempStudent.getEmail());
                    ps.setObject(5, tempStudent.getBatchId());
                }

                @Override
                public int getBatchSize() {
                    return chunk.size();
                }
            });

            // Add results to the combined list
            for (int result : chunkResult) {
                allResults.add(result);
            }
        }

        // Convert List<Integer> to int[]
        return allResults.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public List<TempStudent> findStudentsByBatchIds(List<Long> batchIds) {
        if (batchIds == null || batchIds.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        // Create placeholders for IN clause
        String inSql = batchIds.stream().map(b -> "?").collect(java.util.stream.Collectors.joining(","));
        String sql = "SELECT * FROM temp_students WHERE batch_id IN (" + inSql + ")  ORDER BY id";
        Object[] params = batchIds.toArray();
        return jdbcTemplate.query(sql, params, new TempStudentRowMapper());
    }
}
