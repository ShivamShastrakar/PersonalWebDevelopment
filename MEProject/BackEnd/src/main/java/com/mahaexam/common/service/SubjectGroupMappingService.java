package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.model.SubjectGroupMapping;

public interface SubjectGroupMappingService {
    int createMapping(SubjectGroupMapping mapping);
    int deleteMapping(int mappingId);
    List<SubjectGroupMapping> getAllMappings();
    SubjectGroupMapping getMappingById(int mappingId);
    int saveGroupWithSubjects(int groupId, List<Integer> subjectIds);
}