package com.mahaexam.common.repo;

import com.mahaexam.common.model.BoardSubjectQuestionTypeMapping;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardSubjectQuestionTypeMappingRepositoryImpl implements BoardSubjectQuestionTypeMappingRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardSubjectQuestionTypeMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<BoardSubjectQuestionTypeMapping> ROW_MAPPER = (rs, rowNum) ->
            BoardSubjectQuestionTypeMapping.builder()
                    .id(rs.getInt("id"))
                    .tenantId(rs.getObject("tenant_id", Long.class))
                    .boardId(rs.getInt("board_id"))
                    .subjectId(rs.getInt("subject_id"))
                    .questionTypeId(rs.getInt("question_type_id"))
                    .createdBy(rs.getObject("created_by", Integer.class))
                    .createdAt(rs.getTimestamp("created_at") != null
                            ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                    .updatedBy(rs.getObject("updated_by", Integer.class))
                    .updatedAt(rs.getTimestamp("updated_at") != null
                            ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                    .deletedAt(rs.getTimestamp("deleted_at") != null
                            ? rs.getTimestamp("deleted_at").toLocalDateTime() : null)
                    .deleted(rs.getString("deleted"))
                    .boardName(getStringSafe(rs, "board_name"))
                    .subjectName(getStringSafe(rs, "subject_name"))
                    .questionTypeCode(getStringSafe(rs, "qt_code"))
                    .questionTypeName(getStringSafe(rs, "qt_name"))
                    .build();

    private static String getStringSafe(java.sql.ResultSet rs, String col) {
        try { return rs.getString(col); } catch (Exception e) { return null; }
    }

    private static final String SELECT_WITH_JOINS =
            "SELECT m.*, b.board_name, s.subject_name, " +
            "qt.code AS qt_code, qt.name AS qt_name " +
            "FROM board_subject_question_type_mapping m " +
            "JOIN board b ON b.id = m.board_id " +
            "JOIN subject s ON s.subject_id = m.subject_id " +
            "JOIN question_type qt ON qt.id = m.question_type_id " +
            "WHERE m.deleted = '0'";

    @Override
    public List<BoardSubjectQuestionTypeMapping> findAll(Long tenantId) {
        String sql = SELECT_WITH_JOINS + " AND (m.tenant_id = ? OR m.tenant_id IS NULL) ORDER BY m.id";
        return jdbcTemplate.query(sql, ROW_MAPPER, tenantId);
    }

    @Override
    public Optional<BoardSubjectQuestionTypeMapping> findById(int id) {
        String sql = SELECT_WITH_JOINS + " AND m.id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<BoardSubjectQuestionTypeMapping> findByBoardId(int boardId, Long tenantId) {
        String sql = SELECT_WITH_JOINS + " AND m.board_id = ? AND (m.tenant_id = ? OR m.tenant_id IS NULL) ORDER BY m.id";
        return jdbcTemplate.query(sql, ROW_MAPPER, boardId, tenantId);
    }

    @Override
    public List<BoardSubjectQuestionTypeMapping> findByBoardAndSubject(int boardId, int subjectId, Long tenantId) {
        String sql = SELECT_WITH_JOINS + " AND m.board_id = ? AND m.subject_id = ? AND (m.tenant_id = ? OR m.tenant_id IS NULL) ORDER BY m.id";
        return jdbcTemplate.query(sql, ROW_MAPPER, boardId, subjectId, tenantId);
    }

    @Override
    public List<BoardSubjectQuestionTypeMapping> findQuestionTypesByBoardAndSubject(int boardId, int subjectId, Long tenantId) {
        return findByBoardAndSubject(boardId, subjectId, tenantId);
    }

    @Override
    public int save(BoardSubjectQuestionTypeMapping m) {
        String sql = "INSERT INTO board_subject_question_type_mapping " +
                     "(tenant_id, board_id, subject_id, question_type_id, created_by) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, m.getTenantId(), m.getBoardId(), m.getSubjectId(),
                m.getQuestionTypeId(), m.getCreatedBy());
    }

    @Override
    public int softDelete(int id) {
        String sql = "UPDATE board_subject_question_type_mapping " +
                     "SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByBoardSubjectAndQuestionType(int boardId, int subjectId, int questionTypeId, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM board_subject_question_type_mapping " +
                     "WHERE board_id = ? AND subject_id = ? AND question_type_id = ? " +
                     "AND (tenant_id = ? OR tenant_id IS NULL) AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, boardId, subjectId, questionTypeId, tenantId);
        return count != null && count > 0;
    }
}
