package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserHierarchyLevel;

import java.util.List;
import java.util.Optional;

public interface UserHierarchyLevelRepository {
    UserHierarchyLevel save(UserHierarchyLevel userHierarchyLevel);

    Optional<UserHierarchyLevel> findById(Integer id);

    Optional<UserHierarchyLevel> findByLevelName(String levelName);

    List<UserHierarchyLevel> findByTenantId(Long tenantId);
    
    UserHierarchyLevel findByByGivenLevelOrderIdAndTenantId(Long tenantId, Integer levelOrderId);

    UserHierarchyLevel update(UserHierarchyLevel userHierarchyLevel);

    void delete(Integer id);

    List<UserHierarchyLevel> findAll();

    boolean existsById(Integer id);
}
