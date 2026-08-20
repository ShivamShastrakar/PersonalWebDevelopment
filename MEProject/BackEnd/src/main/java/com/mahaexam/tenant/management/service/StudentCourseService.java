package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.StudentCourse;

public interface StudentCourseService {
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