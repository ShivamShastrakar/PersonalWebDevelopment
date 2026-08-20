package com.mahaexam.tenant.management.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelPartnerRegistrationBean {
	
	//user Info
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
    private String refererUrl;
    
    //address
    
    private String addressText;
    private Integer stateId;
    private Integer districtId;
    private Integer talukaId;
    private String place;
    private String pincode;

    private String password;

    private String reTypePassword;


    //Channel partner 
	private Long partnerId;
	private Long userId;
    private String companyName;
    @NotBlank(message = "Business Type is required")
    private String businessType;
    private String panNumber;
    private String tanNumber;
    private String gstNumber;
    private Integer businessExpYears;
    @NotBlank(message = "Service Type is required")
    private String serviceType;
    private Integer deeperAssociationYears;
    private Long parentPartnerId;
    private String otp;

    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;

}
