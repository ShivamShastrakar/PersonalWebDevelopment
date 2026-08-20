package com.mahaexam.papertemplate.controller;

import com.mahaexam.common.bean.PaperTemplateResponse;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.papertemplate.bean.PaperTemplateRequest;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.papertemplate.model.PaperTemplate;
import com.mahaexam.papertemplate.service.PaperTemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mahaexam.question.service.QuestionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paper-templates")
public class PaperTemplateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PaperTemplateController.class);
	private final PaperTemplateService paperTemplateService;
	
	  public PaperTemplateController(
	            PaperTemplateService paperTemplateService) {
	        this.paperTemplateService = paperTemplateService;
	    }
    
 // SAVE
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @RequestBody PaperTemplateRequest request) {

        logger.info("=== POST /api/paper-templates endpoint called ===");
        Map<String, Object> response = new HashMap<>();
        try {
            UserBean user = getUser();
            if (user == null) {
                logger.error("UserBean is null - user not authenticated or session expired");
                response.put("status", "ERROR");
                response.put("message", "User not authenticated or session expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            logger.info("Creating paper template for tenant: {}, user: {}",
                       user.getTenantId(), user.getUserId());

            paperTemplateService.create(request, user);

            logger.info("Successfully created paper template");
            response.put("status", 201);
            response.put("message", "Paper Template created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error in create(): ", e);
            response.put("status", "ERROR");
            response.put("message", "Error creating paper template: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/getFullHierarchy/{templateId}")
    public ResponseEntity<PaperTemplateResponse> getFullTemplate(
            @PathVariable Long templateId) {

        return ResponseEntity.ok(
                paperTemplateService.getFullHierarchy(templateId)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @RequestBody PaperTemplateRequest request) {

        logger.info("=== PUT /api/paper-templates/{} endpoint called ===", id);
        Map<String, Object> response = new HashMap<>();
        try {
            UserBean user = getUser();
            if (user == null) {
                logger.error("UserBean is null - user not authenticated or session expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            logger.info("Updating paper template id: {} for tenant: {}, user: {}",
                       id, user.getTenantId(), user.getUserId());

            paperTemplateService.update(id, request, user);

            logger.info("Successfully updated paper template with id: {}", id);
            response.put("status", 200);
            response.put("message", "Paper Template Updated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            logger.error("Error in update({}): ", id, e);
            response.put("status", "ERROR");
            response.put("message", "Error updating paper template");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<PaperTemplate>> getAll(
            @RequestParam(required = false) Long boardId,
            @RequestParam(required = false) Integer classId) {
        logger.info("=== GET /api/paper-templates endpoint called ===");

        try {
            UserBean user = getUser();
            if (user == null) {
                logger.error("UserBean is null - user not authenticated or session expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            logger.info("Fetching all paper templates for tenant: {}, user: {}, boardId: {}, classId: {}",
                       user.getTenantId(), user.getUserId(), boardId, classId);

            List<PaperTemplate> templates = (boardId != null || classId != null)
                    ? paperTemplateService.findAll(user, boardId, classId)
                    : paperTemplateService.findAll(user);

            logger.info("Successfully fetched {} paper templates", templates != null ? templates.size() : 0);

            return ResponseEntity.ok(templates);
        } catch (Exception e) {
            logger.error("Error in getAll(): ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaperTemplate> getById(@PathVariable Long id) {
        logger.info("=== GET /api/paper-templates/{} endpoint called ===", id);

        try {
            UserBean user = getUser();
            if (user == null) {
                logger.error("UserBean is null - user not authenticated or session expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            logger.info("Fetching paper template by id: {} for tenant: {}", id, user.getTenantId());

            PaperTemplate template = paperTemplateService.findById(id);
            if (template == null) {
                logger.warn("Paper template not found with id: {}", id);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(template);
        } catch (Exception e) {
            logger.error("Error in getById({}): ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        logger.info("=== DELETE /api/paper-templates/{} endpoint called ===", id);

        Map<String, Object> response = new HashMap<>();
        try {
            UserBean user = getUser();
            if (user == null) {
                logger.error("UserBean is null - user not authenticated or session expired");
                response.put("status", "ERROR");
                response.put("message", "User not authenticated or session expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            logger.info("Deleting paper template id: {} for tenant: {}", id, user.getTenantId());

            paperTemplateService.delete(id);
            logger.info("Successfully deleted paper template with id: {}", id);
            response.put("status", 200);
            response.put("message", "Paper Template deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | ValidationException e) {
            logger.error("Validation error: {}", e.getMessage());
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error in delete({}): ", id, e);
            response.put("status", "ERROR");
            response.put("message", "Error deleting paper template");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}