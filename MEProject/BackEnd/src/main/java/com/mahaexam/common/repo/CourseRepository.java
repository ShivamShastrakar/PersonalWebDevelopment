package com.mahaexam.common.repo;

import java.util.List;

import com.mahaexam.common.model.Course;

public interface CourseRepository {
    List<Course> findAllByTenant(Long tenantId);
    Course findById(int id);
    int save(Course course);
    int update(Course course);
    int softDelete(int id);
    boolean existsByCourseNameAndTenantId(String courseName, Long tenantId);
    boolean existsByCourseNameAndTenantIdExceptId(String courseName, Long tenantId, int excludeId);
	List<Course> findAllByPackageIds(List<Integer> packageIds);
    Course findByName(String name);
    List<Course> findByNames(List<String> names);
    List<Course> findAllByTenantAndClassId(Long tenantId, Integer classId);
}
