package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.ExamGroupDtls;
import com.mahaexam.tenant.management.repository.ExamGroupDtlsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExamGroupDtlsServiceImpl implements ExamGroupDtlsService {
    private static final Logger logger = LoggerFactory.getLogger(ExamGroupDtlsServiceImpl.class);
    private final ExamGroupDtlsRepository examGroupDtlsRepository;

    public ExamGroupDtlsServiceImpl(ExamGroupDtlsRepository examGroupDtlsRepository) {
        this.examGroupDtlsRepository = examGroupDtlsRepository;
    }

    @Override
    @Transactional
    public ExamGroupDtls save(ExamGroupDtls examGroupDtls) {
        if (examGroupDtls.getName() == null || examGroupDtls.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Exam Group name is required");
        }
        logger.info("Saving ExamGroupDtls with name: {}", examGroupDtls.getName());
        return examGroupDtlsRepository.save(examGroupDtls);
    }

    @Override
    public Optional<ExamGroupDtls> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Exam Group ID is required");
        }
        logger.info("Finding ExamGroupDtls by ID: {}", id);
        return examGroupDtlsRepository.findById(id);
    }

    @Override
    public List<ExamGroupDtls> findAll() {
        logger.info("Fetching all ExamGroupDtls");
        return examGroupDtlsRepository.findAll();
    }

    @Override
    @Transactional
    public ExamGroupDtls update(ExamGroupDtls examGroupDtls) {
        if (examGroupDtls.getId() == null) {
            throw new IllegalArgumentException("Exam Group ID is required for update");
        }
        if (examGroupDtls.getName() == null || examGroupDtls.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Exam Group name is required");
        }
        if (!examGroupDtlsRepository.existsById(examGroupDtls.getId())) {
            throw new IllegalArgumentException("Exam Group not found with ID: " + examGroupDtls.getId());
        }
        logger.info("Updating ExamGroupDtls with ID: {}", examGroupDtls.getId());
        return examGroupDtlsRepository.update(examGroupDtls);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Exam Group ID is required for delete");
        }
        if (!examGroupDtlsRepository.existsById(id)) {
            throw new IllegalArgumentException("Exam Group not found with ID: " + id);
        }
        logger.info("Deleting ExamGroupDtls with ID: {}", id);
        examGroupDtlsRepository.delete(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return examGroupDtlsRepository.existsById(id);
    }
}
