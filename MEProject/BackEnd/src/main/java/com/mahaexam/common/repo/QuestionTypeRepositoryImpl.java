package com.mahaexam.common.repo;

import com.mahaexam.common.model.QuestionType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionTypeRepositoryImpl implements QuestionTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuestionTypeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<QuestionType> ROW_MAPPER = (rs, rowNum) -> QuestionType.builder()
            .id(rs.getInt("id"))
            .tenantId(rs.getObject("tenant_id", Long.class))
            .code(rs.getString("code"))
            .name(rs.getString("name"))
            .description(rs.getString("description"))
            .createdAt(rs.getTimestamp("created_at") != null
                    ? rs.getTimestamp("created_at").toLocalDateTime() : null)
            .build();

    @Override
    public List<QuestionType> findAll(Long tenantId) {
        String sql = "SELECT * FROM question_type WHERE (tenant_id = ? OR tenant_id IS NULL) ORDER BY id";
        return jdbcTemplate.query(sql, ROW_MAPPER, tenantId);
    }

    @Override
    public Optional<QuestionType> findById(int id) {
        String sql = "SELECT * FROM question_type WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<QuestionType> findByCode(String code, Long tenantId) {
        String sql = "SELECT * FROM question_type WHERE code = ? AND (tenant_id = ? OR tenant_id IS NULL) LIMIT 1";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, code, tenantId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<QuestionType> findByBoardAndSubject(int boardId, int subjectId, Long tenantId) {
        String sql = "SELECT qt.* FROM question_type qt " +
                     "INNER JOIN board_subject_question_type_mapping m ON m.question_type_id = qt.id " +
                     "WHERE m.board_id = ? AND m.subject_id = ? AND m.deleted = '0' " +
                     "AND (m.tenant_id = ? OR m.tenant_id IS NULL) " +
                     "AND (qt.tenant_id = ? OR qt.tenant_id IS NULL) " +
                     "ORDER BY qt.id";
        return jdbcTemplate.query(sql, ROW_MAPPER, boardId, subjectId, tenantId, tenantId);
    }

    @Override
    public int save(QuestionType qt) {
        String sql = "INSERT INTO question_type (tenant_id, code, name, description) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, qt.getTenantId(), qt.getCode(), qt.getName(), qt.getDescription());
    }

    @Override
    public int update(QuestionType qt) {
        String sql = "UPDATE question_type SET code = ?, name = ?, description = ? WHERE id = ? AND (tenant_id = ? OR tenant_id IS NULL)";
        return jdbcTemplate.update(sql, qt.getCode(), qt.getName(), qt.getDescription(), qt.getId(), qt.getTenantId());
    }

    @Override
    public int deleteById(int id, Long tenantId) {
        String sql = "DELETE FROM question_type WHERE id = ? AND (tenant_id = ? OR tenant_id IS NULL)";
        return jdbcTemplate.update(sql, id, tenantId);
    }

    @Override
    public boolean existsByCode(String code, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM question_type WHERE code = ? AND (tenant_id = ? OR tenant_id IS NULL)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCodeExceptId(String code, int excludeId, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM question_type WHERE code = ? AND id <> ? AND (tenant_id = ? OR tenant_id IS NULL)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code, excludeId, tenantId);
        return count != null && count > 0;
    }
}
