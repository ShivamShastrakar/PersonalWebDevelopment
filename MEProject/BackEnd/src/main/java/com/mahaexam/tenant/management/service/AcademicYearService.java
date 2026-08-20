package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.AcademicYear;
import java.util.List;
import java.util.Optional;

public interface AcademicYearService {
    AcademicYear save(AcademicYear academicYear);
    Optional<AcademicYear> findById(Long id);
    Optional<AcademicYear> findByNameAndTenantId(String name, Long tenantId);
    Optional<AcademicYear> findByNameTenantAndBoardId(String name, Long tenantId, Integer boardId);
    List<AcademicYear> findAllByTenantId(Long tenantId);
    AcademicYear update(AcademicYear academicYear);
    void delete(Long id);
}
