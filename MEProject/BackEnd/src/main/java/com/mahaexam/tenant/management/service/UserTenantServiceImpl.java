package com.mahaexam.tenant.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.tenant.management.model.UserTenant;
import com.mahaexam.tenant.management.repository.UserTenantRepository;

@Service
public class UserTenantServiceImpl implements UserTenantService {
    private final UserTenantRepository repository;

    public UserTenantServiceImpl(UserTenantRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public UserTenant save(UserTenant userTenant) {
        validateUserTenant(userTenant);
        if (userTenant.getCreatedAt() == null) {
            userTenant.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(userTenant);
    }

    @Override
    public Optional<UserTenant> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<UserTenant> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public UserTenant update(UserTenant userTenant) {
        validateUserTenant(userTenant);
        return repository.update(userTenant);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public List<UserTenant> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    private void validateUserTenant(UserTenant userTenant) {
        if (userTenant.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (userTenant.getTenantId() == null) {
            throw new IllegalArgumentException("Tenant ID is required");
        }
    }
}
