package com.mahaexam.tenant.management.service.bulkservice;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.config.S3Service;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.exception.ServiceException;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.packagemanagment.bean.PackageBean;
import com.mahaexam.packagemanagment.bean.PaymentResponse;
import com.mahaexam.packagemanagment.bean.PaymentTransactionBean;
import com.mahaexam.packagemanagment.model.PaymentTransaction;
import com.mahaexam.packagemanagment.model.StudentPackageMapping;
import com.mahaexam.packagemanagment.repository.PaymentTransactionRepository;
import com.mahaexam.packagemanagment.repository.StudentPackageMappingRepository;
import com.mahaexam.packagemanagment.service.PackageService;
import com.mahaexam.packagemanagment.service.payment.PaymentLinkService;
import com.mahaexam.payment.bean.PaymentRequest;
import com.mahaexam.tenant.management.bean.BatchUploadResponse;
import com.mahaexam.tenant.management.bean.OfflinePayment;
import com.mahaexam.tenant.management.bean.StudentDataLoadBean;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.OfflinePaymentModel;
import com.mahaexam.tenant.management.model.TempStudent;
import com.mahaexam.tenant.management.model.UploadBatch;
import com.mahaexam.tenant.management.repository.UploadBatchRepository;
import com.mahaexam.tenant.management.service.ApplicationUserService;
import com.mahaexam.tenant.management.service.OfflinePaymentService;
import com.mahaexam.tenant.management.service.TempStudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenericUploadService {
    private static final Logger logger = LogManager.getLogger(GenericUploadService.class);
    private final UploadProcessorFactory processorFactory;
    private final UploadBatchRepository uploadBatchRepository;
    private final S3Service s3Service;
    private final ConfigService configService;
    private final TempStudentService tempStudentService;
    private final PaymentLinkService payUMoneyPaymentLinkService;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PackageService packageService;
    private final ApplicationUserService applicationUserService;
    private final StudentPackageMappingRepository studentPackageMappingRepository;
    private final OfflinePaymentService offlinePaymentService;

    public GenericUploadService(UploadProcessorFactory processorFactory,
                                UploadBatchRepository uploadBatchRepository,
                                S3Service s3Service, ConfigService configService,
                                TempStudentService tempStudentService,
                                PaymentLinkService payUMoneyPaymentLinkService,
                                PaymentTransactionRepository paymentTransactionRepository,
                                PackageService packageService, ApplicationUserService applicationUserService,
                                StudentPackageMappingRepository studentPackageMappingRepository,
                                OfflinePaymentService offlinePaymentService) {
        this.processorFactory = processorFactory;
        this.uploadBatchRepository = uploadBatchRepository;
        this.s3Service = s3Service;
        this.configService = configService;
        this.tempStudentService = tempStudentService;
        this.payUMoneyPaymentLinkService = payUMoneyPaymentLinkService;
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.packageService = packageService;
        this.applicationUserService = applicationUserService;
        this.studentPackageMappingRepository = studentPackageMappingRepository;
        this.offlinePaymentService = offlinePaymentService;
    }

    public Long createUploadBatch(MultipartFile file, String entityType, UserBean user) throws IOException {
        String originalFileName = "original_" + entityType + "_" + System.currentTimeMillis() + ".xlsx";

        Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
        String s3BucketName = config.getValue();

        configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER);
        config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "Package Folder NoT Found : " + ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER));
        String bulkUploadFolderName = config.getValue();
        s3Service.uploadFile (s3BucketName,  bulkUploadFolderName,  originalFileName,  file);

        File destFile = new File(originalFileName);
        file.transferTo(destFile);

        UploadBatch batch = new UploadBatch();
        batch.setEntityType(entityType);
        batch.setStatus("PENDING");
        batch.setOriginalFilePath(bulkUploadFolderName+"/"+originalFileName);
        batch.setCreatedBy(user.getUserId());
        batch.setTenantId(user.getTenantId());
        uploadBatchRepository.save(batch);

        return batch.getBatchId();
    }

    public void processUploadBatch(Long batchId) throws Exception {
        UploadBatch batch = uploadBatchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found"));

        UploadProcessor<?> processor = processorFactory.getProcessor(batch.getEntityType());
        if (processor == null) {
            throw new IllegalArgumentException("No processor for type: " + batch.getEntityType());
        }

        List<?> entities = processor.readExcelToEntities(batch.getOriginalFilePath());
        ValidationResult<?> result = processor.validateEntities(entities);

        if (!result.getValidEntities().isEmpty()) {
            processor.insertValidEntities(result.getValidEntities(), false);
        }
        if (!result.getInvalidEntities().isEmpty()) {
            String errorFilePath = processor.generateErrorFile(result.getInvalidEntities(), batchId);
            batch.setErrorFilePath(errorFilePath);
            batch.setTotalCount(result.getValidEntities().size() + result.getInvalidEntities().size());
            batch.setSuccessCount(result.getValidEntities().size());
            batch.setStatus("PARTIAL_FAILURE");
        } else {
            batch.setStatus("SUCCESS");
            batch.setTotalCount(result.getValidEntities().size() + result.getInvalidEntities().size());
            batch.setSuccessCount(batch.getTotalCount());
        }

        uploadBatchRepository.save(batch);
    }

    public List<UploadBatch> getAllUploadBatches(UserBean userBean) {
        if(Objects.isNull(userBean.getApplicationUser())) {
            ApplicationUser applicationUser = applicationUserService.findByUserId(userBean.getUserId()).orElseThrow(() -> new IllegalArgumentException(
                    "User not found: " + userBean.getUserId()));
            userBean.setApplicationUser(applicationUser);
        }
        List<UploadBatch> allOrderedByUploadTimeDesc = uploadBatchRepository.findAllOrderedByUploadTimeDesc(userBean);
        List<Long> batchIds = allOrderedByUploadTimeDesc.stream().map(UploadBatch::getBatchId).toList();
        // Fetch all valid students for these batches and group them by batchId
        List<TempStudent> tempStudents = tempStudentService.findStudentsByBatchIds(batchIds);
        java.util.Map<Long, List<TempStudent>> studentsByBatch = tempStudents.stream()
                .collect(java.util.stream.Collectors.groupingBy(TempStudent::getBatchId));

        // Now set displayStatus based on existing logic; maps are available if you want to use counts
        allOrderedByUploadTimeDesc.forEach(up -> {
            if (up.getStatus().equalsIgnoreCase("PENDING")) {
               // generateAndSetStatusForPendingStatus(up);


                List<TempStudent> tempStudents1 = studentsByBatch.get(up.getBatchId());
                if(Objects.nonNull(tempStudents1)) {
                    // Partition students into valid and invalid with single iteration
                    Map<Boolean, List<TempStudent>> partitionedStudents = tempStudents1.stream()
                            .collect(Collectors.partitioningBy(this::isValidStudent));

                    List<TempStudent> validEntities = partitionedStudents.get(true);
                    List<TempStudent> invalidEntities = partitionedStudents.get(false);
                    if (!invalidEntities.isEmpty() && validEntities.isEmpty()) {
                        up.setDisplayStatus("Upload Failed - All Records Invalid");
                    } else if (!invalidEntities.isEmpty() && !validEntities.isEmpty()) {
                        up.setDisplayStatus("Partial Upload Pending");
                    } else if (invalidEntities.isEmpty() && !validEntities.isEmpty()) {
                        up.setDisplayStatus("Upload Pending");
                    } else {
                        up.setDisplayStatus("Upload Pending - No Records Found");
                    }
                }
            } else if (up.getStatus().equalsIgnoreCase("SUCCESS-PENDING_PAYMENT")) {
                if (up.getIsOffline()) {
                    up.setDisplayStatus("Upload Successful - Pending Payment Confirmation");
                } else {
                    up.setDisplayStatus("Upload Successful - Pending Payment");
                }
            } else if (up.getStatus().equalsIgnoreCase("PARTIAL_FAILURE-PENDING_PAYMENT")) {
                if (up.getIsOffline()) {
                    up.setDisplayStatus("Partial Upload Successful - Pending Payment Confirmation");
                } else {
                    up.setDisplayStatus("Partial Upload Successful - Pending Payment");
                }
            } else {
                up.setDisplayStatus("Unknown Status: Contact Support");
            }
        });

        return allOrderedByUploadTimeDesc;
    }

    private static void generateAndSetStatusForPendingStatus(UploadBatch up) {
        int totalCount = up.getTotalCount()==null?0: up.getTotalCount();
        int successCount = up.getSuccessCount()==null?0: up.getSuccessCount();
        int invalidCount =  totalCount - successCount;

        if(invalidCount>0 && successCount==0) {
            up.setDisplayStatus("Upload Failed - All Records Invalid");
        } else if(invalidCount>0  && successCount>0) {
            up.setDisplayStatus("Partial Upload Pending");
        } else if(invalidCount==0 && successCount>0) {
            up.setDisplayStatus("Upload Pending");
        } else {
            up.setDisplayStatus("Upload Pending - No Records Found");
        }
        logger.info("totalCount: {}, successCount: {}, invalidCount: {} for batchId: {} and status: {}",
                totalCount, successCount, invalidCount, up.getBatchId(),up.getDisplayStatus());
    }

    public InputStreamResource getUploadBatcFile(Long batchId, String fileType) {
        UploadBatch uploadBatch= uploadBatchRepository.findById(batchId).orElseThrow(() -> new IllegalArgumentException("Batch not found"));
        String filePath = null;
        if("Original".equals(fileType)){
            filePath =  uploadBatch.getOriginalFilePath();
        } else if ("Error".equals(fileType)) {
            filePath =  uploadBatch.getErrorFilePath();
        }else{
            throw new IllegalArgumentException("Invalid File Type provided :"+fileType);
        }
        Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
        String s3BucketName = config.getValue();

        byte[] bytes = s3Service.getFile(s3BucketName, filePath);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new InputStreamResource(byteArrayInputStream);

    }

    /**
     * Helper method to check if a student is valid (no error message or empty error message)
     */
    private boolean isValidStudent(TempStudent student) {
        String errorMessage = student.getErrorMessage();
        return errorMessage == null || errorMessage.trim().isEmpty();
    }
