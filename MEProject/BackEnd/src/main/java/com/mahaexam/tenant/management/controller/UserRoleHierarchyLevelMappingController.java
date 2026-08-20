package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import com.mahaexam.tenant.management.service.UserRoleHierarchyLevelMappingService;
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
@RequestMapping("/api/v1/user-role-hierarchy-mappings")
@Tag(name = "User Role Hierarchy Level Mapping Management", description = "APIs for managing user role hierarchy level mappings")
public class UserRoleHierarchyLevelMappingController {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleHierarchyLevelMappingController.class);
    private final UserRoleHierarchyLevelMappingService mappingService;

    public UserRoleHierarchyLevelMappingController(UserRoleHierarchyLevelMappingService mappingService) {
        this.mappingService = mappingService;
    }

    @PostMapping
    @Operation(summary = "Create a new user role hierarchy level mapping")
    public ResponseEntity<?> createMapping(@Valid @RequestBody UserRoleHierarchyLevelMapping mapping) {
        try {
            logger.info("Creating user role hierarchy level mapping for user role ID: {} and hierarchy level ID: {}", 
                    mapping.getUserRoleId(), mapping.getUserHierarchyLevelId());
            UserRoleHierarchyLevelMapping savedMapping = mappingService.save(mapping);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMapping);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ValidationException e) {
            logger.error("Validation error creating mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get mapping by ID")
    public ResponseEntity<?> getMappingById(@PathVariable Integer id) {
        try {
            logger.info("Fetching mapping with ID: {}", id);
            Optional<UserRoleHierarchyLevelMapping> mappingOpt = mappingService.findById(id);
            return mappingOpt.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("Mapping not found with ID: " + id));
        } catch (ValidationException e) {
            logger.error("Mapping not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user role hierarchy level mapping")
    public ResponseEntity<?> updateMapping(@PathVariable Integer id, @Valid @RequestBody UserRoleHierarchyLevelMapping mapping) {
        try {
            logger.info("Updating mapping with ID: {}", id);
            mapping.setId(id);
            UserRoleHierarchyLevelMapping updatedMapping = mappingService.update(mapping);
            return ResponseEntity.ok(updatedMapping);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ValidationException e) {
            logger.error("Validation error updating mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating mapping with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error updating mapping"));
        }
    }

    @GetMapping
    @Operation(summary = "Get all user role hierarchy level mappings")
    public ResponseEntity<List<UserRoleHierarchyLevelMapping>> getAllMappings() {
        try {
            logger.info("Fetching all mappings");
            List<UserRoleHierarchyLevelMapping> mappings = mappingService.findAll();
            return ResponseEntity.ok(mappings);
        } catch (Exception e) {
            logger.error("Error fetching all mappings: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/by-role/{userRoleId}")
    @Operation(summary = "Get mappings by user role ID")
    public ResponseEntity<?> getMappingsByUserRoleId(@PathVariable Long userRoleId) {
        try {
            logger.info("Fetching mappings for user role ID: {}", userRoleId);
            List<UserRoleHierarchyLevelMapping> mappings = mappingService.findByUserRoleId(userRoleId);
            return ResponseEntity.ok(mappings);
        } catch (Exception e) {
            logger.error("Error fetching mappings by user role ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching mappings"));
        }
    }

    @GetMapping("/by-hierarchy-level/{hierarchyLevelId}")
    @Operation(summary = "Get mappings by hierarchy level ID")
    public ResponseEntity<?> getMappingsByHierarchyLevelId(@PathVariable Integer hierarchyLevelId) {
        try {
            logger.info("Fetching mappings for hierarchy level ID: {}", hierarchyLevelId);
            List<UserRoleHierarchyLevelMapping> mappings = mappingService.findByUserHierarchyLevelId(hierarchyLevelId);
            return ResponseEntity.ok(mappings);
        } catch (Exception e) {
            logger.error("Error fetching mappings by hierarchy level ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching mappings"));
        }
    }

    @GetMapping("/by-role/{userRoleId}/and-level/{hierarchyLevelId}")
    @Operation(summary = "Get mapping by user role ID and hierarchy level ID")
    public ResponseEntity<?> getMappingByRoleAndLevel(@PathVariable Long userRoleId, @PathVariable Integer hierarchyLevelId) {
        try {
            logger.info("Fetching mapping for user role ID: {} and hierarchy level ID: {}", userRoleId, hierarchyLevelId);
            Optional<UserRoleHierarchyLevelMapping> mappingOpt = mappingService.findByUserRoleIdAndHierarchyLevelId(userRoleId, hierarchyLevelId);
            return mappingOpt.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("Mapping not found for user role ID: " + userRoleId + " and hierarchy level ID: " + hierarchyLevelId));
        } catch (ValidationException e) {
            logger.error("Mapping not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user role hierarchy level mapping")
    public ResponseEntity<?> deleteMapping(@PathVariable Integer id) {
        try {
            logger.info("Deleting mapping with ID: {}", id);
            mappingService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            logger.error("Mapping not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            logger.error("Error deleting mapping with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error deleting mapping"));
        }
    }
}
