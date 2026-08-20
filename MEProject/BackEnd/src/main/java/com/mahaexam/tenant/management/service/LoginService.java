package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.bean.ForgotPasswordRequest;
import com.mahaexam.tenant.management.bean.LoginRequest;
import com.mahaexam.tenant.management.bean.LoginResponse;
import jakarta.validation.Valid;

public interface LoginService {
	LoginResponse authenticate(LoginRequest loginRequest);

    boolean forgotPassword(@Valid ForgotPasswordRequest forgotPasswordRequest);

    public boolean resetPasswordWithOtp(String userName, String otp, String newPassword);
}
