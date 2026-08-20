package com.mahaexam.common.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PwdResetOtp {
    private Long id;
    private Long userId;
    private String otp;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
