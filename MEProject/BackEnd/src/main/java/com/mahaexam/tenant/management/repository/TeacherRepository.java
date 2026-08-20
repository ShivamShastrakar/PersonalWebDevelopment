package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Teacher;

public interface TeacherRepository {
    Teacher save(Teacher teacher);
    Optional<Teacher> findById(Long teacherId);
    List<Teacher> findAll();
    Teacher update(Teacher teacher);
    void delete(Long teacherId);
    Optional<Teacher> findByUserId(Long userId);
}