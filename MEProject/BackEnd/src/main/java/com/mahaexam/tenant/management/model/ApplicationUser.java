package com.mahaexam.tenant.management.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mahaexam.common.constants.AppConstants;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUser {
    private Long id;
    private Long userId;
    private String userType;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private LocalDate dateOfBirth;
    private String aadharNumber;
    private String registeredMobileNumber;
    private String whatsappNumber;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long addressId;
    private Long userParentId;
    private BigDecimal additionalCommissionPercent;

    private String photoUrl;
    private String userName;

    public String getName() {
        return getFirstName() + " " + getLastName();
    }
    private String role;

    public boolean isAdmin() {
        return (getUserType().equalsIgnoreCase(AppConstants.USER_TYPE_TECH_ADMIN) ||
                getUserType().equalsIgnoreCase(AppConstants.USER_TYPE_MAHA_ADMIN)
                || getUserType().equalsIgnoreCase(AppConstants.ROLE_ADMIN)
                || getUserType().equalsIgnoreCase(AppConstants.ROLE_TECH_ADMIN));
    }
}
