package com.mahaexam.tenant.management.controller.bulk;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.packagemanagment.bean.PaymentTransactionBean;
import com.mahaexam.tenant.management.bean.OfflinePayment;
import com.mahaexam.tenant.management.model.UploadBatch;
import com.mahaexam.tenant.management.service.OfflinePaymentService;
import com.mahaexam.tenant.management.service.bulkservice.GenericUploadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bulk/upload")
public class UploadBatchController extends BaseController {
    private static final Logger logger = LogManager.getLogger(UploadBatchController.class);
    private final GenericUploadService genericUploadService;
    private final OfflinePaymentService offlinePaymentService;

    public UploadBatchController(GenericUploadService genericUploadService, OfflinePaymentService offlinePaymentService) {
        this.genericUploadService = genericUploadService;
        this.offlinePaymentService = offlinePaymentService;
    }

    @PostMapping
    public ResponseEntity<?> addUploadBatch(@RequestParam("file") MultipartFile file,
                                            @RequestParam("entityType") String entityType) throws Exception {
        long maxBytes = parseFileSize(AppConstants.ALLOWED_MAX_FILE_SIZE);
        if (file.getSize() > maxBytes) {
            return ResponseEntity.badRequest().body("File size exceeds the allowed limit of " + AppConstants.ALLOWED_MAX_FILE_SIZE);
        }
        UserBean userBean = getUser();
        Long batchId = genericUploadService.createUploadBatch(file, entityType, userBean);
        return ResponseEntity.ok("Data Uploaded Succcesfully with Batch Number: " + batchId);
    }

    @GetMapping
    public ResponseEntity<List<UploadBatch>> listUploadBatches() {
        UserBean userBean = getUser();
        return ResponseEntity.ok(genericUploadService.getAllUploadBatches(userBean));
    }

    @GetMapping("/{batchId}/original-file")
    public ResponseEntity<?> downloadOriginalFile(@PathVariable Long batchId) throws IOException {
        InputStreamResource inputStreamResource  = genericUploadService.getUploadBatcFile(batchId,"Original");
        return buildFileResponse(inputStreamResource, "original_file_" + batchId + ".xlsx");
    }

    @GetMapping("/{batchId}/error-file")
    public ResponseEntity<?> downloadErrorFile(@PathVariable Long batchId) throws IOException {
        InputStreamResource inputStreamResource  = genericUploadService.getUploadBatcFile(batchId,"Error");
        if (inputStreamResource == null) {
            return ResponseEntity.notFound().build();
        }
        return buildFileResponse(inputStreamResource, "error_file_" + batchId + ".xlsx");
    }

    private ResponseEntity<InputStreamResource> buildFileResponse(InputStreamResource inputStreamResource, String downloadFileName) throws IOException {


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + downloadFileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(inputStreamResource);
    }

    @GetMapping("/template/student")
    public ResponseEntity<InputStreamResource> downloadStudentTemplate() throws IOException {
        String resourcePath = "template/StudentUploadTemplate.xlsx";
        ClassPathResource resource = new ClassPathResource(resourcePath);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource inputStreamResource = new InputStreamResource(resource.getInputStream());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=StudentUploadTemplate.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(inputStreamResource);
    }

    @PostMapping(value = "/validate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> validateUploadBatch(
            @RequestParam("file") MultipartFile file,
            @RequestParam("entityType") String entityType,
            @RequestParam("packageId") Integer packageId,
            @RequestParam("targetYear") String targetYear,
            @RequestParam("referralId") Long referralId,
            @RequestParam(value = "medium") String medium) {

        try {
            // Validate file size
            long maxBytes = parseFileSize(AppConstants.ALLOWED_MAX_FILE_SIZE);
            if (file.getSize() > maxBytes) {
                return ResponseEntity.badRequest().body("File size exceeds the allowed limit of " + AppConstants.ALLOWED_MAX_FILE_SIZE);
            }

            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Validate file extension
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".xlsx")) {
                return ResponseEntity.badRequest().body("Only .xlsx files are allowed");
            }

