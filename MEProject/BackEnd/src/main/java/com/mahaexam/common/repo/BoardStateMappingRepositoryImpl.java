package com.mahaexam.common.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
public class BoardStateMappingRepositoryImpl implements BoardStateMappingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BoardStateMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveMappingsForBoard(Integer boardId, List<Integer> stateIds) {
        deleteMappingsForBoard(boardId);
        if (stateIds != null && !stateIds.isEmpty()) {
            String sql = "INSERT INTO board_state_mapping (board_id, state_id) VALUES (?, ?)";
            List<Object[]> batchArgs = stateIds.stream().map(stateId -> new Object[]{boardId, stateId}).collect(Collectors.toList());
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
    }

    @Override
    public void deleteMappingsForBoard(Integer boardId) {
        String sql = "DELETE FROM board_state_mapping WHERE board_id = ?";
        jdbcTemplate.update(sql, boardId);
    }

    @Override
    public List<Integer> findStateIdsByBoardId(Integer boardId) {
        String sql = "SELECT state_id FROM board_state_mapping WHERE board_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, boardId);
    }

    @Override
    public List<Integer> findBoardIdsByStateId(Integer stateId) {
        String sql = "SELECT board_id FROM board_state_mapping WHERE state_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, stateId);
    }

    @Override
    public Map<Integer, List<Integer>> findStateIdsForBoards(List<Integer> boardIds) {
        if (boardIds == null || boardIds.isEmpty()) {
            return new HashMap<>();
        }
        
        String inClause = boardIds.stream().map(Object::toString).collect(Collectors.joining(","));
        String sql = "SELECT board_id, state_id FROM board_state_mapping WHERE board_id IN (" + inClause + ")";
        
        Map<Integer, List<Integer>> result = new HashMap<>();
        jdbcTemplate.query(sql, rs -> {
            int bid = rs.getInt("board_id");
            int sid = rs.getInt("state_id");
            result.computeIfAbsent(bid, k -> new ArrayList<>()).add(sid);
        });
        return result;
    }
}

