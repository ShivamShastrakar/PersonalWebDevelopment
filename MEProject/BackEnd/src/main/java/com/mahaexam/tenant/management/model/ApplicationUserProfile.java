package com.mahaexam.tenant.management.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.mahaexam.tenant.management.bean.AddressBean;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUserProfile {
    private Long id;
    private Long userId;
    private String userName;
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
    private BankAccount  bankAccount;
    private AddressBean address;
	private Student student;
	private Teacher teacher;
	private ChannelPartner channelPartner;
    private Parent parentsDtls;
    private String profilePhotoUrl;
    private BigDecimal additionalCommissionPercent;
    List<StudentCourse> studentCourses;
    List<StudentSubjectGroup> studentSubjectGroups;
}
