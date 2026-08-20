package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.StudentCourse;

public interface StudentCourseRepository {
    StudentCourse save(StudentCourse studentCourse);
    Optional<StudentCourse> findById(Long id);
    List<StudentCourse> findAll();
    StudentCourse update(StudentCourse studentCourse);
    void delete(Long id);
    List<StudentCourse> findByStudentId(Long studentId);
	void save(Long studentId, List<Long> courseIds);
	void deleteStudentId(Long studentId);
	List<StudentCourse> findByStudentIds(List<Long> studentIds);
}