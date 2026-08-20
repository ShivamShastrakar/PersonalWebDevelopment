package com.mahaexam.tenant.management.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherRegistrationBean {
	
	@NotBlank(message = "First Name is required")
	private String firstName;
	@NotBlank(message = "Last Name is required")
	private String lastName;
	private String middleName;

	@Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE, or OTHER")
	private String gender;

	private LocalDate dateOfBirth;

	private String aadharNumber;
	
	private String panNumber;

	@NotBlank(message = "Registered Mobile Number is required")
	@Pattern(regexp = "[6-9]\\d{9}", message = "Registered Mobile Number must be a 10-digit number starting with 6, 7, 8, or 9")
	private String registeredMobileNumber;

	@Pattern(regexp = "[6-9]\\d{9}|", message = "WhatsApp Number must be a 10-digit number starting with 6, 7, 8, or 9 if provided")
	private String whatsappNumber;

	@NotBlank(message = "Email is required")
	private String email;

    private String password;

    private String reTypePassword;
	
	private Long userId;
	
	private String refererUrl;
	
	private String instituteIndexNumber;
	
	private Boolean inService;
	
	private Integer subjectId;
	
	private Integer totalExperienceYears;
	
	private String areaOfInterest;
	
	private Boolean onlineLectureTaken;
	
	private String qualification;
	
	private Integer teachingExperience;
	
	private Integer valuationExperience;
	
	private Integer moderationExperience;
	
	private Integer chefModerationExperience;
	
	private Integer boardPaperSettingExperience;
	
	private Integer mhtCetPaperSettingExperience;
	
	private Integer neetPaperSettingExperience;
	
	private Integer jeePaperSettingExperience;
	
	private Integer kvpyPaperSettingExperience;
	
	private String specialtyTopicsSubjects;
	
	private Integer jeeExp;
	
	private Integer mhtCetExp;
	
	private Integer neetExp;
	
	private Integer totalExp;
	
	private String individualRefCode;
	
	private String refCode;
	
	private String addressText;
	
	private Integer stateId;
	
    private Integer districtId;
    
    private Integer talukaId;
    
    private String place;
    
   private String pinCode;

   private String otp;

}
