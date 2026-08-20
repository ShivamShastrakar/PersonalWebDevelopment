package com.mahaexam.common.repo;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.Board;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Board> findAllByTenant(Long tenantId) {
        String sql = "SELECT * FROM board WHERE (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
        return jdbcTemplate.query(sql, new BoardRowMapper(), tenantId);
    }

    @Override
    public Board findById(int id) {
        String sql = "SELECT * FROM board WHERE id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new BoardRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public int save(Board board) {
        String sql = "INSERT INTO board (tenant_id, board_name) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, board.getTenantId());
            ps.setString(2, board.getBoardName());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : 0;
    }

    @Override
    public int update(Board board) {
        String sql = "UPDATE board SET board_name = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, board.getBoardName(), board.getId());
    }

    @Override
    public int softDelete(int id) {
        String sql = "UPDATE board SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByBoardNameAndTenantId(String boardName, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM board WHERE board_name = ? AND (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, boardName, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByBoardNameAndTenantIdExceptId(String boardName, Long tenantId, int excludeId) {
        String sql = "SELECT COUNT(*) FROM board WHERE board_name = ? AND (tenant_id = ? OR tenant_id is null) AND id != ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, boardName, tenantId, excludeId);
        return count != null && count > 0;
    }

    @Override
    public List<Board> findAllByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        String inClause = ids.stream().map(Object::toString).collect(Collectors.joining(","));
        String sql = "SELECT * FROM board WHERE id IN (" + inClause + ") AND deleted = '0'";
        return jdbcTemplate.query(sql, new BoardRowMapper());
    }

}
