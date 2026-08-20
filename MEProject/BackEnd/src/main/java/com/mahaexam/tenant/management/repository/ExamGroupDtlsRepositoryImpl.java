package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.ExamGroupDtls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ExamGroupDtlsRepositoryImpl implements ExamGroupDtlsRepository {
    private static final Logger logger = LoggerFactory.getLogger(ExamGroupDtlsRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ExamGroupDtlsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ExamGroupDtls save(ExamGroupDtls examGroupDtls) {
        String sql = "INSERT INTO exam_group_dtls (name, description) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, examGroupDtls.getName());
            ps.setString(2, examGroupDtls.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            examGroupDtls.setId(key.intValue());
        }

        logger.info("ExamGroupDtls saved with ID: {}", examGroupDtls.getId());
        return examGroupDtls;
    }

    @Override
    public Optional<ExamGroupDtls> findById(Integer id) {
        String sql = "SELECT * FROM exam_group_dtls WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ExamGroupDtlsRowMapper(), id));
        } catch (Exception e) {
            logger.warn("ExamGroupDtls not found with ID: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<ExamGroupDtls> findAll() {
        String sql = "SELECT * FROM exam_group_dtls ORDER BY id ASC";
        return jdbcTemplate.query(sql, new ExamGroupDtlsRowMapper());
    }

    @Override
    public ExamGroupDtls update(ExamGroupDtls examGroupDtls) {
        String sql = "UPDATE exam_group_dtls SET name = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, examGroupDtls.getName(), examGroupDtls.getDescription(), examGroupDtls.getId());
        logger.info("ExamGroupDtls updated with ID: {}", examGroupDtls.getId());
        return examGroupDtls;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM exam_group_dtls WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("ExamGroupDtls deleted with ID: {}", id);
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM exam_group_dtls WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
