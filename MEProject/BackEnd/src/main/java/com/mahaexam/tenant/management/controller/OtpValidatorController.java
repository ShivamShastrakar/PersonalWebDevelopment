package com.mahaexam.tenant.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.tenant.management.bean.OtpRequestBean;
import com.mahaexam.tenant.management.bean.OtpValidationBean;
import com.mahaexam.tenant.management.model.OtpAuth;
import com.mahaexam.tenant.management.service.OtpAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpValidatorController {
	private final OtpAuthService otpAuthService;

	public OtpValidatorController(OtpAuthService otpAuthService) {
		this.otpAuthService = otpAuthService;
	}

	@PostMapping("/generate")
	public ResponseEntity<?> generateOtp(@Valid @RequestBody OtpRequestBean requestDTO) {

		OtpAuth otpAuth = new OtpAuth();
		otpAuth.setEmail(requestDTO.getEmail());
		otpAuth.setMobile(requestDTO.getMobile());
		otpAuthService.generateAndSave(otpAuth);
		return ResponseEntity.status(HttpStatus.OK).body("OTP generated successfully");

	}

	@PostMapping("/validate")
	public ResponseEntity<?> validateOtp(@Valid @RequestBody OtpValidationBean validationDTO) {
		OtpAuth otpAuth = new OtpAuth();
		otpAuth.setEmail(validationDTO.getEmail());
		otpAuth.setMobile(validationDTO.getMobile());
		otpAuth.setOtp(validationDTO.getOtp());

		boolean isValid = otpAuthService.validateOtp(otpAuth);
		if (isValid) {
			return ResponseEntity.status(HttpStatus.OK).body("OTP validated successfully");
		} else {
			throw new IllegalArgumentException("The provided OTP is invalid or expired");
		}
	}
}