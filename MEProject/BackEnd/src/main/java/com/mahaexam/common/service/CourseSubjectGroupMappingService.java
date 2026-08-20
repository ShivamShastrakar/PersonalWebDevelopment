package com.mahaexam.common.service;

import java.util.List;

public interface CourseSubjectGroupMappingService {
    int saveMappingsForCourse(int courseId, List<Long> subjectGroupIds);
}
