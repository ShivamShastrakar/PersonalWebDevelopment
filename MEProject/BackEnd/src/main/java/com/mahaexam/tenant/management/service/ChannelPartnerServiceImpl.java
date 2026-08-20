package com.mahaexam.tenant.management.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mahaexam.common.util.CryptoUtil;
import com.mahaexam.tenant.management.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.util.PasswordSHA2EncryptionUtil;
import com.mahaexam.tenant.management.bean.ChannelPartnerRegistrationBean;
import com.mahaexam.tenant.management.repository.ChannelPartnerRepository;
import com.mahaexam.tenant.management.util.TenantResolver;

@Service
public class ChannelPartnerServiceImpl implements ChannelPartnerService {
    private static final Logger logger = LoggerFactory.getLogger(ChannelPartnerServiceImpl.class);
	private final ChannelPartnerRepository repository;
	private final ApplicationUserService applicationUserService;
	private final UserService userService;
	private final UserTenantService userTenantService;
	private final AddressService addressService;
    private final BankAccountService  bankAccountService;
	private final OtpAuthServiceImpl otpAuthServiceImpl;

	public ChannelPartnerServiceImpl(ChannelPartnerRepository repository, ApplicationUserService applicationUserService,
			UserService userService, UserTenantService userTenantService, AddressService addressService,
			OtpAuthServiceImpl otpAuthServiceImpl, BankAccountService bankAccountService) {
		super();
		this.repository = repository;
		this.applicationUserService = applicationUserService;
		this.userService = userService;
		this.userTenantService = userTenantService;
		this.addressService = addressService;
		this.otpAuthServiceImpl = otpAuthServiceImpl;
        this.bankAccountService = bankAccountService;
	}

	@Override
	public ChannelPartner save(ChannelPartnerRegistrationBean channelPartnerRegBean) {

		ChannelPartner cp = new ChannelPartner();

		return repository.save(cp);
	}

	@Override
	public Optional<ChannelPartner> findById(Long partnerId) {
		return repository.findById(partnerId);
	}

	@Override
	public List<ChannelPartner> findAll() {
		return repository.findAll();
	}

	@Override
	public ChannelPartner update(ChannelPartner channelPartner) {
		return repository.update(channelPartner);
	}

	@Override
	public void delete(Long partnerId) {
		repository.delete(partnerId);
	}

	@Override
	public Optional<ChannelPartner> findByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void resiterCP(ChannelPartnerRegistrationBean channelPartnerRegBean, Boolean validateOtp) {
		// Validation
		validateRegistrationDTO(channelPartnerRegBean,validateOtp);
        if(validateOtp) {
            OtpAuth otpAuth = new OtpAuth();
            otpAuth.setEmail(channelPartnerRegBean.getEmail());
            otpAuth.setMobile(channelPartnerRegBean.getRegisteredMobileNumber());
            otpAuth.setOtp(channelPartnerRegBean.getOtp());
            boolean isValid = otpAuthServiceImpl.validateOtp(otpAuth);
            if (!isValid) {
                throw new IllegalArgumentException("The provided OTP is invalid or expired");
            }
        }
		// Check for email uniqueness
		Optional<ApplicationUser> existingUser = applicationUserService.findByEmailId(channelPartnerRegBean.getEmail());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("Email is already registered for this user.");
		}

		// Save User
		UserBean userBean = new UserBean();
		userBean.setIsActive(Boolean.TRUE);
		userBean.setUserName(channelPartnerRegBean.getEmail());
		userBean.setTenantId(TenantResolver.resoveTenant(channelPartnerRegBean.getRefererUrl()));
		userBean.setIsSalt(Boolean.TRUE);
		String salt = channelPartnerRegBean.getEmail();
		String plaintext = channelPartnerRegBean.getPassword();
		String decryptedPassword = plaintext;
		if (validateOtp) {
			decryptedPassword = CryptoUtil.decrypt(plaintext);
		}
		String hashsedPwd = PasswordSHA2EncryptionUtil.hash(decryptedPassword, salt);

		userBean.setPassword(hashsedPwd);
		UserBean userBeanDB = userService.save(userBean);

		channelPartnerRegBean.setUserId(userBeanDB.getUserId());

		// Save User Tenant
		UserTenant userTenant = new UserTenant();
		userTenant.setUserId(userBeanDB.getUserId());
		userTenant.setTenantId(userBean.getTenantId());
		userTenantService.save(userTenant);

        // channel partner member address

