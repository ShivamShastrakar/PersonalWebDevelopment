package com.mahaexam.tenant.management.bean;


import com.fasterxml.jackson.annotation.JsonInclude;

import com.mahaexam.tenant.management.model.BankAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUserProfileBean {
	
	 @Size(min = 1, max = 50, message = "User type must be between 1 and 50 characters")
	    private String userType;
	    
	    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
	    private String firstName;
	    
	    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
	    private String lastName;
	    
	    private String middleName;
	    
	    private String gender;
	    
	    private LocalDate dateOfBirth;
	    
	    private String aadharNumber;
	    
	    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
	    private String registeredMobileNumber;
	    
	    private String whatsappNumber;
	    
	    @Email(message = "Email must be valid")
	    private String email;

	    private Long userParentId;

    private BigDecimal additionalCommissionPercent;

    private BankAccount bankAccount;
        private AddressBean address;
	    private StudentDTO student;
	    private TeacherDTO teacher;
	    private ChannelPartnerDTO channelPartner;

        private ParentBean parentsDtls;
        private AcademicInfo academicInfo;

        private String photoImg;
	    // Getters and setters


    public String getPhotoImg() {
        return photoImg;
    }

    public void setPhotoImg(String photoImg) {
        this.photoImg = photoImg;
    }

    public AcademicInfo getAcademicInfo() {
        return academicInfo;
    }

    public void setAcademicInfo(AcademicInfo academicInfo) {
        this.academicInfo = academicInfo;
    }

    public ParentBean getParentsDtls() {
            return parentsDtls;
        }

        public void setParentsDtls(ParentBean parentsDtls) {
            this.parentsDtls = parentsDtls;
        }

    public String getUserType() { return userType; }
	    public void setUserType(String userType) { this.userType = userType; }
	    public String getFirstName() { return firstName; }
	    public void setFirstName(String firstName) { this.firstName = firstName; }
	    public String getLastName() { return lastName; }
	    public void setLastName(String lastName) { this.lastName = lastName; }
	    public String getMiddleName() { return middleName; }
	    public void setMiddleName(String middleName) { this.middleName = middleName; }
	    public String getGender() { return gender; }
	    public void setGender(String gender) { this.gender = gender; }
	    public LocalDate getDateOfBirth() { return dateOfBirth; }
	    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
	    public String getAadharNumber() { return aadharNumber; }
	    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }
	    public String getRegisteredMobileNumber() { return registeredMobileNumber; }
	    public void setRegisteredMobileNumber(String registeredMobileNumber) { this.registeredMobileNumber = registeredMobileNumber; }
	    public String getWhatsappNumber() { return whatsappNumber; }
	    public void setWhatsappNumber(String whatsappNumber) { this.whatsappNumber = whatsappNumber; }
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	    public Long getUserParentId() { return userParentId; }
	    public void setUserParentId(Long userParentId) { this.userParentId = userParentId; }
	    public StudentDTO getStudent() { return student; }
	    public void setStudent(StudentDTO student) { this.student = student; }
	    public TeacherDTO getTeacher() { return teacher; }
	    public void setTeacher(TeacherDTO teacher) { this.teacher = teacher; }
	    public ChannelPartnerDTO getChannelPartner() { return channelPartner; }
	    public void setChannelPartner(ChannelPartnerDTO channelPartner) { this.channelPartner = channelPartner; }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public static class AcademicInfo {
        private Long studentId;
        private Integer classId;
        private Integer subjectGroupId;
        private Integer targetFinalExamYear;
        private List<Long> courseIds;
        private List<Integer> subjectGroupIds;
        private String medium;

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public Integer getClassId() {
            return classId;
        }

        public void setClassId(Integer classId) {
            this.classId = classId;
        }

        public Integer getSubjectGroupId() {
            return subjectGroupId;
        }

        public void setSubjectGroupId(Integer subjectGroupId) {
            this.subjectGroupId = subjectGroupId;
        }

        public Integer getTargetFinalExamYear() {
            return targetFinalExamYear;
        }

        public void setTargetFinalExamYear(Integer targetFinalExamYear) {
            this.targetFinalExamYear = targetFinalExamYear;
        }

        public List<Long> getCourseIds() {
            return courseIds;
        }

        public void setCourseIds(List<Long> courseIds) {
            this.courseIds = courseIds;
        }

        public List<Integer> getSubjectGroupIds() {
            return subjectGroupIds;
        }

        public void setSubjectGroupIds(List<Integer> subjectGroupIds) {
            this.subjectGroupIds = subjectGroupIds;
        }

        public String getMedium() {
            return medium;
        }
        public void setMedium(String medium) {
            this.medium = medium;
        }
    }
    public static class StudentDTO {
	    	private Long studentId;
	    	private Long studentReferenceId;
	        private Integer currentClassId;
	        private Integer currentSubjectGroupId;
	        private Integer targetFinalExamYear;
            private String medium;
			private String schoolName;
			private String schoolAddress;

        public Long getStudentId() { return studentId; }
	        public void setStudentId(Long studentId) { this.studentId = studentId; }
	        public Long getStudentReferenceId() { return studentReferenceId; }
			public void setStudent_reference_id(Long student_reference_id) { this.studentReferenceId = student_reference_id; }
			public Integer getCurrentClassId() { return currentClassId; }
	        public void setCurrentClassId(Integer currentClassId) { this.currentClassId = currentClassId; }
	        public Integer getCurrentSubjectGroupId() { return currentSubjectGroupId; }
	        public void setCurrentSubjectGroupId(Integer currentSubjectGroupId) { this.currentSubjectGroupId = currentSubjectGroupId; }
	        public Integer getTargetFinalExamYear() { return targetFinalExamYear; }
	        public void setTargetFinalExamYear(Integer targetFinalExamYear) { this.targetFinalExamYear = targetFinalExamYear; }
            public String getMedium() {
                return medium;
            }
            public void setMedium(String medium) {
                this.medium = medium;
            }
			public String getSchoolName() { return schoolName; }
			public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
			public String getSchoolAddress() { return schoolAddress; }
			public void setSchoolAddress(String schoolAddress) { this.schoolAddress = schoolAddress; }

	    }

	    public static class TeacherDTO {
	    	private Long  teacherId;
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
			private String panNumber;

	        // Getters and setters
	  	 	public Long getTeacherId() { return teacherId; }
	        public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
	        public String getInstituteIndexNumber() { return instituteIndexNumber; }
			public void setInstituteIndexNumber(String instituteIndexNumber) { this.instituteIndexNumber = instituteIndexNumber; }
	        public Boolean getInService() { return inService; }
	        public void setInService(Boolean inService) { this.inService = inService; }
	        public Integer getSubjectId() { return subjectId; }
	        public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }
	        public Integer getTotalExperienceYears() { return totalExperienceYears; }
	        public void setTotalExperienceYears(Integer totalExperienceYears) { this.totalExperienceYears = totalExperienceYears; }
	        public String getAreaOfInterest() { return areaOfInterest; }
	        public void setAreaOfInterest(String areaOfInterest) { this.areaOfInterest = areaOfInterest; }
	        public Boolean getOnlineLectureTaken() { return onlineLectureTaken; }
	        public void setOnlineLectureTaken(Boolean onlineLectureTaken) { this.onlineLectureTaken = onlineLectureTaken; }
	        public String getQualification() { return qualification; }
	        public void setQualification(String qualification) { this.qualification = qualification; }
	        public Integer getTeachingExperience() { return teachingExperience; }
	        public void setTeachingExperience(Integer teachingExperience) { this.teachingExperience = teachingExperience; }
	        public Integer getValuationExperience() { return valuationExperience; }
	        public void setValuationExperience(Integer valuationExperience) { this.valuationExperience = valuationExperience; }
	        public Integer getModerationExperience() { return moderationExperience; }
	        public void setModerationExperience(Integer moderationExperience) { this.moderationExperience = moderationExperience; }
	        public Integer getChefModerationExperience() { return chefModerationExperience; }
	        public void setChefModerationExperience(Integer chefModerationExperience) { this.chefModerationExperience = chefModerationExperience; }
	        public Integer getBoardPaperSettingExperience() { return boardPaperSettingExperience; }
	        public void setBoardPaperSettingExperience(Integer boardPaperSettingExperience) { this.boardPaperSettingExperience = boardPaperSettingExperience; }
	        public Integer getMhtCetPaperSettingExperience() { return mhtCetPaperSettingExperience; }
	        public void setMhtCetPaperSettingExperience(Integer mhtCetPaperSettingExperience) { this.mhtCetPaperSettingExperience = mhtCetPaperSettingExperience; }
	        public Integer getNeetPaperSettingExperience() { return neetPaperSettingExperience; }
	        public void setNeetPaperSettingExperience(Integer neetPaperSettingExperience) { this.neetPaperSettingExperience = neetPaperSettingExperience; }
	        public Integer getJeePaperSettingExperience() { return jeePaperSettingExperience; }
	        public void setJeePaperSettingExperience(Integer jeePaperSettingExperience) { this.jeePaperSettingExperience = jeePaperSettingExperience; }
	        public Integer getKvpyPaperSettingExperience() { return kvpyPaperSettingExperience; }
	        public void setKvpyPaperSettingExperience(Integer kvpyPaperSettingExperience) { this.kvpyPaperSettingExperience = kvpyPaperSettingExperience; }
	        public String getSpecialtyTopicsSubjects() { return specialtyTopicsSubjects; }
	        public void setSpecialtyTopicsSubjects(String specialtyTopicsSubjects) { this.specialtyTopicsSubjects = specialtyTopicsSubjects; }
	        public Integer getJeeExp() { return jeeExp; }
	        public void setJeeExp(Integer jeeExp) { this.jeeExp = jeeExp; }
	        public Integer getMhtCetExp() { return mhtCetExp; }
	        public void setMhtCetExp(Integer mhtCetExp) { this.mhtCetExp = mhtCetExp; }
	        public Integer getNeetExp() { return neetExp; }
	        public void setNeetExp(Integer neetExp) { this.neetExp = neetExp; }
	        public Integer getTotalExp() { return totalExp; }
	        public void setTotalExp(Integer totalExp) { this.totalExp = totalExp; }
	        public String getIndividualRefCode() { return individualRefCode; }
	        public void setIndividualRefCode(String individualRefCode) { this.individualRefCode = individualRefCode; }
	        public String getRefCode() { return refCode; }
	        public void setRefCode(String refCode) { this.refCode = refCode; }
	        public String getAddressText() { return addressText; }
	        public void setAddressText(String addressText) { this.addressText = addressText; }
	        public Integer getStateId() { return stateId; }
	        public void setStateId(Integer stateId) { this.stateId = stateId; }
	        public Integer getDistrictId() { return districtId; }
	        public void setDistrictId(Integer districtId) { this.districtId = districtId; }
	        public Integer getTalukaId() { return talukaId; }
	        public void setTalukaId(Integer talukaId) { this.talukaId = talukaId; }
	        public String getPlace() { return place; }
	        public void setPlace(String place) { this.place = place; }
	        public String getPinCode() { return pinCode; }
	        public void setPinCode(String pinCode) { this.pinCode = pinCode; }
			public String getPanNumber() { return panNumber; }
			public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
	    }

	    public static class ChannelPartnerDTO {
	    	private Long channelPartnerId;
	        private String companyName;
	        private String businessType;
	        private String panNumber;
	        private String tanNumber;
	        private String gstNumber;
	        private Integer businessExpYears;
	        private String serviceType;
	        private Integer deeperAssociationYears;
	        private Long parentPartnerId;

	        // Getters and setters
	        public Long getChannelPartnerId() {	return channelPartnerId; }
			public void setChannelPartnerId(Long channelPartnerId) { this.channelPartnerId = channelPartnerId; }
	        public String getCompanyName() { return companyName; }
			public void setCompanyName(String companyName) { this.companyName = companyName; }
	        public String getBusinessType() { return businessType; }
	        public void setBusinessType(String businessType) { this.businessType = businessType; }
	        public String getPanNumber() { return panNumber; }
	        public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
	        public String getTanNumber() { return tanNumber; }
	        public void setTanNumber(String tanNumber) { this.tanNumber = tanNumber; }
	        public String getGstNumber() { return gstNumber; }
	        public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }
	        public Integer getBusinessExpYears() { return businessExpYears; }
	        public void setBusinessExpYears(Integer businessExpYears) { this.businessExpYears = businessExpYears; }
	        public String getServiceType() { return serviceType; }
	        public void setServiceType(String serviceType) { this.serviceType = serviceType; }
	        public Integer getDeeperAssociationYears() { return deeperAssociationYears; }
	        public void setDeeperAssociationYears(Integer deeperAssociationYears) { this.deeperAssociationYears = deeperAssociationYears; }
	        public Long getParentPartnerId() { return parentPartnerId; }
	        public void setParentPartnerId(Long parentPartnerId) { this.parentPartnerId = parentPartnerId; }
	    }

}