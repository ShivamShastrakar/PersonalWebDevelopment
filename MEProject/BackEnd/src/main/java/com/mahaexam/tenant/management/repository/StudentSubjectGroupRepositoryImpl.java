package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.StudentSubjectGroup;

@Repository
public class StudentSubjectGroupRepositoryImpl implements StudentSubjectGroupRepository {
	private static final Logger logger = LoggerFactory.getLogger(StudentSubjectGroupRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 2000;

	public StudentSubjectGroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public StudentSubjectGroup save(StudentSubjectGroup studentSubjectGroup) {
		String sql = "INSERT INTO student_subject_group (student_id, subject_group_id) VALUES (?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, studentSubjectGroup.getStudentId());
			ps.setLong(2, studentSubjectGroup.getSubjectGroupId());
			return ps;
		}, keyHolder);

		// Retrieve and set the generated ID
		Number key = keyHolder.getKey();
		if (key != null) {
			studentSubjectGroup.setId(key.longValue());
		}

		return studentSubjectGroup;

	}

	@Override
	public Optional<StudentSubjectGroup> findById(Long id) {
		String sql = "SELECT * FROM student_subject_group WHERE id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudentSubjectGroupRowMapper(), id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<StudentSubjectGroup> findAll() {
		String sql = "SELECT * FROM student_subject_group";
		return jdbcTemplate.query(sql, new StudentSubjectGroupRowMapper());
	}

	@Override
	public StudentSubjectGroup update(StudentSubjectGroup studentSubjectGroup) {
		String sql = "UPDATE student_subject_group SET student_id = ?, subject_group_id = ? WHERE id = ?";

		jdbcTemplate.update(sql, studentSubjectGroup.getStudentId(), studentSubjectGroup.getSubjectGroupId(),
				studentSubjectGroup.getId());
		return studentSubjectGroup;
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM student_subject_group WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	@Override
	public void deleteStudentId(Long studentId) {
		String sql = "DELETE FROM student_subject_group WHERE student_id = ?";
		jdbcTemplate.update(sql, studentId);
	}

	@Override
	public List<StudentSubjectGroup> findByStudentId(Long studentId) {
		String sql = """
				 SELECT ssg.id, ssg.student_id, ssg.subject_group_id, sg.group_name, sg.description
                FROM student_subject_group ssg
                INNER JOIN subject_group sg ON sg.group_id = ssg.subject_group_id
                WHERE student_id  = ?
				""";
		return jdbcTemplate.query(sql, new StudentSubjectGroupRowMapper(), studentId);
	}
	
	@Override
    public List<StudentSubjectGroup> findByStudentIds(List<Long> studentIds) {
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
                SELECT ssg.id, ssg.student_id, ssg.subject_group_id, sg.group_name, sg.description
                FROM student_subject_group ssg
                INNER JOIN subject_group sg ON sg.group_id = ssg.subject_group_id
                WHERE student_id IN (%s)
                """;

        List<StudentSubjectGroup> allStudentSubjectGroups = new ArrayList<>();
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
                List<StudentSubjectGroup> batchResults = jdbcTemplate.query(
                        batchSql,
                        batchIds.toArray(),
                        new StudentSubjectGroupRowMapper()
                );
                allStudentSubjectGroups.addAll(batchResults);
                logger.info("Fetched {} student_subject_group records for batch {}/{} ({} studentIds)",
                        batchResults.size(), i + 1, batches.size(), batchIds.size());
            } catch (Exception e) {
                logger.error("Failed to fetch student_subject_group records for batch {}: {}", i + 1, e.getMessage(), e);
                throw new RuntimeException("Failed to fetch student subject groups for batch " + (i + 1), e);
            }
        }

        logger.info("Total fetched {} student_subject_group records for {} studentIds",
                allStudentSubjectGroups.size(), validStudentIds.size());
        return allStudentSubjectGroups;
    }

}