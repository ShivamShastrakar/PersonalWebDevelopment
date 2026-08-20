package com.mahaexam.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahaexam.common.model.SubjectGroupMapping;
import com.mahaexam.common.repo.SubjectGroupMappingRepository;

@Service
public class SubjectGroupMappingServiceImpl implements SubjectGroupMappingService {

    private final SubjectGroupMappingRepository repository;

    public SubjectGroupMappingServiceImpl(SubjectGroupMappingRepository repository) {
        this.repository = repository;
    }

    @Override
    public int createMapping(SubjectGroupMapping mapping) {
        return repository.save(mapping);
    }

    @Override
    public int deleteMapping(int mappingId) {
        return repository.softDelete(mappingId);
    }

    @Override
    public List<SubjectGroupMapping> getAllMappings() {
        return repository.findAll();
    }

    @Override
    public SubjectGroupMapping getMappingById(int mappingId) {
        return repository.findById(mappingId);
    }

    @Override
    public int saveGroupWithSubjects(int groupId, List<Integer> subjectIds) {
        int count = 0;
        for (Integer subjectId : subjectIds) {
            SubjectGroupMapping mapping = new SubjectGroupMapping();
            mapping.setGroupId(groupId);
            mapping.setSubjectId(subjectId);
            count += repository.save(mapping);
        }
        return count;
    }
}