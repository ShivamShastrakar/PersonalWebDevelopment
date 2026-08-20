package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.SubjectGroup;

public interface SubjectGroupRepository {
    List<SubjectGroup> findAllByTenant(Long tenantId);

    SubjectGroup findById(int id);

    int save(SubjectGroup group);

    int update(SubjectGroup group);

    int softDelete(int id);

    boolean existsByGroupNameAndTenantId(String groupName, Long tenantId);

    boolean existsByGroupNameAndTenantIdExceptId(String groupName, Long tenantId, int excludeId);

    Optional<SubjectGroup> findGroupByName(String subjectGroupName);
}
