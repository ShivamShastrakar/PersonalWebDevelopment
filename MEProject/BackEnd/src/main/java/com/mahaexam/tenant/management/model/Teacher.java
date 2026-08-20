package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class Teacher {
	private Long teacherId;
	private Long userId;
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

    // PAN number for teacher
    private String panNumber;
}
