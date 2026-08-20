package com.mahaexam.tenant.management.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import com.mahaexam.tenant.management.repository.UserHierarchyLevelRepository;
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
public class UserHierarchyLevelServiceImpl implements UserHierarchyLevelService {
    private static final Logger logger = LoggerFactory.getLogger(UserHierarchyLevelServiceImpl.class);
    private final UserHierarchyLevelRepository userHierarchyLevelRepository;

    public UserHierarchyLevelServiceImpl(UserHierarchyLevelRepository userHierarchyLevelRepository) {
        this.userHierarchyLevelRepository = userHierarchyLevelRepository;
    }

    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'hierarchy_level_' + #userHierarchyLevel.id")
    public UserHierarchyLevel save(UserHierarchyLevel userHierarchyLevel) {
        validateUserHierarchyLevel(userHierarchyLevel);
        logger.info("Saving UserHierarchyLevel with name: {}", userHierarchyLevel.getLevelName());
        return userHierarchyLevelRepository.save(userHierarchyLevel);
    }

    @Override
    @Cacheable(value = "configCache", key = "'hierarchy_level_' + #id")
    public Optional<UserHierarchyLevel> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Hierarchy Level ID is required");
        }
        logger.info("Finding UserHierarchyLevel by ID: {}", id);
        return userHierarchyLevelRepository.findById(id);
    }

    @Override
    public Optional<UserHierarchyLevel> findByLevelName(String levelName) {
        if (levelName == null || levelName.trim().isEmpty()) {
            throw new IllegalArgumentException("Level Name is required");
        }
        logger.info("Finding UserHierarchyLevel by level name: {}", levelName);
        return userHierarchyLevelRepository.findByLevelName(levelName.trim());
    }

    @Override
    public List<UserHierarchyLevel> findByTenantId(Long tenantId) {
        if (tenantId == null || tenantId <= 0) {
            throw new IllegalArgumentException("Tenant ID is required and must be positive");
        }
        logger.info("Finding UserHierarchyLevels by tenant ID: {}", tenantId);
        return userHierarchyLevelRepository.findByTenantId(tenantId);
    }
    
    @Override
    public UserHierarchyLevel findByByGivenLevelOrderIdAndTenantId(Long tenantId, Integer levelOrderId){
    			if (tenantId == null || tenantId <= 0) {
			throw new IllegalArgumentException("Tenant ID is required and must be positive");
		}
		if (levelOrderId == null || levelOrderId < 0) {
			throw new IllegalArgumentException("Level Order ID is required and must be non-negative");
		}
		logger.info("Finding UserHierarchyLevels by tenant ID: {} and level order ID: {}", tenantId, levelOrderId);
		return userHierarchyLevelRepository.findByByGivenLevelOrderIdAndTenantId(tenantId, levelOrderId);
    }

    @Override
    @Transactional
    @CachePut(value = "configCache", key = "'hierarchy_level_' + #userHierarchyLevel.id")
    public UserHierarchyLevel update(UserHierarchyLevel userHierarchyLevel) {
        validateUserHierarchyLevel(userHierarchyLevel);
        if (userHierarchyLevel.getId() == null) {
            throw new IllegalArgumentException("Hierarchy Level ID is required for update");
        }
        if (!userHierarchyLevelRepository.existsById(userHierarchyLevel.getId())) {
            throw new ValidationException("UserHierarchyLevel with ID " + userHierarchyLevel.getId() + " does not exist");
        }
        logger.info("Updating UserHierarchyLevel with ID: {}", userHierarchyLevel.getId());
        return userHierarchyLevelRepository.update(userHierarchyLevel);
    }

    @Override
    @Transactional
    @CacheEvict(value = "configCache", key = "'hierarchy_level_' + #id")
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Hierarchy Level ID is required");
        }
        if (!userHierarchyLevelRepository.existsById(id)) {
            throw new ValidationException("UserHierarchyLevel with ID " + id + " does not exist");
        }
        logger.info("Deleting UserHierarchyLevel with ID: {}", id);
        userHierarchyLevelRepository.delete(id);
    }

    @Override
    public List<UserHierarchyLevel> findAll() {
        logger.info("Fetching all UserHierarchyLevels");
        return userHierarchyLevelRepository.findAll();
    }

    @Override
    public boolean existsById(Integer id) {
        if (id == null) {
            return false;
        }
        return userHierarchyLevelRepository.existsById(id);
    }

    private void validateUserHierarchyLevel(UserHierarchyLevel userHierarchyLevel) {
        if (userHierarchyLevel == null) {
            throw new IllegalArgumentException("UserHierarchyLevel cannot be null");
        }
        if (userHierarchyLevel.getLevelName() == null || userHierarchyLevel.getLevelName().trim().isEmpty()) {
            throw new IllegalArgumentException("Level Name is required");
        }
        if (userHierarchyLevel.getLevelOrder() == null || userHierarchyLevel.getLevelOrder() < 0) {
            throw new IllegalArgumentException("Level Order is required and must be non-negative");
        }
        if (userHierarchyLevel.getTenantId() == null || userHierarchyLevel.getTenantId() <= 0) {
            throw new IllegalArgumentException("Tenant ID is required and must be positive");
        }

        Optional<UserHierarchyLevel> existingLevel = userHierarchyLevelRepository.findByLevelName(userHierarchyLevel.getLevelName().trim());

        if (userHierarchyLevel.getId() == null) {
            // On create
            if (existingLevel.isPresent()) {
                throw new ValidationException("Level name '" + userHierarchyLevel.getLevelName() + "' already exists");
            }
        } else {
            // On update
            if (existingLevel.isPresent() && !existingLevel.get().getId().equals(userHierarchyLevel.getId())) {
                throw new ValidationException("Level name '" + userHierarchyLevel.getLevelName() + "' is already used by another level");
            }
        }
    }
}
