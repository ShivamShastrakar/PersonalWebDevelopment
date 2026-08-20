package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.ExamGroupDtls;
import com.mahaexam.tenant.management.service.ExamGroupDtlsService;
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
@RequestMapping("/api/exam-group-dtls")
@Tag(name = "Exam Group Details Management", description = "APIs for managing exam group details")
public class ExamGroupDtlsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamGroupDtlsController.class);
    private final ExamGroupDtlsService examGroupDtlsService;

    public ExamGroupDtlsController(ExamGroupDtlsService examGroupDtlsService) {
        this.examGroupDtlsService = examGroupDtlsService;
    }

    @PostMapping
    @Operation(summary = "Create a new exam group detail")
    public ResponseEntity<?> create(@Valid @RequestBody ExamGroupDtls examGroupDtls) {
        try {
            logger.info("Creating exam group detail: {}", examGroupDtls.getName());
            ExamGroupDtls saved = examGroupDtlsService.save(examGroupDtls);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating exam group detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exam group detail by ID")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            logger.info("Fetching exam group detail with ID: {}", id);
            Optional<ExamGroupDtls> result = examGroupDtlsService.findById(id);
            return result.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ValidationException("Exam Group Detail not found with ID: " + id));
        } catch (ValidationException e) {
            logger.error("Exam group detail not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all exam group details")
    public ResponseEntity<List<ExamGroupDtls>> getAll() {
        logger.info("Fetching all exam group details");
        List<ExamGroupDtls> list = examGroupDtlsService.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an exam group detail")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody ExamGroupDtls examGroupDtls) {
        try {
            examGroupDtls.setId(id);
            logger.info("Updating exam group detail with ID: {}", id);
            ExamGroupDtls updated = examGroupDtlsService.update(examGroupDtls);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating exam group detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exam group detail")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            logger.info("Deleting exam group detail with ID: {}", id);
            examGroupDtlsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting exam group detail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
