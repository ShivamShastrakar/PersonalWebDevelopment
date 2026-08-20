package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.ExamGroupPackageCategoryMapper;
import com.mahaexam.tenant.management.service.ExamGroupPackageCategoryMapperService;
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
@RequestMapping("/api/exam-group-package-category-mapper")
@Tag(name = "Exam Group Package Category Mapping Management", description = "APIs for managing exam group to package category mappings")
public class ExamGroupPackageCategoryMapperController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamGroupPackageCategoryMapperController.class);
    private final ExamGroupPackageCategoryMapperService service;

    public ExamGroupPackageCategoryMapperController(ExamGroupPackageCategoryMapperService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new exam group to package category mapping")
    public ResponseEntity<?> create(@Valid @RequestBody ExamGroupPackageCategoryMapper mapping) {
        try {
            logger.info("Creating exam group package category mapping for examGroupId: {}", mapping.getExamGroupId());
            ExamGroupPackageCategoryMapper saved = service.save(mapping);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get mapping by ID")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            logger.info("Fetching mapping with ID: {}", id);
            Optional<ExamGroupPackageCategoryMapper> result = service.findById(id);
            return result.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("Mapping not found with ID: " + id));
        } catch (ValidationException e) {
            logger.error("Mapping not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all mappings")
    public ResponseEntity<List<ExamGroupPackageCategoryMapper>> getAll() {
        logger.info("Fetching all exam group package category mappings");
        List<ExamGroupPackageCategoryMapper> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/exam-group/{examGroupId}")
    @Operation(summary = "Get all mappings by exam group ID")
    public ResponseEntity<?> getByExamGroupId(@PathVariable Integer examGroupId) {
        try {
            logger.info("Fetching mappings for examGroupId: {}", examGroupId);
            List<ExamGroupPackageCategoryMapper> list = service.findByExamGroupId(examGroupId);
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching mappings: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/package-category/{packageCategoryId}")
    @Operation(summary = "Get all mappings by package category ID")
    public ResponseEntity<?> getByPackageCategoryId(@PathVariable Integer packageCategoryId) {
        try {
            logger.info("Fetching mappings for packageCategoryId: {}", packageCategoryId);
            List<ExamGroupPackageCategoryMapper> list = service.findByPackageCategoryId(packageCategoryId);
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching mappings: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a mapping")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody ExamGroupPackageCategoryMapper mapping) {
        try {
            mapping.setId(id);
            logger.info("Updating mapping with ID: {}", id);
            ExamGroupPackageCategoryMapper updated = service.update(mapping);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a mapping")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            logger.info("Deleting mapping with ID: {}", id);
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting mapping: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
