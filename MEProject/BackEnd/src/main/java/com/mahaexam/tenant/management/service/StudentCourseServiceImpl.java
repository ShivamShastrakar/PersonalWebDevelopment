package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mahaexam.tenant.management.model.StudentCourse;
import com.mahaexam.tenant.management.repository.StudentCourseRepository;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
	private final StudentCourseRepository repository;

	public StudentCourseServiceImpl(StudentCourseRepository repository) {
		this.repository = repository;
	}

	@Override
	public StudentCourse save(StudentCourse studentCourse) {
		return repository.save(studentCourse);
	}

	@Override
	public void save(Long studentId, List<Long> courseIds) {
		repository.save(studentId, courseIds);
	}

	@Override
	public Optional<StudentCourse> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<StudentCourse> findAll() {
		return repository.findAll();
	}

	@Override
	public StudentCourse update(StudentCourse studentCourse) {
		return repository.update(studentCourse);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public void deleteStudentId(Long studentId) {
		repository.deleteStudentId(studentId);
	}

	@Override
	public List<StudentCourse> findByStudentId(Long studentId) {
		return repository.findByStudentId(studentId);
	}
	@Override
	public List<StudentCourse> findByStudentIds(List<Long> studentIds) {
		return repository.findByStudentIds(studentIds);
	}
}