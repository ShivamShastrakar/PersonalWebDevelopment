package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.bean.CourseBean;
import com.mahaexam.common.model.Course;

public interface CourseService {
    List<Course> getAllCoursesByTenant(Long tenantId);
    Course getCourseById(int id);
    int createCourse(Course course,List<Long> subjectGroupIds);
    int updateCourse(Course course);
    int deleteCourse(int id);
	List<CourseBean> findAllByPackageIds(List<Integer> packageIds);
    boolean existsByCourseNameAndTenantId(String courseName, Long tenantId);
    Course findByName(String name);
    List<Course> findByNames(List<String> coursesNames);
    List<Course> getAllCoursesByTenantAndClassId(Long tenantId, Integer classId);
}
