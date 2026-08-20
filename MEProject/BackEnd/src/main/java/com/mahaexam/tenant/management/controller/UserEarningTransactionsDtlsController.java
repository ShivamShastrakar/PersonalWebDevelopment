package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.UserEarningTransactionsDtls;
import com.mahaexam.tenant.management.service.UserEarningTransactionsDtlsService;
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
@RequestMapping("/api/user-earning-transactions-dtls")
@Tag(name = "User Earning Transactions Details Management", description = "APIs for managing user earning transaction details")
public class UserEarningTransactionsDtlsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserEarningTransactionsDtlsController.class);
    private final UserEarningTransactionsDtlsService userEarningTransactionsDtlsService;

    public UserEarningTransactionsDtlsController(UserEarningTransactionsDtlsService userEarningTransactionsDtlsService) {
        this.userEarningTransactionsDtlsService = userEarningTransactionsDtlsService;
    }

    @PostMapping
    @Operation(summary = "Create a new user earning transaction detail")
    public ResponseEntity<?> createUserEarningTransactionsDtls(@Valid @RequestBody UserEarningTransactionsDtls dto) {
        try {
            logger.info("Creating user earning transaction detail for studentId: {}", dto.getStudentId());
            UserEarningTransactionsDtls saved = userEarningTransactionsDtlsService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating user earning transaction detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ValidationException e) {
            logger.error("Validation error creating user earning transaction detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user earning transaction detail by ID")
    public ResponseEntity<?> getUserEarningTransactionsDtlsById(@PathVariable Long id) {
        try {
            logger.info("Fetching user earning transaction detail with ID: {}", id);
            Optional<UserEarningTransactionsDtls> result = userEarningTransactionsDtlsService.findById(id);
            return result.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("User Earning Transaction Detail not found with ID: " + id));
        } catch (ValidationException e) {
            logger.error("User earning transaction detail not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/referral-user/{referralUserId}")
    @Operation(summary = "Get user earning transaction details by Referral User ID")
    public ResponseEntity<?> getUserEarningTransactionsDtlsByReferralUserId(@PathVariable Long referralUserId) {
        try {
            logger.info("Fetching user earning transaction details for referralUserId: {}", referralUserId);
            List<UserEarningTransactionsDtls> results = userEarningTransactionsDtlsService.findByReferralUserId(referralUserId);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching user earning transaction details: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get user earning transaction details by Student ID")
    public ResponseEntity<?> getUserEarningTransactionsDtlsByStudentId(@PathVariable Long studentId) {
        try {
            logger.info("Fetching user earning transaction details for studentId: {}", studentId);
            List<UserEarningTransactionsDtls> results = userEarningTransactionsDtlsService.findByStudentId(studentId);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching user earning transaction details: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all user earning transaction details")
    public ResponseEntity<?> getAllUserEarningTransactionsDtls() {
        logger.info("Fetching all user earning transaction details");
        List<UserEarningTransactionsDtls> results = userEarningTransactionsDtlsService.findAll();
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user earning transaction detail")
    public ResponseEntity<?> updateUserEarningTransactionsDtls(@PathVariable Long id, @Valid @RequestBody UserEarningTransactionsDtls dto) {
        try {
            dto.setId(id);
            logger.info("Updating user earning transaction detail with ID: {}", id);
            UserEarningTransactionsDtls updated = userEarningTransactionsDtlsService.update(dto);
            return ResponseEntity.ok(updated);
        } catch (ValidationException e) {
            logger.error("Error updating user earning transaction detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user earning transaction detail")
    public ResponseEntity<?> deleteUserEarningTransactionsDtls(@PathVariable Long id) {
        try {
            logger.info("Deleting user earning transaction detail with ID: {}", id);
            userEarningTransactionsDtlsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            logger.error("Error deleting user earning transaction detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
