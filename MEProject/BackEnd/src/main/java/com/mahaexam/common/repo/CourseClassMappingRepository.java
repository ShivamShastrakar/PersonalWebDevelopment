package com.mahaexam.common.repo;

import com.mahaexam.common.model.CourseClassMapping;
import java.util.List;

public interface CourseClassMappingRepository {
    void saveMappingsForCourse(Integer courseId, List<Integer> classIds);
    void deleteMappingsForCourse(Integer courseId);
    List<Integer> findClassIdsByCourseId(Integer courseId);
    List<Integer> findCourseIdsByClassId(Integer classId);
}

