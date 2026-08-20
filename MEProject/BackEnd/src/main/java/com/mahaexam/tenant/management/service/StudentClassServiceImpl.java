package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.tenant.management.model.StudentClass;
import com.mahaexam.tenant.management.repository.StudentClassRepository;

@Service
public class StudentClassServiceImpl implements StudentClassService {
    private final StudentClassRepository repository;

    public StudentClassServiceImpl(StudentClassRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public StudentClass save(StudentClass studentClass) {
        validateStudentClass(studentClass);
        return repository.save(studentClass);
    }

    @Override
    public Optional<StudentClass> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<StudentClass> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public StudentClass update(StudentClass studentClass) {
        validateStudentClass(studentClass);
        return repository.update(studentClass);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void delete(Long id) {
        repository.delete(id);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void deleteStudentId(Long studentId) {
        repository.deleteStudentId(studentId);
    }
    
    @Override
    public List<StudentClass> findByStudentId(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    private void validateStudentClass(StudentClass studentClass) {
        if (studentClass.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID is required");
        }
        if (studentClass.getClassId() == null) {
            throw new IllegalArgumentException("Class ID is required");
        }
    }
}
