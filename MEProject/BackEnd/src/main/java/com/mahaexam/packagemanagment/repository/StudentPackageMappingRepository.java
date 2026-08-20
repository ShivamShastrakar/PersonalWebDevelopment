package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.StudentPackageMapping;

import java.util.List;

public interface StudentPackageMappingRepository {
    StudentPackageMapping save(StudentPackageMapping mapping);
    StudentPackageMapping findById(Integer id);
    List<StudentPackageMapping> findByStudentId(Long studentId);
    void updateStatus(Integer id, String status);
    List<StudentPackageMapping> saveMultiple(List<StudentPackageMapping> mappings);
}