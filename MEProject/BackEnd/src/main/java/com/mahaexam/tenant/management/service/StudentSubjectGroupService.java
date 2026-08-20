package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.StudentSubjectGroup;

public interface StudentSubjectGroupService {
    StudentSubjectGroup save(StudentSubjectGroup studentSubjectGroup);
    Optional<StudentSubjectGroup> findById(Long id);
    List<StudentSubjectGroup> findAll();
    StudentSubjectGroup update(StudentSubjectGroup studentSubjectGroup);
    void delete(Long id);
    List<StudentSubjectGroup> findByStudentId(Long studentId);
	void deleteStudentId(Long studentId);
	List<StudentSubjectGroup> findByStudentIds(List<Long> studentIds);
}
