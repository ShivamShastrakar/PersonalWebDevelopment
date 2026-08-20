package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.MyEarningStats;
import com.mahaexam.tenant.management.service.MyEarningStatsService;
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
@RequestMapping("/api/my-earning-stats")
@Tag(name = "My Earning Stats Management", description = "APIs for managing my earning stats")
public class MyEarningStatsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MyEarningStatsController.class);
    private final MyEarningStatsService myEarningStatsService;

    public MyEarningStatsController(MyEarningStatsService myEarningStatsService) {
        this.myEarningStatsService = myEarningStatsService;
    }

    @PostMapping
    @Operation(summary = "Create a new my earning stats record")
    public ResponseEntity<?> createMyEarningStats(@Valid @RequestBody MyEarningStats dto) {
        try {
            logger.info("Creating my earning stats for userId: {}", dto.getUserId());
            MyEarningStats saved = myEarningStatsService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating my earning stats: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (ValidationException e) {
            logger.error("Validation error creating my earning stats: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get my earning stats by ID")
    public ResponseEntity<?> getMyEarningStatsById(@PathVariable Long id) {
        try {
            logger.info("Fetching my earning stats with ID: {}", id);
            Optional<MyEarningStats> result = myEarningStatsService.findById(id);
            return result.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("My Earning Stats not found with ID: " + id));
        } catch (ValidationException e) {
            logger.error("My earning stats not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get my earning stats by User ID")
    public ResponseEntity<?> getMyEarningStatsByUserId(@PathVariable Long userId) {
        try {
            logger.info("Fetching my earning stats for userId: {}", userId);
            List<MyEarningStats> results = myEarningStatsService.findByUserId(userId);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching my earning stats: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all my earning stats")
    public ResponseEntity<?> getAllMyEarningStats() {
        logger.info("Fetching all my earning stats");
        List<MyEarningStats> results = myEarningStatsService.findAll();
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update my earning stats")
    public ResponseEntity<?> updateMyEarningStats(@PathVariable Long id, @Valid @RequestBody MyEarningStats dto) {
        try {
            dto.setId(id);
            logger.info("Updating my earning stats with ID: {}", id);
            MyEarningStats updated = myEarningStatsService.update(dto);
            return ResponseEntity.ok(updated);
        } catch (ValidationException e) {
            logger.error("Error updating my earning stats: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete my earning stats")
    public ResponseEntity<?> deleteMyEarningStats(@PathVariable Long id) {
        try {
            logger.info("Deleting my earning stats with ID: {}", id);
            myEarningStatsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException e) {
            logger.error("Error deleting my earning stats: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
