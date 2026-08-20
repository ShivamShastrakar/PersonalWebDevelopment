package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;

import java.util.List;
import java.util.Optional;

public interface UserRoleHierarchyLevelMappingService {
    UserRoleHierarchyLevelMapping save(UserRoleHierarchyLevelMapping mapping);

    Optional<UserRoleHierarchyLevelMapping> findById(Integer id);

    UserRoleHierarchyLevelMapping update(UserRoleHierarchyLevelMapping mapping);

    void delete(Integer id);

    List<UserRoleHierarchyLevelMapping> findAll();

    List<UserRoleHierarchyLevelMapping> findByUserRoleId(Long userRoleId);

    List<UserRoleHierarchyLevelMapping> findByUserHierarchyLevelId(Integer hierarchyLevelId);

    Optional<UserRoleHierarchyLevelMapping> findByUserRoleIdAndHierarchyLevelId(Long userRoleId, Integer hierarchyLevelId);

    boolean existsById(Integer id);
    
    UserRoleHierarchyLevelMapping findUserRoleHierarchyForGivenRoleId(Long roleId);
}
