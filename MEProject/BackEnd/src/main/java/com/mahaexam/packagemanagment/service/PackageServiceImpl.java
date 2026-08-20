package com.mahaexam.packagemanagment.service;

import com.mahaexam.common.bean.ClassBean;
import com.mahaexam.common.bean.CourseBean;
import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.config.S3Service;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ClassService;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.common.service.CourseService;
import com.mahaexam.common.util.StringUtil;
import com.mahaexam.packagemanagment.bean.*;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.packagemanagment.repository.PackageRepository;
import com.mahaexam.tenant.management.model.Student;
import com.mahaexam.tenant.management.repository.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {
    private static final Logger logger = LogManager.getLogger(PackageServiceImpl.class);
    private final PackageRepository packageRepository;
    private final PackageClassService packageClassService;
    private final PackageCourseService packageCourseService;
    private final PackageServiceService packageServiceService;
    private final ClassService classService;
    private final CourseService courseService;
    private final ServiceService serviceService;
    private final S3Service s3Service;
    private final ConfigService configService;
    private final StudentRepository studentRepository;

    public PackageServiceImpl(PackageRepository packageRepository, PackageClassService packageClassService,
                              PackageCourseService packageCourseService, PackageServiceService packageServiceService, S3Service s3Service,
                              ConfigService configService, ClassService classService, CourseService courseService,
                              ServiceService serviceService, StudentRepository studentRepository) {
        this.packageRepository = packageRepository;
        this.packageClassService = packageClassService;
        this.packageCourseService = packageCourseService;
        this.packageServiceService = packageServiceService;
        this.s3Service = s3Service;
        this.configService = configService;

        this.classService = classService;
        this.courseService = courseService;
        this.serviceService = serviceService;
        this.studentRepository = studentRepository;
    }

    // Convert PackageBean to PackageModel
    private PackageModel toModel(PackageBean bean) {
        PackageModel model = new PackageModel();
        model.setId(bean.getId());
        model.setPackageName(bean.getPackageName());
        model.setPackageDetails(bean.getPackageDetails());
        model.setAmount(bean.getAmount());
        model.setPackageFor(bean.getPackageFor());
        model.setPackageType(bean.getPackageType());
        model.setPackageTargetStudents(bean.getPackageTargetStudents());
        model.setPackageMode(bean.getPackageMode());
        model.setFlag(bean.getFlag());
        model.setPackageTypeName(bean.getPackageTypeName());
        model.setPkgExamGroup(bean.getPkgExamGroup());
        model.setIsArchived(bean.getIsArchived());
        model.setArchivedBy(bean.getArchivedBy());
        model.setStartDate(bean.getStartDate());
        model.setEndDate(bean.getEndDate());
        model.setTargetYear(bean.getTargetYear());
        model.setShowStrikePrice(bean.getShowStrikePrice());
        model.setStrikePrice(bean.getStrikePrice());
        model.setIsTestingPackage(bean.getIsTestingPackage());
        model.setUpdatedBy(bean.getUpdatedBy());
        model.setPackageImgUrl(bean.getPackageImgUrl());
        model.setTenantId(bean.getTenantId());
        model.setStudentId(bean.getStudentId());
        model.setStudentId(bean.getStudentId());
        model.setCourseId(bean.getCourseId());
        model.setClassId(bean.getClassId());
        model.setServiceId(bean.getServiceId());
        model.setSubscriptiontype(bean.getSubscriptiontype());
        model.setPackageImg(bean.getPackageImg());
        model.setNo_of_mock_exams(bean.getNo_of_mock_exams());
        model.setNo_of_pactice_exams(bean.getNo_of_pactice_exams());
        model.setNo_of_bonus_exams(bean.getNo_of_bonus_exams());
        model.setPackageCategoryId(bean.getPackageCategoryId());
        return model;
    }

    // Convert PackageModel to PackageBean
    private PackageBean toBean(PackageModel model) {
        PackageBean bean = new PackageBean();
        bean.setId(model.getId());
        bean.setPackageName(model.getPackageName());
        bean.setPackageDetails(model.getPackageDetails());
        bean.setAmount(model.getAmount());
        bean.setPackageFor(model.getPackageFor());
        bean.setPackageType(model.getPackageType());
        bean.setPackageTargetStudents(model.getPackageTargetStudents());
        bean.setPackageMode(model.getPackageMode());
        bean.setFlag(model.getFlag());
        bean.setPackageTypeName(model.getPackageTypeName());
        bean.setPkgExamGroup(model.getPkgExamGroup());
        bean.setIsArchived(model.getIsArchived());
        bean.setArchivedBy(model.getArchivedBy());
        bean.setStartDate(model.getStartDate());
        bean.setEndDate(model.getEndDate());
        bean.setTargetYear(model.getTargetYear());
        bean.setShowStrikePrice(model.getShowStrikePrice());
        bean.setStrikePrice(model.getStrikePrice());
        bean.setIsTestingPackage(model.getIsTestingPackage());
        bean.setUpdatedBy(model.getUpdatedBy());
        bean.setPackageImgUrl(model.getPackageImgUrl());
        bean.setTenantId(model.getTenantId());
        bean.setStudentId(model.getStudentId());
        bean.setCourseId(model.getCourseId());
        bean.setClassId(model.getClassId());
        bean.setServiceId(model.getServiceId());
        bean.setSubscriptiontype(model.getSubscriptiontype());
        bean.setPackageImg(model.getPackageImg());
        bean.setNo_of_mock_exams(model.getNo_of_mock_exams());
        bean.setNo_of_pactice_exams(model.getNo_of_pactice_exams());
        bean.setNo_of_bonus_exams(model.getNo_of_bonus_exams());
        bean.setPackageCategoryId(model.getPackageCategoryId());
        
        bean.setCreatedAt(model.getCreatedAt());
        bean.setUpdatedAt(model.getUpdatedAt());
        bean.setDeletedAt(model.getDeletedAt());
        return bean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PackageBean createPackage(UserBean user,PackageBean pkg) {
        if (pkg.getPackageName() == null || pkg.getPackageName().isBlank()) {
            throw new IllegalArgumentException("Package name cannot be null or empty");
        }
        if (packageRepository.existsByPackageName(pkg.getPackageName().trim())) {
            throw new ValidationException("Package name already exists: " + pkg.getPackageName());
        }
        if (pkg.getPackageTargetStudents() == null
                || !List.of("New", "Existing").contains(pkg.getPackageTargetStudents())) {
            throw new IllegalArgumentException("Package target students must be 'New' or 'Existing'");
        }
        if (pkg.getPackageMode() == null
                || !List.of("ONLINE", "OFFLINE", "CBT", "With_Course").contains(pkg.getPackageMode())) {
            throw new IllegalArgumentException("Package mode must be one of: ONLINE, OFFLINE, CBT, With_Course");
        }
        PackageModel model = toModel(pkg);
        model.setCreatedAt(LocalDateTime.now());
        model.setDeleted("0");
        model.setIsArchived(0);

        if (Objects.nonNull(pkg.getPackageImg())) {
            Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
            Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
            String s3BucketName = config.getValue();

            configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_PACKAGE_IMG_FOLDER);
            config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "Package Folder NoT Found : " + ConfigService.AZ_S3_BUCKET_PACKAGE_IMG_FOLDER));
            String packageFolderName = config.getValue();
            String fileName = StringUtil.extractFilenameFromBase64(pkg.getPackageImg());
            model.setPackageImgUrl(packageFolderName + "/" + fileName);

            s3Service.uploadBase64ImageToS3(s3BucketName, packageFolderName, fileName, pkg.getPackageImg());
        }

        PackageModel savedModel = packageRepository.save(model);
        packageClassService.createMapping(
                PackageClassBean.builder().packageId(savedModel.getId()).classId(pkg.getClassId()).build());
        packageCourseService.createMapping(
                PackageCourseBean.builder().packageId(savedModel.getId()).courseId(pkg.getCourseId()).build());
        if(Objects.nonNull(pkg.getServiceBeans())){
            List<PackageServiceBean> mappings = new ArrayList<>();
            pkg.getServiceBeans().forEach(s->{
                mappings.add(PackageServiceBean.builder().packageId(savedModel.getId()).serviceId(s.getId()).createdBy(user.getUserId().intValue())
                        .createdDate(LocalDateTime.now()).build());
            });
            packageServiceService.createMappings(mappings);
        }
               return toBean(savedModel);
    }

    @Override
    public Optional<PackageBean> getPackageById(Integer id, boolean fetchImage) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid package ID");
        }
        Optional<PackageBean> packageBean = Optional.empty();
        Optional<PackageModel> packageOpt = packageRepository.findById(id);
        if (packageOpt.isPresent()) {
            PackageModel packageObj = packageOpt.get();
            try {
                if (fetchImage && Objects.nonNull(packageObj.getPackageImgUrl()) && !packageObj.getPackageImgUrl().isEmpty()) {
                    Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
                    Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                            "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
                    String s3BucketName = config.getValue();
                    String base64Img = s3Service.getImageAsBase64(s3BucketName, packageObj.getPackageImgUrl());
                    String extension = packageObj.getPackageImgUrl()
                            .substring(packageObj.getPackageImgUrl().lastIndexOf(".") + 1).toLowerCase();
                    String mimeType = StringUtil.getMimeTypeByExtension(extension);
                    packageObj.setPackageImgUrl("data:" + mimeType + ";base64," + base64Img);
                }
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            packageBean = Optional.of(toBean(packageObj));
            List<ServiceBean> serviceBeans = serviceService.findAllByPackageIds(List.of(packageObj.getId()));
            packageBean.get().setServiceBeans(serviceBeans);
        }

        return packageBean;
    }

    @Override
    public PackageBean getPackageImageById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid package ID");
        }
        PackageBean packageBean = null;
        Optional<PackageModel> packageOpt = packageRepository.findById(id);
        String packageImg = null;
        if (packageOpt.isPresent()) {
            PackageModel packageObj = packageOpt.get();
            if (Objects.nonNull(packageObj.getPackageImgUrl())) {
                Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
                Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                        "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
                String s3BucketName = config.getValue();
                String base64Img = s3Service.getImageAsBase64(s3BucketName, packageObj.getPackageImgUrl());
                String extension = packageObj.getPackageImgUrl()
                        .substring(packageObj.getPackageImgUrl().lastIndexOf(".") + 1).toLowerCase();
                String mimeType = StringUtil.getMimeTypeByExtension(extension);
                packageImg = "data:" + mimeType + ";base64," + base64Img;
            }
//			packageBean = toBean(packageObj);
            packageBean = new PackageBean();
            packageBean.setId(id);
            packageBean.setPackageImgUrl(packageObj.getPackageImgUrl());
            packageBean.setPackageImg(packageImg);
        }

        return packageBean;
    }


    @Override
    public PackageBean updatePackage(UserBean user, Integer id, PackageBean pkg) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid package ID");
        }
        if (pkg.getPackageName() == null || pkg.getPackageName().isBlank()) {
            throw new IllegalArgumentException("Package name cannot be null or empty");
        }
        if (packageRepository.existsByPackageNameExcludingId(pkg.getPackageName().trim(), id)) {
            throw new ValidationException("Package name already exists: " + pkg.getPackageName());
        }
        if (pkg.getPackageTargetStudents() != null
                && !List.of("New", "Existing").contains(pkg.getPackageTargetStudents())) {
            throw new IllegalArgumentException("Package target students must be 'New' or 'Existing'");
        }
        if (pkg.getPackageMode() != null
                && !List.of("ONLINE", "OFFLINE", "CBT", "with_course").contains(pkg.getPackageMode())) {
            throw new IllegalArgumentException("Package mode must be one of: ONLINE, OFFLINE, CBT, with_course");
        }
        Optional<PackageModel> existing = packageRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Package with ID " + id + " not found");
        }
        PackageModel model = toModel(pkg);
        model.setId(id);
        model.setUpdatedAt(LocalDateTime.now());
        
        if (Objects.nonNull(pkg.getPackageImg())) {
            Optional<Config> configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_NAME);
            Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "S3 Bucket Name Not Found : " + ConfigService.AZ_S3_BUCKET_NAME));
            String s3BucketName = config.getValue();

            configOpt = configService.findByName(ConfigService.AZ_S3_BUCKET_PACKAGE_IMG_FOLDER);
            config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                    "Package Folder NoT Found : " + ConfigService.AZ_S3_BUCKET_PACKAGE_IMG_FOLDER));
            String packageFolderName = config.getValue();
            String fileName = StringUtil.extractFilenameFromBase64(pkg.getPackageImg());
            model.setPackageImgUrl(packageFolderName + "/" + fileName);
            
            s3Service.uploadBase64ImageToS3(s3BucketName, packageFolderName, fileName, pkg.getPackageImg());
        }
        packageRepository.update(model);
        
        /*
         * First delete all sub object package reference and then add.
         */
        packageClassService.deletebyGivenPackageId(model.getId());
        packageCourseService.deletebyGivenPackageId(model.getId());
        packageServiceService.deletebyGivenPackageId(model.getId());
        
        packageClassService.createMapping(
                PackageClassBean.builder().packageId(model.getId()).classId(pkg.getClassId()).build());
        packageCourseService.createMapping(
                PackageCourseBean.builder().packageId(model.getId()).courseId(pkg.getCourseId()).build());
        if(Objects.nonNull(pkg.getServiceBeans())){
            List<PackageServiceBean> mappings = new ArrayList<>();
            pkg.getServiceBeans().forEach(s->{
                mappings.add(PackageServiceBean.builder().packageId(model.getId()).serviceId(s.getId()).createdBy(user.getUserId().intValue())
                        .createdDate(LocalDateTime.now()).build());
            });
            packageServiceService.createMappings(mappings);
        }
       
        
        
        
        return toBean(model);
    }

    @Override
    public void deletePackage(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid package ID");
        }
        Optional<PackageModel> existing = packageRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Package with ID " + id + " not found");
        }
        packageRepository.delete(id);
    }

    @Override
    public PaginatedResponse<PackageBean> searchPackages(UserBean user, PackageSearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Search request cannot be null");
        }

        PaginatedResponse<PackageModel> paginatedResponse = packageRepository.search(user, request);
        List<PackageModel> packageModels = paginatedResponse.getContent();

        if (packageModels == null || packageModels.isEmpty()) {
            return PaginatedResponse.<PackageBean>builder()
                    .content(List.of())
                    .page(request.getPage())
                    .size(request.getSize())
                    .totalElements(paginatedResponse.getTotalElements())
                    .totalPages(paginatedResponse.getTotalPages())
                    .build();
        }

        List<PackageBean> packageBeans = getPackageDetails(packageModels);

        return PaginatedResponse.<PackageBean>builder()
                .content(packageBeans)
                .page(request.getPage())
                .size(request.getSize())
                .totalElements(paginatedResponse.getTotalElements())
                .totalPages(paginatedResponse.getTotalPages())
                .build();
    }

    @NotNull
    private List<PackageBean> getPackageDetails(List<PackageModel> packageModels) {
        List<PackageBean> packageBeans = packageModels.stream()
                .filter(Objects::nonNull)
                .map(this::toBean)
                .collect(Collectors.toList());

        List<Integer> packageIds = packageBeans.stream()
                .map(PackageBean::getId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (!packageIds.isEmpty()) {
            try {
                List<ClassBean> classBeansFull = classService.findAllByPackageIds(packageIds);
                List<CourseBean> courseBeansFull = courseService.findAllByPackageIds(packageIds);
                List<ServiceBean> serviceBeansFull = serviceService.findAllByPackageIds(packageIds);

                packageBeans.forEach(p -> {
                    if (p.getId() != null) {
                        // Set class bean
                        if (classBeansFull != null && !classBeansFull.isEmpty()) {
                            List<ClassBean> classBeans = classBeansFull.stream()
                                    .filter(c -> Objects.nonNull(c) && Objects.nonNull(c.getPackageId()) &&
                                            p.getId().equals(c.getPackageId()))
                                    .distinct()
                                    .collect(Collectors.toList());
                            if (!classBeans.isEmpty()) {
                                p.setClassBean(classBeans.get(0));
                            }
                        }

                        // Set course bean
                        if (courseBeansFull != null && !courseBeansFull.isEmpty()) {
                            List<CourseBean> courseBeans = courseBeansFull.stream()
                                    .filter(c -> Objects.nonNull(c) && Objects.nonNull(c.getPackageId()) &&
                                            p.getId().equals(c.getPackageId()))
                                    .distinct()
                                    .collect(Collectors.toList());
                            if (!courseBeans.isEmpty()) {
                                p.setCourseBean(courseBeans.get(0));
                            }
                        }

                        // Set service beans
                        if (serviceBeansFull != null && !serviceBeansFull.isEmpty()) {
                            List<ServiceBean> serviceBeans = serviceBeansFull.stream()
                                    .filter(s -> Objects.nonNull(s) && Objects.nonNull(s.getPackageId()) &&
                                            p.getId().equals(s.getPackageId()))
                                    .distinct()
                                    .collect(Collectors.toList());
                            p.setServiceBeans(serviceBeans);
                        }
                    }
                });
            } catch (Exception e) {
                // Log the error but don't fail the entire request
                // Just return packages without the additional data
            }
        }
        return packageBeans;
    }

    @Override
    public List<PackageBean> findAllByUserId(Long userId, UserBean user) {
        List<PackageBean> packageBeans = packageRepository.findAllByUserId(userId, user).stream().map(this::toBean).collect(Collectors.toList());
        List<Integer> packageIds = packageBeans.stream()
                .map(PackageBean::getId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (!packageIds.isEmpty()) {
            List<ServiceBean> serviceBeansFull = serviceService.findAllByPackageIds(packageIds);
            packageBeans.forEach(p -> {
                if (p.getId() != null) {

                    // Set service beans
                    if (serviceBeansFull != null && !serviceBeansFull.isEmpty()) {
                        List<ServiceBean> serviceBeans = serviceBeansFull.stream()
                                .filter(s -> Objects.nonNull(s) && Objects.nonNull(s.getPackageId()) &&
                                        p.getId().equals(s.getPackageId()))
                                .distinct()
                                .collect(Collectors.toList());
                        p.setServiceBeans(serviceBeans);
                    }
                }
            });
        }


        return packageBeans;
    }

    @Override
    public List<PackageBean> findAllByUserId(UserBean user, List<Long> studentIds) {
        return packageRepository.findAllByStudentIds(user, studentIds).stream().map(this::toBean).collect(Collectors.toList());
    }

    /**
     * Helper method to remove packages that user already has from suggested packages
     * @param suggestedPackages List of suggested packages
     * @param userPackages List of packages user already has
     * @return List of filtered packages
     */
    private List<PackageBean> removeExistingUserPackages(List<PackageBean> suggestedPackages, List<PackageBean> userPackages) {
        if (userPackages == null || userPackages.isEmpty()) {
            return suggestedPackages;
        }

        Set<Integer> userPackageIds = userPackages.stream()
                .map(PackageBean::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return suggestedPackages.stream()
                .filter(pkg -> !userPackageIds.contains(pkg.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PackageBean> getSuggestedPackages(UserBean user) {
        Student student = studentRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found for user ID: " + user.getUserId()));

        // Get user's existing packages
        List<PackageBean> userPackageBeans = packageRepository.findAllByUserId(user.getUserId(), user)
                .stream()
                .map(this::toBean)
                .collect(Collectors.toList());

        // Get suggested packages based on student criteria
        List<PackageBean> suggestedPackages = packageRepository.findSuggestedPackages(
                student.getCurrentClassId(),
                student.getCurrentSubjectGroupId(),
                student.getTargetFinalExamYear())
                .stream()
                .map(this::toBean)
                .collect(Collectors.toList());

        // Remove packages user already has
        suggestedPackages = removeExistingUserPackages(suggestedPackages, userPackageBeans);

        // Enrich with package relationships if any suggestions remain
        if (!suggestedPackages.isEmpty()) {
            List<Integer> packageIds = suggestedPackages.stream()
                    .map(PackageBean::getId)
                    .collect(Collectors.toList());

            // Get all service beans for these packages
            List<ServiceBean> serviceBeansFull = serviceService.findAllByPackageIds(packageIds);

            // Attach service beans to each package
            suggestedPackages.forEach(pkg -> {
                if (pkg.getId() != null && serviceBeansFull != null && !serviceBeansFull.isEmpty()) {
                    List<ServiceBean> serviceBeans = serviceBeansFull.stream()
                            .filter(s -> Objects.nonNull(s) &&
                                    Objects.nonNull(s.getPackageId()) &&
                                    pkg.getId().equals(s.getPackageId()))
                            .collect(Collectors.toList());
                }
            });
        }

        return suggestedPackages;
    }

    @Override
    public List<PackageBean> getAllPackages(UserBean user, String type, String targetYear) {
        List<PackageModel> packages = packageRepository.findAll(user, type, targetYear);

        // Filter by type if provided
        if (type != null && !type.trim().isEmpty()) {
            packages = packages.stream()
                    .filter(pkg -> type.equalsIgnoreCase(pkg.getPackageType()))
                    .collect(Collectors.toList());
        }

        // Filter by targetYear if provided
        if (targetYear != null && !targetYear.trim().isEmpty()) {
            packages = packages.stream()
                    .filter(pkg -> targetYear.equals(pkg.getTargetYear()))
                    .collect(Collectors.toList());
        }
        return getPackageDetails(packages);
    }
}
