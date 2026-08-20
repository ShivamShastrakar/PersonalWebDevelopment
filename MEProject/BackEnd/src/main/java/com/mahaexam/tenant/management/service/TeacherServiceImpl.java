package com.mahaexam.tenant.management.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mahaexam.common.util.CryptoUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.util.PasswordSHA2EncryptionUtil;
import com.mahaexam.tenant.management.bean.TeacherRegistrationBean;
import com.mahaexam.tenant.management.model.Address;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.OtpAuth;
import com.mahaexam.tenant.management.model.Teacher;
import com.mahaexam.tenant.management.model.UserTenant;
import com.mahaexam.tenant.management.repository.TeacherRepository;
import com.mahaexam.tenant.management.util.TenantResolver;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
	private final TeacherRepository teacherRepository;
	private final ApplicationUserService applicationUserService;
	private final UserService userService;
	private final UserTenantService userTenantService;
	private final AddressService addressService;
	private final OtpAuthServiceImpl otpAuthServiceImpl;

	public TeacherServiceImpl(TeacherRepository teacherRepository, ApplicationUserService applicationUserService,
			UserService userService, UserTenantService userTenantService, AddressService addressService,
			OtpAuthServiceImpl otpAuthServiceImpl) {
		this.teacherRepository = teacherRepository;
		this.applicationUserService = applicationUserService;
		this.userService = userService;
		this.userTenantService = userTenantService;
		this.addressService = addressService;
		this.otpAuthServiceImpl = otpAuthServiceImpl;
	}

	@Override
	public Teacher save(Teacher teacher) {
		return teacherRepository.save(teacher);
	}

	@Override
	public Optional<Teacher> findById(Long teacherId) {
		return teacherRepository.findById(teacherId);
	}

	@Override
	public List<Teacher> findAll() {
		return teacherRepository.findAll();
	}

	@Override
	public Teacher update(Teacher teacher) {
		return teacherRepository.update(teacher);
	}

	@Override
	public void delete(Long teacherId) {
		teacherRepository.delete(teacherId);
	}

	@Override
	public Optional<Teacher> findByUserId(Long userId) {
		return teacherRepository.findByUserId(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void registerTeacher(TeacherRegistrationBean registrationDTO, Boolean validateOtp) {
		// Validation
		validateRegistrationDTO(registrationDTO,validateOtp);
        if(validateOtp) {
            OtpAuth otpAuth = new OtpAuth();
            otpAuth.setEmail(registrationDTO.getEmail());
            otpAuth.setMobile(registrationDTO.getRegisteredMobileNumber());
            otpAuth.setOtp(registrationDTO.getOtp());
            boolean isValid = otpAuthServiceImpl.validateOtp(otpAuth);
            if (!isValid) {
                throw new IllegalArgumentException("The provided OTP is invalid or expired");
            }
        }
		Optional<ApplicationUser> existingUser = applicationUserService.findByEmailId(registrationDTO.getEmail());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("Email is already registered for this user");
		}

		// Save User
		UserBean userBean = new UserBean();
		userBean.setIsActive(Boolean.TRUE);
		userBean.setUserName(registrationDTO.getEmail());
		userBean.setTenantId(TenantResolver.resoveTenant(registrationDTO.getRefererUrl()));
		userBean.setIsSalt(Boolean.TRUE);

		String salt = registrationDTO.getEmail();
		String plaintext = registrationDTO.getPassword();
		String decryptedPassword = plaintext;
		if (validateOtp) {
			decryptedPassword = CryptoUtil.decrypt(plaintext);
		}
		String hashsedPwd = PasswordSHA2EncryptionUtil.hash(decryptedPassword, salt);

		userBean.setPassword(hashsedPwd);
		UserBean userBeanDB = userService.save(userBean);

		// Save User Tenant
		UserTenant userTenant = new UserTenant();
		userTenant.setUserId(userBeanDB.getUserId());
		userTenant.setTenantId(userBean.getTenantId());
		userTenantService.save(userTenant);
		
		Address address = new Address();
		address.setAddressText(
				Objects.nonNull(registrationDTO.getAddressText()) ? registrationDTO.getAddressText() : null);
		address.setStateId(Objects.nonNull(registrationDTO.getStateId()) ? registrationDTO.getStateId() : null);
		address.setDistrictId(
				Objects.nonNull(registrationDTO.getDistrictId()) ? registrationDTO.getDistrictId() : null);
		address.setTalukaId(Objects.nonNull(registrationDTO.getTalukaId()) ? registrationDTO.getTalukaId() : null);
		address.setPlace(Objects.nonNull(registrationDTO.getPlace()) ? registrationDTO.getPlace() : null);
		address.setPincode(Objects.nonNull(registrationDTO.getPinCode()) ? registrationDTO.getPinCode() : null);
		address.setUserId(userBeanDB.getUserId());
		address = addressService.save(address);
		
		
		// Save ApplicationUser
		registrationDTO.setUserId(userBeanDB.getUserId());
		ApplicationUser user = new ApplicationUser();
		user.setUserId(userBeanDB.getUserId());
		user.setUserType(AppConstants.USER_TYPE_TEACHER);
		user.setFirstName(registrationDTO.getFirstName());
		user.setLastName(registrationDTO.getLastName());
		user.setMiddleName(registrationDTO.getMiddleName());
		user.setGender(registrationDTO.getGender());
		user.setDateOfBirth(registrationDTO.getDateOfBirth());
		user.setAadharNumber(registrationDTO.getAadharNumber());
		user.setRegisteredMobileNumber(registrationDTO.getRegisteredMobileNumber());
		user.setWhatsappNumber(registrationDTO.getWhatsappNumber());
		user.setEmail(registrationDTO.getEmail());
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		if(Objects.nonNull(address.getAddressId())) {
			user.setAddressId(address.getAddressId());
		}
		applicationUserService.save(user);



		// Create a new Teacher object from the registration DTO
		Teacher teacher = new Teacher();
		teacher.setUserId(registrationDTO.getUserId());
		teacher.setSubjectId(registrationDTO.getSubjectId());
		teacher.setTeachingExperience(Objects.nonNull(registrationDTO.getTeachingExperience()) ? registrationDTO.getTeachingExperience() : 0);
		teacher.setTotalExperienceYears(Objects.nonNull(registrationDTO.getTotalExperienceYears()) ? registrationDTO.getTotalExperienceYears() : 0);
		teacher.setAreaOfInterest(Objects.nonNull(registrationDTO.getAreaOfInterest()) ?registrationDTO.getAreaOfInterest(): null);
		teacher.setBoardPaperSettingExperience(Objects.nonNull(registrationDTO.getBoardPaperSettingExperience()) ?registrationDTO.getBoardPaperSettingExperience() : 0);
		teacher.setChefModerationExperience(Objects.nonNull(registrationDTO.getChefModerationExperience()) ? registrationDTO.getChefModerationExperience() : 0);
		teacher.setIndividualRefCode(Objects.nonNull(registrationDTO.getIndividualRefCode()) ? registrationDTO.getIndividualRefCode(): null);
		teacher.setInService(Objects.nonNull(registrationDTO.getInService()) ? registrationDTO.getInService():Boolean.FALSE);
		teacher.setInstituteIndexNumber(Objects.nonNull(registrationDTO.getInstituteIndexNumber()) ?registrationDTO.getInstituteIndexNumber() :null);
		teacher.setJeeExp(Objects.nonNull(registrationDTO.getJeeExp()) ? registrationDTO.getJeeExp():0);
		teacher.setKvpyPaperSettingExperience(Objects.nonNull(registrationDTO.getKvpyPaperSettingExperience()) ? registrationDTO.getKvpyPaperSettingExperience() : 0);
		teacher.setValuationExperience(Objects.nonNull(registrationDTO.getValuationExperience()) ? registrationDTO.getValuationExperience() : 0 );
		teacher.setMhtCetExp(Objects.nonNull(registrationDTO.getMhtCetExp()) ? registrationDTO.getMhtCetExp() : 0);
		teacher.setMhtCetPaperSettingExperience(Objects.nonNull(registrationDTO.getMhtCetPaperSettingExperience()) ? registrationDTO.getMhtCetPaperSettingExperience() : 0);
		teacher.setModerationExperience(Objects.nonNull(registrationDTO.getModerationExperience()) ?  registrationDTO.getModerationExperience() : 0);
		teacher.setNeetExp(Objects.nonNull(registrationDTO.getNeetExp()) ? registrationDTO.getNeetExp() : 0);
		teacher.setSpecialtyTopicsSubjects(Objects.nonNull(registrationDTO.getSpecialtyTopicsSubjects()) ? registrationDTO.getSpecialtyTopicsSubjects() : null);
		teacher.setTotalExp(Objects.nonNull(registrationDTO.getTotalExp()) ? registrationDTO.getTotalExp() : 0);
		teacher.setNeetPaperSettingExperience(Objects.nonNull(registrationDTO.getNeetPaperSettingExperience()) ? registrationDTO.getNeetPaperSettingExperience() : 0);
		teacher.setJeePaperSettingExperience(Objects.nonNull(registrationDTO.getJeePaperSettingExperience()) ? registrationDTO.getJeePaperSettingExperience() :  0);
		teacher.setOnlineLectureTaken(Objects.nonNull(registrationDTO.getOnlineLectureTaken()) ? registrationDTO.getOnlineLectureTaken() : Boolean.FALSE );
		teacher.setQualification(Objects.nonNull(registrationDTO.getQualification()) ? registrationDTO.getQualification() : null);
		teacher.setRefCode(Objects.nonNull(registrationDTO.getRefCode()) ? registrationDTO.getRefCode() : null);
		// Save the Teacher object
		teacher.setPanNumber(Objects.nonNull(registrationDTO.getPanNumber()) ? registrationDTO.getPanNumber() : null);
		teacherRepository.save(teacher);

		otpAuthServiceImpl.delete(registrationDTO.getEmail(), registrationDTO.getRegisteredMobileNumber());
	}

	private void validateRegistrationDTO(TeacherRegistrationBean dto, Boolean validateOtp) {
		// Mandatory field validation
		if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
			throw new IllegalArgumentException("First Name is required");
		}
		if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
			throw new IllegalArgumentException("Last Name is required");
		}
