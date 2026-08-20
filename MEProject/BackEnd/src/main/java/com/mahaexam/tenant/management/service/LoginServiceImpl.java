package com.mahaexam.tenant.management.service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.config.TokenValidator;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.model.MessageTemplate;
import com.mahaexam.common.model.PwdResetOtp;
import com.mahaexam.common.service.*;
import com.mahaexam.common.util.CryptoUtil;
import com.mahaexam.common.util.PasswordSHA2EncryptionUtil;
import com.mahaexam.tenant.management.bean.ForgotPasswordRequest;
import com.mahaexam.tenant.management.bean.LoginRequest;
import com.mahaexam.tenant.management.bean.LoginResponse;
import com.mahaexam.tenant.management.bean.StudentDetailsBean;
import com.mahaexam.tenant.management.model.Role;
import com.mahaexam.tenant.management.model.Student;
import com.mahaexam.tenant.management.repository.StudentRepositoryImpl;
import com.mahaexam.tenant.management.util.SessionIdentifierGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);
    private final RoleService roleService;
    private final UserService userService;
    private final StudentService studentService;
    private final PowersTextSmsService smsService;
    private final EmailService emailService;
    private final MessageTemplateService messageTemplateService;
    private final ConfigService configService;
    private final PwdResetOtpService pwdResetOtpService;

    // In production, fetch from ConfigService
    private static final long JWT_EXPIRATION_MS = 3600000; // 1 hour
    private static final int OTP_EXPIRY_MINUTES = 30; // 30 minutes OTP expiry
    private static final int MAX_OTP_PER_USER = 3; // Maximum 3 active OTPs per user

    public LoginServiceImpl(UserService userService, RoleService roleService, StudentService studentService,
                            PowersTextSmsService smsService, EmailService emailService, MessageTemplateService messageTemplateService,
                            ConfigService configService, PwdResetOtpService pwdResetOtpService) {
        this.userService = userService;
        this.roleService = roleService;
        this.studentService = studentService;
        this.smsService = smsService;
        this.emailService = emailService;
        this.messageTemplateService = messageTemplateService;
        this.configService = configService;
        this.pwdResetOtpService = pwdResetOtpService;
    }

    private static final int MAX_ATTEMPTS = 5;
    private Map<String, Integer> loginAttempts = new ConcurrentHashMap<>();

    @Override
    public LoginResponse authenticate(LoginRequest loginRequestDTO) {
        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username (email) is required");
        }
        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        loginAttempts.putIfAbsent(loginRequestDTO.getUsername().trim(), 0);
        if (loginAttempts.get(loginRequestDTO.getUsername().trim()) >= MAX_ATTEMPTS) {
            throw new IllegalStateException("Account locked due to too many failed attempts");
        }
        boolean isAuthenticated = true;
        // Find user by username (email)
        Optional<UserBean> userOpt = userService.findByUsername(loginRequestDTO.getUsername(), true);
        if (userOpt.isEmpty()) {
            isAuthenticated = false;
            loginAttempts.merge(loginRequestDTO.getUsername(), 1, Integer::sum);
            return null;
        }
        UserBean user = userOpt.get();

        // Check if user is active
        if (!user.getIsActive()) {
            isAuthenticated = false;
        }

        String salt = "";
        if (user.getIsSalt()) {
            salt = loginRequestDTO.getUsername();
        }
        String decryptedPassword = CryptoUtil.decrypt(loginRequestDTO.getPassword());
        String hashsedPwd = PasswordSHA2EncryptionUtil.hash(decryptedPassword, salt);
        // Verify password
        isAuthenticated = (Objects.nonNull(hashsedPwd) && hashsedPwd.equals(user.getPassword()));
        String token = null;
        if (!isAuthenticated) {
            loginAttempts.merge(loginRequestDTO.getUsername(), 1, Integer::sum);
        } else {
            loginAttempts.remove(loginRequestDTO.getUsername());

            try {
                token = Jwts.builder().setSubject(user.getUserId() + "")
                        .claim("roles", user.getApplicationUser().getUserType())
                        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                        .signWith(SignatureAlgorithm.HS256, TokenValidator.JWT_SECRET).compact();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to serialize roles or permissions for JWT: " + e.getMessage());
            }

//			// Fetch user roles from user_tenant
            List<Role> roles = roleService.findRolesByUserId(user.getUserId());
            if (Objects.isNull(roles) || roles.isEmpty()) {
                throw new IllegalStateException("User is not activated. Please contact administrator.");
            }
            boolean hasPackage = studentService.hasPackage(user.getUserId());
            Optional<Role> roleFound = roles.stream().filter(r -> r.getName().equalsIgnoreCase(AppConstants.ROLE_STUDENT)).findFirst();
            StudentDetailsBean studentDetailsBean = null;
            if(roleFound.isPresent()){
                Optional<Student> studentOpt = studentService.findByUserId(user.getUserId());
                if (studentOpt.isPresent()) {
                    studentDetailsBean = studentService.findByIdFull(user.getUserId());
                }
            }

            logger.info("hasPackage==>"+hasPackage);
            return LoginResponse.builder().token(token).roles(roles).userId(user.getUserId())
                    .hasPackage(hasPackage).displayName(user.getApplicationUser().getFirstName() + " " + user.getApplicationUser().getLastName())
                    .student(studentDetailsBean).build();
        }
        return null;
    }

    @Override
    @Transactional
    public boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<UserBean> userOpt = userService.findByUsername(forgotPasswordRequest.getUserName(), true);
        UserBean userBean = userOpt.orElseThrow(() -> new IllegalArgumentException("This email ID/user name is not registered. Please sign up or check your email address."));

        // Check if user has reached OTP limit
        if (pwdResetOtpService.hasReachedOtpLimit(userBean.getUserId(), MAX_OTP_PER_USER)) {
            throw new IllegalStateException("Too many OTP requests. Please wait before requesting a new OTP or use an existing valid OTP.");
        }

        Optional<Config> configOptional = configService.findByName(ConfigService.DEFAULT_SMS);
        String defaultSMS = configOptional.map(Config::getValue).orElse(null);

        // Generate and store OTP in pwd_reset_otp table BEFORE sending SMS
        PwdResetOtp pwdResetOtp = pwdResetOtpService.generateOtp(userBean.getUserId(), OTP_EXPIRY_MINUTES);
        String plaintext = pwdResetOtp.getOtp();
        if("1".equals(defaultSMS)){
            plaintext="0000";
        }
        // Send SMS
        MessageTemplate templateByNameAndType = messageTemplateService.getTemplateByNameAndType("reset_password", "SMS");
        String smsText = templateByNameAndType.getContent();
        smsText = String.format(smsText, plaintext);
        smsService.sendSms(templateByNameAndType.getSmsTemplateId(), smsText, userBean.getApplicationUser().getRegisteredMobileNumber());
        logger.info(smsText+"forgotPassword SMS sent to user: {}", userBean.getUserName());

        // Send Email
        templateByNameAndType = messageTemplateService.getTemplateByNameAndType("reset_password", "EMAIL");
        String emailText = templateByNameAndType.getContent();
        emailText = String.format(emailText, userBean.getApplicationUser().getEmail(), plaintext);
        emailService.sendEmail(userBean.getApplicationUser().getEmail(), null, null, templateByNameAndType.getSubject(), emailText, false);
        logger.info("forgotPassword email sent to user: {}", userBean.getUserName());

        return true;
    }

    /**
     * Validate OTP for password reset
     * @param userName User's email/username
     * @param otp OTP to validate
     * @return true if OTP is valid
     */
    public boolean validateResetOtp(String userName, String otp) {
        Optional<UserBean> userOpt = userService.findByUsername(userName, true);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        UserBean userBean = userOpt.get();
        return pwdResetOtpService.validateOtp(userBean.getUserId(), otp);
    }

    /**
     * Reset password using OTP
     * @param userName User's email/username
     * @param otp OTP for validation
     * @param newPassword New password
     * @return true if password reset successful
     */
    @Transactional
    public boolean resetPasswordWithOtp(String userName, String otp, String newPassword) {
        Optional<UserBean> userOpt = userService.findByUsername(userName, true);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        UserBean userBean = userOpt.get();

        // Validate and consume OTP
        Optional<PwdResetOtp> validOtp = pwdResetOtpService.validateAndConsumeOtp(userBean.getUserId(), otp);
        if (validOtp.isEmpty()) {
            throw new IllegalArgumentException("Invalid or expired OTP");
        }

        // Update password
        userBean.setIsSalt(Boolean.TRUE);
        String salt = userBean.getUserName();
        String hashedPassword = PasswordSHA2EncryptionUtil.hash(newPassword, salt);
        userBean.setPassword(hashedPassword);
        userService.update(userBean);

        // Clean up any remaining OTPs for this user
        pwdResetOtpService.deleteUserOtps(userBean.getUserId());

        logger.info("Password reset successful for user: {}", userName);
        return true;
    }
}
