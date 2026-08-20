package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.OtpAuth;

public interface OtpAuthService {
    OtpAuth generateAndSave(OtpAuth otpAuth);
    Optional<OtpAuth> findById(Long id);
    List<OtpAuth> findAll();
    OtpAuth update(OtpAuth otpAuth);
    void delete(Long id);
    Optional<OtpAuth> findByEmail(String email);
    Optional<OtpAuth> findByEmailAndMobile(String email, String mobile);
    boolean validateOtp(OtpAuth otpAuth);
    public void delete(String email, String mobile);
}