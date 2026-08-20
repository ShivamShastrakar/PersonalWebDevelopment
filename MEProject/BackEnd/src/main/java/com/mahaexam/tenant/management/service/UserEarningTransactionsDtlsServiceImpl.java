package com.mahaexam.tenant.management.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserEarningTransactionsDtls;
import com.mahaexam.tenant.management.repository.UserEarningTransactionsDtlsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserEarningTransactionsDtlsServiceImpl implements UserEarningTransactionsDtlsService {
    private static final Logger logger = LoggerFactory.getLogger(UserEarningTransactionsDtlsServiceImpl.class);
    private final UserEarningTransactionsDtlsRepository userEarningTransactionsDtlsRepository;

    public UserEarningTransactionsDtlsServiceImpl(UserEarningTransactionsDtlsRepository userEarningTransactionsDtlsRepository) {
        this.userEarningTransactionsDtlsRepository = userEarningTransactionsDtlsRepository;
    }

    @Override
    @Transactional
    public UserEarningTransactionsDtls save(UserEarningTransactionsDtls dto) {
        if (dto.getStudentId() == null) {
            throw new ValidationException("Student ID is required");
        }
        if (dto.getStudentPackageId() == null) {
            throw new ValidationException("Student Package ID is required");
        }
        logger.info("Saving UserEarningTransactionsDtls for studentId: {}", dto.getStudentId());
        return userEarningTransactionsDtlsRepository.save(dto);
    }

    @Override
    public Optional<UserEarningTransactionsDtls> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required");
        }
        logger.info("Finding UserEarningTransactionsDtls by ID: {}", id);
        return userEarningTransactionsDtlsRepository.findById(id);
    }

    @Override
    public List<UserEarningTransactionsDtls> findByReferralUserId(Long referralUserId) {
        if (referralUserId == null || referralUserId <= 0) {
            throw new IllegalArgumentException("Referral User ID is required and must be positive");
        }
        logger.info("Finding UserEarningTransactionsDtls by referralUserId: {}", referralUserId);
        return userEarningTransactionsDtlsRepository.findByReferralUserId(referralUserId);
    }

    @Override
    public List<UserEarningTransactionsDtls> findByStudentId(Long studentId) {
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student ID is required and must be positive");
        }
        logger.info("Finding UserEarningTransactionsDtls by studentId: {}", studentId);
        return userEarningTransactionsDtlsRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public UserEarningTransactionsDtls update(UserEarningTransactionsDtls dto) {
        if (dto.getId() == null) {
            throw new ValidationException("ID is required for update");
        }
        if (!userEarningTransactionsDtlsRepository.existsById(dto.getId())) {
            throw new ValidationException("UserEarningTransactionsDtls not found with ID: " + dto.getId());
        }
        logger.info("Updating UserEarningTransactionsDtls with ID: {}", dto.getId());
        return userEarningTransactionsDtlsRepository.update(dto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required");
        }
        if (!userEarningTransactionsDtlsRepository.existsById(id)) {
            throw new ValidationException("UserEarningTransactionsDtls not found with ID: " + id);
        }
        logger.info("Deleting UserEarningTransactionsDtls with ID: {}", id);
        userEarningTransactionsDtlsRepository.delete(id);
    }

    @Override
    public List<UserEarningTransactionsDtls> findAll() {
        logger.info("Finding all UserEarningTransactionsDtls");
        return userEarningTransactionsDtlsRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return userEarningTransactionsDtlsRepository.existsById(id);
    }
}
