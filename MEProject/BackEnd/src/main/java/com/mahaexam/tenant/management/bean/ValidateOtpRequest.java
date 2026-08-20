package com.mahaexam.tenant.management.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOtpRequest {

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "OTP is required")
    @Pattern(regexp = "\\d{4,6}", message = "OTP must be 4-6 digits")
    private String otp;
}
