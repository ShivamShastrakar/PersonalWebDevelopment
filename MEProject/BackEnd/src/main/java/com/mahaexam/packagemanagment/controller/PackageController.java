package com.mahaexam.packagemanagment.controller;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.packagemanagment.bean.PackageBean;
import com.mahaexam.packagemanagment.bean.PackageSearchRequest;
import com.mahaexam.packagemanagment.bean.PaymentTransactionBean;
import com.mahaexam.packagemanagment.bean.StudentPackageSelectionSummaryBean;
import com.mahaexam.packagemanagment.service.PackageSelectIonService;
import com.mahaexam.packagemanagment.service.PackageService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/packages")
public class PackageController extends BaseController {
    private static final Logger logger = LogManager.getLogger(PackageController.class);
    private final PackageService packageService;
    private final PackageSelectIonService packageSelectIonService;

    public PackageController(PackageService packageService, PackageSelectIonService packageSelectIonService) {
        this.packageService = packageService;
        this.packageSelectIonService = packageSelectIonService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseBean> createPackage(@RequestBody PackageBean pkg) {
        logger.info("Invoked createPackage method.");
        try {
            UserBean user = getUser();
            pkg.setTenantId(user.getTenantId());
            PackageBean created = packageService.createPackage(user,pkg);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Package created successfully" + created.getId()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageBean> getPackageById(@PathVariable Integer id) {
        try {
            Optional<PackageBean> pkg = packageService.getPackageById(id, Boolean.TRUE);
            return pkg.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse<PackageBean>> searchPackages(@Valid @RequestBody PackageSearchRequest request) {
        UserBean user = getUser();
        PaginatedResponse<PackageBean> packages = packageService.searchPackages(
                user,
                request
        );
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> updatePackage(@PathVariable Integer id, @RequestBody PackageBean pkg) {
        try {
        	UserBean user = getUser();
            pkg.setTenantId(user.getTenantId());
            PackageBean updated = packageService.updatePackage(user,id, pkg);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Package updated successfully" + updated.getId()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Integer id) {
        try {
            packageService.deletePackage(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<PackageBean> getPackageImageById(@PathVariable Integer id) {
        try {
            PackageBean pkg = packageService.getPackageImageById(id);
            return new ResponseEntity<>(pkg, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/select")
    public ResponseEntity<PaymentTransactionBean> doPackageSelection(@Valid @RequestBody StudentPackageSelectionSummaryBean packageSelectionSummaryBean) {
        // Validate input
        if (packageSelectionSummaryBean == null || packageSelectionSummaryBean.getStudentId() == null ||
                packageSelectionSummaryBean.getPackageSelectionBeans() == null ||
                packageSelectionSummaryBean.getPackageSelectionBeans().isEmpty()) {
            throw new IllegalArgumentException("Invalid package selection summary or empty package selections");
        }

        // Convert and save summary
        if (packageSelectionSummaryBean.getSelectedAt() == null) {
            packageSelectionSummaryBean.setSelectedAt(java.time.LocalDateTime.now());
        }
        if (packageSelectionSummaryBean.getStatus() == null) {
            packageSelectionSummaryBean.setStatus("PENDING");
        }
        PaymentTransactionBean paymentTransaction = packageSelectIonService.selectPackages(
                packageSelectionSummaryBean);
        return ResponseEntity.ok(paymentTransaction);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<PackageBean>> findAllByUserId(@PathVariable Long userId) {
        UserBean user = getUser();
        List<PackageBean> packageBeans = packageService.findAllByUserId(userId, user);
        return new ResponseEntity<>(packageBeans, HttpStatus.OK);
    }
    
    @GetMapping("/allPackages")
    public List<PackageBean> getAllPackages(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String targetYear) {
    	UserBean user = getUser();
        List<PackageBean> packageBeans = packageService.getAllPackages(user, type, targetYear);
        return packageBeans;
    }

    /**
     * Get suggested packages based on student's current class, subject group and target year
     * @return ResponseEntity containing list of suggested packages or error response
     */
    @GetMapping("/suggested")
    public ResponseEntity<?> getSuggestedPackages() {
        UserBean user = getUser();
        try {
            List<PackageBean> suggestedPackages = packageService.getSuggestedPackages(user);
            return ResponseEntity.ok(suggestedPackages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid request: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Error getting suggested packages for userId: " + user.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }
}