// New Changes
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public BatchUploadResponse validateUploadBatch(MultipartFile file, String entityType, Integer packageId, String targetYear, Long referralId, String medium, UserBean user) throws IOException {
        // Read file into byte array immediately to avoid temporary file deletion issues
        byte[] fileBytes = file.getBytes();
        String originalFileName = "original_" + entityType + "_" + System.currentTimeMillis() + ".xlsx";

        Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
        String s3BucketName = config.getValue();

        configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER);
        config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "Package Folder NoT Found : " + ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER));
        String bulkUploadFolderName = config.getValue();

        // Upload file to S3 using byte array
        s3Service.uploadFile(s3BucketName, bulkUploadFolderName, originalFileName, fileBytes,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        UploadBatch batch = new UploadBatch();
        batch.setEntityType(entityType);
        batch.setStatus("PENDING");
        batch.setOriginalFilePath(bulkUploadFolderName + "/" + originalFileName);
        batch.setCreatedBy(user.getUserId());
        batch.setTenantId(user.getTenantId());
        uploadBatchRepository.save(batch);

        Long referralIdLocal = user.getUserId();
        if (Objects.nonNull(referralId)) {
            referralIdLocal = referralId;
        }

        // Pass the byte array for further processing
        return processUploadBatch(batch.getBatchId(), referralIdLocal, packageId, targetYear, medium, fileBytes);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public BatchUploadResponse processUploadBatch(Long batchId, Long referralId, Integer packageId, String targetYear, String medium, byte[] fileBytes) {
        UploadBatch batch = uploadBatchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found"));

        UploadProcessor<?> processor = processorFactory.getProcessor(batch.getEntityType());
        if (processor == null) {
            throw new IllegalArgumentException("No processor for type: " + batch.getEntityType());
        }
        try {
            // Read from S3 using file path
            List<?> entities = processor.readExcelToEntitiesV1(fileBytes);
            ValidationResult<?> result = processor.validateEntities(entities);

            return storeandGetBatchUploadResponse( referralId, packageId, targetYear, medium, batch, result, entities);

        } catch (IOException e) {
            logger.error("Error processing upload batch", e);
            throw new ServiceException("Error processing upload batch.");
        }
    }

    private BatchUploadResponse storeandGetBatchUploadResponse(Long referralId, Integer packageId, String targetYear, String medium,
                                                               UploadBatch batch, ValidationResult<?> result, List<?> entities) throws IOException {
        // Safe casting - only cast if we're dealing with Student entities
        List<StudentDataLoadBean> validEntities = null;
        List<StudentDataLoadBean> invalidEntities = null;

        if ("Student".equalsIgnoreCase(batch.getEntityType())) {
            validEntities = (List<StudentDataLoadBean>) result.getValidEntities();
            invalidEntities = (List<StudentDataLoadBean>) result.getInvalidEntities();
        }
        List<TempStudent> tempStudents = new ArrayList<>();

        entities.stream().forEach(ent -> {
            StudentDataLoadBean stu = (StudentDataLoadBean) ent;

            // Convert List<Long> courseIds to comma-separated string
            String courseIdsStr = null;
            if (stu.getCourseIds() != null && !stu.getCourseIds().isEmpty()) {
                courseIdsStr = stu.getCourseIds().stream()
                        .map(String::valueOf)
                        .reduce((a, b) -> a + "," + b)
                        .orElse(null);
            }

            TempStudent tempStudent = TempStudent.builder()
                    .adharNo(stu.getAdharNo())
                    .email(stu.getEmail())
                    .classId(stu.getClassId())
                    .className(stu.getClassName())
                    .batchId(batch.getBatchId())
                    .courses(stu.getCourses())
                    .courseIds(courseIdsStr)
                    .errorMessage(stu.getErrorMessage())
                    .lastName(stu.getLastName())
                    .middleName(stu.getMiddleName())
                    .firstName(stu.getFirstName())
                    .examGroup(stu.getExamGroup())
                    .subjectGroupId(stu.getSubjectGroupId())
                    .mobileNumber(stu.getMobileNumber())
                    .targetFinalExamYear(Integer.valueOf(targetYear))
                    .packageId(packageId)
                    .referenceId(referralId)
                    .medium(medium)
                    .build();

            tempStudents.add(tempStudent);
        });
        tempStudentService.batchInsert(tempStudents);
        List<TempStudent> invalidTempStudentList = tempStudents.stream().filter(ts -> Objects.nonNull(ts.getErrorMessage()) && !ts.getErrorMessage().isEmpty()).collect(Collectors.toList());
        Optional<PackageBean> packageBeanOptional = packageService.getPackageById(packageId, Boolean.FALSE);
        PackageBean packageBean = packageBeanOptional.orElseThrow(() -> new IllegalArgumentException("Package not found"));
        BigDecimal totalPackageAmount = new BigDecimal(0);
        if(Objects.nonNull(validEntities) && !validEntities.isEmpty()) {
            totalPackageAmount = packageBean.getAmount()
                    .multiply(BigDecimal.valueOf(validEntities.size()));
        }
        String errorFilePath = generateErrorFileFromTemplate(batch.getBatchId(), invalidTempStudentList);
        // Update batch with error file path
        batch.setErrorFilePath(errorFilePath);
        batch.setTotalCount(result.getValidEntities().size() + result.getInvalidEntities().size());
        batch.setSuccessCount(result.getValidEntities().size());
        uploadBatchRepository.save(batch);
        return BatchUploadResponse.builder()
                .batchId(batch.getBatchId())
                .totalPackageAmount(totalPackageAmount)
                .validEntities(validEntities)
                .invalidEntities(invalidEntities)
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void processUploadBatchFromTempTable(Long batchId, boolean withPayment, boolean withRegistration,boolean keepStatusPendingPayment) throws Exception {
        UploadBatch batch = uploadBatchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found"));
        List<TempStudent> tempStudents = tempStudentService.findByBatchId(batchId);
        if(tempStudents.isEmpty()){
            throw  new IllegalArgumentException("Batch not found");
        }
        UploadProcessor<?> processor = processorFactory.getProcessor(batch.getEntityType());
        if (processor == null) {
            throw new IllegalArgumentException("No processor for type: " + batch.getEntityType());
        }

        // Partition students into valid and invalid with single iteration
        Map<Boolean, List<TempStudent>> partitionedStudents = tempStudents.stream()
                .collect(Collectors.partitioningBy(this::isValidStudent));

        List<TempStudent> validEntities = partitionedStudents.get(true);
        List<TempStudent> invalidEntities = partitionedStudents.get(false);


        List<StudentDataLoadBean> validStudentEntities = getStudentDataLoadBeans(validEntities);

        List<StudentDataLoadBean> inValidStudentEntities = getStudentDataLoadBeans(invalidEntities);
        if (!validEntities.isEmpty()) {
            Integer packageId = validEntities.get(0).getPackageId();
            logger.info("Processing {} valid entities for batchId: {} with packageId: {} and has student Id {}",
                    validEntities.size(), batchId, packageId,!Objects.isNull(validEntities.get(0).getStudentId()));
            Optional<PackageBean> packageBeanOptional = packageService.getPackageById(packageId, Boolean.FALSE);
            PackageBean packageBean = packageBeanOptional.orElseThrow(() -> new IllegalArgumentException("Package not found"));
            BigDecimal packagePrice = packageBean.getAmount();
            List<StudentDataLoadBean> validStudentEntitiesToProcess = validStudentEntities.stream().filter(s->(Objects.isNull(s.getStudentId()) || s.getStudentId()<=0))
                    .peek(ve -> {
                        ve.setPacakagePrice(packagePrice);
                        ve.setPackageId(packageId);
                        ve.setTenantId(batch.getTenantId());
                    })
                    .collect(Collectors.toList());
                    //This will be Empty if it is coming from webhook then need to make entry in mapping table
                    if(!validStudentEntitiesToProcess.isEmpty()) {
                        processor.insertValidEntities(validStudentEntitiesToProcess, withPayment);
                        tempStudentService.batchUpdateStudentId(validStudentEntitiesToProcess);
                    }
                    if(withPayment){
                        List<StudentPackageMapping> mappings = new ArrayList<>();
                        validEntities.stream().filter(s->(Objects.nonNull(s.getStudentId()) && s.getStudentId()>0)).forEach(sp -> {
                            mappings.add(StudentPackageMapping.builder()
                                    .packageId(sp.getPackageId())
                                    .studentId(sp.getStudentId())
                                    .status(AppConstants.PACKAGE_STATUS_ACTIVE)
                                    .createdDate(LocalDateTime.now())
                                    .build());
                        });
                        studentPackageMappingRepository.saveMultiple(mappings);
                    }
        }
        if (!invalidEntities.isEmpty()) {
            String errorFilePath = processor.generateErrorFile(inValidStudentEntities, batchId);
            batch.setErrorFilePath(errorFilePath);
            batch.setTotalCount(validStudentEntities.size() + inValidStudentEntities.size());
            batch.setSuccessCount(validStudentEntities.size());
            if(keepStatusPendingPayment) {
                batch.setStatus("PARTIAL_FAILURE-PENDING_PAYMENT");
            }else if(withPayment) {
                batch.setStatus("PARTIAL_FAILURE");
            }else {
                batch.setStatus("PARTIAL_FAILURE-PENDING_PAYMENT");
            }
        } else {
            if(keepStatusPendingPayment) {
                batch.setStatus("SUCCESS-PENDING_PAYMENT");
            }else if(withPayment) {
                batch.setStatus("SUCCESS");
            }else {
                batch.setStatus("SUCCESS-PENDING_PAYMENT");
            }

        }
        batch.setTotalCount(validStudentEntities.size() + inValidStudentEntities.size());
        batch.setSuccessCount(validStudentEntities.size());
        logger.info("batch processing completed for batchId: {}, validCount: {}, invalidCount: {}, status: {}",
                batchId, validStudentEntities.size(), inValidStudentEntities.size(), batch.getStatus());
        uploadBatchRepository.save(batch);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void processUploadBatchFromTempTableForOffLinePayment(OfflinePayment offlinePayment, boolean withPayment, boolean withRegistration,boolean keepStatusPendingPayment) throws Exception {
        Long batchId = offlinePayment.getBatchId();
        processUploadBatchFromTempTable(batchId, withPayment, withRegistration, keepStatusPendingPayment);
        OfflinePaymentModel offlinePaymentModel = new OfflinePaymentModel();
        BeanUtils.copyProperties(offlinePayment,offlinePaymentModel);
        offlinePaymentService.save(offlinePaymentModel);

    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void approvePaymentByBatchId(Long batchId) {
        UploadBatch batch = uploadBatchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found"));
        List<TempStudent> tempStudents = tempStudentService.findByBatchId(batchId);
        if(tempStudents.isEmpty()){
            throw  new IllegalArgumentException("Batch not found");
        }
        UploadProcessor<?> processor = processorFactory.getProcessor(batch.getEntityType());
        if (processor == null) {
            throw new IllegalArgumentException("No processor for type: " + batch.getEntityType());
        }

        // Partition students into valid and invalid with single iteration
        Map<Boolean, List<TempStudent>> partitionedStudents = tempStudents.stream()
                .collect(Collectors.partitioningBy(this::isValidStudent));

        List<TempStudent> validEntities = partitionedStudents.get(true);
        offlinePaymentService. approvePaymentByBatchId(batchId);
        List<StudentPackageMapping> mappings = new ArrayList<>();
        validEntities.forEach(sp -> {
            mappings.add(StudentPackageMapping.builder()
                    .packageId(sp.getPackageId())
                    .studentId(sp.getStudentId())
                    .status(AppConstants.PACKAGE_STATUS_ACTIVE)
                    .createdDate(LocalDateTime.now())
                    .build());
        });
        studentPackageMappingRepository.saveMultiple(mappings);
        batch.setStatus("SUCCESS");
        logger.info("batch processing Payment Confirmation completed for batchId: {}",
                batchId);
        uploadBatchRepository.save(batch);
    }
    @NotNull
    private static List<StudentDataLoadBean> getStudentDataLoadBeans(List<TempStudent> entities) {
        return entities.stream()
                .map(stu -> StudentDataLoadBean.builder()
                        .adharNo(stu.getAdharNo())
                        .email(stu.getEmail())
                        .classId(stu.getClassId())
                        .className(stu.getClassName())
                        .courses(stu.getCourses())
                        .courseIds(stu.getCourseIds() != null && !stu.getCourseIds().isEmpty() ?
                                Arrays.stream(stu.getCourseIds().split(","))
                                        .map(Long::valueOf)
                                        .collect(Collectors.toList()) : null)
                        .lastName(stu.getLastName())
                        .middleName(stu.getMiddleName())
                        .firstName(stu.getFirstName())
                        .examGroup(stu.getExamGroup())
                        .subjectGroupId(stu.getSubjectGroupId())
                        .mobileNumber(stu.getMobileNumber())
                        .targetFinalExamYear(stu.getTargetFinalExamYear())
                        .packageId(stu.getPackageId())
                        .referenceId(stu.getReferenceId())
                        .medium(stu.getMedium())
                        .studentId(stu.getStudentId())
                        .batchId(stu.getBatchId())
                        .errorMessage(stu.getErrorMessage())
                        .build())
                .collect(Collectors.toList());
    }



    /**
     * Create payment link for batch where channel partner pays for all students
     * @param batchId The batch ID containing valid students
     * @param totalAmount The total amount to be paid for all students
     * @param user The channel partner (current user) who will make the payment
     * @return PaymentTransactionBean containing payment link and transaction details
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PaymentTransactionBean createBatchPaymentLink(Long batchId, BigDecimal totalAmount, UserBean user) {
        // Validate batch exists and belongs to user
        UploadBatch batch = uploadBatchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found"));

//        if (!batch.getCreatedBy().equals(user.getUserId()) && !user.getApplicationUser().isAdmin()) {
//            throw new IllegalArgumentException("Unauthorised access to batch");
//        }

        // Get valid students count from temp table for this batch
        List<TempStudent> validStudents = tempStudentService.findValidStudentsByBatchId(batchId);
        if (validStudents.isEmpty()) {
            throw new IllegalArgumentException("No valid students found in batch for payment");
        }

        int studentCount = validStudents.size();

        // Validate the total amount
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than zero");
        }

        // Create description for payment
        String description = String.format("Batch Payment for %d students (Batch ID: %d) by User", studentCount, batchId);
        Optional<ApplicationUser> applicationUserOptional = applicationUserService.findByUserId(user.getUserId());
        ApplicationUser applicationUser = applicationUserOptional.orElseThrow(() -> new IllegalArgumentException("ApplicationUser not found"));
        // Channel partner details (user is the payer)
        String customerName = applicationUser.getFirstName() + " " + applicationUser.getLastName();
        String customerEmail = applicationUser.getEmail();
        String customerMobile = applicationUser.getRegisteredMobileNumber();

        // Create payment request
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(totalAmount)
                .customerName(customerName)
                .customerEmail(customerEmail)
                .customerMobile(customerMobile)
                .description(description)
                .batchId(batchId+"")
                .build();

        // Create payment link via PayU
        PaymentResponse paymentResponse  = payUMoneyPaymentLinkService.handlPaymentLinkCreationRequest (paymentRequest);
        String payuTransactionId = paymentResponse.getInvoiceNumber();
        // Save payment transaction
        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .payuTransactionId(payuTransactionId)
                .paymentLink(paymentResponse.getPaymentLink())
                .paymentStatus(AppConstants.PAYU_STATUS_INITIATED)
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .selectionSummaryId(null)
                .paymentLinkId(paymentResponse.getPaymentLinkId())
                .remark("")
                .build();

        PaymentTransaction savedTransaction = paymentTransactionRepository.save(paymentTransaction);

        // Update batch with payment transaction reference
        batch.setPaymentTransactionId(savedTransaction.getTransactionId());
       // batch.setStatus("INITIATED");
        uploadBatchRepository.save(batch);

        logger.info("Batch payment link created for batchId: {}, transactionId: {}, amount: {}",
                   batchId, payuTransactionId, totalAmount);

        // Return payment transaction bean
        return PaymentTransactionBean.builder()
                .transactionId(savedTransaction.getTransactionId())
                .payuTransactionId(payuTransactionId)
                .totalAmount(totalAmount)
                .paymentStatus(AppConstants.PAYU_STATUS_INITIATED)
                .paymentLink(paymentResponse.getPaymentLink())
                .selectionSummaryId(null)
                .paymentLinkId(paymentResponse.getPaymentLinkId())
                .remark("")
                .build();
    }



    /**
     * Helper method to get config values
     */
    private String getConfigValue(String configName) {
        return configService.findByName(configName)
                .map(Config::getValue)
                .orElseThrow(() -> new IllegalArgumentException("Config not found: " + configName));
    }

    /**
     * Generate error file from temp_students table for a given batch
     * Uses the student template and populates it with invalid student data
     */
    public InputStreamResource downloadErrorFile(Long batchId) throws IOException {
        // Validate batch exists
        UploadBatch batch = uploadBatchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found"));

        if(Objects.nonNull(batch.getErrorFilePath())){
            // If error file already exists, fetch from S3 and return
            Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
            Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
            String s3BucketName = config.getValue();

            byte[] bytes = s3Service.getFile(s3BucketName, batch.getErrorFilePath());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            return new InputStreamResource(byteArrayInputStream);
        }
        return null;
    }

    /**
     * Generate error file from temp_students table for a given batch
     * Uses the student template and populates it with invalid student data
     */
    public String generateErrorFileFromTemplate(Long batchId, List<TempStudent> invalidStudents) throws IOException {
        if (Objects.isNull(invalidStudents) || invalidStudents.isEmpty()) {
            return null; // No errors to report
        }

        // Load the template from classpath
        String templatePath = "template/StudentUploadTemplate.xlsx";
        ClassPathResource templateResource = new ClassPathResource(templatePath);

        if (!templateResource.exists()) {
            throw new IllegalArgumentException("Template file not found: " + templatePath);
        }

        // Read template and populate with error data
        Workbook workbook;
        try (InputStream templateStream = templateResource.getInputStream()) {
            workbook = new XSSFWorkbook(templateStream);
        }

        // Get the first sheet (assuming template has headers)
        Sheet sheet = workbook.getSheetAt(0);

        // Add "Error" column header in the last column (row 0 is the header row)
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            headerRow = sheet.createRow(0);
        }

        // Find the last column index with data in the header row
        int lastColumnIndex = headerRow.getLastCellNum();
        if (lastColumnIndex < 0) {
            lastColumnIndex = 0;
        }

        // Add "Error" header in the next column
        Cell errorHeaderCell = headerRow.createCell(lastColumnIndex);
        errorHeaderCell.setCellValue("Error");

        // Copy the style from the first header cell to maintain consistency
        if (lastColumnIndex > 0) {
            Cell firstHeaderCell = headerRow.getCell(0);
            if (firstHeaderCell != null && firstHeaderCell.getCellStyle() != null) {
                errorHeaderCell.setCellStyle(firstHeaderCell.getCellStyle());
            }
        }

        // Start writing data from row 1 (row 0 should be headers from template)
        int rowNum = 1;
        for (TempStudent student : invalidStudents) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;

            // Populate columns based on template structure
            createCell(row, colNum++, student.getLastName());
            createCell(row, colNum++, student.getFirstName());
            createCell(row, colNum++, student.getMiddleName());
            createCell(row, colNum++, student.getAdharNo());
            createCell(row, colNum++, student.getMobileNumber());
            createCell(row, colNum++, student.getEmail());
            createCell(row, colNum++, student.getClassName());
            createCell(row, colNum++, student.getExamGroup());
            createCell(row, colNum++, student.getCourses());

            // Add error message in the last column
            createCell(row, colNum++, student.getErrorMessage());
        }

        // Write workbook to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        byte[] excelBytes = baos.toByteArray();

        // Optionally save to S3 for future reference
        String errorFileName = "error_batch_" + batchId + "_" + System.currentTimeMillis() + ".xlsx";
        Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
        String s3BucketName = config.getValue();

        configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER);
        config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "Package Folder Not Found : " + ConfigService.AZ_S3_BUCKET_BULK_UPLOAD_FOLDER));
        String bulkUploadFolderName = config.getValue();

        s3Service.uploadFile(s3BucketName, bulkUploadFolderName, errorFileName,
                excelBytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return bulkUploadFolderName + "/" + errorFileName;
    }
    /**
     * Helper method to create cell with string value
     */
    private void createCell(Row row, int columnIndex, Object value) {
        Cell cell = row.createCell(columnIndex);
        if (value != null) {
            if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public BatchUploadResponse reValidateUploadBatch(Long batchId, String entityType, UserBean user) throws IOException {
        UploadBatch batch = uploadBatchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found"));

        if(batch.getStatus().equalsIgnoreCase("PENDING")){
//            generateAndSetStatusForPendingStatus(batch);

            List<TempStudent> tempStudents = tempStudentService.findByBatchId(batchId);
//            batch.setDisplayStatus("All Invalid Data");
            Map<Boolean, List<TempStudent>> partitionedStudents = tempStudents.stream()
                    .collect(Collectors.partitioningBy(this::isValidStudent));
            List<TempStudent> validEntities = partitionedStudents.get(true);
            List<TempStudent> invalidEntities = partitionedStudents.get(false);
            if(!invalidEntities.isEmpty() && validEntities.isEmpty()) {
                batch.setDisplayStatus("Upload Failed - All Records Invalid");
            } else if(!invalidEntities.isEmpty() && !validEntities.isEmpty()) {
                batch.setDisplayStatus("Partial Upload Pending");
            } else if(invalidEntities.isEmpty() && !validEntities.isEmpty()) {
                batch.setDisplayStatus("Upload Pending");
            } else {
                batch.setDisplayStatus("Upload Pending - No Records Found");
            }
        } else if(batch.getStatus().equalsIgnoreCase("SUCCESS-PENDING_PAYMENT")){
            if(batch.getIsOffline()){
                batch.setDisplayStatus("Upload Successful - Pending Payment Confirmation");
            } else {
                batch.setDisplayStatus("Upload Successful - Pending Payment");
            }
        }  else if(batch.getStatus().equalsIgnoreCase("PARTIAL_FAILURE-PENDING_PAYMENT")){
            if(batch.getIsOffline()){
                batch.setDisplayStatus("Partial Upload Successful - Pending Payment Confirmation");
            } else {
                batch.setDisplayStatus("Partial Upload Successful - Pending Payment");
            }
        }else {
            batch.setDisplayStatus("Unknown Status: Contact Support");
        }
        List<TempStudent> tempStudents = tempStudentService.findByBatchId(batchId);
        if(tempStudents.isEmpty()){
            throw  new IllegalArgumentException("Batch not found");
        }
        Optional<TempStudent> firstTS = tempStudents.stream().filter(ts -> Objects.nonNull(ts.getStudentId()) && ts.getStudentId()>0).findFirst();
        boolean registrationDone = firstTS.isPresent();



        if(registrationDone) {
            TempStudent tempStudent = tempStudents.get(0);
            Optional<PackageBean> packageBeanOptional = packageService.getPackageById(tempStudent.getPackageId(), Boolean.FALSE);
            PackageBean packageBean = packageBeanOptional.orElseThrow(() -> new IllegalArgumentException("Package not found"));

            // Partition students into valid and invalid with single iteration
            Map<Boolean, List<TempStudent>> partitionedStudents = tempStudents.stream()
                    .collect(Collectors.partitioningBy(this::isValidStudent));

            List<TempStudent> validEntities = partitionedStudents.get(true);
            List<TempStudent> invalidEntities = partitionedStudents.get(false);


            List<StudentDataLoadBean> validStudentEntities = getStudentDataLoadBeans(validEntities);

            List<StudentDataLoadBean> inValidStudentEntities = getStudentDataLoadBeans(invalidEntities);


            BigDecimal totalPackageAmount = new BigDecimal(0);
            if (!validEntities.isEmpty()) {
                totalPackageAmount = packageBean.getAmount()
                        .multiply(BigDecimal.valueOf(validEntities.size()));
            }
            String referenceName = applicationUserService.findByUserId(tempStudent.getReferenceId())
                    .map(u -> u.getFirstName() + " " + u.getLastName())
                    .orElse("N/A");

            List<OfflinePaymentModel> offlinePaymentModels = offlinePaymentService.findByBatchId(batchId);
            OfflinePaymentModel offlinePaymentModel = null;
            if (!offlinePaymentModels.isEmpty()) {
                offlinePaymentModel = offlinePaymentModels.get(0);
            }

            return BatchUploadResponse.builder()
                    .batchId(batch.getBatchId())
                    .totalPackageAmount(totalPackageAmount)
                    .validEntities(validStudentEntities)
                    .invalidEntities(inValidStudentEntities)
                    .year(tempStudent.getTargetFinalExamYear())
                    .referenceId(tempStudent.getReferenceId())
                    .referenceName(referenceName)
                    .packageId(tempStudent.getPackageId())
                    .fileName(batch.getOriginalFilePath())
                    .medium(tempStudent.getMedium())
                    .offlinePayment(offlinePaymentModel)
                    .status(batch.getStatus())
                    .displayStatus(batch.getDisplayStatus())
                    .build();
        }else {
            List<StudentDataLoadBean> entities = getStudentDataLoadBeans(tempStudents);
                    UploadProcessor<?> processor = processorFactory.getProcessor(batch.getEntityType());
                    if (processor == null) {
                        throw new IllegalArgumentException("No processor for type: " + batch.getEntityType());
                    }
                    ValidationResult<?> result = processor.validateEntities(entities);
            TempStudent tempStudent = tempStudents.get(0);
            return reProcessUploadBatch(batch, tempStudent,result);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public BatchUploadResponse reProcessUploadBatch(UploadBatch batch,  TempStudent tempStudent,ValidationResult<?> result) {
        try {
            // Safe casting - only cast if we're dealing with Student entities
            List<StudentDataLoadBean> totalEntities = new ArrayList<>();
            List<StudentDataLoadBean>    validEntities = (List<StudentDataLoadBean>) result.getValidEntities();
            List<StudentDataLoadBean>    invalidEntities = (List<StudentDataLoadBean>) result.getInvalidEntities();
            totalEntities.addAll(validEntities);
            totalEntities.addAll(invalidEntities);
            List<TempStudent> tempStudents = new ArrayList<>();
            totalEntities.forEach(ent -> {
                StudentDataLoadBean stu = (StudentDataLoadBean) ent;
                // Convert List<Long> courseIds to comma-separated string
                String courseIdsStr = null;
                if (stu.getCourseIds() != null && !stu.getCourseIds().isEmpty()) {
                    courseIdsStr = stu.getCourseIds().stream()
                            .map(String::valueOf)
                            .reduce((a, b) -> a + "," + b)
                            .orElse(null);
                }

                TempStudent tempStudentObj = TempStudent.builder()
                        .adharNo(stu.getAdharNo())
                        .email(stu.getEmail())
                        .classId(stu.getClassId())
                        .className(stu.getClassName())
                        .batchId(batch.getBatchId())
                        .courses(stu.getCourses())
                        .courseIds(courseIdsStr)
                        .errorMessage(stu.getErrorMessage())
                        .lastName(stu.getLastName())
                        .middleName(stu.getMiddleName())
                        .firstName(stu.getFirstName())
                        .examGroup(stu.getExamGroup())
                        .subjectGroupId(stu.getSubjectGroupId())
                        .mobileNumber(stu.getMobileNumber())
                        .targetFinalExamYear(tempStudent.getTargetFinalExamYear())
                        .packageId(tempStudent.getPackageId())
                        .referenceId(tempStudent.getReferenceId())
                        .medium(tempStudent.getMedium())
                        .build();
                tempStudents.add(tempStudentObj);
            });
            tempStudentService.deleteByBatchId(batch.getBatchId());
            tempStudentService.batchInsert(tempStudents);
            List<TempStudent> invalidTempStudentList = tempStudents.stream().filter(ts -> Objects.nonNull(ts.getErrorMessage()) && !ts.getErrorMessage().isEmpty()).collect(Collectors.toList());
            Optional<PackageBean> packageBeanOptional = packageService.getPackageById(tempStudent.getPackageId(), Boolean.FALSE);
            PackageBean packageBean = packageBeanOptional.orElseThrow(() -> new IllegalArgumentException("Package not found"));
            BigDecimal totalPackageAmount = new BigDecimal(0);
            if(!validEntities.isEmpty()) {
                totalPackageAmount = packageBean.getAmount()
                        .multiply(BigDecimal.valueOf(validEntities.size()));
            }
            String errorFilePath = generateErrorFileFromTemplate(batch.getBatchId(), invalidTempStudentList);


            //Delete Error File from S3 if exists before updating new file path
            String errorFileName = batch.getErrorFilePath();

            // Update batch with error file path
            batch.setErrorFilePath(errorFilePath);

            Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
            Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
            String s3BucketName = config.getValue();
            s3Service.deleteFile(s3BucketName,errorFileName);
            batch.setTotalCount(result.getValidEntities().size() + result.getInvalidEntities().size());
            batch.setSuccessCount(result.getValidEntities().size());
            uploadBatchRepository.save(batch);
            String referenceName = applicationUserService.findByUserId(tempStudent.getReferenceId())
                    .map(user -> user.getFirstName() + " " + user.getLastName())
                    .orElse("N/A");
            return BatchUploadResponse.builder()
                    .batchId(batch.getBatchId())
                    .totalPackageAmount(totalPackageAmount)
                    .validEntities(validEntities)
                    .invalidEntities(invalidEntities)
                    .year(tempStudent.getTargetFinalExamYear())
                    .referenceId(tempStudent.getReferenceId())
                    .referenceName(referenceName)
                    .packageId(tempStudent.getPackageId())
                    .fileName(batch.getOriginalFilePath())
                    .medium(tempStudent.getMedium())
                    .displayStatus(batch.getDisplayStatus())
                    .build();

        } catch (IOException e) {
            logger.error("Error processing upload batch", e);
            throw new ServiceException("Error processing upload batch.");
        }
    }
}