        Address address = new Address();
        address.setUserId(userBeanDB.getUserId());
        address.setAddressText(channelPartnerRegBean.getAddressText());
        address.setPlace(channelPartnerRegBean.getPlace());
        address.setDistrictId(channelPartnerRegBean.getDistrictId());
        address.setPincode(channelPartnerRegBean.getPincode());
        address.setStateId(channelPartnerRegBean.getStateId());
        address.setTalukaId(channelPartnerRegBean.getTalukaId());
        Address addressDB = addressService.save(address);
        if(Objects.nonNull(channelPartnerRegBean.getBankName())) {
            BankAccount bankAccount  = new BankAccount();
            bankAccount.setUserId(userBeanDB.getUserId());
            bankAccount.setBankName(channelPartnerRegBean.getBankName());
            bankAccount.setBranchName(channelPartnerRegBean.getBranchName());
            bankAccount.setAccountNumber(channelPartnerRegBean.getAccountNumber());
            bankAccount.setIfscCode(channelPartnerRegBean.getIfscCode());
            bankAccountService.save(bankAccount);
        }
        // Save ApplicationUser
		ApplicationUser user = new ApplicationUser();
		user.setUserId(userBeanDB.getUserId());
		user.setUserType(AppConstants.USER_TYPE_CHANNEL_PARTNER);
		user.setFirstName(channelPartnerRegBean.getFirstName());
		user.setLastName(channelPartnerRegBean.getLastName());
		user.setMiddleName(channelPartnerRegBean.getMiddleName());
		user.setGender(channelPartnerRegBean.getGender());
		user.setDateOfBirth(channelPartnerRegBean.getDateOfBirth());
		user.setAadharNumber(channelPartnerRegBean.getAadharNumber());
		user.setRegisteredMobileNumber(channelPartnerRegBean.getRegisteredMobileNumber());
		user.setWhatsappNumber(channelPartnerRegBean.getWhatsappNumber());
		user.setEmail(channelPartnerRegBean.getEmail());
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
        user.setAddressId(addressDB.getAddressId());
		applicationUserService.save(user);



		// Channel Partner add
		ChannelPartner cp = new ChannelPartner();
		cp.setUserId(userBeanDB.getUserId());
		cp.setCompanyName(channelPartnerRegBean.getCompanyName());
		cp.setBusinessType(channelPartnerRegBean.getBusinessType());
		cp.setPanNumber(channelPartnerRegBean.getPanNumber());
		cp.setTanNumber(channelPartnerRegBean.getTanNumber());
		cp.setServiceType(channelPartnerRegBean.getServiceType());
		cp.setGstNumber(channelPartnerRegBean.getGstNumber());
		cp.setBusinessExpYears(channelPartnerRegBean.getBusinessExpYears());
		cp.setDeeperAssociationYears(channelPartnerRegBean.getDeeperAssociationYears());
		cp.setParentPartnerId(channelPartnerRegBean.getParentPartnerId());
		repository.registerCP(cp);

		otpAuthServiceImpl.delete(channelPartnerRegBean.getEmail(), channelPartnerRegBean.getRegisteredMobileNumber());
        logger.info("Channel partner registered successfully with user id : " + userBeanDB.getUserId());
	}

	private void validateRegistrationDTO(ChannelPartnerRegistrationBean cpBean,Boolean validateOtp) {

		if (cpBean.getFirstName() == null || cpBean.getFirstName().trim().isEmpty()) {
			throw new IllegalArgumentException("First Name is required");
		}
		if (cpBean.getLastName() == null || cpBean.getLastName().trim().isEmpty()) {
			throw new IllegalArgumentException("Last Name is required");
		}
//		if (dto.getAadharNumber() == null || dto.getAadharNumber().trim().isEmpty()) {
//			throw new IllegalArgumentException("Aadhar Number is required");
//		}
		if (cpBean.getRegisteredMobileNumber() == null || cpBean.getRegisteredMobileNumber().trim().isEmpty()) {
			throw new IllegalArgumentException("Registered Mobile Number is required");
		}
		if (cpBean.getEmail() == null || cpBean.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("Email is required");
		}

		if (cpBean.getBusinessType() == null || cpBean.getBusinessType().trim().isEmpty()) {
			throw new IllegalArgumentException("Business Type is required");
		}
        if (cpBean.getPassword() == null || cpBean.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (cpBean.getReTypePassword() == null || cpBean.getReTypePassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Retype Password is required");
        }
        if (cpBean.getPassword().trim().length()>255) {
            throw new IllegalArgumentException("Password must not exceed 255 characters");
        }
        if (cpBean.getReTypePassword().trim().length()>255) {
            throw new IllegalArgumentException("Retype Password must not exceed 255 characters");
        }
        if (!cpBean.getPassword().equals(cpBean.getReTypePassword())) {
            throw new IllegalArgumentException("Password and Retype Password must not matching.");
        }
		// Valid value validation
		List<String> validGenders = Arrays.asList("MALE", "FEMALE", "OTHER");
		if (cpBean.getGender() != null && !validGenders.contains(cpBean.getGender().toUpperCase())) {
			throw new IllegalArgumentException("Gender must be one of: " + String.join(", ", validGenders));
		}
		if (cpBean.getAddressText() == null || cpBean.getAddressText().trim().isEmpty()) {
			throw new IllegalArgumentException("Address is required");
		}
		if (cpBean.getStateId() == null || (cpBean.getStateId() == 0)) {
			throw new IllegalArgumentException("State is required");
		}
		if (cpBean.getDistrictId() == null || (cpBean.getDistrictId() == 0)) {
			throw new IllegalArgumentException("District is required");
		}
		if (cpBean.getTalukaId() == null || (cpBean.getTalukaId() == 0)) {
			throw new IllegalArgumentException("District is required");
		}
        if(validateOtp){
            if (cpBean.getOtp() == null || cpBean.getOtp().trim().isEmpty()) {
                throw new IllegalArgumentException("District is required");
            }
        }

	}
}
