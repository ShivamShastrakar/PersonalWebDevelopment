package com.mahaexam.tenant.management.bean;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OtpValidationBean {
	@Email(message = "Invalid Email format")
	private String email;
	
	@Pattern(regexp = "[6-9]\\d{9}|", message = "Mobile number must be a 10-digit number starting with 6, 7, 8, or 9 if provided")
	private String mobile;
	
	@NotNull(message = "OTP is required")
	private String otp;
}
