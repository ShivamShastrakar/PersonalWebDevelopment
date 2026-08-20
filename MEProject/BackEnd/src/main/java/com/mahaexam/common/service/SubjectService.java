package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.model.Subject;

public interface SubjectService {
    List<Subject> getAllSubjectsByTenant(Long tenantId);
    Subject getSubjectById(int id);
    int createSubject(Subject subject);
    int updateSubject(Subject subject);
    int deleteSubject(int id);
    List<Subject> getSubjectsByBoardAndClass(Integer boardId, Integer classId, Long tenantId);
    List<Subject> getSubjectsByBoardAndClassAndMedium(Integer boardId, Integer classId, String medium, Long tenantId);
}
