package com.mahaexam.common.repo;

import java.util.List;

import com.mahaexam.common.model.Subject;

public interface SubjectRepository {
    List<Subject> findAllByTenant(Long tenantId);
    Subject findById(int id);
    int save(Subject subject);
    int update(Subject subject);
    int softDelete(int id);
    boolean existsBySubjectNameAndTenantId(String subjectName, Long tenantId);
    boolean existsBySubjectNameAndTenantIdExceptId(String subjectName, Long tenantId, int excludeId);
    List<Subject> findByBoardAndClass(Integer boardId, Integer classId, Long tenantId);
    List<Subject> findByBoardAndClassAndMedium(Integer boardId, Integer classId, String medium, Long tenantId);
}
