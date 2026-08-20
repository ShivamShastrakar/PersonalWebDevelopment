package com.mahaexam.tenant.management.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NetworkPartnerUpdate {
    private Integer id;
    private Long userId;
    private String name;
    private String imageUrl;
    private String userType;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private LocalDate dateOfBirth;

    private String registeredMobileNumber;
    private String whatsappNumber;
    private String email;

    private Long addressId;
    private String addressText;
    private Integer stateId;
    private Integer districtId;
    private Integer talukaId;
    
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
	private String pinCode;
	private String place;

	private BigDecimal additionalCommissionPercent;
	

   
}
