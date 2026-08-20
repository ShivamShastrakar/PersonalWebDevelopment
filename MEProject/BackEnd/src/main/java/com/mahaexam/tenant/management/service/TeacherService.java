package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.bean.TeacherRegistrationBean;
import com.mahaexam.tenant.management.model.Teacher;

import jakarta.validation.Valid;

public interface TeacherService {
    Teacher save(Teacher teacher);
    Optional<Teacher> findById(Long teacherId);
    List<Teacher> findAll();
    Teacher update(Teacher teacher);
    void delete(Long teacherId);
    Optional<Teacher> findByUserId(Long userId);
	void registerTeacher(@Valid TeacherRegistrationBean registrationDTO, Boolean validateOtp);
}