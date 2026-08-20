package com.mahaexam.common.repo;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Chapter;

import java.util.List;

public interface ChapterRepository {
    List<Chapter> findAll(UserBean user);

    Chapter findById(int id);

    Chapter save(Chapter chapter);

    Chapter update(Chapter chapter);

    int softDelete(int id);

    boolean existsByChapterName(String chapterName, int subjectId, int classId, int chapterId);

    Chapter getBySubjectId(int subjectId);

    List<Chapter> findByBoardClassSubjectAndMedium(Integer boardId, Integer classId, Integer subjectId, String medium, Long tenantId);
}