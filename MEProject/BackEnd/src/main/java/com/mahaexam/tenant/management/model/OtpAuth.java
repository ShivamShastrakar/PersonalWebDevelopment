package com.mahaexam.tenant.management.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OtpAuth {
	private Long id;
    private String mobile;
    private String email;
    private String otp;
    private LocalDateTime createdAt;
}
