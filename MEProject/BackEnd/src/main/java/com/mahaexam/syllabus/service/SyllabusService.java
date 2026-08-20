package com.mahaexam.syllabus.service;

import java.util.List;

import com.mahaexam.papertemplate.bean.CreateSyllabusRequestDTO;
import com.mahaexam.papertemplate.bean.SyllabusResponseDTO;
import com.mahaexam.papertemplate.model.Syllabus;

public interface SyllabusService {

    SyllabusResponseDTO createSyllabus(
            CreateSyllabusRequestDTO request, Long tenantId, Long userId);

    Syllabus getSyllabus(
            Long classId,
            Long subjectId,
            String medium,
            Integer academicYear,
            Long tenantId
    );

    boolean syllabusExists(
            Long classId,
            Long subjectId,
            Long boardId,
            String medium,
            Integer academicYear,
            Long tenantId
    );

    Syllabus getSyllabusById(Long id);

    void updateSyllabus(Syllabus syllabus);

    List<Syllabus> getAllSyllabi(Long tenantId, String status);

	int deleteSyllabus(int id);
}


