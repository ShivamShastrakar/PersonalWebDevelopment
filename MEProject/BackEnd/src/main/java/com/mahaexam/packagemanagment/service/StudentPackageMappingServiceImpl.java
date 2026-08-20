package com.mahaexam.packagemanagment.service;

import com.mahaexam.packagemanagment.model.StudentPackageMapping;
import com.mahaexam.packagemanagment.repository.StudentPackageMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPackageMappingServiceImpl implements StudentPackageMappingService {
    private final StudentPackageMappingRepository repository;

    @Autowired
    public StudentPackageMappingServiceImpl(StudentPackageMappingRepository repository) {
        this.repository = repository;
    }

    @Override
    public StudentPackageMapping save(StudentPackageMapping mapping) {
        return repository.save(mapping);
    }

    @Override
    public StudentPackageMapping findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<StudentPackageMapping> findByStudentId(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    @Override
    public void updateStatus(Integer id, String status) {
        repository.updateStatus(id, status);
    }

    @Override
    public List<StudentPackageMapping> saveMultiple(List<StudentPackageMapping> mappings) {
        return repository.saveMultiple(mappings);
    }
}
