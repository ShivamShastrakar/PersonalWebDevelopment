package com.mahaexam.tenant.management.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import com.mahaexam.tenant.management.repository.UserRoleHierarchyLevelMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleHierarchyLevelMappingServiceImpl implements UserRoleHierarchyLevelMappingService {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleHierarchyLevelMappingServiceImpl.class);
    private final UserRoleHierarchyLevelMappingRepository mappingRepository;

    public UserRoleHierarchyLevelMappingServiceImpl(UserRoleHierarchyLevelMappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'hierarchy_mapping_' + #mapping.id")
    public UserRoleHierarchyLevelMapping save(UserRoleHierarchyLevelMapping mapping) {
        validateMapping(mapping);
        logger.info("Saving UserRoleHierarchyLevelMapping with user role ID: {} and hierarchy level ID: {}", 
                mapping.getUserRoleId(), mapping.getUserHierarchyLevelId());
        return mappingRepository.save(mapping);
    }

    @Override
    @Cacheable(value = "configCache", key = "'hierarchy_mapping_' + #id")
    public Optional<UserRoleHierarchyLevelMapping> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Mapping ID is required");
        }
        logger.info("Finding UserRoleHierarchyLevelMapping by ID: {}", id);
        return mappingRepository.findById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'hierarchy_mapping_' + #mapping.id")
    public UserRoleHierarchyLevelMapping update(UserRoleHierarchyLevelMapping mapping) {
        validateMapping(mapping);
        if (mapping.getId() == null) {
            throw new IllegalArgumentException("Mapping ID is required for update");
        }
        if (!mappingRepository.existsById(mapping.getId())) {
            throw new ValidationException("UserRoleHierarchyLevelMapping with ID " + mapping.getId() + " does not exist");
        }
        logger.info("Updating UserRoleHierarchyLevelMapping with ID: {}", mapping.getId());
        return mappingRepository.update(mapping);
    }

    @Override
    @Transactional
    @CacheEvict(value = "configCache", key = "'hierarchy_mapping_' + #id")
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Mapping ID is required");
        }
        if (!mappingRepository.existsById(id)) {
            throw new ValidationException("UserRoleHierarchyLevelMapping with ID " + id + " does not exist");
        }
        logger.info("Deleting UserRoleHierarchyLevelMapping with ID: {}", id);
        mappingRepository.delete(id);
    }

    @Override
    public List<UserRoleHierarchyLevelMapping> findAll() {
        logger.info("Fetching all UserRoleHierarchyLevelMappings");
        return mappingRepository.findAll();
    }

    @Override
    public List<UserRoleHierarchyLevelMapping> findByUserRoleId(Long userRoleId) {
        if (userRoleId == null) {
            throw new IllegalArgumentException("User Role ID is required");
        }
        logger.info("Finding UserRoleHierarchyLevelMappings by user role ID: {}", userRoleId);
        return mappingRepository.findByUserRoleId(userRoleId);
    }

    @Override
    public List<UserRoleHierarchyLevelMapping> findByUserHierarchyLevelId(Integer hierarchyLevelId) {
        if (hierarchyLevelId == null) {
            throw new IllegalArgumentException("Hierarchy Level ID is required");
        }
        logger.info("Finding UserRoleHierarchyLevelMappings by hierarchy level ID: {}", hierarchyLevelId);
        return mappingRepository.findByUserHierarchyLevelId(hierarchyLevelId);
    }

    @Override
    public Optional<UserRoleHierarchyLevelMapping> findByUserRoleIdAndHierarchyLevelId(Long userRoleId, Integer hierarchyLevelId) {
        if (userRoleId == null || hierarchyLevelId == null) {
            throw new IllegalArgumentException("User Role ID and Hierarchy Level ID are required");
        }
        logger.info("Finding UserRoleHierarchyLevelMapping by user role ID: {} and hierarchy level ID: {}", 
                userRoleId, hierarchyLevelId);
        return mappingRepository.findByUserRoleIdAndHierarchyLevelId(userRoleId, hierarchyLevelId);
    }

    @Override
    public boolean existsById(Integer id) {
        if (id == null) {
            return false;
        }
        return mappingRepository.existsById(id);
    }

    @Override
    public UserRoleHierarchyLevelMapping findUserRoleHierarchyForGivenRoleId(Long roleId) {
        if (roleId == null || roleId <= 0) {
            throw new IllegalArgumentException("Role ID is required and must be positive");
        }
        logger.info("Finding UserRoleHierarchyLevelMapping for role ID: {}", roleId);
        
        List<UserRoleHierarchyLevelMapping> mappings = mappingRepository.findByUserRoleId(roleId);
        
        if (mappings == null || mappings.isEmpty()) {
            logger.warn("No UserRoleHierarchyLevelMapping found for role ID: {}", roleId);
            throw new ValidationException("No hierarchy level mapping found for role ID: " + roleId);
        }
        
        // Return the first mapping found
        UserRoleHierarchyLevelMapping mapping = mappings.get(0);
        logger.info("Found UserRoleHierarchyLevelMapping for role ID: {} with hierarchy level ID: {}", 
                roleId, mapping.getUserHierarchyLevelId());
        return mapping;
    }

    // ...existing code...
    private void validateMapping(UserRoleHierarchyLevelMapping mapping) {
        if (mapping == null) {
            throw new IllegalArgumentException("UserRoleHierarchyLevelMapping cannot be null");
        }
        if (mapping.getUserRoleId() == null || mapping.getUserRoleId() <= 0) {
            throw new IllegalArgumentException("User Role ID is required and must be positive");
        }
        if (mapping.getUserHierarchyLevelId() == null || mapping.getUserHierarchyLevelId() <= 0) {
            throw new IllegalArgumentException("User Hierarchy Level ID is required and must be positive");
        }

        // Check if mapping already exists
        Optional<UserRoleHierarchyLevelMapping> existingMapping = mappingRepository.findByUserRoleIdAndHierarchyLevelId(
                mapping.getUserRoleId(), mapping.getUserHierarchyLevelId());

        if (mapping.getId() == null) {
            // On create
            if (existingMapping.isPresent()) {
                throw new ValidationException("Mapping for User Role ID " + mapping.getUserRoleId() + 
                        " and Hierarchy Level ID " + mapping.getUserHierarchyLevelId() + " already exists");
            }
        } else {
            // On update
            if (existingMapping.isPresent() && !existingMapping.get().getId().equals(mapping.getId())) {
                throw new ValidationException("Mapping for User Role ID " + mapping.getUserRoleId() + 
                        " and Hierarchy Level ID " + mapping.getUserHierarchyLevelId() + " is already used by another mapping");
            }
        }
    }
}
