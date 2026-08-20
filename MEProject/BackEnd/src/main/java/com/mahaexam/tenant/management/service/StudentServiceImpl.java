package com.mahaexam.tenant.management.service;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.config.S3Helper;
import com.mahaexam.common.config.S3Service;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.model.MessageTemplate;
import com.mahaexam.common.model.SubjectGroup;
import com.mahaexam.common.service.*;
import com.mahaexam.common.util.CryptoUtil;
import com.mahaexam.common.util.PasswordSHA2EncryptionUtil;
import com.mahaexam.common.util.StringUtil;
import com.mahaexam.packagemanagment.bean.PackageBean;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.packagemanagment.model.StudentPackageMapping;
import com.mahaexam.packagemanagment.service.PackageService;
import com.mahaexam.packagemanagment.service.StudentPackageMappingService;
import com.mahaexam.tenant.management.bean.*;
import com.mahaexam.tenant.management.model.*;
import com.mahaexam.tenant.management.repository.StudentRepository;
import com.mahaexam.tenant.management.util.TenantResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final ApplicationUserService applicationUserService;
    private final StudentClassService studentClassService;
    private final StudentSubjectGroupService studentSubjectGroupService;
    private final StudentCourseService studentCourseService;
    private final UserService userService;
    private final UserTenantService userTenantService;
    private final RoleService roleService;
    private final OtpAuthServiceImpl otpAuthServiceImpl;
    private final ConfigService configService;
    private final S3Service s3Service;
    private final ParentService parentService;
    private final AddressService addressService;
    private final PackageService packageService;
    private final S3Helper s3Helper;
    private final StudentPackageMappingService studentPackageMappingService;
    private final MessageTemplateService messageTemplateService;
    private final EmailService emailService;
    private final PowersTextSmsService smsService;
    private final ClassService classService;
    private final SubjectGroupService subjectGroupService;

    public StudentServiceImpl(StudentRepository studentRepository, ApplicationUserService applicationUserService,
                              StudentClassService studentClassService, StudentSubjectGroupService studentSubjectGroupService,
                              StudentCourseService studentCourseService, UserService userService, UserTenantService userTenantService,
                              RoleService roleService, OtpAuthServiceImpl otpAuthServiceImpl, ConfigService configService,
                              S3Service s3Service, ParentService parentService, AddressService addressService,
                              PackageService packageService, S3Helper s3Helper,
                              StudentPackageMappingService studentPackageMappingService,
                              MessageTemplateService messageTemplateService,
                              EmailService emailService, PowersTextSmsService smsService, ClassService classService,
                              SubjectGroupService subjectGroupService) {
        this.studentRepository = studentRepository;
        this.applicationUserService = applicationUserService;
        this.studentClassService = studentClassService;
        this.studentSubjectGroupService = studentSubjectGroupService;
        this.studentCourseService = studentCourseService;
        this.userService = userService;
        this.userTenantService = userTenantService;
        this.roleService = roleService;
        this.otpAuthServiceImpl = otpAuthServiceImpl;
        this.configService = configService;
        this.s3Service = s3Service;
        this.parentService = parentService;
        this.addressService = addressService;
        this.packageService = packageService;
        this.s3Helper = s3Helper;
        this.studentPackageMappingService = studentPackageMappingService;
        this.messageTemplateService = messageTemplateService;
        this.emailService = emailService;
        this.smsService = smsService;
        this.classService = classService;
        this.subjectGroupService = subjectGroupService;
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public StudentDetailsBean findByIdFull(Long studentId) {
        Optional<StudentDetailsBean> studentOpt = studentRepository.findByIdFull(studentId);
        StudentDetailsBean studentDetailsBean = null;
        if (studentOpt.isPresent()) {
            studentDetailsBean = studentOpt.get();
            studentId = studentDetailsBean.getStudentId();
            Optional<ApplicationUser> applicationUserOpt = applicationUserService
                    .findByUserId(studentDetailsBean.getUserId());
            ApplicationUser applicationUser = applicationUserOpt.orElseThrow(() -> new IllegalArgumentException("User not found, invalid request."));
            studentDetailsBean.setPhotoUrl(applicationUser.getPhotoUrl());
            String photoImg = null;
            try {
                if (Objects.nonNull(studentDetailsBean.getPhotoUrl())) {
                    Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
                    Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                            "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
                    String s3BucketName = config.getValue();
                    String base64Img = s3Service.getImageAsBase64(s3BucketName, studentDetailsBean.getPhotoUrl());
                    String extension = studentDetailsBean.getPhotoUrl()
                            .substring(studentDetailsBean.getPhotoUrl().lastIndexOf(".") + 1).toLowerCase();
                    String mimeType = StringUtil.getMimeTypeByExtension(extension);
                    photoImg = "data:" + mimeType + ";," + base64Img;
                }
            } catch (Exception e) {
                logger.error("Error fetching student image for studentId: " + studentId, e);
            }
            List<StudentCourse> courses = studentCourseService.findByStudentId(studentId);
            List<StudentSubjectGroup> studentSubjectGroups = studentSubjectGroupService.findByStudentId(studentId);
            Optional<Parent> optionalParent = parentService.findByStudentId(studentId);
            Optional<Address> addresOpt = addressService.findByUserId(studentDetailsBean.getUserId());
            studentDetailsBean.setPhotoImg(photoImg);
            studentDetailsBean.setStudentCourses(courses);
            studentDetailsBean.setStudentSubjectGroups(studentSubjectGroups);
            studentDetailsBean.setParent(ParentConverter.toParentBean(optionalParent.orElse(null)));
            studentDetailsBean.setAddress(AddressConverter.toAddressBean(addresOpt.orElse(null)));
        }
        return studentDetailsBean;
    }



    @Override
    public StudentDetailsBean getStudentImageById(Long studentUserId) {
        Optional<Student> studentOpt = studentRepository.findByUserId(studentUserId);
        StudentDetailsBean studentDetailsBean = null;
        if (studentOpt.isPresent()) {
            Student studentObj = studentOpt.get();
            String photoImg = null;
            Optional<ApplicationUser> applicationUserOpt = applicationUserService
                    .findByUserId(studentObj.getUserId());
            ApplicationUser applicationUser = applicationUserOpt.orElseThrow(() -> new IllegalArgumentException("User not found, invalid request."));
            studentObj.setPhotoUrl(applicationUser.getPhotoUrl());
            if (Objects.nonNull(studentObj.getPhotoUrl())) {
                Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
                Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                        "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
                String s3BucketName = config.getValue();
                String base64Img = s3Service.getImageAsBase64(s3BucketName, studentObj.getPhotoUrl());
                String extension = studentObj.getPhotoUrl().substring(studentObj.getPhotoUrl().lastIndexOf(".") + 1)
                        .toLowerCase();
                String mimeType = StringUtil.getMimeTypeByExtension(extension);
                photoImg = "data:" + mimeType + ";base64," + base64Img;
            }
            studentDetailsBean = new StudentDetailsBean();
            studentDetailsBean.setStudentId(studentObj.getStudentId());
            studentDetailsBean.setPhotoUrl(studentObj.getPhotoUrl());
            studentDetailsBean.setPhotoImg(photoImg);
        }
        return studentDetailsBean;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public PaginatedResponse<StudentDetailsBean> search(UserBean user, StudentSerchBean studentSerchBean) {
        Optional<ApplicationUser> userOpt = applicationUserService.findByUserId(user.getUserId());
        if (userOpt.isPresent()) {
            if(AppConstants.USER_TYPE_CHANNEL_PARTNER.equals(userOpt.get().getUserType())){
                studentSerchBean.setStudentReferenceId(userOpt.get().getUserId());
            } else if (AppConstants.USER_TYPE_TEACHER.equals(userOpt.get().getUserType())) {
                studentSerchBean.setStudentReferenceId(userOpt.get().getUserId());
            }
        }
        PaginatedResponse<StudentDetailsBean> paginatedResponse = studentRepository.search(user, studentSerchBean);
        List<StudentDetailsBean> studentDetailsBeans = paginatedResponse.getContent();

        List<Long> studentIds = studentDetailsBeans.stream().map(StudentDetailsBean::getStudentId).collect(Collectors.toList());
        List<Long> userIds = studentDetailsBeans.stream().map(StudentRegistrationBean::getUserId).collect(Collectors.toList());
        List<StudentCourse> studentCourses = studentCourseService.findByStudentIds(studentIds);
        List<StudentSubjectGroup> studentSubjectGroups = studentSubjectGroupService.findByStudentIds(studentIds);
        List<Parent> parents = parentService.findByStudentIds(studentIds);
        List<Address> addresses = addressService.findByUserIds(userIds);
        List<PackageBean> packageBeans = packageService.findAllByUserId(user, studentIds);
        studentDetailsBeans.forEach(sdb -> {
            List<StudentCourse> courses = studentCourses.stream()
                    .filter(sc -> sdb.getStudentId().equals(sc.getStudentId())).collect(Collectors.toList());
            List<StudentSubjectGroup> subjectGroups = studentSubjectGroups.stream()
                    .filter(sc -> sdb.getStudentId().equals(sc.getStudentId())).collect(Collectors.toList());
            Optional<Parent> optionalParent = parents.stream()
                    .filter(sc -> sdb.getStudentId().equals(sc.getStudentId())).findFirst();

            Optional<Address> addresOpt = addresses.stream().filter(add -> sdb.getUserId().equals(add.getUserId()))
                    .findFirst();
            List<PackageBean> studentPackages = packageBeans.stream()
                    .filter(sc -> sdb.getStudentId().equals(sc.getStudentId())).collect(Collectors.toList());
            sdb.setStudentCourses(courses);
            sdb.setStudentSubjectGroups(subjectGroups);
            sdb.setParent(ParentConverter.toParentBean(optionalParent.orElse(null)));
            sdb.setAddress(AddressConverter.toAddressBean(addresOpt.orElse(null)));
            sdb.setStudentPackages(studentPackages);
        });
        paginatedResponse.setContent(studentDetailsBeans);
        return paginatedResponse;
    }

    @Override
    public Student update(Student student) {
        return studentRepository.update(student);
    }

    @Override
    public void delete(Long studentId) {
        studentRepository.delete(studentId);
    }

    @Override
    public Optional<Student> findByUserId(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public StudentDetailsBean update(StudentDetailsBean studentDetailsBean) {
        validateRegistrationDTO(false, studentDetailsBean);
        if(Objects.isNull(studentDetailsBean.getApplicationUserId())){
            studentDetailsBean.setApplicationUserId(studentDetailsBean.getStudentId());
        }
        Optional<ApplicationUser> applicationUserOpt = applicationUserService.findByUserId(studentDetailsBean.getUserId());
        if (!applicationUserOpt.isPresent()) {
            logger.error("Application User not Found for the id {}", studentDetailsBean.getApplicationUserId());
            throw new IllegalArgumentException("User Not Found.");
        }
        ApplicationUser applicationUser = applicationUserOpt.get();
        studentDetailsBean.setPhotoUrl(applicationUser.getPhotoUrl());

        // Save ApplicationUser
        ApplicationUser user = new ApplicationUser();
        user.setId(applicationUser.getId());
        user.setUserId(studentDetailsBean.getUserId());
        user.setUserType(AppConstants.USER_TYPE_STUDENT);
        user.setFirstName(studentDetailsBean.getFirstName());
        user.setLastName(studentDetailsBean.getLastName());
        user.setMiddleName(studentDetailsBean.getMiddleName());
        user.setGender(studentDetailsBean.getGender());
        user.setDateOfBirth(studentDetailsBean.getDateOfBirth());
        user.setAadharNumber(studentDetailsBean.getAadharNumber());
        user.setRegisteredMobileNumber(studentDetailsBean.getRegisteredMobileNumber());
        user.setWhatsappNumber(studentDetailsBean.getWhatsappNumber());
        user.setEmail(studentDetailsBean.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPhotoUrl(studentDetailsBean.getPhotoUrl());
        if (Objects.nonNull(studentDetailsBean.getPhotoImg())) {
            Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
            Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
            String s3BucketName = config.getValue();

            configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_PHOTO_IMG_FOLDER);
            String photoFolderName = "photo";
            if (configOpt.isPresent()) {
                config = configOpt.get();
                photoFolderName = config.getValue();

            }
            String fileName = StringUtil.extractFilenameFromBase64(studentDetailsBean.getPhotoImg());
            user.setPhotoUrl(photoFolderName + "/" + fileName);
            s3Service.uploadBase64ImageToS3(s3BucketName, photoFolderName, fileName, studentDetailsBean.getPhotoImg());
            s3Helper.deleteExistingImage(s3BucketName, studentDetailsBean.getPhotoUrl());
        }



        Student student = new Student();
        ParentBean parent = studentDetailsBean.getParent();
        if (Objects.nonNull(parent)) {
            Parent parentModel = ParentConverter.toParent(parent);
            Optional<Parent> parentOptional = parentService.findByStudentId(studentDetailsBean.getStudentId());
            parentOptional.ifPresent(value -> parentModel.setParentId(value.getParentId()));

            if (Objects.nonNull(parentModel.getParentId())) {
                parentService.updateParent(parentModel);
            } else {
                parentService.saveParent(parentModel);
            }
            student.setParentId(parentModel.getParentId());
        }

        student.setStudentId(studentDetailsBean.getStudentId());
        student.setUserId(studentDetailsBean.getUserId());
        student.setCurrentClassId(studentDetailsBean.getClassId());
        student.setCurrentSubjectGroupId(studentDetailsBean.getSubjectGroupId());
        student.setTargetFinalExamYear(studentDetailsBean.getTargetFinalExamYear());
        student.setStudentReferenceId(studentDetailsBean.getStudentReferenceId());
        student.setMedium(studentDetailsBean.getMedium());
        student.setSchoolName(studentDetailsBean.getSchoolName());
        student.setSchoolAddress(studentDetailsBean.getSchoolAddress());
        student.setInstituteName(studentDetailsBean.getInstituteName());
        student.setCategory(studentDetailsBean.getCategory());
        student.setParallelReservation(studentDetailsBean.getParallelReservation());
        if (Objects.nonNull(studentDetailsBean.getParent()) && Objects.nonNull(studentDetailsBean.getParent().getParentId())) {
            student.setParentId(studentDetailsBean.getParent().getParentId());
        }
        studentRepository.update(student);

        AddressBean addressBean = studentDetailsBean.getAddress();
        if (Objects.nonNull(addressBean)) {
            Address address = AddressConverter.toAddress(addressBean);
            Optional<Address> addressOptional = addressService.findByUserId(studentDetailsBean.getUserId());
            addressOptional.ifPresent(value -> address.setAddressId(value.getAddressId()));
            address.setUserId(studentDetailsBean.getUserId());
            if (Objects.nonNull(address.getAddressId()) && address.getAddressId()>0) {
                addressService.update(address);
            } else {
                address.setAddressId(null);
                addressService.save(address);
            }
            user.setAddressId(address.getAddressId());
        }
        applicationUserService.update(user);
        // Save StudentClass
        StudentClass studentClass = new StudentClass();
        studentClass.setStudentId(student.getStudentId());
        studentClass.setClassId(studentDetailsBean.getClassId());
        studentClassService.deleteStudentId(student.getStudentId());
        studentClassService.save(studentClass);

        // Save StudentSubjectGroup
        StudentSubjectGroup studentSubjectGroup = new StudentSubjectGroup();
        studentSubjectGroup.setStudentId(student.getStudentId());
        studentSubjectGroup.setSubjectGroupId(student.getCurrentSubjectGroupId());
        studentSubjectGroupService.deleteStudentId(student.getStudentId());
        studentSubjectGroupService.save(studentSubjectGroup);

        // Save StudentCourses
        List<Long> courses;
        if(Objects.nonNull(studentDetailsBean.getStudentCourses())) {
            courses = studentDetailsBean.getStudentCourses().stream().map(sc -> sc.getCourseId())
                    .collect(Collectors.toList());
        }else {
            courses = studentDetailsBean.getCourses();
        }

        studentCourseService.deleteStudentId(student.getStudentId());
        studentCourseService.save(student.getStudentId(), courses);

        return studentDetailsBean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public Long registerStudent(StudentRegistrationBean registrationDTO, boolean verifyOtp, boolean addDefaultPackage, boolean fromDataLoad) {
        // Validation
        validateRegistrationDTO(verifyOtp, registrationDTO);
        OtpAuth otpAuth = new OtpAuth();
        otpAuth.setEmail(registrationDTO.getEmail());
        otpAuth.setMobile(registrationDTO.getRegisteredMobileNumber());
        otpAuth.setOtp(registrationDTO.getOtp());
        if (verifyOtp) {
            boolean isValid = otpAuthServiceImpl.validateOtp(otpAuth);
            if (!isValid) {
                throw new IllegalArgumentException("The provided OTP is invalid or expired");
            }
        }


        // Check for email uniqueness
        Optional<ApplicationUser> existingUser = applicationUserService.findByEmailId(registrationDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already registered for this user.");
        }
        Long tenantId = Objects.isNull(registrationDTO.getTenantId()) ? TenantResolver.resoveTenant(registrationDTO.getRefererUrl()) : registrationDTO.getTenantId();
        ClassEntity classEntity  = classService.getClassById(registrationDTO.getClassId());
        if(classEntity.getIsExamGroupRequired()){
            if(Objects.isNull(registrationDTO.getSubjectGroupId()) || registrationDTO.getSubjectGroupId()<=0) {
                throw new IllegalArgumentException("Subject Group is required for the selected class.");
            }
        } else {
            List<SubjectGroup> allGroupsByTenant = subjectGroupService.getAllGroupsByTenant(tenantId);
            Optional<SubjectGroup> all = allGroupsByTenant.stream().filter(g -> g.getGroupName().equalsIgnoreCase("all")).findFirst();
            all.ifPresent(f->registrationDTO.setSubjectGroupId(f.getGroupId()));
        }

        // Save User
        UserBean userBean = new UserBean();
        userBean.setIsActive(Boolean.TRUE);
        userBean.setUserName(registrationDTO.getEmail());
        userBean.setTenantId(tenantId);
        userBean.setIsSalt(Boolean.TRUE);
        String salt = registrationDTO.getEmail();
        String plaintext = registrationDTO.getPassword();
        String decryptedPassword = plaintext;
		if (verifyOtp) {
			decryptedPassword = CryptoUtil.decrypt(plaintext);
		}
        String hashsedPwd = PasswordSHA2EncryptionUtil.hash(decryptedPassword, salt);

        userBean.setPassword(hashsedPwd);
        UserBean userBeanDB = userService.save(userBean);

        registrationDTO.setUserId(userBeanDB.getUserId());

        // Save User Tenant
        UserTenant userTenant = new UserTenant();
        userTenant.setUserId(userBeanDB.getUserId());
        userTenant.setTenantId(userBean.getTenantId());
        userTenantService.save(userTenant);
        Long parentId = null;
        if (Objects.nonNull(registrationDTO.getParent())) {
            Parent parent = parentService.saveParent(ParentConverter.toParent(registrationDTO.getParent()));
            parentId = parent.getParentId();
        }
        Long addressId = null;
        if (Objects.nonNull(registrationDTO.getAddress())) {
            registrationDTO.getAddress().setUserId(userBeanDB.getUserId());
            Address save = addressService.save(AddressConverter.toAddress(registrationDTO.getAddress()));
            addressId = save.getAddressId();
        }
        // Save ApplicationUser
        ApplicationUser user = new ApplicationUser();
        user.setUserId(userBeanDB.getUserId());
        user.setUserType(AppConstants.USER_TYPE_STUDENT);
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
        if(Objects.nonNull(addressId)){
            user.setAddressId(addressId);
        }
        applicationUserService.save(user);

        // Save Student
        Student student = new Student();
        student.setUserId(registrationDTO.getUserId());
        student.setCurrentClassId(registrationDTO.getClassId());
        student.setCurrentSubjectGroupId(registrationDTO.getSubjectGroupId());
        student.setTargetFinalExamYear(registrationDTO.getTargetFinalExamYear());
        student.setStudentReferenceId(registrationDTO.getStudentReferenceId());
        if(Objects.nonNull(parentId)){
            student.setParentId(parentId);
        }
        
        if(Objects.isNull(registrationDTO.getMedium())){
        	student.setMedium("English");
        }
        else
        {
        	student.setMedium(registrationDTO.getMedium());
        }
        student.setSchoolName(registrationDTO.getSchoolName());
        student.setSchoolAddress(registrationDTO.getSchoolAddress());
        student.setInstituteName(registrationDTO.getInstituteName());
        student.setCategory(registrationDTO.getCategory());
        student.setParallelReservation(registrationDTO.getParallelReservation());
        studentRepository.save(student);

        // Save StudentClass
        StudentClass studentClass = new StudentClass();
        studentClass.setStudentId(student.getStudentId());
        studentClass.setClassId(registrationDTO.getClassId());
        studentClassService.save(studentClass);

        // Save StudentSubjectGroup
        StudentSubjectGroup studentSubjectGroup = new StudentSubjectGroup();
        studentSubjectGroup.setStudentId(student.getStudentId());
        studentSubjectGroup.setSubjectGroupId(student.getCurrentSubjectGroupId());
        studentSubjectGroupService.save(studentSubjectGroup);

        // Save StudentCourses
        studentCourseService.save(student.getStudentId(), registrationDTO.getCourses());
//		for (Long courseId : registrationDTO.getCourses()) {
//			StudentCourse studentCourse = new StudentCourse();
//			studentCourse.setStudentId(student.getStudentId());
//			studentCourse.setCourseId(courseId);
//			studentCourseService.save(studentCourse);
//		}

        // Default Role as Student
        Optional<Role> roleOpt = roleService.findByName(AppConstants.ROLE_STUDENT);

        Role role = roleOpt.orElseThrow(() -> new IllegalArgumentException("Student Role not found, invalid request."));
        userService.assignRole(userBeanDB.getUserId(), role.getRoleId());
        if(addDefaultPackage){
            // Assign Default Package
            String classWisePackageConigName = ConfigService.DEFAULT_PACKAGE + "_" + classEntity.getClassName();
            Optional<Config> configOpt = configService.findByName(classWisePackageConigName);
            int packageId = 0;
            if(configOpt.isPresent()){
                Config config = configOpt.get();
                packageId = Integer.parseInt(config.getValue());
            }
            if(packageId>0) {
                List<StudentPackageMapping> mappings = new ArrayList<>();
                mappings.add(StudentPackageMapping.builder()
                        .packageId(packageId)
                        .studentId(student.getStudentId())
                        .status(AppConstants.PACKAGE_STATUS_ACTIVE)
                        .createdDate(LocalDateTime.now())
                        .build());
                studentPackageMappingService.saveMultiple(mappings);
            }
        }
        otpAuthServiceImpl.delete(registrationDTO.getEmail(), registrationDTO.getRegisteredMobileNumber());

        sendRegistratinComunication(registrationDTO, otpAuth,fromDataLoad);


        return student.getStudentId();
    }

    private void sendRegistratinComunication(StudentRegistrationBean registrationDTO, OtpAuth otpAuth, boolean fromDataLoad) {
        String appUrl = configService.findByName(ConfigService.SMS_APP_URL)
                .map(Config::getValue)
                .orElse("");
        if ( fromDataLoad) {
            MessageTemplate templateByNameAndType = messageTemplateService.getTemplateByNameAndType("user_registration_welcome_v1", "SMS");
            String smsText = templateByNameAndType.getContent();
            //You have been registered successfully on MahaExam. Please click to %s and use your username %s to reset your password. -EDUVAL
            smsText = String.format(smsText,  appUrl, registrationDTO.getEmail());
            smsService.sendSms(templateByNameAndType.getSmsTemplateId(), smsText, otpAuth.getMobile());
            logger.info("Registration Process smsText==>" + smsText);
            templateByNameAndType = messageTemplateService.getTemplateByNameAndType("user_registration_welcome_v1", "EMAIL");
            smsText = templateByNameAndType.getContent();
            //You have been registered successfully on MahaExam. Please click to %s and use your username %s to reset your password. -EDUVAL
            smsText = String.format(smsText, registrationDTO.getFirstName(), appUrl, registrationDTO.getEmail());
            logger.info("Registration Process emailText==>" + smsText);
            emailService.sendEmail(otpAuth.getEmail(), null, null, templateByNameAndType.getSubject(), smsText, true);
        }else{
            MessageTemplate templateByNameAndType = messageTemplateService.getTemplateByNameAndType("user_registration_welcome", "SMS");
            String smsText = templateByNameAndType.getContent();
            //Welcome to MahaExam. Your registration is successful. Your username is %s To login click %s -EDUVAL
            smsText = String.format(smsText, registrationDTO.getEmail(),appUrl);
            smsService.sendSms(templateByNameAndType.getSmsTemplateId(), smsText, otpAuth.getMobile());
            logger.info("Registration Process smsText==>" + smsText);
            templateByNameAndType = messageTemplateService.getTemplateByNameAndType("user_registration_welcome", "EMAIL");
            smsText = templateByNameAndType.getContent();
            //Dear %s,<br/>Welcome to MahaExam! We're excited to have you join our platform.<br/>You are successfully registered to MahaExam with user name - "%s"<br/>Welcome aboard!<br/>Best regards,<br/>MahaExam Team
            smsText = String.format(smsText, registrationDTO.getFirstName(), registrationDTO.getEmail());
            logger.info("Registration Process emailText==>" + smsText);
            emailService.sendEmail(otpAuth.getEmail(), null, null, templateByNameAndType.getSubject(), smsText, true);
        }
    }

    @Override
    public Integer getStudentPackageCount(Long studentId) {
        return studentRepository.getStudentPackageCount(studentId);
    }

    @Override
    public List<PackageModel> getStudentPackages(Long studentId) {
        return studentRepository.getStudentPackages(studentId);
    }
    private void validateRegistrationDTO(boolean isPasswrodCheckRequired, StudentRegistrationBean dto) {
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
        if (isPasswrodCheckRequired) {
            if (dto.getOtp() == null || dto.getOtp().trim().isEmpty()) {
                throw new IllegalArgumentException("Otp is required");
            }
            if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password is required");
            }

            if (dto.getReTypePassword() == null || dto.getReTypePassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Retype Password is required");
            }
            if (!dto.getPassword().equals(dto.getReTypePassword())) {
                throw new IllegalArgumentException("Password and Retype Password is not Matching.");
            }
            if (dto.getCourses() == null || dto.getCourses().isEmpty()) {
                throw new IllegalArgumentException("At least one Course is required");
            }
        }

        if (dto.getClassId() == null) {
            throw new IllegalArgumentException("Class is required");
        }
        if (dto.getSubjectGroupId() == null || dto.getSubjectGroupId() < 0) {
            throw new IllegalArgumentException("Exam Group is required");
        }

        if (dto.getTargetFinalExamYear() == null) {
            throw new IllegalArgumentException("Target year of Final Exam is required");
        }

        // Valid value validation
        List<String> validGenders = Arrays.asList("MALE", "FEMALE", "OTHER");
        if (dto.getGender() != null && !validGenders.contains(dto.getGender().toUpperCase())) {
            throw new IllegalArgumentException("Gender must be one of: " + String.join(", ", validGenders));
        }

        // Additional validation (e.g., Aadhar number format, email format, mobile
        // number format)
//        if (Objects.nonNull(dto.getAadharNumber()) && !dto.getAadharNumber().matches("\\d{12}")) {
//            throw new IllegalArgumentException("Aadhar Number must be a 12-digit number");
//        }
        if (!dto.getRegisteredMobileNumber().matches("\\d{10}")) {
            throw new IllegalArgumentException("Registered Mobile Number must be a 10-digit number");
        }
        if (dto.getWhatsappNumber() != null && !dto.getWhatsappNumber().matches("\\d{10}")) {
            throw new IllegalArgumentException("WhatsApp Number must be a 10-digit number if provided");
        }
        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid Email format");
        }
        if (dto.getTargetFinalExamYear() < LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Target year of Final Exam cannot be in past");
        }

        // Validate Target Final Exam Year in the service layer
        int currentYear = LocalDateTime.now().getYear();
        if (dto.getTargetFinalExamYear() < currentYear) {
            throw new IllegalArgumentException("arget year of Final Exam cannot be in past");
        }
    }

    @Override
    public List<Student> getAllStudentsByRefferalUsreId(Long studentReferralId) {
        return studentRepository.getAllStudentsByRefferalUsreId(studentReferralId);
    }

    @Override
    public Integer getStudentCountRefferedByGivenUserId(Long studentRefferalId) {
        return studentRepository.getStudentCountRefferedByGivenUserId(studentRefferalId);
    }

    @Override
    public Integer getAllStudentsCount(UserBean userbean) {
        return studentRepository.getAllStudentsCount(userbean);
    }

    @Override
    public Integer getAllStudentsCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        return studentRepository.getAllStudentsCount(tenantId, academicYearId, boardId, days);
    }

    @Override
    public boolean hasPackage(Long userId) {
        return studentRepository.hasPackage(userId);
    }

    @Override
    public PaginatedResponse<Student> getStudentsWithoutPackage(Pageable pageable) {
        return studentRepository.getStudentsWithoutPackage(pageable);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void addNewStudent(StudentDetailsBean studentDetailsBean) {
        // Validation
//        validateRegistrationDTO(verifyOtp, registrationDTO);
//        OtpAuth otpAuth = new OtpAuth();
//        otpAuth.setEmail(registrationDTO.getEmail());
//        otpAuth.setMobile(registrationDTO.getRegisteredMobileNumber());
//        otpAuth.setOtp(registrationDTO.getOtp());
//        if (verifyOtp) {
//            boolean isValid = otpAuthServiceImpl.validateOtp(otpAuth);
//            if (!isValid) {
//                throw new IllegalArgumentException("The provided OTP is invalid or expired");
//            }
//        }


        // Check for email uniqueness
        Optional<ApplicationUser> existingUser = applicationUserService.findByEmailId(studentDetailsBean.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already registered for this user.");
        }

        // Save User
        UserBean userBean = new UserBean();
        userBean.setIsActive(Boolean.TRUE);
        userBean.setUserName(studentDetailsBean.getEmail());
        userBean.setTenantId(TenantResolver.resoveTenant(studentDetailsBean.getRefererUrl()));
        userBean.setIsSalt(Boolean.TRUE);
        String salt = studentDetailsBean.getEmail();
        String plaintext = studentDetailsBean.getPassword();

        String hashsedPwd = PasswordSHA2EncryptionUtil.hash(plaintext, salt);

        userBean.setPassword(hashsedPwd);
        UserBean userBeanDB = userService.save(userBean);

        studentDetailsBean.setUserId(userBeanDB.getUserId());

        // Save User Tenant
        UserTenant userTenant = new UserTenant();
        userTenant.setUserId(userBeanDB.getUserId());
        userTenant.setTenantId(userBean.getTenantId());
        userTenantService.save(userTenant);
        Long parentId = null;
        if (Objects.nonNull(studentDetailsBean.getParent())) {
            Parent parent = parentService.saveParent(ParentConverter.toParent(studentDetailsBean.getParent()));
            parentId = parent.getParentId();
        }
        Long addressId = null;
        if (Objects.nonNull(studentDetailsBean.getAddress())) {
        	studentDetailsBean.getAddress().setUserId(userBeanDB.getUserId());
            Address save = addressService.save(AddressConverter.toAddress(studentDetailsBean.getAddress()));
            addressId = save.getAddressId();
        }
        // Save ApplicationUser
        ApplicationUser user = new ApplicationUser();
        user.setUserId(userBeanDB.getUserId());
        user.setUserType(AppConstants.USER_TYPE_STUDENT);
        user.setFirstName(studentDetailsBean.getFirstName());
        user.setLastName(studentDetailsBean.getLastName());
        user.setMiddleName(studentDetailsBean.getMiddleName());
        user.setGender(studentDetailsBean.getGender());
        user.setDateOfBirth(studentDetailsBean.getDateOfBirth());
        user.setAadharNumber(studentDetailsBean.getAadharNumber());
        user.setRegisteredMobileNumber(studentDetailsBean.getRegisteredMobileNumber());
        user.setWhatsappNumber(studentDetailsBean.getWhatsappNumber());
        user.setEmail(studentDetailsBean.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        if(Objects.nonNull(addressId)){
            user.setAddressId(addressId);
        }
        applicationUserService.save(user);

        // Save Student
        Student student = new Student();
        student.setUserId(studentDetailsBean.getUserId());
        student.setCurrentClassId(studentDetailsBean.getClassId());
        student.setCurrentSubjectGroupId(studentDetailsBean.getSubjectGroupId());
        student.setTargetFinalExamYear(studentDetailsBean.getTargetFinalExamYear());
        student.setStudentReferenceId(studentDetailsBean.getStudentReferenceId());
        if(Objects.nonNull(parentId)){
            student.setParentId(parentId);
        }
        student.setSchoolName(studentDetailsBean.getSchoolName());
        student.setSchoolAddress(studentDetailsBean.getSchoolAddress());
        studentRepository.save(student);

        // Save StudentClass
        StudentClass studentClass = new StudentClass();
        studentClass.setStudentId(student.getStudentId());
        studentClass.setClassId(studentDetailsBean.getClassId());
        studentClassService.save(studentClass);

        // Save StudentSubjectGroup
        StudentSubjectGroup studentSubjectGroup = new StudentSubjectGroup();
        studentSubjectGroup.setStudentId(student.getStudentId());
        studentSubjectGroup.setSubjectGroupId(student.getCurrentSubjectGroupId());
        studentSubjectGroupService.save(studentSubjectGroup);

        // Save StudentCourses
        studentCourseService.save(student.getStudentId(), studentDetailsBean.getCourses());
//		for (Long courseId : registrationDTO.getCourses()) {
//			StudentCourse studentCourse = new StudentCourse();
//			studentCourse.setStudentId(student.getStudentId());
//			studentCourse.setCourseId(courseId);
//			studentCourseService.save(studentCourse);
//		}

        // Default Role as Student
        Optional<Role> roleOpt = roleService.findByName(AppConstants.ROLE_STUDENT);

        Role role = roleOpt.orElseThrow(() -> new IllegalArgumentException("Student Role not found, invalid request."));
        userService.assignRole(userBeanDB.getUserId(), role.getRoleId());
        otpAuthServiceImpl.delete(studentDetailsBean.getEmail(), studentDetailsBean.getRegisteredMobileNumber());

    }

    @Override
    public List<StudentDetailsBean> getAllStudentsByChannelPartnerId(Long channelPartnerId) {
        return studentRepository.getAllStudentsByChannelPartnerId(channelPartnerId);
    }
    
    @Override
    public PaginatedResponse<StudentDetailsBean> getActiveStudentsWithPaidPackage(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        return studentRepository.getActiveStudentsWithPaidPackage(tenantId, academicYearId, boardId, days, pageable);
    }
    
    @Override
    public Integer getActiveStudentsWithPaidPackageCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        return studentRepository.getActiveStudentsWithPaidPackageCount(tenantId, academicYearId, boardId, days);
    }

    @Override
    public PaginatedResponse<QuestionPaperResponseDTO> getTotalExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        return studentRepository.getTotalExamDetails(tenantId, academicYearId, boardId, days, pageable);
    }

    @Override
    public Integer getTotalExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        return studentRepository.getTotalExamCount(tenantId, academicYearId, boardId, days);
    }

    @Override
    public Integer getUpcomingExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        return studentRepository.getUpcomingExamCount(tenantId, academicYearId, boardId, days);
    }

    @Override
    public PaginatedResponse<QuestionPaperResponseDTO> getUpcomingExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        return studentRepository.getUpcomingExamDetails(tenantId, academicYearId, boardId, days, pageable);
    }

    @Override
    public PaginatedResponse<CompletedExamDetailsBean> getCompletedExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, Pageable pageable) {
        return studentRepository.getCompletedExamDetails(tenantId, academicYearId, boardId, days, pageable);
    }

    @Override
    public Integer getCompletedExamCount(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        return studentRepository.getCompletedExamCount(tenantId, academicYearId, boardId, days);
    }

    @Override
    public List<EnrollmentByExamBean> getStudentEnrollmentByExam(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        return studentRepository.getStudentEnrollmentByExam(tenantId, academicYearId, boardId, days);
    }

    @Override
    public PaginatedResponse<StudentDetailsBean> getStudentEnrollmentByExamDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, String examName, Pageable pageable) {
        return studentRepository.getStudentEnrollmentByExamDetails(tenantId, academicYearId, boardId, days, examName, pageable);
    }

    @Override
    public List<StudentJourneyBean> getStudentJourneyStats(Long tenantId, Integer academicYearId, Integer boardId, Integer days) {
        return studentRepository.getStudentJourneyStats(tenantId, academicYearId, boardId, days);
    }

    @Override
    public PaginatedResponse<CompletedExamDetailsBean> getStudentJourneyDetails(Long tenantId, Integer academicYearId, Integer boardId, Integer days, String packageType, Pageable pageable) {
        return studentRepository.getStudentJourneyDetails(tenantId, academicYearId, boardId, days, packageType, pageable);
    }
}
