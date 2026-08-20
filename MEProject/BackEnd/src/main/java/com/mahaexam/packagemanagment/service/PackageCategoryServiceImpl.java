package com.mahaexam.packagemanagment.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.packagemanagment.bean.PackageCategoryBean;
import com.mahaexam.packagemanagment.model.PackageCategoryModel;
import com.mahaexam.packagemanagment.repository.PackageCategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageCategoryServiceImpl implements PackageCategoryService {
    private static final Logger logger = LogManager.getLogger(PackageCategoryServiceImpl.class);
    private final PackageCategoryRepository packageCategoryRepository;

    public PackageCategoryServiceImpl(PackageCategoryRepository packageCategoryRepository) {
        this.packageCategoryRepository = packageCategoryRepository;
    }

    // Convert PackageCategoryBean to PackageCategoryModel
    private PackageCategoryModel toModel(PackageCategoryBean bean) {
        PackageCategoryModel model = new PackageCategoryModel();
        model.setId(bean.getId());
        model.setName(bean.getName());
        model.setDescription(bean.getDescription());
        model.setTenantId(bean.getTenantId());
        model.setCreatedDate(bean.getCreatedDate());
        model.setCreatedBy(bean.getCreatedBy());
        return model;
    }

    // Convert PackageCategoryModel to PackageCategoryBean
    private PackageCategoryBean toBean(PackageCategoryModel model) {
        PackageCategoryBean bean = new PackageCategoryBean();
        bean.setId(model.getId());
        bean.setName(model.getName());
        bean.setDescription(model.getDescription());
        bean.setTenantId(model.getTenantId());
        bean.setCreatedDate(model.getCreatedDate());
        bean.setCreatedBy(model.getCreatedBy());
        return bean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PackageCategoryBean createPackageCategory(PackageCategoryBean bean) {
        logger.info("Creating new package category: {}", bean.getName());

        // Validate input
        if (bean.getName() == null || bean.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Package category name cannot be null or empty");
        }

        if (bean.getDescription() == null || bean.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Package category description cannot be null or empty");
        }

        if (bean.getTenantId() == null) {
            throw new IllegalArgumentException("Tenant ID is required");
        }

        // Check if category name already exists
        if (packageCategoryRepository.existsByName(bean.getName().trim(), bean.getTenantId())) {
            throw new ValidationException("Package category with name '" + bean.getName() + "' already exists");
        }

        // Set created date if not provided
        if (bean.getCreatedDate() == null) {
            bean.setCreatedDate(LocalDateTime.now());
        }

        PackageCategoryModel model = toModel(bean);
        PackageCategoryModel saved = packageCategoryRepository.save(model);
        logger.info("Package category created successfully with ID: {}", saved.getId());
        return toBean(saved);
    }

    @Override
    public Optional<PackageCategoryBean> getPackageCategoryById(Integer id) {
        logger.info("Fetching package category by ID: {}", id);

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid package category ID");
        }

        Optional<PackageCategoryModel> model = packageCategoryRepository.findById(id);
        return model.map(this::toBean);
    }

    @Override
    public List<PackageCategoryBean> getAllPackageCategories(Long tenantId) {
        logger.info("Fetching all package categories for tenant: {}", tenantId);

        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID is required");
        }

        List<PackageCategoryModel> models = packageCategoryRepository.findAll(tenantId);
        return models.stream()
                .map(this::toBean)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PackageCategoryBean updatePackageCategory(Integer id, PackageCategoryBean bean) {
        logger.info("Updating package category with ID: {}", id);

        // Validate input
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid package category ID");
        }

        if (bean.getName() == null || bean.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Package category name cannot be null or empty");
        }

        if (bean.getDescription() == null || bean.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Package category description cannot be null or empty");
        }

        // Fetch existing category
        Optional<PackageCategoryModel> existingModel = packageCategoryRepository.findById(id);
        if (!existingModel.isPresent()) {
            throw new IllegalArgumentException("Package category not found with ID: " + id);
        }

        // Check if name already exists (excluding current ID)
        if (packageCategoryRepository.existsByNameExcludingId(bean.getName().trim(), id, bean.getTenantId())) {
            throw new ValidationException("Package category with name '" + bean.getName() + "' already exists");
        }

        PackageCategoryModel model = existingModel.get();
        model.setName(bean.getName());
        model.setDescription(bean.getDescription());
        model.setTenantId(bean.getTenantId());
        model.setCreatedBy(bean.getCreatedBy());

        packageCategoryRepository.update(model);
        logger.info("Package category updated successfully with ID: {}", id);
        return toBean(model);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void deletePackageCategory(Integer id) {
        logger.info("Deleting package category with ID: {}", id);

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid package category ID");
        }

        // Check if category exists before deleting
        Optional<PackageCategoryModel> existingModel = packageCategoryRepository.findById(id);
        if (!existingModel.isPresent()) {
            throw new IllegalArgumentException("Package category not found with ID: " + id);
        }

        packageCategoryRepository.delete(id);
        logger.info("Package category deleted successfully with ID: {}", id);
    }
}
