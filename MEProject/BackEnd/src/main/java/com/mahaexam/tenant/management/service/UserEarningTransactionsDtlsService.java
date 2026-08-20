package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.UserEarningTransactionsDtls;

import java.util.List;
import java.util.Optional;

public interface UserEarningTransactionsDtlsService {
    UserEarningTransactionsDtls save(UserEarningTransactionsDtls userEarningTransactionsDtls);

    Optional<UserEarningTransactionsDtls> findById(Long id);

    List<UserEarningTransactionsDtls> findByReferralUserId(Long referralUserId);

    List<UserEarningTransactionsDtls> findByStudentId(Long studentId);

    UserEarningTransactionsDtls update(UserEarningTransactionsDtls userEarningTransactionsDtls);

    void delete(Long id);

    List<UserEarningTransactionsDtls> findAll();

    boolean existsById(Long id);
}
