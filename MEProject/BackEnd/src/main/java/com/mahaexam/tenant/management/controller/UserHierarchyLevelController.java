package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import com.mahaexam.tenant.management.service.UserHierarchyLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hierarchy-levels")
@Tag(name = "User Hierarchy Level Management", description = "APIs for managing user hierarchy levels")
public class UserHierarchyLevelController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserHierarchyLevelController.class);
    private final UserHierarchyLevelService userHierarchyLevelService;

    public UserHierarchyLevelController(UserHierarchyLevelService userHierarchyLevelService) {
        this.userHierarchyLevelService = userHierarchyLevelService;
    }

    @PostMapping
    @Operation(summary = "Create a new user hierarchy level")
    public ResponseEntity<?> createUserHierarchyLevel(@Valid @RequestBody UserHierarchyLevel userHierarchyLevel) {
        try {
            logger.info("Creating user hierarchy level: {}", userHierarchyLevel.getLevelName());
            UserBean user = getUser();
            userHierarchyLevel.setTenantId(user.getTenantId());
//            userHierarchyLevel.setCreatedBy(user.getUserId());
            UserHierarchyLevel savedLevel = userHierarchyLevelService.save(userHierarchyLevel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLevel);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating user hierarchy level: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ValidationException e) {
            logger.error("Validation error creating user hierarchy level: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user hierarchy level by ID")
    public ResponseEntity<?> getUserHierarchyLevelById(@PathVariable Integer id) {
        try {
            logger.info("Fetching user hierarchy level with ID: {}", id);
            Optional<UserHierarchyLevel> levelOpt = userHierarchyLevelService.findById(id);
            return levelOpt.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("User Hierarchy Level not found with ID: " + id));
        } catch (ValidationException e) {
            logger.error("User hierarchy level not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/name/{levelName}")
    @Operation(summary = "Get user hierarchy level by name")
    public ResponseEntity<?> getUserHierarchyLevelByName(@PathVariable String levelName) {
        try {
            logger.info("Fetching user hierarchy level with name: {}", levelName);
            Optional<UserHierarchyLevel> levelOpt = userHierarchyLevelService.findByLevelName(levelName);
            return levelOpt.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("User Hierarchy Level not found with name: " + levelName));
        } catch (ValidationException e) {
            logger.error("User hierarchy level not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/tenant/{tenantId}")
    @Operation(summary = "Get all user hierarchy levels for a tenant")
    public ResponseEntity<?> getUserHierarchyLevelsByTenant(@PathVariable Long tenantId) {
        try {
        	UserBean user = getUser();
               	if (tenantId == null || tenantId <= 0) {
               		tenantId = user.getTenantId();
//				throw new IllegalArgumentException("Tenant ID is required and must be positive");
			}
            logger.info("Fetching user hierarchy levels for tenant ID: {}", tenantId);
            List<UserHierarchyLevel> levels = userHierarchyLevelService.findByTenantId(tenantId);
            return ResponseEntity.ok(levels);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching user hierarchy levels: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (Exception e) {
            logger.error("Error fetching user hierarchy levels for tenant {}: {}", tenantId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching user hierarchy levels"));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user hierarchy level")
    public ResponseEntity<?> updateUserHierarchyLevel(@PathVariable Integer id, @Valid @RequestBody UserHierarchyLevel userHierarchyLevel) {
        try {
            logger.info("Updating user hierarchy level with ID: {}", id);
            UserBean user = getUser();
            userHierarchyLevel.setId(id);
            userHierarchyLevel.setTenantId(user.getTenantId());
            UserHierarchyLevel updatedLevel = userHierarchyLevelService.update(userHierarchyLevel);
            return ResponseEntity.ok(updatedLevel);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating user hierarchy level: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ValidationException e) {
            logger.error("Validation error updating user hierarchy level: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating user hierarchy level with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error updating user hierarchy level"));
        }
    }

    @GetMapping
    @Operation(summary = "Get all user hierarchy levels")
    public ResponseEntity<List<UserHierarchyLevel>> getAllUserHierarchyLevels() {
        try {
            logger.info("Fetching all user hierarchy levels");
            List<UserHierarchyLevel> levels = userHierarchyLevelService.findAll();
            return ResponseEntity.ok(levels);
        } catch (Exception e) {
            logger.error("Error fetching all user hierarchy levels: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user hierarchy level")
    public ResponseEntity<?> deleteUserHierarchyLevel(@PathVariable Integer id) {
        try {
            logger.info("Deleting user hierarchy level with ID: {}", id);
            userHierarchyLevelService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            logger.error("User hierarchy level not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            logger.error("Error deleting user hierarchy level with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error deleting user hierarchy level"));
        }
    }
}
