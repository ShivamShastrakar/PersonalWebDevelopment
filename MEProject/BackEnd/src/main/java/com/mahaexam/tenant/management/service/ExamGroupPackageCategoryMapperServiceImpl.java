package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.ExamGroupPackageCategoryMapper;
import com.mahaexam.tenant.management.repository.ExamGroupPackageCategoryMapperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExamGroupPackageCategoryMapperServiceImpl implements ExamGroupPackageCategoryMapperService {
    private static final Logger logger = LoggerFactory.getLogger(ExamGroupPackageCategoryMapperServiceImpl.class);
    private final ExamGroupPackageCategoryMapperRepository repository;

    public ExamGroupPackageCategoryMapperServiceImpl(ExamGroupPackageCategoryMapperRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ExamGroupPackageCategoryMapper save(ExamGroupPackageCategoryMapper mapping) {
        if (mapping.getExamGroupId() == null) {
            throw new IllegalArgumentException("Exam Group ID is required");
        }
        logger.info("Saving ExamGroupPackageCategoryMapper with examGroupId: {} and packageCategoryId: {}",
                mapping.getExamGroupId(), mapping.getPackageCategoryId());
        return repository.save(mapping);
    }

    @Override
    public Optional<ExamGroupPackageCategoryMapper> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Mapping ID is required");
        }
        logger.info("Finding ExamGroupPackageCategoryMapper by ID: {}", id);
        return repository.findById(id);
    }

    @Override
    public List<ExamGroupPackageCategoryMapper> findAll() {
        logger.info("Fetching all ExamGroupPackageCategoryMapper records");
        return repository.findAll();
    }

    @Override
    public List<ExamGroupPackageCategoryMapper> findByExamGroupId(Integer examGroupId) {
        if (examGroupId == null) {
            throw new IllegalArgumentException("Exam Group ID is required");
        }
        logger.info("Finding ExamGroupPackageCategoryMapper by examGroupId: {}", examGroupId);
        return repository.findByExamGroupId(examGroupId);
    }

    @Override
    public List<ExamGroupPackageCategoryMapper> findByPackageCategoryId(Integer packageCategoryId) {
        if (packageCategoryId == null) {
            throw new IllegalArgumentException("Package Category ID is required");
        }
        logger.info("Finding ExamGroupPackageCategoryMapper by packageCategoryId: {}", packageCategoryId);
        return repository.findByPackageCategoryId(packageCategoryId);
    }

    @Override
    @Transactional
    public ExamGroupPackageCategoryMapper update(ExamGroupPackageCategoryMapper mapping) {
        if (mapping.getId() == null) {
            throw new IllegalArgumentException("Mapping ID is required for update");
        }
        if (mapping.getExamGroupId() == null) {
            throw new IllegalArgumentException("Exam Group ID is required");
        }
        if (!repository.existsById(mapping.getId())) {
            throw new IllegalArgumentException("Mapping not found with ID: " + mapping.getId());
        }
        logger.info("Updating ExamGroupPackageCategoryMapper with ID: {}", mapping.getId());
        return repository.update(mapping);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Mapping ID is required for delete");
        }
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Mapping not found with ID: " + id);
        }
        logger.info("Deleting ExamGroupPackageCategoryMapper with ID: {}", id);
        repository.delete(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
