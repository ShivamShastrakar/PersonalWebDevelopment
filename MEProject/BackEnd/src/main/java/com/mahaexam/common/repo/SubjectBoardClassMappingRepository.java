package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.SubjectBoardClassMapping;

public interface SubjectBoardClassMappingRepository {
    int save(SubjectBoardClassMapping mapping);
    int softDelete(int id);
    List<SubjectBoardClassMapping> findAll();
    boolean existsBySubjectClassBoard(Integer subjectId, Integer classId, Integer boardId, String medium);
    Optional<SubjectBoardClassMapping> findById(Integer id);
    int deleteBySubjectId(int subjectId);
    int[] save(List<SubjectBoardClassMapping> mappings);
    List<SubjectBoardClassMapping> findByIds(List<Integer> subjectIds);
}