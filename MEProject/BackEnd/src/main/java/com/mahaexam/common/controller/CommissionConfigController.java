package com.mahaexam.common.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.CommissionConfigRequest;
import com.mahaexam.common.service.CommissionConfigService;
import com.mahaexam.tenant.management.bean.EarningSummaryBean;
import com.mahaexam.tenant.management.service.EarningService;
import com.mahaexam.tenant.management.service.StudentService;

@RestController
@RequestMapping("/api/commissions")
public class CommissionConfigController extends BaseController{
	private static final Logger logger = LogManager.getLogger(CommissionConfigController.class);

	private final CommissionConfigService service;
	private final StudentService studentService;
    private final EarningService earningService;

    public CommissionConfigController(CommissionConfigService service, StudentService studentService, EarningService earningService) {
        this.service = service;
        this.studentService = studentService;
        this.earningService = earningService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody CommissionConfigRequest request) {
    	UserBean user = getUser();
    	CommissionConfigRequest cfr = new CommissionConfigRequest();
    	cfr.setActive(request.getActive());
    	cfr.setCommissionType(request.getCommissionType());
    	cfr.setPackageType(request.getPackageType() != null ? request.getPackageType() : "Test");
    	cfr.setHierarchyLevelId(request.getHierarchyLevelId());
    	cfr.setSlabs(request.getSlabs());
    	cfr.setTenantId(user.getTenantId());
    	cfr.setPackageCategoryId(request.getPackageCategoryId());
    	cfr.setExamGroupId(request.getExamGroupId());
    	cfr.setCreated_date(LocalDateTime.now());
       	cfr.setCreated_by(user.getUserId().longValue());
    	cfr.setUpdated_by(user.getUserId().longValue());
		
		try {
			CommissionConfigRequest response = service.create(cfr);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (DuplicateKeyException e) {
			// Prefer the SQL root-cause message (e.g. "Duplicate entry 'x' for key '...'")
			String msg = null;
			if (e.getMostSpecificCause() != null) {
				msg = e.getMostSpecificCause().getMessage();
			}
			if (msg == null) {
				msg = e.getMessage();
			}
			logger.warn("Duplicate key while creating commission config: {}", msg);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommissionConfigRequest> update(
            @PathVariable Long id,
            @RequestBody CommissionConfigRequest request) {
    	if (request.getPackageType() == null) {
    		request.setPackageType("Test");
    	}
    	CommissionConfigRequest response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommissionConfigRequest>> list(
            @RequestParam(required = false) Integer hierarchy_level_id,
            @RequestParam(required = false) String packageType,
            @RequestParam(required = false) Boolean active) {
        List<CommissionConfigRequest> list = service.list(hierarchy_level_id, packageType, active);
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid commission config ID for delete: {}", id);
            return ResponseEntity.badRequest().build();
        }
        try {
            service.delete(id);
            logger.info("Commission config deleted successfully for ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Commission config not found for delete, ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<CommissionConfigRequest> getCommissionSlabsByRoleId(@PathVariable Integer roleId) {
        if (roleId == null || roleId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<CommissionConfigRequest> config = service.getCommissionSlabsByRoleId(roleId);
        return config.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
 // changed path to avoid ambiguity with /{channelPartnerId}
    @GetMapping("/summary/{channelPartnerId}")
    public ResponseEntity<EarningSummaryBean> getEarningSummary(@PathVariable Long userId) {
    	UserBean user = getUser();
    	if (userId == null || userId <= 0) {
            logger.warn("Invalid channelPartnerId for summary: {}", userId);
            userId = user.getUserId(); // default to current user's ID if not provided or invalid
//            throw new ValidationException("Channel Partner ID must be greater than 0");
        }

        try {
        	
            EarningSummaryBean summary = earningService.computeEarningSummary(userId, user.getTenantId());
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            logger.error("Error computing earning summary for channel partner ID: {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
}