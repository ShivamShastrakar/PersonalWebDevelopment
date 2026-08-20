package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.ExamGroupPackageCategoryMapper;

import java.util.List;
import java.util.Optional;

public interface ExamGroupPackageCategoryMapperRepository {
    ExamGroupPackageCategoryMapper save(ExamGroupPackageCategoryMapper mapping);

    Optional<ExamGroupPackageCategoryMapper> findById(Integer id);

    List<ExamGroupPackageCategoryMapper> findAll();

    List<ExamGroupPackageCategoryMapper> findByExamGroupId(Integer examGroupId);

    List<ExamGroupPackageCategoryMapper> findByPackageCategoryId(Integer packageCategoryId);

    ExamGroupPackageCategoryMapper update(ExamGroupPackageCategoryMapper mapping);

    void delete(Integer id);

    boolean existsById(Integer id);
}
