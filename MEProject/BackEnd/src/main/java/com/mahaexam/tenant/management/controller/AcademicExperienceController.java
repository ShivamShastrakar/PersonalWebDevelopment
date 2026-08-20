package com.mahaexam.tenant.management.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.tenant.management.bean.AcademicExperienceBean;
import com.mahaexam.tenant.management.model.AcademicExperience;
import com.mahaexam.tenant.management.service.AcademicExperienceService;

import java.util.List;

@RestController
@RequestMapping("/api/academic-experiences")
public class AcademicExperienceController extends BaseController {
	private static final Logger logger = LogManager.getLogger(AcademicExperienceController.class);

    @Autowired
    private AcademicExperienceService academicExperienceService;

    @PostMapping
    public ResponseEntity<SuccessResponseBean> createAcademicExperience(@RequestBody AcademicExperienceBean academicExperienceDTO) {
        try {
        	academicExperienceService.createAcademicExperience(academicExperienceDTO);
        	return ResponseEntity.status(HttpStatus.OK)
					.body(SuccessResponseBean.builder().status("success").message(
							"Academic Experience Added Succfully ")
							.build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicExperience> updateAcademicExperience(@PathVariable Long id, @RequestBody AcademicExperienceBean academicExperience) {
        try {
            AcademicExperience updatedExperience = academicExperienceService.updateAcademicExperience(id, academicExperience);
            return ResponseEntity.ok(updatedExperience);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
			throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicExperience(@PathVariable Long id) {
        try {
            academicExperienceService.deleteAcademicExperience(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
			throw e;
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AcademicExperience>> getAcademicExperiencesByUserId(@PathVariable Long userId) {
        List<AcademicExperience> experiences = academicExperienceService.getAcademicExperiencesByUserId(userId);
        return ResponseEntity.ok(experiences);
    }
}