package com.mahaexam.common.controller;

import com.mahaexam.common.bean.QuestionTypeBean;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.QuestionType;
import com.mahaexam.common.service.QuestionTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/question-types")
@Tag(name = "Question Type API", description = "CRUD operations for question types")
public class QuestionTypeController extends BaseController {

    private static final Logger logger = LogManager.getLogger(QuestionTypeController.class);

    private final QuestionTypeService service;

    public QuestionTypeController(QuestionTypeService service) {
        this.service = service;
    }

    /** Resolves tenant ID from session user, falls back to request referer. */
    private Long resolveTenantId() {
        UserBean user = getUser();
        return Objects.nonNull(user) ? user.getTenantId() : getCurrentTenantId();
    }

    // ── GET all ──────────────────────────────────────────────────────────────
    @Operation(summary = "Get all question types for the current tenant (+ global types)")
    @GetMapping
    public ResponseEntity<List<QuestionType>> getAll() {
        Long tenantId = resolveTenantId();
        logger.info("GET /api/question-types tenantId={}", tenantId);
        return ResponseEntity.ok(service.getAll(tenantId));
    }

    // ── GET by ID ─────────────────────────────────────────────────────────────
    @Operation(summary = "Get a question type by ID")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionType> getById(@PathVariable int id) {
        logger.info("GET /api/question-types/{}", id);
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── GET by code ───────────────────────────────────────────────────────────
    @Operation(summary = "Get a question type by code (e.g. mcq, paragraph-based-mcq)")
    @GetMapping("/code/{code}")
    public ResponseEntity<QuestionType> getByCode(@PathVariable String code) {
        Long tenantId = resolveTenantId();
        logger.info("GET /api/question-types/code/{} tenantId={}", code, tenantId);
        return service.getByCode(code, tenantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── GET by board + subject ────────────────────────────────────────────────
    @Operation(summary = "Get allowed question types for a specific board and subject")
    @GetMapping("/board/{boardId}/subject/{subjectId}")
    public ResponseEntity<List<QuestionType>> getByBoardAndSubject(
            @PathVariable int boardId, @PathVariable int subjectId) {
        Long tenantId = resolveTenantId();
        logger.info("GET /api/question-types/board/{}/subject/{} tenantId={}", boardId, subjectId, tenantId);
        return ResponseEntity.ok(service.getByBoardAndSubject(boardId, subjectId, tenantId));
    }

    // ── POST create ───────────────────────────────────────────────────────────
    @Operation(summary = "Create a new question type")
    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody QuestionTypeBean bean) {
        try {
            Long tenantId = resolveTenantId();
            logger.info("POST /api/question-types code={} tenantId={}", bean.getCode(), tenantId);
            QuestionType entity = QuestionType.builder()
                    .tenantId(tenantId)
                    .code(bean.getCode() != null ? bean.getCode().trim().toLowerCase() : null)
                    .name(bean.getName() != null ? bean.getName().trim() : null)
                    .description(bean.getDescription())
                    .build();
            service.create(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder()
                            .status("success")
                            .message("Question type created successfully")
                            .build());
        } catch (Exception e) {
            logger.error("Error creating question type: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── PUT update ────────────────────────────────────────────────────────────
    @Operation(summary = "Update an existing question type")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id,
                                                      @RequestBody QuestionTypeBean bean) {
        try {
            Long tenantId = resolveTenantId();
            logger.info("PUT /api/question-types/{} code={} tenantId={}", id, bean.getCode(), tenantId);
            QuestionType entity = QuestionType.builder()
                    .id(id)
                    .tenantId(tenantId)
                    .code(bean.getCode() != null ? bean.getCode().trim().toLowerCase() : null)
                    .name(bean.getName() != null ? bean.getName().trim() : null)
                    .description(bean.getDescription())
                    .build();
            service.update(entity);
            return ResponseEntity.ok(SuccessResponseBean.builder()
                    .status("success")
                    .message("Question type updated successfully")
                    .build());
        } catch (Exception e) {
            logger.error("Error updating question type id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    @Operation(summary = "Delete a question type by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> delete(@PathVariable int id) {
        try {
            Long tenantId = resolveTenantId();
            logger.info("DELETE /api/question-types/{} tenantId={}", id, tenantId);
            service.delete(id, tenantId);
            return ResponseEntity.ok(SuccessResponseBean.builder()
                    .status("success")
                    .message("Question type deleted successfully")
                    .build());
        } catch (Exception e) {
            logger.error("Error deleting question type id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
