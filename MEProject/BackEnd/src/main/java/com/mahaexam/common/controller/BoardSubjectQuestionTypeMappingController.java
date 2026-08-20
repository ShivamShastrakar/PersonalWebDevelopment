package com.mahaexam.common.controller;

import com.mahaexam.common.bean.BoardSubjectQuestionTypeMappingBean;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.BoardSubjectQuestionTypeMapping;
import com.mahaexam.common.service.BoardSubjectQuestionTypeMappingService;
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
@RequestMapping("/api/board-subject-question-type-mappings")
@Tag(name = "Board Subject Question Type Mapping API", description = "Manage allowed question types per board and subject")
public class BoardSubjectQuestionTypeMappingController extends BaseController {

    private static final Logger logger = LogManager.getLogger(BoardSubjectQuestionTypeMappingController.class);
    private final BoardSubjectQuestionTypeMappingService service;

    public BoardSubjectQuestionTypeMappingController(BoardSubjectQuestionTypeMappingService service) {
        this.service = service;
    }

    /** Resolves tenant ID from session user, falls back to request referer. */
    private Long resolveTenantId() {
        UserBean user = getUser();
        return Objects.nonNull(user) ? user.getTenantId() : getCurrentTenantId();
    }

    // ── GET all ───────────────────────────────────────────────────────────────
    @Operation(summary = "Get all mappings for the current tenant (+ global mappings)")
    @GetMapping
    public ResponseEntity<List<BoardSubjectQuestionTypeMapping>> getAll() {
        Long tenantId = resolveTenantId();
        logger.info("GET /api/board-subject-question-type-mappings tenantId={}", tenantId);
        return ResponseEntity.ok(service.getAll(tenantId));
    }

    // ── GET by ID ─────────────────────────────────────────────────────────────
    @Operation(summary = "Get mapping by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BoardSubjectQuestionTypeMapping> getById(@PathVariable int id) {
        logger.info("GET /api/board-subject-question-type-mappings/{}", id);
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── GET by board ──────────────────────────────────────────────────────────
    @Operation(summary = "Get all mappings for a board")
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<BoardSubjectQuestionTypeMapping>> getByBoard(@PathVariable int boardId) {
        Long tenantId = resolveTenantId();
        logger.info("GET /api/board-subject-question-type-mappings/board/{} tenantId={}", boardId, tenantId);
        return ResponseEntity.ok(service.getByBoardId(boardId, tenantId));
    }

    // ── GET by board + subject ────────────────────────────────────────────────
    @Operation(summary = "Get allowed question types for a board and subject")
    @GetMapping("/board/{boardId}/subject/{subjectId}")
    public ResponseEntity<List<BoardSubjectQuestionTypeMapping>> getByBoardAndSubject(
            @PathVariable int boardId, @PathVariable int subjectId) {
        Long tenantId = resolveTenantId();
        logger.info("GET /api/board-subject-question-type-mappings/board/{}/subject/{} tenantId={}", boardId, subjectId, tenantId);
        return ResponseEntity.ok(service.getByBoardAndSubject(boardId, subjectId, tenantId));
    }

    // ── POST create ───────────────────────────────────────────────────────────
    @Operation(summary = "Create a new board-subject-questionType mapping")
    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody BoardSubjectQuestionTypeMappingBean bean) {
        try {
            UserBean user = getUser();
            Long tenantId = resolveTenantId();
            logger.info("POST /api/board-subject-question-type-mappings boardId={} subjectId={} questionTypeId={} tenantId={}",
                    bean.getBoardId(), bean.getSubjectId(), bean.getQuestionTypeId(), tenantId);
            BoardSubjectQuestionTypeMapping entity = BoardSubjectQuestionTypeMapping.builder()
                    .tenantId(tenantId)
                    .boardId(bean.getBoardId())
                    .subjectId(bean.getSubjectId())
                    .questionTypeId(bean.getQuestionTypeId())
                    .createdBy(user != null && user.getUserId() != null ? user.getUserId().intValue() : null)
                    .build();
            service.create(entity);
            return ResponseEntity.status(HttpStatus.OK).body(SuccessResponseBean.builder()
                    .status("success").message("Mapping created successfully").build());
        } catch (Exception e) {
            logger.error("Error creating mapping: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    @Operation(summary = "Delete (soft-delete) a mapping by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> delete(@PathVariable int id) {
        try {
            logger.info("DELETE /api/board-subject-question-type-mappings/{}", id);
            service.delete(id);
            return ResponseEntity.ok(SuccessResponseBean.builder()
                    .status("success").message("Mapping deleted successfully").build());
        } catch (Exception e) {
            logger.error("Error deleting mapping id={}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
