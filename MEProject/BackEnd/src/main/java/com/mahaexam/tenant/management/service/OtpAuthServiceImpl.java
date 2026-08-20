package com.mahaexam.tenant.management.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.Config;
import com.mahaexam.common.model.MessageTemplate;
import com.mahaexam.common.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.OtpAuth;
import com.mahaexam.tenant.management.repository.OtpAuthRepository;
import com.mahaexam.tenant.management.util.SessionIdentifierGenerator;

@Service
public class OtpAuthServiceImpl implements OtpAuthService {

    private static final Logger logger = LogManager.getLogger(OtpAuthServiceImpl.class);
	private final OtpAuthRepository otpAuthRepository;

	private final ApplicationUserService applicationUserService;
	private final PowersTextSmsService smsService;
	private final EmailService emailService;
	private final UserService userService;
    private final MessageTemplateService messageTemplateService;
    private final ConfigService configService;

	public OtpAuthServiceImpl(OtpAuthRepository otpAuthRepository, ApplicationUserService applicationUserService,
                              PowersTextSmsService smsService, EmailService emailService,  UserService userService,
                              MessageTemplateService messageTemplateService, ConfigService configService) {
		this.otpAuthRepository = otpAuthRepository;
		this.applicationUserService = applicationUserService;
		this.smsService = smsService;
		this.emailService = emailService;
		this.userService = userService;
        this.messageTemplateService = messageTemplateService;
        this.configService = configService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public OtpAuth generateAndSave(OtpAuth otpAuth) {
		validateOtpAuth(otpAuth);
		if (otpAuth.getCreatedAt() == null) {
			otpAuth.setCreatedAt(LocalDateTime.now());
		}
		Optional<UserBean> existingUser = userService.findByUsername(otpAuth.getEmail(),false);
		if(existingUser.isPresent()) {
			throw new IllegalArgumentException("User is already registered with us for Email Id "+otpAuth.getEmail());
		}
        existingUser = userService.findByMoblieNo(otpAuth.getMobile(),false);
        if(existingUser.isPresent()) {
            throw new IllegalArgumentException("User is already registered with us for Mobile Number "+otpAuth.getMobile());
        }

		Optional<OtpAuth> existingMobile = otpAuthRepository.findByMobile(otpAuth.getMobile());
        existingMobile.ifPresent(auth -> otpAuthRepository.delete(auth.getId()));

        Optional<ApplicationUser> applicationUserOpt = applicationUserService
				.findByEmailIdAndMobileNo(otpAuth.getEmail(), otpAuth.getMobile());
		if (applicationUserOpt.isPresent()) {
			throw new IllegalArgumentException("User is already registered with us.");
		}

        Optional<Config> configOptional = configService.findByName(ConfigService.DEFAULT_SMS);
        String defaultSMS = configOptional.map(Config::getValue).orElse(null);
        String otp;
        if("1".equals(defaultSMS)){
            otp = "0000";
        }else {
            otp = SessionIdentifierGenerator.getOTP(AppConstants.OTP_LENGTH);
        }
		otpAuth.setOtp(otp);

		OtpAuth otpAuthDB = otpAuthRepository.save(otpAuth);
//		String smsText = ConfigService.APP_RESET_EMAIL_BODY;
//		smsText = MessageFormat.format(smsText, otpAuth.getEmail(), opt);

        //The OTP to complete your registration on MahaExam is 036207 To login click mahaexam.org.in -EDUVAL

        String appUrl = configService.findByName(ConfigService.SMS_APP_URL)
                .map(Config::getValue)
                .orElse("");
        MessageTemplate templateByNameAndType = messageTemplateService.getTemplateByNameAndType("opt_verification", "SMS");
        String  smsText = templateByNameAndType.getContent();
        //The OTP to complete your registration on MahaExam is %s To login click %s -EDUVAL
        smsText = String.format(smsText,  otp, appUrl);
        smsService.sendSms(templateByNameAndType.getSmsTemplateId(), smsText, otpAuth.getMobile());
        logger.info("Registration Process smsText==>"+smsText);
        templateByNameAndType = messageTemplateService.getTemplateByNameAndType("opt_verification", "EMAIL");
        smsText = templateByNameAndType.getContent();
        smsText = String.format(smsText,  otp);
        emailService.sendEmail( otpAuth.getEmail(), null, null, templateByNameAndType.getSubject(), smsText, true);
		return otpAuthDB;
	}

	@Override
	public Optional<OtpAuth> findById(Long id) {
		return otpAuthRepository.findById(id);
	}

	@Override
	public List<OtpAuth> findAll() {
		return otpAuthRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public OtpAuth update(OtpAuth otpAuth) {
		validateOtpAuth(otpAuth);
		return otpAuthRepository.update(otpAuth);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public void delete(Long id) {
        otpAuthRepository.delete(id);
	}

	@Override
	public Optional<OtpAuth> findByEmail(String email) {
		return otpAuthRepository.findByEmail(email);
	}

	@Override
	public Optional<OtpAuth> findByEmailAndMobile(String email, String mobile) {
		return otpAuthRepository.findByEmailAndMobile(email, mobile);
	}
	@Override
	public void delete(String email, String mobile) {
        otpAuthRepository.delete(email, mobile);
	}

	@Override
	public boolean validateOtp(OtpAuth otpAuthParm) {
		Optional<OtpAuth> otpAuth = findByEmailAndMobile(otpAuthParm.getEmail(), otpAuthParm.getMobile());
		if (otpAuth.isEmpty()) {
			return false;
		}
		OtpAuth record = otpAuth.get();
		// Check if OTP matches and is not expired (e.g., valid for 5 minutes)
		if (!record.getOtp().equals(otpAuthParm.getOtp())) {
			return false;
		}
//		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime expiryTime = record.getCreatedAt().plusMinutes(5);
//		return !now.isAfter(expiryTime);
		return true;
	}

	private void validateOtpAuth(OtpAuth otpAuth) {
		if (otpAuth.getEmail() == null || otpAuth.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("Email is required");
		}
		if (otpAuth.getMobile() != null && !otpAuth.getMobile().trim().isEmpty()
				&& !otpAuth.getMobile().matches("[6-9]\\d{9}")) {
			throw new IllegalArgumentException(
					"Mobile number must be a 10-digit number starting with 6, 7, 8, or 9 if provided");
		}
	}
}