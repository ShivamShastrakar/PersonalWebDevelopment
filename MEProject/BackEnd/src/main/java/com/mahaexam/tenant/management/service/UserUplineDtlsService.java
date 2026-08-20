package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.UserUplineDtls;

import java.util.List;
import java.util.Optional;

public interface UserUplineDtlsService {
    UserUplineDtls save(UserUplineDtls userUplineDtls);

    Optional<UserUplineDtls> findById(Long id);

    List<UserUplineDtls> findByUserLevel1Id(Long userLevel1Id);

    UserUplineDtls update(UserUplineDtls userUplineDtls);

    void delete(Long id);

    List<UserUplineDtls> findAll();

    boolean existsById(Long id);
}
