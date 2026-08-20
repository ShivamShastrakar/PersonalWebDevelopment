package com.mahaexam.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.model.CourseSubjectGroupMapping;
import com.mahaexam.common.repo.CourseSubjectGroupMappingRepository;

@Service
public class CourseSubjectGroupMappingServiceImpl implements CourseSubjectGroupMappingService {

	private final CourseSubjectGroupMappingRepository repository;

	public CourseSubjectGroupMappingServiceImpl(CourseSubjectGroupMappingRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public int saveMappingsForCourse(int courseId, List<Long> subjectGroupIds) {
		int count = 0;
		repository.deleteByCourseId(courseId);
		for (Long subjectGroupId : subjectGroupIds) {
			CourseSubjectGroupMapping mapping = new CourseSubjectGroupMapping();
			mapping.setCourseId(courseId);
			mapping.setSubjectGroupId(subjectGroupId.intValue());
			count += repository.save(mapping);
		}
		return count;
	}
}