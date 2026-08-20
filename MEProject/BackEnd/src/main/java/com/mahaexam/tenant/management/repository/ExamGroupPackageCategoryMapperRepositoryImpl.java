package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.ExamGroupPackageCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class ExamGroupPackageCategoryMapperRepositoryImpl implements ExamGroupPackageCategoryMapperRepository {
    private static final Logger logger = LoggerFactory.getLogger(ExamGroupPackageCategoryMapperRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ExamGroupPackageCategoryMapperRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ExamGroupPackageCategoryMapper save(ExamGroupPackageCategoryMapper mapping) {
        String sql = "INSERT INTO exam_group_package_category_mappingdtls (exam_group_id, package_category_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, mapping.getExamGroupId());
            if (mapping.getPackageCategoryId() != null) {
                ps.setInt(2, mapping.getPackageCategoryId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            mapping.setId(key.intValue());
        }

        logger.info("ExamGroupPackageCategoryMapper saved with ID: {}", mapping.getId());
        return mapping;
    }

    @Override
    public Optional<ExamGroupPackageCategoryMapper> findById(Integer id) {
        String sql = "SELECT * FROM exam_group_package_category_mappingdtls WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ExamGroupPackageCategoryMapperRowMapper(), id));
        } catch (Exception e) {
            logger.warn("ExamGroupPackageCategoryMapper not found with ID: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<ExamGroupPackageCategoryMapper> findAll() {
        String sql = "SELECT * FROM exam_group_package_category_mappingdtls ORDER BY id ASC";
        return jdbcTemplate.query(sql, new ExamGroupPackageCategoryMapperRowMapper());
    }

    @Override
    public List<ExamGroupPackageCategoryMapper> findByExamGroupId(Integer examGroupId) {
        String sql = "SELECT * FROM exam_group_package_category_mappingdtls WHERE exam_group_id = ? ORDER BY id ASC";
        return jdbcTemplate.query(sql, new ExamGroupPackageCategoryMapperRowMapper(), examGroupId);
    }

    @Override
    public List<ExamGroupPackageCategoryMapper> findByPackageCategoryId(Integer packageCategoryId) {
        String sql = "SELECT * FROM exam_group_package_category_mappingdtls WHERE package_category_id = ? ORDER BY id ASC";
        return jdbcTemplate.query(sql, new ExamGroupPackageCategoryMapperRowMapper(), packageCategoryId);
    }

    @Override
    public ExamGroupPackageCategoryMapper update(ExamGroupPackageCategoryMapper mapping) {
        String sql = "UPDATE exam_group_package_category_mappingdtls SET exam_group_id = ?, package_category_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, mapping.getExamGroupId(), mapping.getPackageCategoryId(), mapping.getId());
        logger.info("ExamGroupPackageCategoryMapper updated with ID: {}", mapping.getId());
        return mapping;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM exam_group_package_category_mappingdtls WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("ExamGroupPackageCategoryMapper deleted with ID: {}", id);
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM exam_group_package_category_mappingdtls WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
