package com.mahaexam.syllabus.repository;

import java.util.List;

import com.mahaexam.papertemplate.model.SyllabusChapter;

public interface SyllabusChapterRepository {

    void save(SyllabusChapter syllabusChapter);

    List<SyllabusChapter> findBySyllabusId(Long syllabusId);

    void deleteBySyllabusId(Long syllabusId);
}