//		if (dto.getAadharNumber() == null || dto.getAadharNumber().trim().isEmpty()) {
//			throw new IllegalArgumentException("Aadhar Number is required");
//		}
		if (dto.getRegisteredMobileNumber() == null || dto.getRegisteredMobileNumber().trim().isEmpty()) {
			throw new IllegalArgumentException("Registered Mobile Number is required");
		}
		if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("Email is required");
		}
		if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
			throw new IllegalArgumentException("Password is required");
		}

		if (dto.getReTypePassword() == null || dto.getReTypePassword().trim().isEmpty()) {
			throw new IllegalArgumentException("Retype Password is required");
		}
        if (dto.getPassword().length()>64) {
            throw new IllegalArgumentException("Password must not exceed 64 characters");
        }

        if (dto.getReTypePassword().length()>64) {
            throw new IllegalArgumentException("ReType Password must not exceed 64 characters");
        }
		if (!dto.getPassword().equals(dto.getReTypePassword())) {
			throw new IllegalArgumentException("Password and Retype Password is not Matching.");
		}
		// Valid value validation
		List<String> validGenders = Arrays.asList("MALE", "FEMALE", "OTHER");
		if (dto.getGender() != null && !validGenders.contains(dto.getGender().toUpperCase())) {
			throw new IllegalArgumentException("Gender must be one of: " + String.join(", ", validGenders));
		}

		// Additional validation (e.g., Aadhar number format, email format, mobile
		// number format)

		if (!dto.getRegisteredMobileNumber().matches("\\d{10}")) {
			throw new IllegalArgumentException("Registered Mobile Number must be a 10-digit number");
		}
		if ((dto.getWhatsappNumber() != null &&  !dto.getWhatsappNumber().isEmpty()) && !dto.getWhatsappNumber().matches("\\d{10}")) {
			throw new IllegalArgumentException("WhatsApp Number must be a 10-digit number if provided");
		}
		if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("Invalid Email format");
		}
        if(validateOtp) {
            if (dto.getOtp() == null || dto.getOtp().trim().isEmpty()) {
                throw new IllegalArgumentException("Otp Password is required");
            }
        }
	}
}