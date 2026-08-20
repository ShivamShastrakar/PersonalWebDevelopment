package com.mahaexam.tenant.management.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentRegistrationBean {
    @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "First Name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 50, message = "Last Name must not exceed 50 characters")
    private String lastName;



    private String middleName;

    @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    private LocalDate dateOfBirth;

    private String aadharNumber;

    @NotBlank(message = "Registered Mobile Number is required")
    @Pattern(regexp = "[6-9]\\d{9}", message = "Registered Mobile Number must be a 10-digit number starting with 6, 7, 8, or 9")
    private String registeredMobileNumber;

    @Pattern(regexp = "[6-9]\\d{9}|", message = "WhatsApp Number must be a 10-digit number starting with 6, 7, 8, or 9 if provided")
    private String whatsappNumber;

    @NotBlank(message = "Email is required")
    private String email;

    @Size(max = 255, message = "Password must not exceed 255 characters")
    private String password;

    @Size(max = 255, message = "Retype Password must not exceed 255 characters")
    private String reTypePassword;

    @NotNull(message = "Class is required")
    private Integer classId;

    @NotNull(message = "Exam Group is required")
    private Integer subjectGroupId;

    @NotNull(message = "Target year of Final Exam is required")
    private Integer targetFinalExamYear;
    private Long userId;

    private List<Long> courses;

    private String refererUrl;

    private String otp;

    private Long studentReferenceId;

    private AddressBean address;
    private ParentBean parent;

    private String userType;

    private Long studentId;

    private Long tenantId;
    
    private String medium;
    private String schoolName;
    private String schoolAddress;
    private String category;
    private String instituteName;
    private String parallelReservation;
}
