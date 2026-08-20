package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.BoardSubjectQuestionTypeMapping;
import com.mahaexam.common.repo.BoardSubjectQuestionTypeMappingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardSubjectQuestionTypeMappingServiceImpl implements BoardSubjectQuestionTypeMappingService {

    private static final Logger logger = LogManager.getLogger(BoardSubjectQuestionTypeMappingServiceImpl.class);

    private final BoardSubjectQuestionTypeMappingRepository repository;

    public BoardSubjectQuestionTypeMappingServiceImpl(BoardSubjectQuestionTypeMappingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BoardSubjectQuestionTypeMapping> getAll(Long tenantId) {
        logger.info("Fetching all board-subject-questionType mappings for tenantId={}", tenantId);
        return repository.findAll(tenantId);
    }

    @Override
    public Optional<BoardSubjectQuestionTypeMapping> getById(int id) {
        logger.info("Fetching mapping by id={}", id);
        return repository.findById(id);
    }

    @Override
    public List<BoardSubjectQuestionTypeMapping> getByBoardId(int boardId, Long tenantId) {
        logger.info("Fetching mappings for boardId={} tenantId={}", boardId, tenantId);
        return repository.findByBoardId(boardId, tenantId);
    }

    @Override
    public List<BoardSubjectQuestionTypeMapping> getByBoardAndSubject(int boardId, int subjectId, Long tenantId) {
        logger.info("Fetching mappings for boardId={}, subjectId={}, tenantId={}", boardId, subjectId, tenantId);
        return repository.findByBoardAndSubject(boardId, subjectId, tenantId);
    }

    @Override
    public int create(BoardSubjectQuestionTypeMapping mapping) {
        if (mapping.getBoardId() <= 0)
            throw new ValidationException("Board ID is required");
        if (mapping.getSubjectId() <= 0)
            throw new ValidationException("Subject ID is required");
        if (mapping.getQuestionTypeId() <= 0)
            throw new ValidationException("Question Type ID is required");
        if (repository.existsByBoardSubjectAndQuestionType(
                mapping.getBoardId(), mapping.getSubjectId(), mapping.getQuestionTypeId(), mapping.getTenantId()))
            throw new ValidationException("Mapping already exists for this Board, Subject and Question Type");

        logger.info("Creating mapping: boardId={}, subjectId={}, questionTypeId={}, tenantId={}",
                mapping.getBoardId(), mapping.getSubjectId(), mapping.getQuestionTypeId(), mapping.getTenantId());
        return repository.save(mapping);
    }

    @Override
    public int delete(int id) {
        logger.info("Deleting mapping id={}", id);
        return repository.softDelete(id);
    }
}
