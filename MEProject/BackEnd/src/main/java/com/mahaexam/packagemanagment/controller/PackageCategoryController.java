package com.mahaexam.packagemanagment.controller;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.packagemanagment.bean.PackageCategoryBean;
import com.mahaexam.packagemanagment.service.PackageCategoryService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/package-categories")
public class PackageCategoryController extends BaseController {
    private static final Logger logger = LogManager.getLogger(PackageCategoryController.class);
    private final PackageCategoryService packageCategoryService;

    public PackageCategoryController(PackageCategoryService packageCategoryService) {
        this.packageCategoryService = packageCategoryService;
    }

    /**
     * Create a new package category
     * @param bean PackageCategoryBean containing category details
     * @return ResponseEntity with success message and created category ID
     */
    @PostMapping("/create")
    public ResponseEntity<SuccessResponseBean> createPackageCategory(@Valid @RequestBody PackageCategoryBean bean) {
        logger.info("Invoked createPackageCategory method with name: {}", bean.getName());
        try {
            UserBean user = getUser();
            bean.setTenantId(user.getTenantId());
            bean.setCreatedBy(user.getUserId() != null ? user.getUserId().intValue() : null);
            
            PackageCategoryBean created = packageCategoryService.createPackageCategory(bean);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(SuccessResponseBean.builder()
                            .status("success")
                            .message("Package category created successfully with ID: " + created.getId())
                            .build());
        } catch (Exception e) {
            logger.error("Error creating package category: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get package category by ID
     * @param id Package category ID
     * @return ResponseEntity with PackageCategoryBean if found, otherwise NOT_FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<PackageCategoryBean> getPackageCategoryById(@PathVariable Integer id) {
        logger.info("Fetching package category with ID: {}", id);
        try {
            Optional<PackageCategoryBean> bean = packageCategoryService.getPackageCategoryById(id);
            return bean.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid package category ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all package categories for a tenant
     * @return ResponseEntity with list of PackageCategoryBean objects
     */
    @GetMapping("/all")
    public ResponseEntity<List<PackageCategoryBean>> getAllPackageCategories() {
        logger.info("Fetching all package categories");
        try {
            UserBean user = getUser();
            List<PackageCategoryBean> categories = packageCategoryService.getAllPackageCategories(user.getTenantId());
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching package categories: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Update an existing package category
     * @param id Package category ID to update
     * @param bean PackageCategoryBean with updated details
     * @return ResponseEntity with success message
     */
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> updatePackageCategory(
            @PathVariable Integer id,
            @Valid @RequestBody PackageCategoryBean bean) {
        logger.info("Updating package category with ID: {}", id);
        try {
            UserBean user = getUser();
            bean.setTenantId(user.getTenantId());
            
            PackageCategoryBean updated = packageCategoryService.updatePackageCategory(id, bean);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder()
                            .status("success")
                            .message("Package category updated successfully with ID: " + updated.getId())
                            .build());
        } catch (Exception e) {
            logger.error("Error updating package category: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Delete a package category
     * @param id Package category ID to delete
     * @return ResponseEntity with HTTP 200 OK or error status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> deletePackageCategory(@PathVariable Integer id) {
        logger.info("Deleting package category with ID: {}", id);
        try {
            packageCategoryService.deletePackageCategory(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder()
                            .status("success")
                            .message("Package category deleted successfully with ID: " + id)
                            .build());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid package category ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(SuccessResponseBean.builder()
                            .status("error")
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            logger.error("Error deleting package category: {}", e.getMessage(), e);
            throw e;
        }
    }
}
