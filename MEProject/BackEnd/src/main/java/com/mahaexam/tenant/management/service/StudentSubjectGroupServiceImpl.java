package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.tenant.management.model.StudentSubjectGroup;
import com.mahaexam.tenant.management.repository.StudentSubjectGroupRepository;

@Service
public class StudentSubjectGroupServiceImpl implements StudentSubjectGroupService {
	private final StudentSubjectGroupRepository repository;

	public StudentSubjectGroupServiceImpl(StudentSubjectGroupRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public StudentSubjectGroup save(StudentSubjectGroup studentSubjectGroup) {
		return repository.save(studentSubjectGroup);
	}

	@Override
	public Optional<StudentSubjectGroup> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<StudentSubjectGroup> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public StudentSubjectGroup update(StudentSubjectGroup studentSubjectGroup) {
		return repository.update(studentSubjectGroup);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void deleteStudentId(Long studentId) {
		repository.deleteStudentId(studentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public List<StudentSubjectGroup> findByStudentId(Long studentId) {
		return repository.findByStudentId(studentId);
	}

	@Override
	public List<StudentSubjectGroup> findByStudentIds(List<Long> studentIds) {
		return repository.findByStudentIds(studentIds);
	}
}