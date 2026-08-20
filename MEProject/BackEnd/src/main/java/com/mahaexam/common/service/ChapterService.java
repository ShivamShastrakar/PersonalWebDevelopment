package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Chapter;

public interface ChapterService {
    List<Chapter> getAllChapterByTenant(UserBean user);
    Chapter getChapterById(int id);
    int save(Chapter chapter);
    int updateChapter(Chapter chapter);
    int deleteChapter(int id);
	Chapter getBySubjectId(int subjectId);
    List<Chapter> getChaptersByBoardClassSubjectAndMedium(Integer boardId, Integer classId, Integer subjectId, String medium, Long tenantId);
}