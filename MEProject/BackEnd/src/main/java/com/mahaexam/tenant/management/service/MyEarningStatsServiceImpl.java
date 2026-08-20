package com.mahaexam.tenant.management.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.MyEarningStats;
import com.mahaexam.tenant.management.repository.MyEarningStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MyEarningStatsServiceImpl implements MyEarningStatsService {
    private static final Logger logger = LoggerFactory.getLogger(MyEarningStatsServiceImpl.class);
    private final MyEarningStatsRepository myEarningStatsRepository;

    public MyEarningStatsServiceImpl(MyEarningStatsRepository myEarningStatsRepository) {
        this.myEarningStatsRepository = myEarningStatsRepository;
    }

    @Override
    @Transactional
    public MyEarningStats save(MyEarningStats dto) {
        if (dto.getUserId() == null) {
            throw new ValidationException("User ID is required");
        }
        logger.info("Saving MyEarningStats for userId: {}", dto.getUserId());
        return myEarningStatsRepository.save(dto);
    }

    @Override
    public Optional<MyEarningStats> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required");
        }
        logger.info("Finding MyEarningStats by ID: {}", id);
        return myEarningStatsRepository.findById(id);
    }

    @Override
    public List<MyEarningStats> findByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID is required and must be positive");
        }
        logger.info("Finding MyEarningStats by userId: {}", userId);
        return myEarningStatsRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public MyEarningStats update(MyEarningStats dto) {
        if (dto.getId() == null) {
            throw new ValidationException("ID is required for update");
        }
        if (!myEarningStatsRepository.existsById(dto.getId())) {
            throw new ValidationException("MyEarningStats not found with ID: " + dto.getId());
        }
        logger.info("Updating MyEarningStats with ID: {}", dto.getId());
        return myEarningStatsRepository.update(dto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required");
        }
        if (!myEarningStatsRepository.existsById(id)) {
            throw new ValidationException("MyEarningStats not found with ID: " + id);
        }
        logger.info("Deleting MyEarningStats with ID: {}", id);
        myEarningStatsRepository.delete(id);
    }

    @Override
    public List<MyEarningStats> findAll() {
        logger.info("Finding all MyEarningStats");
        return myEarningStatsRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return myEarningStatsRepository.existsById(id);
    }
}
