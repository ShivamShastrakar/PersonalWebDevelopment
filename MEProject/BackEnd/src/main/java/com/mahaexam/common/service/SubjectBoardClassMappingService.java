package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.SubjectBoardClassMapping;

public interface SubjectBoardClassMappingService {
	int createMapping(SubjectBoardClassMapping mapping);

	int deleteMapping(int id);

	List<SubjectBoardClassMapping> getAllMappings();

	Optional<SubjectBoardClassMapping> getMappingById(int id);

	int saveMappingsForSubjects(List<SubjectBoardClassMapping> mappings);
}