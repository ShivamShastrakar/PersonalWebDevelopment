package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.SubjectGroup;

public interface SubjectGroupService {
    List<SubjectGroup> getAllGroupsByTenant(Long tenantId);
    SubjectGroup getGroupById(int id);
    int createGroup(SubjectGroup group);
    int updateGroup(SubjectGroup group);
    int deleteGroup(int id);
    Optional<SubjectGroup> findGroupByName(String subjectGroupName);
}