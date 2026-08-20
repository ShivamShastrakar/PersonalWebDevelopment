package com.mahaexam.common.repo;

import com.mahaexam.common.model.ChapterBoardClassMapping;
import java.util.List;
import java.util.Optional;

public interface ChapterBoardClassMappingRepository {
    // Save single mapping
    int save(ChapterBoardClassMapping mapping);
    // Batch save entities
    int[] save(List<ChapterBoardClassMapping> mappings);
    // Save multiple combinations for a chapter (cartesian product of classIds x boardIds)
    int[] save(Integer chapterId, List<Long> boardIds, List<Long> classIds);
    // Soft delete by id (if deleted column exists in schema)
    int softDelete(int id);
    // Hard delete by chapter id
    int deleteByChapterId(int chapterId);
    // Find all non-deleted
    List<ChapterBoardClassMapping> findAll();
    // Find by id
    Optional<ChapterBoardClassMapping> findById(Integer id);
    // Existence check
    boolean existsByChapterClassBoard(Integer chapterId, Integer classId, Integer boardId);
    // Find by list of chapter ids
    List<ChapterBoardClassMapping> findByChapterIds(List<Integer> chapterIds);
}
