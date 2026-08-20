package com.mahaexam.packagemanagment.service;

import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.common.util.StringUtil;
import com.mahaexam.packagemanagment.bean.*;
import com.mahaexam.packagemanagment.model.PaymentTransaction;
import com.mahaexam.packagemanagment.model.StudentPackageMapping;
import com.mahaexam.packagemanagment.model.StudentPackageSelection;
import com.mahaexam.packagemanagment.model.StudentPackageSelectionSummary;
import com.mahaexam.packagemanagment.repository.PaymentTransactionRepository;
import com.mahaexam.packagemanagment.repository.StudentPackageSelectionRepository;
import com.mahaexam.packagemanagment.repository.StudentPackageSelectionSummaryRepository;
import com.mahaexam.packagemanagment.service.payment.PaymentLinkService;
import com.mahaexam.payment.bean.PaymentRequest;
import com.mahaexam.tenant.management.bean.StudentDetailsBean;
import com.mahaexam.tenant.management.repository.UploadBatchRepository;
import com.mahaexam.tenant.management.service.StudentService;
import com.mahaexam.tenant.management.service.bulkservice.GenericUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PackageSelectIonServiceImpl implements PackageSelectIonService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final StudentPackageSelectionRepository studentPackageSelectionRepository;
    private final StudentPackageSelectionSummaryRepository studentPackageSelectionSummaryRepository;
    private final StudentService studentService;
    private final PaymentLinkService payUMoneyPaymentLinkService;
    private final ConfigService configService;
    private final StudentPackageMappingService studentPackageMappingService;
    private final UploadBatchRepository uploadBatchRepository;
    private final GenericUploadService genericUploadService;
    public PackageSelectIonServiceImpl(PaymentTransactionRepository paymentTransactionRepository, StudentPackageSelectionRepository studentPackageSelectionRepository,
                                       StudentPackageSelectionSummaryRepository studentPackageSelectionSummaryRepository,
                                       StudentService studentService, PaymentLinkService payUMoneyPaymentLinkService,
                                       ConfigService configService, StudentPackageMappingService studentPackageMappingService,
                                       UploadBatchRepository uploadBatchRepository, GenericUploadService genericUploadService) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.studentPackageSelectionRepository = studentPackageSelectionRepository;
        this.studentPackageSelectionSummaryRepository = studentPackageSelectionSummaryRepository;
        this.studentService = studentService;
        this.payUMoneyPaymentLinkService = payUMoneyPaymentLinkService;
        this.configService = configService;
        this.studentPackageMappingService = studentPackageMappingService;
        this.uploadBatchRepository = uploadBatchRepository;
        this.genericUploadService = genericUploadService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PaymentTransactionBean selectPackages(StudentPackageSelectionSummaryBean packageSelectionSummaryBean) {
        StudentPackageSelectionSummary packageSelectionSummary = StudentPackageSelectionConverter.toEntity(packageSelectionSummaryBean);
        StudentDetailsBean student = studentService.findByIdFull(packageSelectionSummary.getStudentId());
        packageSelectionSummaryBean.setStudentId(student.getStudentId());
        packageSelectionSummary.setStudentId(student.getStudentId());
        String packageNames = packageSelectionSummaryBean.getPackageSelectionBeans()
                .stream()
                .map(StudentPackageSelectionBean::getPackageName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(","));
        BigDecimal totalAmount = packageSelectionSummary.getPackageSelections()
                .stream()
                .map(StudentPackageSelection::getAmount)
                .filter(Objects::nonNull) // Handle null amounts
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String customerName = student.getFirstName() + " " + student.getLastName();
        String description = "Payment for : " + packageNames;

        packageSelectionSummary.setTotalAmount(totalAmount);

        StudentPackageSelectionSummary studentPackageSelectionSummaryDB = studentPackageSelectionSummaryRepository.save(packageSelectionSummary);

        PaymentResponse paymentResponse  = payUMoneyPaymentLinkService.handlPaymentLinkCreationRequest(
                PaymentRequest.builder().amount(totalAmount).customerName(customerName)
                        .customerEmail(student.getEmail()).customerMobile(student.getRegisteredMobileNumber())
                        .description(description).build());
//                        .invoiceNumber(payuTransactionId).description(description).clientId(clientId)
//                        .clientSecret(clientSecret).merchantKey(merchantKey).environment(environment)
//                        .sUrl(sUrl).fUrl(fUrl).apiUrl(apiUrl).authTokenUrl(authTokenUrl).build());

        String payuTransactionId = paymentResponse.getInvoiceNumber();
        List<StudentPackageSelection> packageSelections = packageSelectionSummary.getPackageSelections().stream()
                .peek(ps -> {
                    ps.setSelectionSummaryId(studentPackageSelectionSummaryDB.getSelectionSummaryId());
                    ps.setStudentId(packageSelectionSummary.getStudentId());
                    ps.setSelectedAt(packageSelectionSummary.getSelectedAt());
                })
                .collect(Collectors.toList());
        studentPackageSelectionRepository.save(packageSelections);

        PaymentTransactionBean paymentTransactionBean = PaymentTransactionBean.builder().payuTransactionId(payuTransactionId)
                .totalAmount(totalAmount).paymentStatus(AppConstants.PAYU_STATUS_INITIATED)
                .paymentLink(paymentResponse.getPaymentLink()).selectionSummaryId(studentPackageSelectionSummaryDB.getSelectionSummaryId()).build();
        PaymentTransaction paymentTransaction = paymentTransactionRepository.save(PaymentTransaction.builder().payuTransactionId(payuTransactionId)
                .paymentLink(paymentTransactionBean.getPaymentLink()).paymentStatus(paymentTransactionBean.getPaymentStatus())
                .totalAmount(paymentTransactionBean.getTotalAmount()).createdAt(LocalDateTime.now())
                .selectionSummaryId(studentPackageSelectionSummaryDB.getSelectionSummaryId())
                .paymentLinkId(paymentResponse.getPaymentLinkId()).remark("").build());
        paymentTransactionBean.setTransactionId(paymentTransaction.getTransactionId());
        return paymentTransactionBean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public String handlePayuWebhook(Map<String, String> postParams) {
        logger.info("Inside handlePayuWebhook handler method.");
        PaymentPayuWebhook paymentPayuWebhook = payUMoneyPaymentLinkService.handlePayuWebhook(postParams);
        String invoiceNumber = paymentPayuWebhook.getInvoiceNumber();

        handleWbHook(paymentPayuWebhook, invoiceNumber);
        return paymentPayuWebhook.getStatus();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void handleWbHook(PaymentPayuWebhook paymentPayuWebhook, String invoiceNumber) {
        logger.info("Handling webhook for invoice number: " + invoiceNumber + " with status: " + paymentPayuWebhook.getStatus());
        if ("OK".equals(paymentPayuWebhook.getStatus())) {
            paymentTransactionRepository.updatePaymentStatus(invoiceNumber, AppConstants.PAYU_STATUS_SUCCESS);
            List<StudentPackageSelection> packageSelections = studentPackageSelectionRepository.findByInvoiceNumber(invoiceNumber);
            logger.info("packageSelections: {} and size {} " , packageSelections,packageSelections.size());
            List<StudentPackageMapping> mappings = new ArrayList<>();
            if(!packageSelections.isEmpty()) {
                logger.info("Package Selection found for invoice number: " + invoiceNumber);
                packageSelections.forEach(sp -> {
                    mappings.add(StudentPackageMapping.builder()
                            .packageId(sp.getPackageId())
                            .studentId(sp.getStudentId())
                            .status(AppConstants.PACKAGE_STATUS_ACTIVE)
                            .createdDate(LocalDateTime.now())
                            .build());
                });
                studentPackageMappingService.saveMultiple(mappings);
            }else {
                logger.info("New Data Upload for invoice number: " + invoiceNumber);
                Long btachIdByInvoiceNumber = uploadBatchRepository.findBtachIdByInvoiceNumber(invoiceNumber);
                try {
                    genericUploadService.processUploadBatchFromTempTable(btachIdByInvoiceNumber,true,true,false);
                } catch (Exception e) {
                    logger.error("Error processing upload batch from temp table for batchId: " + btachIdByInvoiceNumber, e);
                }
            }
        } else {
            paymentTransactionRepository.updatePaymentStatus(invoiceNumber, AppConstants.PAYU_STATUS_FAILED);
        }
    }

    private String getConfigValue(String configKay) {
        Optional<Config> configOpt = configService.findByName(configKay);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + configKay));
        return config.getValue();
    }

    @Override
    public String getPaymentStatusByTransactionId(Long transactionId) {
        return paymentTransactionRepository.getPaymentStatusByTransactionId(transactionId);
    }
}
