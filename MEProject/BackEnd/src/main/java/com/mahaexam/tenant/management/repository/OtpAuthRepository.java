package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.OtpAuth;

public interface OtpAuthRepository {
    OtpAuth save(OtpAuth otpAuth);
    Optional<OtpAuth> findById(Long id);
    List<OtpAuth> findAll();
    OtpAuth update(OtpAuth otpAuth);
    void delete(Long id);
    Optional<OtpAuth> findByEmail(String email);
    Optional<OtpAuth> findByMobile(String mobile);
    Optional<OtpAuth> findByEmailAndMobile(String email, String mobile);
    public void delete(String email, String mobile);
}