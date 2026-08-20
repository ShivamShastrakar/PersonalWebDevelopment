package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.ExamGroupDtls;

import java.util.List;
import java.util.Optional;

public interface ExamGroupDtlsService {
    ExamGroupDtls save(ExamGroupDtls examGroupDtls);

    Optional<ExamGroupDtls> findById(Integer id);

    List<ExamGroupDtls> findAll();

    ExamGroupDtls update(ExamGroupDtls examGroupDtls);

    void delete(Integer id);

    boolean existsById(Integer id);
}
