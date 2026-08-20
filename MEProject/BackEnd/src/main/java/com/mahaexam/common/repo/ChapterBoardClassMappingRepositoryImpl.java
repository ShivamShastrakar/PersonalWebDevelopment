package com.mahaexam.common.repo;

import com.mahaexam.common.model.ChapterBoardClassMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ChapterBoardClassMappingRepositoryImpl implements ChapterBoardClassMappingRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChapterBoardClassMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(ChapterBoardClassMapping mapping) {
        String sql = "INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, mapping.getChapterId(), mapping.getClassId(), mapping.getBoardId());
    }

    @Override
    public int[] save(List<ChapterBoardClassMapping> mappings) {
        if (mappings == null || mappings.isEmpty()) {
            return new int[0];
        }
        String sql = "INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (?, ?, ?)";
        List<Object[]> batchArgs = mappings.stream()
                .map(m -> new Object[]{m.getChapterId(), m.getClassId(), m.getBoardId()})
                .collect(Collectors.toList());
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public int[] save(Integer chapterId, List<Long> boardIds, List<Long> classIds) {
        if (chapterId == null || boardIds == null || classIds == null || boardIds.isEmpty() || classIds.isEmpty()) {
            return new int[0];
        }
        String sql = "INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        for (Long classId : classIds) {
            for (Long boardId : boardIds) {
                batchArgs.add(new Object[]{chapterId, classId.intValue(), boardId.intValue()});
            }
        }
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public int softDelete(int id) {
        String sql = "UPDATE chapter_board_class_mapping SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int deleteByChapterId(int chapterId) {
        String sql = "DELETE FROM chapter_board_class_mapping WHERE chapter_id = ?";
        return jdbcTemplate.update(sql, chapterId);
    }

    @Override
    public List<ChapterBoardClassMapping> findAll() {
        String sql = "SELECT * FROM chapter_board_class_mapping WHERE deleted = '0'";
        return jdbcTemplate.query(sql, new ChapterBoardClassMappingRowMapper());
    }

    @Override
    public Optional<ChapterBoardClassMapping> findById(Integer id) {
        String sql = "SELECT * FROM chapter_board_class_mapping WHERE id = ? AND deleted = '0'";
        try {
            ChapterBoardClassMapping mapping = jdbcTemplate.queryForObject(sql, new ChapterBoardClassMappingRowMapper(), id);
            return Optional.ofNullable(mapping);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByChapterClassBoard(Integer chapterId, Integer classId, Integer boardId) {
        String sql = "SELECT COUNT(*) FROM chapter_board_class_mapping WHERE chapter_id = ? AND class_id = ? AND board_id = ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, chapterId, classId, boardId);
        return count != null && count > 0;
    }

    @Override
    public List<ChapterBoardClassMapping> findByChapterIds(List<Integer> chapterIds) {
        if (chapterIds == null || chapterIds.isEmpty()) {
            return Collections.emptyList();
        }
        String placeholders = chapterIds.stream().map(id -> "?").collect(Collectors.joining(","));
        String sql = "SELECT cbcm.*, c.class_name, b.board_name, ch.chapter_name FROM chapter_board_class_mapping cbcm " +
                "INNER JOIN class c ON c.id = cbcm.class_id " +
                "INNER JOIN board b ON b.id = cbcm.board_id " +
                "INNER JOIN chapters ch ON ch.id = cbcm.chapter_id " +
                "WHERE cbcm.chapter_id IN (" + placeholders + ") AND c.deleted = '0'";
        Object[] params = chapterIds.toArray();
        return jdbcTemplate.query(sql, params, new ChapterBoardClassMappingRowMapper());
    }
}
