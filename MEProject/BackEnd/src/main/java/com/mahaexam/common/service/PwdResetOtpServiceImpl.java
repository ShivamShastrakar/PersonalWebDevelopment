package com.mahaexam.common.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.model.PwdResetOtp;
import com.mahaexam.common.repo.PwdResetOtpRepository;
import com.mahaexam.tenant.management.util.SessionIdentifierGenerator;

@Service
public class PwdResetOtpServiceImpl implements PwdResetOtpService {

    private static final Logger logger = LogManager.getLogger(PwdResetOtpServiceImpl.class);

    private final PwdResetOtpRepository pwdResetOtpRepository;
    private final ConfigService configService;

    public PwdResetOtpServiceImpl(PwdResetOtpRepository pwdResetOtpRepository, ConfigService configService) {
        this.pwdResetOtpRepository = pwdResetOtpRepository;
        this.configService = configService;
    }

    @Override
    @Transactional
    public PwdResetOtp generateOtp(Long userId, int otpExpiryMinutes) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (otpExpiryMinutes <= 0) {
            throw new IllegalArgumentException("OTP expiry minutes must be positive");
        }

        Optional<Config> configOptional = configService.findByName(ConfigService.DEFAULT_SMS);
        String defaultSMS = configOptional.map(Config::getValue).orElse(null);

        // Generate and store OTP in pwd_reset_otp table BEFORE sending SMS
        String plaintext;
        if("1".equals(defaultSMS)){
            plaintext = "0000";
        }else {
            plaintext = SessionIdentifierGenerator.getOTP(AppConstants.OTP_LENGTH);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(otpExpiryMinutes);

        PwdResetOtp pwdResetOtp = PwdResetOtp.builder()
                .userId(userId)
                .otp(plaintext)
                .createdAt(now)
                .expiresAt(expiresAt)
                .build();

        PwdResetOtp savedOtp = pwdResetOtpRepository.save(pwdResetOtp);
        logger.info("Generated OTP for user ID: {} with expiry: {}", userId, expiresAt);

        return savedOtp;
    }

    @Override
    public boolean validateOtp(Long userId, String otp) {
        if (userId == null || otp == null || otp.trim().isEmpty()) {
            logger.warn("Invalid parameters for OTP validation - userId: {}, otp: {}", userId, otp != null ? "***" : "null");
            return false;
        }

        Optional<PwdResetOtp> otpRecord = pwdResetOtpRepository.findByUserIdAndOtp(userId, otp.trim());

        if (otpRecord.isPresent()) {
            logger.info("OTP validation successful for user ID: {}", userId);
            return true;
        } else {
            logger.warn("OTP validation failed for user ID: {} - OTP not found or expired", userId);
            return false;
        }
    }

    @Override
    @Transactional
    public Optional<PwdResetOtp> validateAndConsumeOtp(Long userId, String otp) {
        Optional<PwdResetOtp> otpRecord = pwdResetOtpRepository.findByUserIdAndOtp(userId, otp);

        if (otpRecord.isPresent()) {
            PwdResetOtp validOtp = otpRecord.get();

            // Delete the OTP after successful validation to prevent reuse
            pwdResetOtpRepository.deleteById(validOtp.getId());
            logger.info("OTP consumed and deleted for user ID: {}", userId);

            return Optional.of(validOtp);
        }

        logger.warn("Failed to validate and consume OTP for user ID: {}", userId);
        return Optional.empty();
    }

    @Override
    public List<PwdResetOtp> getValidOtpsByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return pwdResetOtpRepository.findValidOtpsByUserId(userId);
    }

    @Override
    public boolean hasReachedOtpLimit(Long userId, int maxOtpsAllowed) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (maxOtpsAllowed <= 0) {
            throw new IllegalArgumentException("Max OTPs allowed must be positive");
        }

        int currentValidOtps = pwdResetOtpRepository.countValidOtpsByUserId(userId);
        boolean limitReached = currentValidOtps >= maxOtpsAllowed;

        if (limitReached) {
            logger.warn("User ID: {} has reached OTP limit. Current: {}, Max: {}", userId, currentValidOtps, maxOtpsAllowed);
        }

        return limitReached;
    }

    @Override
    @Transactional
    public void deleteUserOtps(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        pwdResetOtpRepository.deleteByUserId(userId);
        logger.info("Deleted all OTPs for user ID: {}", userId);
    }

    @Override
    @Transactional
    public int cleanupExpiredOtps() {
        try {
            // Get count before deletion for logging
            // Note: This is an approximation since we can't get exact count from delete operation
            pwdResetOtpRepository.deleteExpiredOtps();
            logger.info("Cleanup completed for expired OTPs");
            return 1; // Return success indicator
        } catch (Exception e) {
            logger.error("Error during OTP cleanup: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to cleanup expired OTPs", e);
        }
    }

    @Override
    public Optional<PwdResetOtp> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return pwdResetOtpRepository.findById(id);
    }
}
