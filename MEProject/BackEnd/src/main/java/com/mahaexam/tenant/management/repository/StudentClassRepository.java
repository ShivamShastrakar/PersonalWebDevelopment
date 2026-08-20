package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.StudentClass;

public interface StudentClassRepository {
	StudentClass save(StudentClass studentClass);

	Optional<StudentClass> findById(Long id);

	List<StudentClass> findAll();

	StudentClass update(StudentClass studentClass);

	void delete(Long id);

	List<StudentClass> findByStudentId(Long studentId);

	void deleteStudentId(Long studentId);
}