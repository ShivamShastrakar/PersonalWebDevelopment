package com.mahaexam.tenant.management.controller;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.tenant.management.bean.ForgotPasswordRequest;
import com.mahaexam.tenant.management.bean.LoginRequest;
import com.mahaexam.tenant.management.bean.LoginResponse;
import com.mahaexam.tenant.management.bean.ResetPasswordRequest;
import com.mahaexam.tenant.management.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequestDTO) {
        try {
            LoginResponse loginResponse = loginService.authenticate(loginRequestDTO);
            if (Objects.nonNull(loginResponse)) {
                // In production, return JWT token or session ID
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(LoginResponse.builder().errorMessage("Invalid username or password.").build());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<SuccessResponseBean> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        // Your logic could initiate a reset, send email, etc.
        boolean success = loginService.forgotPassword(forgotPasswordRequest);
        if (success) {
            return ResponseEntity.ok(SuccessResponseBean.builder().status("success").message("Password reset otp has been sent to your email/mobile.").build());
        } else {
            throw new IllegalArgumentException("Invalid request");
        }
    }



    /**
     * Reset password using OTP
     * @param resetPasswordRequest Request containing username, OTP, and new password
     * @return Success response if password reset is successful
     */
    @PostMapping("/reset-password")
    public ResponseEntity<SuccessResponseBean> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            boolean success = loginService.resetPasswordWithOtp(
                    resetPasswordRequest.getUserName(),
                    resetPasswordRequest.getOtp(),
                    resetPasswordRequest.getNewPassword()
            );

            if (success) {
                return ResponseEntity.ok(SuccessResponseBean.builder()
                        .status("success")
                        .message("Password has been reset successfully. You can now login with your new password.")
                        .build());
            } else {
                return ResponseEntity.badRequest()
                        .body(SuccessResponseBean.builder()
                                .status("error")
                                .message("Failed to reset password. Please try again.")
                                .build());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(SuccessResponseBean.builder()
                            .status("error")
                            .message(e.getMessage())
                            .build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(SuccessResponseBean.builder()
                            .status("error")
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SuccessResponseBean.builder()
                            .status("error")
                            .message("An error occurred while resetting password. Please try again.")
                            .build());
        }
    }
}
