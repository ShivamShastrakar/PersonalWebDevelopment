package com.mahaexam.tenant.management.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.tenant.management.bean.TeacherRegistrationBean;
import com.mahaexam.tenant.management.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teacher")
public class TeacherRegistrationController {
	private static final Logger logger = LogManager.getLogger(TeacherRegistrationController.class);
	private final TeacherService teacherService;

	public TeacherRegistrationController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@PostMapping("/register")
	public ResponseEntity<SuccessResponseBean> registerTeacher(
			@Valid @RequestBody TeacherRegistrationBean registrationDTO) {
		try {
			teacherService.registerTeacher(registrationDTO,true);
			return ResponseEntity.status(HttpStatus.OK).body(SuccessResponseBean.builder().status("success")
					.message("Teacher registered successfully with Email ID: " + registrationDTO.getEmail()).build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

    @PostMapping("/add")
    public ResponseEntity<SuccessResponseBean> addTeacher(
            @Valid @RequestBody TeacherRegistrationBean registrationDTO) {
        try {
            registrationDTO.setPassword(registrationDTO.getRegisteredMobileNumber());
            registrationDTO.setReTypePassword(registrationDTO.getPassword());
            teacherService.registerTeacher(registrationDTO,false);
            return ResponseEntity.status(HttpStatus.OK).body(SuccessResponseBean.builder().status("success")
                    .message("Teacher registered successfully with Email ID: " + registrationDTO.getEmail()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}