package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.PwdResetOtp;

public interface PwdResetOtpRepository {
    PwdResetOtp save(PwdResetOtp pwdResetOtp);
    Optional<PwdResetOtp> findById(Long id);
    Optional<PwdResetOtp> findByUserIdAndOtp(Long userId, String otp);
    List<PwdResetOtp> findByUserId(Long userId);
    List<PwdResetOtp> findValidOtpsByUserId(Long userId);
    void deleteById(Long id);
    void deleteByUserId(Long userId);
    void deleteExpiredOtps();
    int countValidOtpsByUserId(Long userId);
}
