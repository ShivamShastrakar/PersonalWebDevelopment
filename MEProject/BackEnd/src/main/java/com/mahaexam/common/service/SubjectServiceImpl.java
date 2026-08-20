package com.mahaexam.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.SubjectBoardClassMapping;
import com.mahaexam.common.repo.SubjectBoardClassMappingRepository;
import org.springframework.stereotype.Service;

import com.mahaexam.common.model.Subject;
import com.mahaexam.common.repo.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectBoardClassMappingRepository subjectBoardClassMappingRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectBoardClassMappingRepository subjectBoardClassMappingRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectBoardClassMappingRepository = subjectBoardClassMappingRepository;
    }
    @Override
    public List<Subject> getAllSubjectsByTenant(Long tenantId) {
        List<Subject> subjects = subjectRepository.findAllByTenant(tenantId);
        List<Integer> subjectIds = subjects.stream().map(Subject::getSubjectId).collect(Collectors.toList());
        List<SubjectBoardClassMapping> allMappings = new java.util.ArrayList<>();
        int chunkSize = 2000;
        for (int i = 0; i < subjectIds.size(); i += chunkSize) {
            List<Integer> chunk = subjectIds.subList(i, Math.min(i + chunkSize, subjectIds.size()));
            allMappings.addAll(subjectBoardClassMappingRepository.findByIds(chunk));
        }
        // Group mappings by subjectId
        java.util.Map<Integer, List<SubjectBoardClassMapping>> mappingBySubjectId = allMappings.stream()
            .collect(Collectors.groupingBy(SubjectBoardClassMapping::getSubjectId));
        // Assign mappings to subjects
        subjects.forEach(subject -> {
            List<SubjectBoardClassMapping> mappings = mappingBySubjectId.getOrDefault(subject.getSubjectId(), java.util.Collections.emptyList());
            subject.setSubjectBoardClassMappings(mappings);
        });
        return subjects;
    }

    @Override
    public Subject getSubjectById(int id) {
        Subject subject= subjectRepository.findById(id);
        List<Integer> subjectIds = new ArrayList<>();
        subjectIds.add(id);
        List<SubjectBoardClassMapping> subjectBoardClassMappings = subjectBoardClassMappingRepository.findByIds(subjectIds);
        subject.setSubjectBoardClassMappings(subjectBoardClassMappings);
        return subject;
    }

    @Override
    public int createSubject(Subject subject) {
        if (subjectRepository.existsBySubjectNameAndTenantId(subject.getSubjectName(), subject.getTenantId())) {
            throw new ValidationException("Subject name already exists for this tenant.");
        }
        int subjectId = subjectRepository.save(subject);
        subject.getSubjectBoardClassMappings().forEach(mapping -> mapping.setSubjectId(subjectId));
        subjectBoardClassMappingRepository.save(subject.getSubjectBoardClassMappings());
        return subjectId;
    }

    @Override
    public int updateSubject(Subject subject) {
        if (subjectRepository.existsBySubjectNameAndTenantIdExceptId(
                subject.getSubjectName(), subject.getTenantId(), subject.getSubjectId())) {
            throw new ValidationException("Subject name already exists for this tenant.");
        }
        subjectBoardClassMappingRepository.deleteBySubjectId(subject.getSubjectId());
        subjectBoardClassMappingRepository.save(subject.getSubjectBoardClassMappings());
        return subjectRepository.update(subject);
    }

    @Override
    public int deleteSubject(int id) {
        return subjectRepository.softDelete(id);
    }

    @Override
    public List<Subject> getSubjectsByBoardAndClass(Integer boardId, Integer classId, Long tenantId) {
        List<Subject> subjects = subjectRepository.findByBoardAndClass(boardId, classId, tenantId);
        List<Integer> subjectIds = subjects.stream().map(Subject::getSubjectId).collect(Collectors.toList());

        if (subjectIds.isEmpty()) {
            return subjects;
        }

        List<SubjectBoardClassMapping> allMappings = new ArrayList<>();
        int chunkSize = 2000;
        for (int i = 0; i < subjectIds.size(); i += chunkSize) {
            List<Integer> chunk = subjectIds.subList(i, Math.min(i + chunkSize, subjectIds.size()));
            allMappings.addAll(subjectBoardClassMappingRepository.findByIds(chunk));
        }

        // Group mappings by subjectId
        java.util.Map<Integer, List<SubjectBoardClassMapping>> mappingBySubjectId = allMappings.stream()
            .collect(Collectors.groupingBy(SubjectBoardClassMapping::getSubjectId));

        // Assign mappings to subjects
        subjects.forEach(subject -> {
            List<SubjectBoardClassMapping> mappings = mappingBySubjectId.getOrDefault(subject.getSubjectId(), java.util.Collections.emptyList());
            subject.setSubjectBoardClassMappings(mappings);
        });

        return subjects;
    }

    @Override
    public List<Subject> getSubjectsByBoardAndClassAndMedium(Integer boardId, Integer classId, String medium, Long tenantId) {
        List<Subject> subjects = subjectRepository.findByBoardAndClassAndMedium(boardId, classId, medium, tenantId);
        List<Integer> subjectIds = subjects.stream().map(Subject::getSubjectId).collect(Collectors.toList());

        if (subjectIds.isEmpty()) {
            return subjects;
        }
        /*
        List<SubjectBoardClassMapping> allMappings = new ArrayList<>();
        int chunkSize = 2000;
        for (int i = 0; i < subjectIds.size(); i += chunkSize) {
            List<Integer> chunk = subjectIds.subList(i, Math.min(i + chunkSize, subjectIds.size()));
            allMappings.addAll(subjectBoardClassMappingRepository.findByIds(chunk));
        }

        java.util.Map<Integer, List<SubjectBoardClassMapping>> mappingBySubjectId = allMappings.stream()
            .collect(Collectors.groupingBy(SubjectBoardClassMapping::getSubjectId));

        subjects.forEach(subject -> {
            List<SubjectBoardClassMapping> mappings = mappingBySubjectId.getOrDefault(subject.getSubjectId(), java.util.Collections.emptyList());
            subject.setSubjectBoardClassMappings(mappings);
        });

         */

        return subjects;
    }
}

