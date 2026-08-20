package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.bean.EarningSummaryBean;
import com.mahaexam.tenant.management.bean.IndirectIncomeEarningBean;
import com.mahaexam.tenant.management.bean.StudentDetailsBean;
import com.mahaexam.tenant.management.service.EarningService;
import com.mahaexam.tenant.management.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/earnings")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
public class EarningController extends com.mahaexam.common.controller.BaseController {
    private static final Logger logger = LoggerFactory.getLogger(EarningController.class);
    private final StudentService studentService;
    private final EarningService earningService;

    @Autowired
    public EarningController(StudentService studentService, EarningService earningService) {
        this.studentService = studentService;
        this.earningService = earningService;
    }

    @GetMapping("/{channelPartnerId}")
    public ResponseEntity<List<StudentDetailsBean>> getAllStudentsByChannelPartnerId(@PathVariable Long channelPartnerId) {
        if (channelPartnerId == null || channelPartnerId <= 0) {
            logger.warn("Invalid channelPartnerId: {}", channelPartnerId);
            throw new ValidationException("Channel Partner ID must be greater than 0");
        }

        try {
            logger.info("Fetching all students for channel partner ID: {}", channelPartnerId);
            List<StudentDetailsBean> students = studentService.getAllStudentsByChannelPartnerId(channelPartnerId);

            if (students.isEmpty()) {
                logger.warn("No students found for channel partner ID: {}", channelPartnerId);
            } else {
                logger.info("Found {} students for channel partner ID: {}", students.size(), channelPartnerId);
            }

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("Error fetching students for channel partner ID: {}: {}", channelPartnerId, e.getMessage(), e);
            throw e;
        }
    }

    // changed path to avoid ambiguity with /{channelPartnerId}
    @GetMapping("/summary/{channelPartnerId}")
    public ResponseEntity<EarningSummaryBean> getEarningSummary(@PathVariable Long userId) {
    	UserBean user = getUser();
	      	
    	if (userId == null || userId <= 0) {
            logger.warn("Invalid channelPartnerId for summary: {}", userId);
            userId = user.getUserId();	
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

    /**
     * API endpoint to get direct earning for a given user ID
     * @param userId The user ID for which to compute direct earnings
     * @return ResponseEntity containing EarningSummaryBean with direct earnings breakdown
     */
    @GetMapping("/direct/{userId}")
    public ResponseEntity<EarningSummaryBean> getDirectEarningForGivenUserId() {
    	UserBean user = getUser();
	    Long userId = user.getUserId();	
	    	if (userId == null || userId <= 0) {
	            logger.warn("Invalid userId for indirect earning: {}", userId);
	            throw new ValidationException("User ID must be greater than 0");
	        }
    	
    	try {
            logger.info("Fetching direct earning for user ID: {}", userId);
            EarningSummaryBean directEarning = earningService.computeEarningSummary(userId, user.getTenantId());
            return ResponseEntity.ok(directEarning);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for direct earning calculation: {}", e.getMessage());
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            logger.error("Error computing direct earning for user ID: {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * API endpoint to get indirect earning for a given user ID
     * Includes direct earnings + all 4 levels of downline earnings
     * @param userId The user ID for which to compute indirect earnings
     * @return ResponseEntity containing IndirectIncomeEarningBean with all earning details
     */
//    @GetMapping("/indirect/{userId}")
    @GetMapping("/indirect")
//    public ResponseEntity<IndirectIncomeEarningBean> getIndirectEarningForGivenUserId(@PathVariable Long userId) {
    public ResponseEntity<IndirectIncomeEarningBean> getIndirectEarningForLoggedInUserId() {
        	
    UserBean user = getUser();
    Long userId = user.getUserId();	
    	if (userId == null || userId <= 0) {
            logger.warn("Invalid userId for indirect earning: {}", userId);
            throw new ValidationException("User ID must be greater than 0");
        }

        try {
            logger.info("Fetching indirect earning for user ID: {}", userId);
            IndirectIncomeEarningBean indirectEarning = earningService.computeIndirectEarning(userId, user.getTenantId());
            return ResponseEntity.ok(indirectEarning);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument for indirect earning calculation: {}", e.getMessage());
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            logger.error("Error computing indirect earning for user ID: {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
}