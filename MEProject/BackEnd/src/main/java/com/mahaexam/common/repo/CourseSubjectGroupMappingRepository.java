package com.mahaexam.common.repo;

import java.util.List;

import com.mahaexam.common.model.CourseSubjectGroupMapping;

public interface CourseSubjectGroupMappingRepository {
    int save(CourseSubjectGroupMapping mapping);
    int deleteByCourseId(int courseId);
    List<CourseSubjectGroupMapping> findByCourseId(int courseId);
}