package com.mahaexam.syllabus.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.papertemplate.model.Syllabus;

public interface SyllabusRepository {

	 Optional<Syllabus> findByClassIdAndSubjectIdAndMediumAndAcademicYearAndTenantId(
	            Long classId,
	            Long subjectId,
	            String medium,
	            Integer academicYear,
	            Long tenantId);

	    boolean existsByClassIdAndSubjectIdAndBoardIdAndMediumAndAcademicYearAndTenantId(
	            Long classId,
	            Long subjectId,
	            Long boardId,
	            String medium,
	            Integer academicYear,
	            Long tenantId);

	    Syllabus save(Syllabus syllabus);

	    Optional<Syllabus> findById(Long id);

	    void update(Syllabus syllabus);

	    List<Syllabus> findAll(Long tenantId, String status);

		int softDelete(int id);
}

