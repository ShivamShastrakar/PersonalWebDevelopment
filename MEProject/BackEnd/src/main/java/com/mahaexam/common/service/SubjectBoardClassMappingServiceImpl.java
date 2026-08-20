package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.SubjectBoardClassMapping;
import com.mahaexam.common.repo.SubjectBoardClassMappingRepository;

@Service
public class SubjectBoardClassMappingServiceImpl implements SubjectBoardClassMappingService {

	private final SubjectBoardClassMappingRepository repository;

	public SubjectBoardClassMappingServiceImpl(SubjectBoardClassMappingRepository repository) {
		this.repository = repository;
	}

	@Override
	public int createMapping(SubjectBoardClassMapping mapping) {
		if (repository.existsBySubjectClassBoard(mapping.getSubjectId(), mapping.getClassId(), mapping.getBoardId(), mapping.getMedium())) {
			throw new ValidationException("This subject-class-board-medium combination already exists.");
		}
		return repository.save(mapping);
	}

	@Override
	public int deleteMapping(int id) {
		return repository.softDelete(id);
	}

	@Override
	public List<SubjectBoardClassMapping> getAllMappings() {
		return repository.findAll();
	}

	@Override
	public Optional<SubjectBoardClassMapping> getMappingById(int id) {
		return repository.findById(id);
	}

	@Override
	public int saveMappingsForSubjects(List<SubjectBoardClassMapping> mappings) {
		int[] results = repository.save(mappings);
		return java.util.Arrays.stream(results).sum();
	}
}