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

import com.mahaexam.tenant.management.model.Parent;

@Repository
public class ParentRepositoryImpl implements ParentRepository {
	private static final Logger logger = LoggerFactory.getLogger(ParentRepositoryImpl.class);
	private final JdbcTemplate jdbcTemplate;
	private static final int BATCH_SIZE = 2000;

	public ParentRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Parent save(Parent parent) {
		String sql = "INSERT INTO parent (father_name, father_mobile_number, father_occupation, mother_name, mother_mobile_number, mother_occupation, number_of_siblings, first_sibling_name, first_sibling_std, second_sibling_name, second_sibling_std, parents_yearly_income) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, parent.getFatherName());
			ps.setString(2, parent.getFatherMobileNumber());
			ps.setString(3, parent.getFatherOccupation());
			ps.setString(4, parent.getMotherName());
			ps.setString(5, parent.getMotherMobileNumber());
			ps.setString(6, parent.getMotherOccupation());
			ps.setInt(7, parent.getNumberOfSiblings());
			ps.setString(8, parent.getFirstSiblingName());
			ps.setString(9, parent.getFirstSiblingStd());
			ps.setString(10, parent.getSecondSiblingName());
			ps.setString(11, parent.getSecondSiblingStd());
			ps.setString(12, parent.getParentsYearlyIncome());
			return ps;
		}, keyHolder);
		Long parentId = keyHolder.getKey().longValue();
		parent.setParentId(parentId);
		logger.info("Saved parent with ID: {}", parentId);
		return parent;
	}

	@Override
	public Optional<Parent> findById(Long parentId) {
		String sql = "SELECT * FROM parent WHERE parent_id = ?";
		try {
			Parent parent = jdbcTemplate.queryForObject(sql, new ParentRowMapper(), parentId);
			logger.info("Found parent with ID: {}", parentId);
			return Optional.ofNullable(parent);
		} catch (Exception e) {
			logger.warn("Parent not found with ID: {}", parentId);
			return Optional.empty();
		}
	}

	@Override
	public Optional<Parent> findByStudentId(Long studentId) {
		String sql = """
				   		SELECT p.*,s.student_id FROM parent p
				inner join student s on p.parent_id =s.parent_id
				where s.student_id = ?
				   		""";
		try {
			Parent parent = jdbcTemplate.queryForObject(sql, new ParentRowMapper(), studentId);
			return Optional.ofNullable(parent);
		} catch (Exception e) {
			logger.warn("Parent not found with for studentIdID: {}", studentId);
			return Optional.empty();
		}
	}

	@Override
	public List<Parent> findByStudentIds(List<Long> studentIds) {
		if (studentIds == null || studentIds.isEmpty()) {
			logger.warn("studentIds is null or empty, returning empty list");
			return Collections.emptyList();
		}

		List<Long> validStudentIds = studentIds.stream().filter(id -> id != null).distinct().toList();

		if (validStudentIds.isEmpty()) {
			logger.warn("No valid student IDs provided, returning empty list");
			return Collections.emptyList();
		}

		String sqlTemplate = """
				SELECT p.*,s.student_id FROM parent p
				INNER JOIN student s ON p.parent_id = s.parent_id
				WHERE s.student_id IN (%s)
				""";

		List<Parent> allParents = new ArrayList<>();
		List<List<Long>> batches = IntStream.range(0, (validStudentIds.size() + BATCH_SIZE - 1) / BATCH_SIZE).mapToObj(
				i -> validStudentIds.subList(i * BATCH_SIZE, Math.min((i + 1) * BATCH_SIZE, validStudentIds.size())))
				.toList();

		for (int i = 0; i < batches.size(); i++) {
			List<Long> batchIds = batches.get(i);
			String placeholders = String.join(",", Collections.nCopies(batchIds.size(), "?"));
			String batchSql = sqlTemplate.formatted(placeholders);

			try {
				List<Parent> batchResults = jdbcTemplate.query(batchSql, batchIds.toArray(), new ParentRowMapper());
				allParents.addAll(batchResults);
				logger.info("Fetched {} parent records for batch {}/{} ({} studentIds)", batchResults.size(), i + 1,
						batches.size(), batchIds.size());
			} catch (Exception e) {
				logger.error("Failed to fetch parent records for batch {}: {}", i + 1, e.getMessage(), e);
				throw new RuntimeException("Failed to fetch parents for batch " + (i + 1), e);
			}
		}

		logger.info("Total fetched {} parent records for {} studentIds", allParents.size(), validStudentIds.size());
		return allParents;
	}

	@Override
	public List<Parent> findAll() {
		String sql = "SELECT * FROM parent";
		List<Parent> parents = jdbcTemplate.query(sql, new ParentRowMapper());
		logger.info("Retrieved {} parents", parents.size());
		return parents;
	}

	@Override
	public void update(Parent parent) {
		String sql = "UPDATE parent SET father_name = ?, father_mobile_number = ?, father_occupation = ?, mother_name = ?, mother_mobile_number = ?, mother_occupation = ?, number_of_siblings = ?, first_sibling_name = ?, first_sibling_std = ?, second_sibling_name = ?, second_sibling_std = ?, parents_yearly_income = ? WHERE parent_id = ?";
		int rowsAffected = jdbcTemplate.update(sql, parent.getFatherName(), parent.getFatherMobileNumber(),
				parent.getFatherOccupation(), parent.getMotherName(), parent.getMotherMobileNumber(),
				parent.getMotherOccupation(), parent.getNumberOfSiblings(), parent.getFirstSiblingName(),
				parent.getFirstSiblingStd(), parent.getSecondSiblingName(), parent.getSecondSiblingStd(),
				parent.getParentsYearlyIncome(), parent.getParentId());
		logger.info("Updated parent with ID: {}, rows affected: {}", parent.getParentId(), rowsAffected);
	}

	@Override
	public void delete(Long parentId) {
		String sql = "DELETE FROM parent WHERE parent_id = ?";
		int rowsAffected = jdbcTemplate.update(sql, parentId);
		logger.info("Deleted parent with ID: {}, rows affected: {}", parentId, rowsAffected);
	}
}
