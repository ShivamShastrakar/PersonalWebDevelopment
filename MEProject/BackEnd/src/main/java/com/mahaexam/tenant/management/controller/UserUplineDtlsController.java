package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserUplineDtls;
import com.mahaexam.tenant.management.service.UserUplineDtlsService;
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
@RequestMapping("/api/user-upline-dtls")
@Tag(name = "User Upline Details Management", description = "APIs for managing user upline details")
public class UserUplineDtlsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserUplineDtlsController.class);
    private final UserUplineDtlsService userUplineDtlsService;

    public UserUplineDtlsController(UserUplineDtlsService userUplineDtlsService) {
        this.userUplineDtlsService = userUplineDtlsService;
    }

    @PostMapping
    @Operation(summary = "Create a new user upline detail")
    public ResponseEntity<?> createUserUplineDtls(@Valid @RequestBody UserUplineDtls userUplineDtls) {
        try {
            logger.info("Creating user upline detail for userLevel1Id: {}", userUplineDtls.getUserLevel1Id());
            UserUplineDtls saved = userUplineDtlsService.save(userUplineDtls);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating user upline detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ValidationException e) {
            logger.error("Validation error creating user upline detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user upline detail by ID")
    public ResponseEntity<?> getUserUplineDtlsById(@PathVariable Long id) {
        try {
            logger.info("Fetching user upline detail with ID: {}", id);
            Optional<UserUplineDtls> result = userUplineDtlsService.findById(id);
            return result.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("User Upline Detail not found with ID: " + id));
        } catch (ValidationException e) {
            logger.error("User upline detail not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/user-level1/{userLevel1Id}")
    @Operation(summary = "Get user upline details by User Level 1 ID")
    public ResponseEntity<?> getUserUplineDtlsByUserLevel1Id(@PathVariable Long userLevel1Id) {
        try {
            logger.info("Fetching user upline details for userLevel1Id: {}", userLevel1Id);
            List<UserUplineDtls> results = userUplineDtlsService.findByUserLevel1Id(userLevel1Id);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching user upline details: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all user upline details")
    public ResponseEntity<?> getAllUserUplineDtls() {
        logger.info("Fetching all user upline details");
        List<UserUplineDtls> results = userUplineDtlsService.findAll();
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user upline detail")
    public ResponseEntity<?> updateUserUplineDtls(@PathVariable Long id, @Valid @RequestBody UserUplineDtls userUplineDtls) {
        try {
            userUplineDtls.setId(id);
            logger.info("Updating user upline detail with ID: {}", id);
            UserUplineDtls updated = userUplineDtlsService.update(userUplineDtls);
            return ResponseEntity.ok(updated);
        } catch (ValidationException e) {
            logger.error("Error updating user upline detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user upline detail")
    public ResponseEntity<?> deleteUserUplineDtls(@PathVariable Long id) {
        try {
            logger.info("Deleting user upline detail with ID: {}", id);
            userUplineDtlsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            logger.error("Error deleting user upline detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
