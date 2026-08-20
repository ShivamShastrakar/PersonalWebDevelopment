package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.UserTenant;

public interface UserTenantRepository {
    UserTenant save(UserTenant userTenant);
    Optional<UserTenant> findById(Long id);
    List<UserTenant> findAll();
    UserTenant update(UserTenant userTenant);
    void delete(Long id);
    List<UserTenant> findByUserId(Long userId);
}