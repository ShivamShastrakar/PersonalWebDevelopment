package com.mahaexam.common.repo;

import java.util.List;

import com.mahaexam.common.model.SubjectGroupMapping;

public interface SubjectGroupMappingRepository {
    int save(SubjectGroupMapping mapping);
    int softDelete(int mappingId);
    List<SubjectGroupMapping> findAll();
    SubjectGroupMapping findById(int mappingId);
}
