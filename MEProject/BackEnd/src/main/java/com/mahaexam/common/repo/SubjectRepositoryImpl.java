package com.mahaexam.common.repo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.Subject;

@Repository
public class SubjectRepositoryImpl implements SubjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubjectRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Subject> findAllByTenant(Long tenantId) {
        String sql = """
                SELECT s.* FROM subject s
                WHERE (s.tenant_id = ? OR s.tenant_id IS NULL) AND s.deleted = '0'
                """;
        return jdbcTemplate.query(sql, new SubjectRowMapper(), tenantId);
    }

    @Override
    public Subject findById(int id) {
        String sql = """
                SELECT s.* FROM subject s
                WHERE subject_id = ? AND deleted = '0'
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new SubjectRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int save(Subject subject) {
        String sql = "INSERT INTO subject (tenant_id, subject_name) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, subject.getTenantId());
            ps.setString(2, subject.getSubjectName());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : 0;
    }

    @Override
    public int update(Subject subject) {
        String sql = "UPDATE subject SET subject_name = ?, updated_at = CURRENT_TIMESTAMP WHERE subject_id = ?";
        return jdbcTemplate.update(sql, subject.getSubjectName(), subject.getSubjectId());
    }

    @Override
    public int softDelete(int id) {
        String sql = "UPDATE subject SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE subject_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsBySubjectNameAndTenantId(String subjectName, Long tenantId) {
        String sql = """
                SELECT COUNT(*) FROM subject
                WHERE subject_name = ? AND (tenant_id = ? OR tenant_id IS NULL) AND deleted = '0'
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, subjectName, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsBySubjectNameAndTenantIdExceptId(String subjectName, Long tenantId, int excludeId) {
        String sql = """
                SELECT COUNT(*) FROM subject
                WHERE subject_name = ? AND (tenant_id = ? OR tenant_id IS NULL) AND subject_id != ? AND deleted = '0'
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, subjectName, tenantId, excludeId);
        return count != null && count > 0;
    }

    @Override
    public List<Subject> findByBoardAndClass(Integer boardId, Integer classId, Long tenantId) {
        String sql = """
                SELECT DISTINCT s.* FROM subject s
                INNER JOIN subject_board_class_mapping sbcm ON s.subject_id = sbcm.subject_id
                WHERE sbcm.board_id = ? AND sbcm.class_id = ?
                AND (s.tenant_id = ? OR s.tenant_id IS NULL) AND s.deleted = '0'
                ORDER BY s.subject_name
                """;
        return jdbcTemplate.query(sql, new SubjectRowMapper(), boardId, classId, tenantId);
    }

    @Override
    public List<Subject> findByBoardAndClassAndMedium(Integer boardId, Integer classId, String medium, Long tenantId) {
        String sql = """
                SELECT DISTINCT s.* FROM subject s
                INNER JOIN subject_board_class_mapping sbcm ON s.subject_id = sbcm.subject_id
                WHERE sbcm.board_id = ? AND sbcm.class_id = ? AND sbcm.medium = ?
                AND (s.tenant_id = ? OR s.tenant_id IS NULL) AND s.deleted = '0'
                ORDER BY s.subject_name
                """;
        return jdbcTemplate.query(sql, new SubjectRowMapper(), boardId, classId, medium, tenantId);
    }

}