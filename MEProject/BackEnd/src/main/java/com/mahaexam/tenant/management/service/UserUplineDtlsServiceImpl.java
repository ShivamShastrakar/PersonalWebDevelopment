package com.mahaexam.tenant.management.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserUplineDtls;
import com.mahaexam.tenant.management.repository.UserUplineDtlsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserUplineDtlsServiceImpl implements UserUplineDtlsService {
    private static final Logger logger = LoggerFactory.getLogger(UserUplineDtlsServiceImpl.class);
    private final UserUplineDtlsRepository userUplineDtlsRepository;

    public UserUplineDtlsServiceImpl(UserUplineDtlsRepository userUplineDtlsRepository) {
        this.userUplineDtlsRepository = userUplineDtlsRepository;
    }

    @Override
    @Transactional
    public UserUplineDtls save(UserUplineDtls userUplineDtls) {
        if (userUplineDtls.getUserLevel1Id() == null) {
            throw new ValidationException("User Level 1 ID is required");
        }
        logger.info("Saving UserUplineDtls for userLevel1Id: {}", userUplineDtls.getUserLevel1Id());
        return userUplineDtlsRepository.save(userUplineDtls);
    }

    @Override
    public Optional<UserUplineDtls> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required");
        }
        logger.info("Finding UserUplineDtls by ID: {}", id);
        return userUplineDtlsRepository.findById(id);
    }

    @Override
    public List<UserUplineDtls> findByUserLevel1Id(Long userLevel1Id) {
        if (userLevel1Id == null || userLevel1Id <= 0) {
            throw new IllegalArgumentException("User Level 1 ID is required and must be positive");
        }
        logger.info("Finding UserUplineDtls by userLevel1Id: {}", userLevel1Id);
        return userUplineDtlsRepository.findByUserLevel1Id(userLevel1Id);
    }

    @Override
    @Transactional
    public UserUplineDtls update(UserUplineDtls userUplineDtls) {
        if (userUplineDtls.getId() == null) {
            throw new ValidationException("ID is required for update");
        }
        if (!userUplineDtlsRepository.existsById(userUplineDtls.getId())) {
            throw new ValidationException("UserUplineDtls not found with ID: " + userUplineDtls.getId());
        }
        logger.info("Updating UserUplineDtls with ID: {}", userUplineDtls.getId());
        return userUplineDtlsRepository.update(userUplineDtls);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required");
        }
        if (!userUplineDtlsRepository.existsById(id)) {
            throw new ValidationException("UserUplineDtls not found with ID: " + id);
        }
        logger.info("Deleting UserUplineDtls with ID: {}", id);
        userUplineDtlsRepository.delete(id);
    }

    @Override
    public List<UserUplineDtls> findAll() {
        logger.info("Finding all UserUplineDtls");
        return userUplineDtlsRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return userUplineDtlsRepository.existsById(id);
    }
}
