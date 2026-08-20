package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.QuestionType;
import com.mahaexam.common.repo.QuestionTypeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {

    private static final Logger logger = LogManager.getLogger(QuestionTypeServiceImpl.class);

    private final QuestionTypeRepository repository;

    public QuestionTypeServiceImpl(QuestionTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<QuestionType> getAll(Long tenantId) {
        logger.info("Fetching all question types for tenantId={}", tenantId);
        return repository.findAll(tenantId);
    }

    @Override
    public Optional<QuestionType> getById(int id) {
        logger.info("Fetching question type by id={}", id);
        return repository.findById(id);
    }

    @Override
    public Optional<QuestionType> getByCode(String code, Long tenantId) {
        logger.info("Fetching question type by code={} tenantId={}", code, tenantId);
        return repository.findByCode(code, tenantId);
    }

    @Override
    public List<QuestionType> getByBoardAndSubject(int boardId, int subjectId, Long tenantId) {
        logger.info("Fetching question types for boardId={}, subjectId={}, tenantId={}", boardId, subjectId, tenantId);
        return repository.findByBoardAndSubject(boardId, subjectId, tenantId);
    }

    @Override
    public int create(QuestionType questionType) {
        if (questionType.getCode() == null || questionType.getCode().isBlank())
            throw new ValidationException("Question type code is required");
        if (questionType.getName() == null || questionType.getName().isBlank())
            throw new ValidationException("Question type name is required");
        if (repository.existsByCode(questionType.getCode(), questionType.getTenantId()))
            throw new ValidationException("Question type with code '" + questionType.getCode() + "' already exists for this tenant");

        logger.info("Creating question type code={} tenantId={}", questionType.getCode(), questionType.getTenantId());
        return repository.save(questionType);
    }

    @Override
    public int update(QuestionType questionType) {
        if (questionType.getCode() == null || questionType.getCode().isBlank())
            throw new ValidationException("Question type code is required");
        if (questionType.getName() == null || questionType.getName().isBlank())
            throw new ValidationException("Question type name is required");
        if (repository.existsByCodeExceptId(questionType.getCode(), questionType.getId(), questionType.getTenantId()))
            throw new ValidationException("Question type with code '" + questionType.getCode() + "' already exists for this tenant");

        logger.info("Updating question type id={} tenantId={}", questionType.getId(), questionType.getTenantId());
        return repository.update(questionType);
    }

    @Override
    public int delete(int id, Long tenantId) {
        logger.info("Deleting question type id={} tenantId={}", id, tenantId);
        return repository.deleteById(id, tenantId);
    }
}