            // Validate entity type
            if (!"Student".equalsIgnoreCase(entityType)) {
                return ResponseEntity.badRequest().body("Invalid entity type. Only 'Student' is supported");
            }

            // Validate target year (should be current year or future)
            try {
                int targetYearInt = Integer.parseInt(targetYear);
                int currentYear = java.time.Year.now().getValue();
                if (targetYearInt < currentYear) {
                    return ResponseEntity.badRequest().body("Target year must be current year or future year");
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid target year format");
            }

            // Validate package ID
            if (packageId == null || packageId <= 0) {
                return ResponseEntity.badRequest().body("Package ID must be a positive number");
            }

            UserBean userBean = getUser();
            var response = genericUploadService.validateUploadBatch(file, entityType, packageId, targetYear, referralId, medium, userBean);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            logger.error("Error processing file " , e);
            return ResponseEntity.status(500).body("Error processing file ");
        } catch (Exception e) {
            logger.error("Error processing file " , e);
            return ResponseEntity.status(400).body("Validation error ");
        }
    }

    /**
     * Processes an upload batch from the temporary table From UI for no payment cases
     * @param batchId
     * @return
     */
    @PostMapping(value = "/{batchId}/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processUploadBatchFromTempTable(@PathVariable Long batchId) {
        try {
            // Validate batch ID
            if (batchId == null || batchId <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("status", "error", "message", "Invalid batch ID"));
            }

            UserBean userBean = getUser();

            // Process the batch from temp table
            //As this is the no payment case so marking paymentDone/withPayment=true and withRegistration =false
            genericUploadService.processUploadBatchFromTempTable(batchId,true,false,false);

            return ResponseEntity.ok()
                    .body(Map.of(
                            "status", "success",
                            "message", "Batch processed successfully",
                            "batchId", batchId
                    ));

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: " + batchId, e);
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", "Validation error: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Error processing upload batch from temp table for batchId: " + batchId, e);
            return ResponseEntity.status(500)
                    .body(Map.of("status", "error", "message", "Error processing batch: "));
        }
    }

    @PostMapping("/{batchId}/create-payment-link/{isRePayment}")
    public ResponseEntity<?> createBatchPaymentLink(
            @PathVariable Long batchId,
            @PathVariable String isRePayment,
            @RequestParam("totalAmount") BigDecimal totalAmount) {
        logger.info("Inside createBatchPaymentLink with isRePayment: {}", isRePayment);
        try {
            // Validate batch ID
            if (batchId == null || batchId <= 0) {
                return ResponseEntity.badRequest().body("Invalid batch ID");
            }

            // Validate total amount
            if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("Total amount must be greater than zero");
            }

            UserBean userBean = getUser();

            //As this is the with payment and will mark complete after payment done case so marking paymentDone/withPayment=false and withRegistration =false
            // keepStatusPendingPayment should be true for re-payment scenarios
            if(!Boolean.parseBoolean(isRePayment)) {
                genericUploadService.processUploadBatchFromTempTable(batchId, false, false, true);
            }
            logger.info("Before genericUploadService.createBatchPaymentLink with isRePayment: {}", isRePayment);
            // Create payment link for batch
            PaymentTransactionBean paymentTransaction = genericUploadService.createBatchPaymentLink(batchId, totalAmount, userBean);
            logger.info("After genericUploadService.createBatchPaymentLink with isRePayment: {}", isRePayment);

            return ResponseEntity.ok(paymentTransaction);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: " + batchId, e);
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating batch payment link for batchId: " + batchId, e);
            return ResponseEntity.status(500).body("Error creating payment link: " + e.getMessage());
        }
    }

        @PostMapping("/{batchId}/package-confirmation-payment-link")
        public ResponseEntity<?> createBatchPaymentLinkPackageConfirmation(
        @PathVariable Long batchId,
        @RequestParam("totalAmount") BigDecimal totalAmount) {
        try {
            // Validate batch ID
            if (batchId == null || batchId <= 0) {
                return ResponseEntity.badRequest().body("Invalid batch ID");
            }

            // Validate total amount
            if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("Total amount must be greater than zero");
            }

            UserBean userBean = getUser();
            // Create payment link for batch
            PaymentTransactionBean paymentTransaction = genericUploadService.createBatchPaymentLink(batchId, totalAmount, userBean);

            return ResponseEntity.ok(paymentTransaction);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: " + batchId, e);
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating batch payment link for batchId: " + batchId, e);
            return ResponseEntity.status(500).body("Error creating payment link for batchId: " + batchId);
        }
    }

    @PostMapping("/{batchId}/offLine-payment")
    public ResponseEntity<?> offLinePayment(
            @PathVariable Long batchId,
            @RequestBody OfflinePayment offlinePayment) {

        try {
            // Validate batch ID
            if (batchId == null || batchId <= 0) {
                return ResponseEntity.badRequest().body("Invalid batch ID");
            }

            // Validate total amount
            if (offlinePayment == null || offlinePayment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("Total amount must be greater than zero");
            }

            UserBean userBean = getUser();

            offlinePayment.setBatchId(batchId);
            //As this is the with payment and will mark complete after payment done case so marking paymentDone/withPayment=false and withRegistration =false
            genericUploadService.processUploadBatchFromTempTableForOffLinePayment(offlinePayment,false,false,false);

            return ResponseEntity.ok("Offline payment entry successfully for batch ID: " + batchId);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: " + batchId, e);
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Occurring while Offline payment entry successfully for batch ID: " + batchId, e);
            return ResponseEntity.status(500).body("Error Occurring while Offline payment entry successfully for batch ID: " + batchId);
        }
    }

    /**
     * Approve all offline payments belonging to a batch (batch update) and return an example approved payment
     */
    @PostMapping("/{batchId}/approve-payments")
    public ResponseEntity<?> approvePaymentsByBatchId(@PathVariable Long batchId) {
        try {
            if (batchId == null || batchId <= 0) {
                return ResponseEntity.badRequest().body("Invalid batch ID");
            }

            // Call service to approve payments. Some implementations may return a model, some may be void.
            // Call it and then fetch the payments for sample data to keep API backward compatible.
            genericUploadService.approvePaymentByBatchId(batchId);

            return ResponseEntity.ok("Payments approved for batch " + batchId);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: " + batchId, e);
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error approving payments for batchId: " + batchId, e);
            return ResponseEntity.status(500).body("Error approving payments for batchId: " + batchId);
        }
    }

    @PostMapping("/{batchId}/download-error-file")
    public ResponseEntity<?> createBatchPaymentLink(@PathVariable Long batchId) throws IOException {
        InputStreamResource inputStreamResource  = genericUploadService.downloadErrorFile(batchId);
        if (inputStreamResource == null) {
            return ResponseEntity.notFound().build();
        }
        return buildFileResponse(inputStreamResource, "error_file_" + batchId + ".xlsx");
    }

    /**
     * Re-validates an existing batch with current validation rules
     * @param batchId The ID of the batch to revalidate
     * @return ResponseEntity containing the validation results or error message
     */
    @PostMapping(value = "/{batchId}/revalidate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reValidateUploadBatch(@PathVariable Long batchId) {
        try {
            // Validate batch ID
            if (batchId == null || batchId <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("status", "error", "message", "Invalid batch ID"));
            }

            UserBean userBean = getUser();
            String entityType = "Student"; // Since we only support Student entity type currently

            // Re-validate the batch
            var response = genericUploadService.reValidateUploadBatch(batchId, entityType, userBean);

            return ResponseEntity.ok()
                    .body(response);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error during batch re-validation for batchId: " + batchId, e);
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", "Validation error: " + e.getMessage()));
        } catch (IOException e) {
            logger.error("IO error during batch re-validation for batchId: " + batchId, e);
            return ResponseEntity.status(500)
                    .body(Map.of("status", "error", "message", "Error reading batch file: "  + batchId));
        } catch (Exception e) {
            logger.error("Error re-validating batch for batchId: " + batchId, e);
            return ResponseEntity.status(500)
                    .body(Map.of("status", "error", "message", "Error re-validating batch: "  + batchId));
        }
    }
}
