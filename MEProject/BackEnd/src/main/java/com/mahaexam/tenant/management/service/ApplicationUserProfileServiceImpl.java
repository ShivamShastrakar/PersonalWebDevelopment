package com.mahaexam.tenant.management.service;


import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.config.S3Helper;
import com.mahaexam.common.config.S3Service;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.common.util.StringUtil;
import com.mahaexam.tenant.management.bean.ApplicationUserProfileBean;
import com.mahaexam.tenant.management.bean.ApplicationUserProfileBean.ChannelPartnerDTO;
import com.mahaexam.tenant.management.bean.ApplicationUserProfileBean.StudentDTO;
import com.mahaexam.tenant.management.bean.ApplicationUserProfileBean.TeacherDTO;
import com.mahaexam.tenant.management.bean.ParentBean;
import com.mahaexam.tenant.management.model.*;
import com.mahaexam.tenant.management.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ApplicationUserProfileServiceImpl implements ApplicationUserProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationUserProfileServiceImpl.class);
    private final ApplicationUserRepository applicationUserRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final ChannelPartnerRepository channelPartnerRepository;

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final BankAccountRepository bankAccountRepository;

    private final ParentRepository parentRepository;

    private final ConfigService configService;
    private final S3Service s3Service;
    private final S3Helper s3Helper;
    private final StudentCourseService studentCourseService;
    private final StudentSubjectGroupService studentSubjectGroupService;

    public ApplicationUserProfileServiceImpl(ApplicationUserRepository applicationUserRepository, StudentRepository studentRepository,
                                             TeacherRepository teacherRepository, ChannelPartnerRepository channelPartnerRepository,
                                             UserRepository userRepository, AddressRepository addressRepository,
                                             BankAccountRepository bankAccountRepository, ParentRepository parentRepository,
                                             ConfigService configService, S3Service s3Service, S3Helper s3Helper,
                                             StudentCourseService studentCourseService, StudentSubjectGroupService studentSubjectGroupService) {
        this.applicationUserRepository = applicationUserRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.channelPartnerRepository = channelPartnerRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.parentRepository = parentRepository;
        this.configService = configService;
        this.s3Service = s3Service;
        this.s3Helper = s3Helper;
        this.studentCourseService = studentCourseService;
        this.studentSubjectGroupService = studentSubjectGroupService;
    }

    @Override
    @Transactional
    public ApplicationUserProfile updateProfile(Long userId, ApplicationUserProfileBean updateDTO) {
        ApplicationUserProfile applicationUserProfile = new ApplicationUserProfile();
        Optional<ApplicationUser> optionalProfile = applicationUserRepository.findByUserId(userId);
        applicationUserProfile.setUserId(userId);
        if (!optionalProfile.isPresent()) {
            throw new RuntimeException("Application User not found");
        }
        ApplicationUser profile = optionalProfile.get();
        // Update ApplicationUserProfile fields
        if (updateDTO.getUserType() != null) {
            profile.setUserType(updateDTO.getUserType());
        }
        if (updateDTO.getFirstName() != null) {
            profile.setFirstName(updateDTO.getFirstName());
        }
        if (updateDTO.getLastName() != null) {
            profile.setLastName(updateDTO.getLastName());
        }
        if (updateDTO.getMiddleName() != null) {
            profile.setMiddleName(updateDTO.getMiddleName());
        }
        if (updateDTO.getGender() != null) {
            profile.setGender(updateDTO.getGender());
        }
        if (updateDTO.getDateOfBirth() != null) {
            profile.setDateOfBirth(updateDTO.getDateOfBirth());
        }
        if (updateDTO.getAadharNumber() != null) {
            profile.setAadharNumber(updateDTO.getAadharNumber());
        }
        if (updateDTO.getRegisteredMobileNumber() != null) {
            profile.setRegisteredMobileNumber(updateDTO.getRegisteredMobileNumber());
        }
        if (updateDTO.getWhatsappNumber() != null) {
            profile.setWhatsappNumber(updateDTO.getWhatsappNumber());
        }
        if (updateDTO.getEmail() != null) {
            profile.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getAdditionalCommissionPercent() != null) {
            profile.setAdditionalCommissionPercent(updateDTO.getAdditionalCommissionPercent());
        }


        // Update related entity based on userType
        String userType = profile.getUserType();
        if (userType == null) {
            throw new RuntimeException("User type is not set for profile userId: " + userId);
        }

        switch (userType) {
            case "Student":
                if (updateDTO.getAcademicInfo() != null) {
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setCurrentClassId(updateDTO.getAcademicInfo().getClassId());
                    studentDTO.setCurrentSubjectGroupId(updateDTO.getAcademicInfo().getSubjectGroupId());
                    studentDTO.setTargetFinalExamYear(updateDTO.getAcademicInfo().getTargetFinalExamYear());
                    studentDTO.setMedium(updateDTO.getAcademicInfo().getMedium());
                    studentDTO.setSchoolName(updateDTO.getStudent().getSchoolName());
                    studentDTO.setSchoolAddress(updateDTO.getStudent().getSchoolAddress());
                    updateDTO.setStudent(studentDTO);
                    Student targetStudent = new Student();
                    // Fetch existing Student by studentId for comparison
                    Optional<Student> optionalStudent = studentRepository.findByUserId(userId);
                    copyOptionalStudentToStudent(optionalStudent, targetStudent,studentDTO , applicationUserProfile);
                    updateParentDetails(updateDTO, targetStudent);
                    studentRepository.update(targetStudent);

                    // Handle courses and subject groups if provided
                    Long studentId = targetStudent.getStudentId();

                    // Update courses if courseIds are provided
                    if (updateDTO.getAcademicInfo().getCourseIds() != null && !updateDTO.getAcademicInfo().getCourseIds().isEmpty()) {
                        // Delete existing courses for the student first
                        studentCourseService.deleteStudentId(studentId);
                        // Add new courses
                        studentCourseService.save(studentId, updateDTO.getAcademicInfo().getCourseIds());
                    }

                    // Update subject groups if subjectGroupIds are provided
                    if (updateDTO.getAcademicInfo().getSubjectGroupId() != null) {
                        updateDTO.getAcademicInfo().setSubjectGroupIds(List.of(updateDTO.getAcademicInfo().getSubjectGroupId()));
                        // Delete existing subject groups for the student
                        studentSubjectGroupService.deleteStudentId(studentId);

                        // Add new subject groups
                        for (Integer subjectGroupId : updateDTO.getAcademicInfo().getSubjectGroupIds()) {
                            StudentSubjectGroup studentSubjectGroup = new StudentSubjectGroup();
                            studentSubjectGroup.setStudentId(studentId);
                            studentSubjectGroup.setSubjectGroupId(subjectGroupId);
                            studentSubjectGroupService.save(studentSubjectGroup);
                        }
                    }
                }
                break;
            case "Teacher":
                if (updateDTO.getTeacher() != null) {
                    Teacher targetTeacher = new Teacher();
                    Optional<Teacher> optionalTeacher = teacherRepository.findByUserId(userId);
                    targetTeacher.setUserId(userId);
                    copyOptionalTeacherToTeacher(optionalTeacher, targetTeacher, updateDTO.getTeacher(), applicationUserProfile);
                    teacherRepository.update(targetTeacher);
                }
                break;
            case "Channel Partner":
                if (updateDTO.getChannelPartner() != null) {
                    ChannelPartner targetChannelPartner = new ChannelPartner();
                    // Fetch existing ChannelPartner by partnerId for comparison
                    Optional<ChannelPartner> optionalChannelPartner = channelPartnerRepository.findByUserId(userId);
                    targetChannelPartner.setUserId(userId);
                    copyOptionalChannelPartnerToChannelPartner(optionalChannelPartner, targetChannelPartner,
                            updateDTO.getChannelPartner(), applicationUserProfile);
                    channelPartnerRepository.update(targetChannelPartner);
                    //need add chagnes for updated bank details.

                }
                break;
            default:
                throw new RuntimeException("Invalid user type: " + userType);
        }
        ApplicationUser applicationUser = new ApplicationUser();
        Optional<Address> addressOptional = addressRepository.findByUserId(profile.getUserId());
        if(addressOptional.isPresent()){
            Address address = addressOptional.get();
            applicationUser.setAddressId(address.getAddressId());
            address.setUserId(profile.getUserId());
            address.setAddressText(updateDTO.getAddress().getAddressText());
            address.setStateId(updateDTO.getAddress().getStateId());
            address.setDistrictId(updateDTO.getAddress().getDistrictId());
            address.setTalukaId(updateDTO.getAddress().getTalukaId());
            address.setPlace(updateDTO.getAddress().getPlace());
            address.setPincode(updateDTO.getAddress().getPincode());
            addressRepository.update(address);
        }else {
            Address address = new Address();
            address.setUserId(profile.getUserId());
            address.setAddressText(updateDTO.getAddress().getAddressText());
            address.setStateId(updateDTO.getAddress().getStateId());
            address.setDistrictId(updateDTO.getAddress().getDistrictId());
            address.setTalukaId(updateDTO.getAddress().getTalukaId());
            address.setPlace(updateDTO.getAddress().getPlace());
            address.setPincode(updateDTO.getAddress().getPincode());
            Address saveDB = addressRepository.save(address);
            applicationUser.setAddressId(saveDB.getAddressId());

        }
        if(Objects.nonNull(updateDTO.getPhotoImg())){
            try {
                 // Delete previous image from S3 if exists
                Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
                Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                        "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
                String s3BucketName = config.getValue();
                String existingPhotoUrl = profile.getPhotoUrl();
                s3Helper.deleteExistingImage(s3BucketName,existingPhotoUrl);

                configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_PHOTO_IMG_FOLDER);
                String photoFolderName = "photo";
                if (configOpt.isPresent()) {
                    config = configOpt.get();
                    photoFolderName = config.getValue();

                }
                String fileName = StringUtil.extractFilenameFromBase64(updateDTO.getPhotoImg());
                applicationUser.setPhotoUrl(photoFolderName + "/" + fileName);
                s3Service.uploadBase64ImageToS3(s3BucketName, photoFolderName, fileName, updateDTO.getPhotoImg());

            } catch (Exception e) {
                logger.error("Failed to upload photo: " + e.getMessage(), e);
            }
        }

        applicationUser.setId(profile.getId());
        applicationUser.setUserId(profile.getUserId());
        applicationUser.setUserType(profile.getUserType());
        applicationUser.setAadharNumber(profile.getAadharNumber());
        applicationUser.setDateOfBirth(profile.getDateOfBirth());
        applicationUser.setEmail(profile.getEmail());
        applicationUser.setFirstName(profile.getFirstName());
        applicationUser.setMiddleName(profile.getMiddleName());
        applicationUser.setLastName(profile.getLastName());
        applicationUser.setGender(profile.getGender());
        applicationUser.setRegisteredMobileNumber(profile.getRegisteredMobileNumber());
        applicationUser.setUpdatedAt(LocalDateTime.now());
        applicationUser.setWhatsappNumber(profile.getWhatsappNumber());
        applicationUser.setAdditionalCommissionPercent(profile.getAdditionalCommissionPercent());
        BeanUtils.copyProperties(applicationUserRepository.updateByUserId(applicationUser), applicationUserProfile);


        if (Objects.nonNull(updateDTO.getBankAccount()) && Objects.nonNull(updateDTO.getBankAccount().getAccountNumber())
                && !updateDTO.getBankAccount().getAccountNumber().isEmpty()) {
            Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByUserId(applicationUser.getUserId());
            if (bankAccountOptional.isPresent()) {
                BankAccount bankAccount = bankAccountOptional.get();
                bankAccount.setAccountName(updateDTO.getBankAccount().getAccountName());
                bankAccount.setAccountNumber(updateDTO.getBankAccount().getAccountNumber());
                bankAccount.setBranchName(updateDTO.getBankAccount().getBranchName());
                bankAccount.setIfscCode(updateDTO.getBankAccount().getIfscCode());
                bankAccount.setBankName(updateDTO.getBankAccount().getBankName());
                bankAccountRepository.update(bankAccount);
            } else {
                updateDTO.getBankAccount().setUserId(applicationUser.getUserId());
                bankAccountRepository.save(updateDTO.getBankAccount());
            }
        }

        return applicationUserProfile;
    }

    private void updateParentDetails(ApplicationUserProfileBean updateDTO, Student targetStudent) {
        if(Objects.nonNull(updateDTO.getParentsDtls())) {
            Optional<Parent> optionalParent = parentRepository.findByStudentId(targetStudent.getStudentId());
            ParentBean parent = updateDTO.getParentsDtls();
            if(optionalParent.isPresent()){
                Parent parentDB = optionalParent.get();
                parentDB.setFatherName(Objects.nonNull(parent.getFatherName())?parent.getFatherName():"");
                parentDB.setFatherOccupation(parent.getFatherOccupation());
                parentDB.setFatherMobileNumber(parent.getFatherMobileNumber());

                parentDB.setMotherName(Objects.nonNull(parent.getMotherName())?parent.getMotherName():"");
                parentDB.setMotherOccupation(parent.getMotherOccupation());
                parentDB.setMotherMobileNumber(parent.getMotherMobileNumber());

                // Add missing fields
                parentDB.setNumberOfSiblings(parent.getNumberOfSiblings()==null?0:parent.getNumberOfSiblings());
                parentDB.setFirstSiblingName(parent.getFirstSiblingName());
                parentDB.setFirstSiblingStd(parent.getFirstSiblingStd());
                parentDB.setSecondSiblingName(parent.getSecondSiblingName());
                parentDB.setSecondSiblingStd(parent.getSecondSiblingStd());
                parentDB.setParentsYearlyIncome(Objects.nonNull(parent.getParentsYearlyIncome())?parent.getParentsYearlyIncome():"");

                parentRepository.update(parentDB);
                targetStudent.setParentId(parentDB.getParentId());
            }else{
                Parent parentNew = Parent.builder().fatherName(Objects.nonNull(parent.getFatherName())?parent.getFatherName():"")
                        .fatherMobileNumber(parent.getFatherMobileNumber())
                        .fatherOccupation(parent.getFatherOccupation())
                        .motherName(Objects.nonNull(parent.getMotherName())?parent.getMotherName():"")
                        .motherMobileNumber(parent.getMotherMobileNumber())
                        .motherOccupation(parent.getMotherOccupation())
                        .numberOfSiblings(parent.getNumberOfSiblings()==null?0:parent.getNumberOfSiblings())
                        .firstSiblingName(parent.getFirstSiblingName())
                        .firstSiblingStd(parent.getFirstSiblingStd())
                        .secondSiblingName(parent.getSecondSiblingName())
                        .secondSiblingStd(parent.getSecondSiblingStd())
                        .parentsYearlyIncome(Objects.nonNull(parent.getParentsYearlyIncome())?parent.getParentsYearlyIncome():"")
                        .build();
                parentNew= parentRepository.save(parentNew);
                targetStudent.setParentId(parentNew.getParentId());
            }
        }
    }

    private void copyOptionalStudentToStudent(Optional<Student> optionalStudent, Student targetStudent, StudentDTO studentDTO, ApplicationUserProfile applicationUserProfile) {
        // If Optional is empty, create a new Student or keep target as is
        Student sourceStudent = optionalStudent.orElse(new Student());

        // Update targetStudent with non-null fields from studentDTO
        targetStudent.setStudentId(sourceStudent.getStudentId());
        targetStudent.setUserId(sourceStudent.getUserId());
        if (studentDTO.getCurrentClassId() != null) {
            targetStudent.setCurrentClassId(studentDTO.getCurrentClassId());
        } else if (sourceStudent.getCurrentClassId() != null) {
            targetStudent.setCurrentClassId(sourceStudent.getCurrentClassId());
        }
        if (studentDTO.getCurrentSubjectGroupId() != null) {
            targetStudent.setCurrentSubjectGroupId(studentDTO.getCurrentSubjectGroupId());
        } else if (sourceStudent.getCurrentSubjectGroupId() != null) {
            targetStudent.setCurrentSubjectGroupId(sourceStudent.getCurrentSubjectGroupId());
        }
        if (studentDTO.getTargetFinalExamYear() != null) {
            targetStudent.setTargetFinalExamYear(studentDTO.getTargetFinalExamYear());
        } else if (sourceStudent.getTargetFinalExamYear() != null) {
            targetStudent.setTargetFinalExamYear(sourceStudent.getTargetFinalExamYear());
        }

        if (studentDTO.getStudentReferenceId() != null) {
            targetStudent.setStudentReferenceId(studentDTO.getStudentReferenceId());
        } else if (sourceStudent.getStudentReferenceId() != null) {
            targetStudent.setStudentReferenceId(sourceStudent.getStudentReferenceId());
        }
        if (studentDTO.getMedium() != null) {
            targetStudent.setMedium(studentDTO.getMedium());
        } else if (sourceStudent.getMedium() != null) {
            targetStudent.setMedium(sourceStudent.getMedium());
        }
        if (studentDTO.getSchoolName() != null) {
            targetStudent.setSchoolName(studentDTO.getSchoolName());
        } else if (sourceStudent.getSchoolName() != null) {
            targetStudent.setSchoolName(sourceStudent.getSchoolName());
        }
        
        if (studentDTO.getSchoolAddress() != null) {
            targetStudent.setSchoolAddress(studentDTO.getSchoolAddress());
        } else if (sourceStudent.getSchoolAddress() != null) {
            targetStudent.setSchoolAddress(sourceStudent.getSchoolAddress());
        }
        
        applicationUserProfile.setStudent(targetStudent);
    }

    private void copyOptionalTeacherToTeacher(Optional<Teacher> optionalTeacher, Teacher targetTeacher,
                                              TeacherDTO teacherDTO, ApplicationUserProfile applicationUserProfile) {
        Teacher sourceTeacher = optionalTeacher.orElse(new Teacher());
        // Update targetStudent with non-null fields from TeacherDTO
        if (teacherDTO.getInstituteIndexNumber() != null) {
            targetTeacher.setInstituteIndexNumber(teacherDTO.getInstituteIndexNumber());
        } else if (sourceTeacher.getInstituteIndexNumber() != null) {
            targetTeacher.setInstituteIndexNumber(sourceTeacher.getInstituteIndexNumber());
        }
        if (teacherDTO.getInService() != null) {
            targetTeacher.setInService(teacherDTO.getInService());
        } else if (sourceTeacher.getInService() != null) {
            targetTeacher.setInService(sourceTeacher.getInService());
        }
        if (teacherDTO.getSubjectId() != null) {
            targetTeacher.setSubjectId(teacherDTO.getSubjectId());
        } else if (sourceTeacher.getSubjectId() != null) {
            targetTeacher.setSubjectId(sourceTeacher.getSubjectId());
        }
        if (teacherDTO.getTotalExperienceYears() != null) {
            targetTeacher.setTotalExperienceYears(teacherDTO.getTotalExperienceYears());
        } else if (sourceTeacher.getTotalExperienceYears() != null) {
            targetTeacher.setTotalExperienceYears(sourceTeacher.getTotalExperienceYears());
        }
        if (teacherDTO.getAreaOfInterest() != null) {
            targetTeacher.setAreaOfInterest(teacherDTO.getAreaOfInterest());
        } else if (sourceTeacher.getAreaOfInterest() != null) {
            targetTeacher.setAreaOfInterest(sourceTeacher.getAreaOfInterest());
        }
        if (teacherDTO.getOnlineLectureTaken() != null) {
            targetTeacher.setOnlineLectureTaken(teacherDTO.getOnlineLectureTaken());
        } else if (sourceTeacher.getOnlineLectureTaken() != null) {
            targetTeacher.setOnlineLectureTaken(sourceTeacher.getOnlineLectureTaken());
        }
        if (teacherDTO.getQualification() != null) {
            targetTeacher.setQualification(teacherDTO.getQualification());
        } else if (sourceTeacher.getQualification() != null) {
            targetTeacher.setQualification(sourceTeacher.getQualification());
        }
        if (teacherDTO.getTeachingExperience() != null) {
            targetTeacher.setTeachingExperience(teacherDTO.getTeachingExperience());
        } else if (sourceTeacher.getTeachingExperience() != null) {
            targetTeacher.setTeachingExperience(sourceTeacher.getTeachingExperience());
        }
        if (teacherDTO.getValuationExperience() != null) {
            targetTeacher.setValuationExperience(teacherDTO.getValuationExperience());
        } else if (sourceTeacher.getValuationExperience() != null) {
            targetTeacher.setValuationExperience(sourceTeacher.getValuationExperience());
        }
        if (teacherDTO.getModerationExperience() != null) {
            targetTeacher.setModerationExperience(teacherDTO.getModerationExperience());
        } else if (sourceTeacher.getModerationExperience() != null) {
            targetTeacher.setModerationExperience(sourceTeacher.getModerationExperience());
        }
        if (teacherDTO.getChefModerationExperience() != null) {
            targetTeacher.setChefModerationExperience(teacherDTO.getChefModerationExperience());
        } else if (sourceTeacher.getChefModerationExperience() != null) {
            targetTeacher.setChefModerationExperience(sourceTeacher.getChefModerationExperience());
        }
        if (teacherDTO.getBoardPaperSettingExperience() != null) {
            targetTeacher.setBoardPaperSettingExperience(teacherDTO.getBoardPaperSettingExperience());
        } else if (sourceTeacher.getBoardPaperSettingExperience() != null) {
            targetTeacher.setBoardPaperSettingExperience(sourceTeacher.getBoardPaperSettingExperience());
        }
        if (teacherDTO.getMhtCetPaperSettingExperience() != null) {
            targetTeacher.setMhtCetPaperSettingExperience(teacherDTO.getMhtCetPaperSettingExperience());
        } else if (sourceTeacher.getMhtCetPaperSettingExperience() != null) {
            targetTeacher.setMhtCetPaperSettingExperience(sourceTeacher.getMhtCetPaperSettingExperience());
        }
        if (teacherDTO.getNeetPaperSettingExperience() != null) {
            targetTeacher.setNeetPaperSettingExperience(teacherDTO.getNeetPaperSettingExperience());
        } else if (sourceTeacher.getNeetPaperSettingExperience() != null) {
            targetTeacher.setNeetPaperSettingExperience(sourceTeacher.getNeetPaperSettingExperience());
        }
        if (teacherDTO.getJeePaperSettingExperience() != null) {
            targetTeacher.setJeePaperSettingExperience(teacherDTO.getJeePaperSettingExperience());
        } else if (sourceTeacher.getJeePaperSettingExperience() != null) {
            targetTeacher.setJeePaperSettingExperience(sourceTeacher.getJeePaperSettingExperience());
        }
        if (teacherDTO.getKvpyPaperSettingExperience() != null) {
            targetTeacher.setKvpyPaperSettingExperience(teacherDTO.getKvpyPaperSettingExperience());
        } else if (sourceTeacher.getKvpyPaperSettingExperience() != null) {
            targetTeacher.setKvpyPaperSettingExperience(sourceTeacher.getKvpyPaperSettingExperience());
        }
        if (teacherDTO.getSpecialtyTopicsSubjects() != null) {
            targetTeacher.setSpecialtyTopicsSubjects(teacherDTO.getSpecialtyTopicsSubjects());
        } else if (sourceTeacher.getSpecialtyTopicsSubjects() != null) {
            targetTeacher.setSpecialtyTopicsSubjects(sourceTeacher.getSpecialtyTopicsSubjects());
        }
        if (teacherDTO.getJeeExp() != null) {
            targetTeacher.setJeeExp(teacherDTO.getJeeExp());
        } else if (sourceTeacher.getJeeExp() != null) {
            targetTeacher.setJeeExp(sourceTeacher.getJeeExp());
        }
        if (teacherDTO.getMhtCetExp() != null) {
            targetTeacher.setMhtCetExp(teacherDTO.getMhtCetExp());
        } else if (sourceTeacher.getMhtCetExp() != null) {
            targetTeacher.setMhtCetExp(sourceTeacher.getMhtCetExp());
        }
        if (teacherDTO.getNeetExp() != null) {
            targetTeacher.setNeetExp(teacherDTO.getNeetExp());
        } else if (sourceTeacher.getNeetExp() != null) {
            targetTeacher.setNeetExp(sourceTeacher.getNeetExp());
        }
        if (teacherDTO.getTotalExp() != null) {
            targetTeacher.setTotalExp(teacherDTO.getTotalExp());
        } else if (sourceTeacher.getTotalExp() != null) {
            targetTeacher.setTotalExp(sourceTeacher.getTotalExp());
        }
        if (teacherDTO.getIndividualRefCode() != null) {
            targetTeacher.setIndividualRefCode(teacherDTO.getIndividualRefCode());
        } else if (sourceTeacher.getIndividualRefCode() != null) {
            targetTeacher.setIndividualRefCode(sourceTeacher.getIndividualRefCode());
        }
        if (teacherDTO.getRefCode() != null) {
            targetTeacher.setRefCode(teacherDTO.getRefCode());
        } else if (sourceTeacher.getRefCode() != null) {
            targetTeacher.setRefCode(sourceTeacher.getRefCode());
        }
        if (teacherDTO.getAddressText() != null) {
            targetTeacher.setAddressText(teacherDTO.getAddressText());
        } else if (sourceTeacher.getAddressText() != null) {
            targetTeacher.setAddressText(sourceTeacher.getAddressText());
        }
        if (teacherDTO.getStateId() != null) {
            targetTeacher.setStateId(teacherDTO.getStateId());
        } else if (sourceTeacher.getStateId() != null) {
            targetTeacher.setStateId(sourceTeacher.getStateId());
        }
        if (teacherDTO.getDistrictId() != null) {
            targetTeacher.setDistrictId(teacherDTO.getDistrictId());
        } else if (sourceTeacher.getDistrictId() != null) {
            targetTeacher.setDistrictId(sourceTeacher.getDistrictId());
        }
        if (teacherDTO.getTalukaId() != null) {
            targetTeacher.setTalukaId(teacherDTO.getTalukaId());
        } else if (sourceTeacher.getTalukaId() != null) {
            targetTeacher.setTalukaId(sourceTeacher.getTalukaId());
        }
        if (teacherDTO.getPlace() != null) {
            targetTeacher.setPlace(teacherDTO.getPlace());
        } else if (sourceTeacher.getPlace() != null) {
            targetTeacher.setPlace(sourceTeacher.getPlace());
        }
        if (teacherDTO.getPinCode() != null) {
            targetTeacher.setPinCode(teacherDTO.getPinCode());
        } else if (sourceTeacher.getPinCode() != null) {
            targetTeacher.setPinCode(sourceTeacher.getPinCode());
        }
        targetTeacher.setTeacherId(sourceTeacher.getTeacherId());
        if (teacherDTO.getPanNumber() != null) {
            targetTeacher.setPanNumber(teacherDTO.getPanNumber());
        } else if (sourceTeacher.getPanNumber() != null) {
            targetTeacher.setPanNumber(sourceTeacher.getPanNumber());
        }
        applicationUserProfile.setTeacher(targetTeacher);
    }

    private void copyOptionalChannelPartnerToChannelPartner(Optional<ChannelPartner> optionalChannelPartner, ChannelPartner targetChannelPartner,
                                                            ChannelPartnerDTO channelPartnerDTO, ApplicationUserProfile applicationUserProfile) {
        // If Optional is empty, create a new ChannelPartner or keep target as is
        ChannelPartner sourceChannelPartner = optionalChannelPartner.orElse(new ChannelPartner());

        // Update targetChannelPartner with non-null fields from channelPartnerDTO
        if (channelPartnerDTO.getCompanyName() != null) {
            targetChannelPartner.setCompanyName(channelPartnerDTO.getCompanyName());
        } else if (sourceChannelPartner.getCompanyName() != null) {
            targetChannelPartner.setCompanyName(sourceChannelPartner.getCompanyName());
        }
        if (channelPartnerDTO.getBusinessType() != null) {
            targetChannelPartner.setBusinessType(channelPartnerDTO.getBusinessType());
        } else if (sourceChannelPartner.getBusinessType() != null) {
            targetChannelPartner.setBusinessType(sourceChannelPartner.getBusinessType());
        }
        if (channelPartnerDTO.getPanNumber() != null) {
            targetChannelPartner.setPanNumber(channelPartnerDTO.getPanNumber());
        } else if (sourceChannelPartner.getPanNumber() != null) {
            targetChannelPartner.setPanNumber(sourceChannelPartner.getPanNumber());
        }
        if (channelPartnerDTO.getTanNumber() != null) {
            targetChannelPartner.setTanNumber(channelPartnerDTO.getTanNumber());
        } else if (sourceChannelPartner.getTanNumber() != null) {
            targetChannelPartner.setTanNumber(sourceChannelPartner.getTanNumber());
        }
        if (channelPartnerDTO.getGstNumber() != null) {
            targetChannelPartner.setGstNumber(channelPartnerDTO.getGstNumber());
        } else if (sourceChannelPartner.getGstNumber() != null) {
            targetChannelPartner.setGstNumber(sourceChannelPartner.getGstNumber());
        }
        if (channelPartnerDTO.getBusinessExpYears() != null) {
            targetChannelPartner.setBusinessExpYears(channelPartnerDTO.getBusinessExpYears());
        } else if (sourceChannelPartner.getBusinessExpYears() != null) {
            targetChannelPartner.setBusinessExpYears(sourceChannelPartner.getBusinessExpYears());
        }
        if (channelPartnerDTO.getServiceType() != null) {
            targetChannelPartner.setServiceType(channelPartnerDTO.getServiceType());
        } else if (sourceChannelPartner.getServiceType() != null) {
            targetChannelPartner.setServiceType(sourceChannelPartner.getServiceType());
        }
        if (channelPartnerDTO.getDeeperAssociationYears() != null) {
            targetChannelPartner.setDeeperAssociationYears(channelPartnerDTO.getDeeperAssociationYears());
        } else if (sourceChannelPartner.getDeeperAssociationYears() != null) {
            targetChannelPartner.setDeeperAssociationYears(sourceChannelPartner.getDeeperAssociationYears());
        }
        if (channelPartnerDTO.getParentPartnerId() != null) {
            targetChannelPartner.setParentPartnerId(channelPartnerDTO.getParentPartnerId());
        } else if (sourceChannelPartner.getParentPartnerId() != null) {
            targetChannelPartner.setParentPartnerId(sourceChannelPartner.getParentPartnerId());
        }
        targetChannelPartner.setPartnerId(sourceChannelPartner.getPartnerId());
        applicationUserProfile.setChannelPartner(targetChannelPartner);
    }

    @Override
    public ApplicationUserProfile getUserProfileDetails(Long userId) {
        ApplicationUserProfile applicationUserProfile = new ApplicationUserProfile();
        Optional<ApplicationUser> optionalProfile = applicationUserRepository.findByUserId(userId);

        if (!optionalProfile.isPresent()) {
            throw new RuntimeException("Application User not found");
        }
        ApplicationUser profile = optionalProfile.get();
        String userType = profile.getUserType();
        if (userType == null) {
            throw new RuntimeException("User type is not set for profile userId: " + userId);
        }
        Optional<BankAccount> bankAccountOptional;
        switch (userType) {
            case "Student":
                Student targetStudent = new Student();
                // Fetch existing Student by studentId for comparison
                Optional<Student> optionalStudent = studentRepository.findByUserId(userId);
                StudentDTO studentDTO = new StudentDTO();
                copyOptionalStudentToStudent(optionalStudent, targetStudent, studentDTO, applicationUserProfile);
                Optional<Parent> optionalParent = parentRepository.findByStudentId(targetStudent.getStudentId());
                optionalParent.ifPresent(applicationUserProfile::setParentsDtls);

                // Fetch courses and subject groups for the student
                if (targetStudent.getStudentId() != null) {
                    List<Long> studentIds = List.of(targetStudent.getStudentId());
                    List<StudentCourse> studentCourses = studentCourseService.findByStudentIds(studentIds);
                    List<StudentSubjectGroup> studentSubjectGroups = studentSubjectGroupService.findByStudentIds(studentIds);

                    // Set the courses and subject groups in the application user profile
                    applicationUserProfile.setStudentCourses(studentCourses);
                    applicationUserProfile.setStudentSubjectGroups(studentSubjectGroups);
                }
                break;
            case "Teacher":
                Teacher targetTeacher = new Teacher();
                Optional<Teacher> optionalTeacher = teacherRepository.findByUserId(userId);
                TeacherDTO teacherDTO = new TeacherDTO();
                copyOptionalTeacherToTeacher(optionalTeacher, targetTeacher, teacherDTO, applicationUserProfile);
                bankAccountOptional = bankAccountRepository.findByUserId(userId);
                bankAccountOptional.ifPresent(applicationUserProfile::setBankAccount);
                break;
            case "Channel Partner":
                ChannelPartner targetChannelPartner = new ChannelPartner();
                // Fetch existing ChannelPartner by partnerId for comparison
                Optional<ChannelPartner> optionalChannelPartner = channelPartnerRepository.findByUserId(userId);
                ChannelPartnerDTO channelPartnerDTO = new ChannelPartnerDTO();
                copyOptionalChannelPartnerToChannelPartner(optionalChannelPartner, targetChannelPartner,
                        channelPartnerDTO, applicationUserProfile);
                bankAccountOptional = bankAccountRepository.findByUserId(userId);
                bankAccountOptional.ifPresent(applicationUserProfile::setBankAccount);
                break;
            default:
                //throw new RuntimeException("Invalid user type: " + userType);
        }


        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setId(profile.getId());
        applicationUser.setUserId(profile.getUserId());
        applicationUser.setUserType(profile.getUserType());
        applicationUser.setAadharNumber(profile.getAadharNumber());
        applicationUser.setDateOfBirth(profile.getDateOfBirth());
        applicationUser.setEmail(profile.getEmail());
        applicationUser.setFirstName(profile.getFirstName());
        applicationUser.setMiddleName(profile.getMiddleName());
        applicationUser.setLastName(profile.getLastName());
        applicationUser.setGender(profile.getGender());
        applicationUser.setRegisteredMobileNumber(profile.getRegisteredMobileNumber());
        applicationUser.setUpdatedAt(LocalDateTime.now());
        applicationUser.setWhatsappNumber(profile.getWhatsappNumber());
        applicationUser.setAdditionalCommissionPercent(profile.getAdditionalCommissionPercent());
        applicationUser.setUserName(profile.getUserName());
        applicationUser.setPhotoUrl(profile.getPhotoUrl());
        BeanUtils.copyProperties(applicationUser, applicationUserProfile);
        applicationUserProfile.setProfilePhotoUrl(profile.getPhotoUrl());
        Optional<Address> addressOptional = addressRepository.findById(profile.getAddressId());
        addressOptional.ifPresent(address -> applicationUserProfile.setAddress(AddressConverter.toAddressBean(address)));

        return applicationUserProfile;
    }

    @Override
    public PaginatedResponse<ApplicationUser> findByUserType(Long tenantId,String userType, Boolean isDeleted, Pageable pageable) {
        return applicationUserRepository.findByUserType(tenantId,userType, isDeleted,pageable);
    }
    
    @Override
    @Transactional
    public void deleteByUserId(Long userId){
        applicationUserRepository.deleteByUserId(userId);
        userRepository.delete(userId);
    }

    @Override
    public Integer countByUserType(Long tenantId,String userType) {
        return applicationUserRepository.countByUserType(tenantId,userType);
    }

    @Override
    public List<ApplicationUser> findByFirstOrLastName(String query, Long tenantId, List<String> profileTypes) {
        return applicationUserRepository.findByFirstOrLastName(query, tenantId, profileTypes);
    }
    
    @Override
    public PaginatedResponse<ApplicationUser> findAllUsersForGivenTenantId(Long tenantId, Long user_id, Pageable pageable) {
        return applicationUserRepository.findAllUsersForGivenTenantId(tenantId, user_id, pageable);
    }
    
    @Override
    public void updateUserParentId(Long userParentId, Long userId) {
        applicationUserRepository.updateUserParentId(userParentId, userId);
    }
    
    @Override
	public PaginatedResponse<ApplicationUser> getAllUsersprofilesForGivenTenantId(Long tenantId, Pageable pageable)
    {
    	return applicationUserRepository.getAllUsersprofilesForGivenTenantId(tenantId, pageable);
    
    }
}
