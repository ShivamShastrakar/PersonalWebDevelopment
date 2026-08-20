package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.PwdResetOtp;

public interface PwdResetOtpService {

    /**
     * Generate and save a new OTP for password reset
     * @param userId User ID for whom OTP is generated
     * @param otpExpiryMinutes OTP expiry time in minutes
     * @return Generated PwdResetOtp entity
     */
    PwdResetOtp generateOtp(Long userId, int otpExpiryMinutes);

    /**
     * Validate OTP for a user
     * @param userId User ID
     * @param otp OTP to validate
     * @return true if OTP is valid and not expired, false otherwise
     */
    boolean validateOtp(Long userId, String otp);

    /**
     * Validate and consume OTP (delete after validation)
     * @param userId User ID
     * @param otp OTP to validate and consume
     * @return Optional PwdResetOtp if valid, empty otherwise
     */
    Optional<PwdResetOtp> validateAndConsumeOtp(Long userId, String otp);

    /**
     * Find active OTPs for a user
     * @param userId User ID
     * @return List of valid OTPs for the user
     */
    List<PwdResetOtp> getValidOtpsByUserId(Long userId);

    /**
     * Check if user has reached maximum OTP generation limit
     * @param userId User ID
     * @param maxOtpsAllowed Maximum OTPs allowed per user
     * @return true if limit reached, false otherwise
     */
    boolean hasReachedOtpLimit(Long userId, int maxOtpsAllowed);

    /**
     * Delete all OTPs for a user
     * @param userId User ID
     */
    void deleteUserOtps(Long userId);

    /**
     * Clean up expired OTPs from database
     * @return Number of expired OTPs deleted
     */
    int cleanupExpiredOtps();

    /**
     * Get OTP by ID
     * @param id OTP ID
     * @return Optional PwdResetOtp
     */
    Optional<PwdResetOtp> findById(Long id);
}
