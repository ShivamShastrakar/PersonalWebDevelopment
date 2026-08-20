package com.mahaexam.tenant.management.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mahaexam.tenant.management.model.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
	private String token;
	private Long userId;
    private Boolean hasPackage;
	private List<Role> roles;
    private String displayName;
	private String errorMessage;
    private StudentDetailsBean student;

    /**
     * Short-lived exam token issued when a student starts an exam.
     * Expires in exam duration + 5 min buffer.
     * Used at submission time to verify the student did not exceed the allowed time.
     */
    private String examToken;
}
