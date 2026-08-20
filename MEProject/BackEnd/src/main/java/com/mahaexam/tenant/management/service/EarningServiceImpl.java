package com.mahaexam.tenant.management.service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.model.CommissionConfigRequest;
import com.mahaexam.common.model.CommissionSlab;
import com.mahaexam.common.service.CommissionConfigService;
import com.mahaexam.packagemanagment.bean.PackageCategoryBean;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.packagemanagment.service.PackageCategoryService;
import com.mahaexam.tenant.management.bean.*;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.Role;
import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import com.mahaexam.tenant.management.repository.ApplicationUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class EarningServiceImpl implements EarningService {
    private static final Logger logger = LoggerFactory.getLogger(EarningServiceImpl.class);

    private final StudentService studentService;
    private final CommissionConfigService commissionConfigService;
    private final RoleService roleService;
    private final ApplicationUserRepository applicationUserRepository;
    private final UserHierarchyLevelService userHierarchyLevelService;
    private final ApplicationUserService applicationUserService;
    private final UserRoleHierarchyLevelMappingService userroleHierarchyLevelMappingService;
    private final PackageCategoryService packageCategoryService;
   
    private final UserService userService;

    public EarningServiceImpl(StudentService studentService,
                              CommissionConfigService commissionConfigService,
                              RoleService roleService,
                              ApplicationUserRepository applicationUserRepository,
                              UserHierarchyLevelService userHierarchyLevelService,
                              ApplicationUserService applicationUserService,
                              UserRoleHierarchyLevelMappingService userroleHierarchyLevelMappingService,UserService userService, PackageCategoryService packageCategoryService) {
        this.studentService = studentService;
        this.commissionConfigService = commissionConfigService;
        this.roleService = roleService;
        this.applicationUserRepository = applicationUserRepository;
        this.userHierarchyLevelService = userHierarchyLevelService;
        this.applicationUserService = applicationUserService;
        this.userroleHierarchyLevelMappingService = userroleHierarchyLevelMappingService;
        this.userService = userService;
        this.packageCategoryService = packageCategoryService;
    }

    @Override
    public EarningSummaryBean computeEarningSummary(Long userId, Long tenantId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Channel Partner ID must be greater than 0");
        }
        // Step 1: Fetch all students referred by this channel partner and count total referrals.
        logger.info("Computing earning summary for channel partner ID: {}", userId);
        List<StudentDetailsBean> students = studentService.getAllStudentsByChannelPartnerId(userId);
        int totalReferrals = (students == null) ? 0 : students.size();
        // Step 2: Fetch commission config for channel partner hierarchy level and map by package type.
//        Optional<Role> roleOpt = roleService.findByName(AppConstants.ROLE_CHANNEL_PARTNER);
//        if (roleOpt.isEmpty()) {
//            logger.warn("Role not found: {}", AppConstants.ROLE_CHANNEL_PARTNER);
//            throw new IllegalStateException("Role not found: " + AppConstants.ROLE_CHANNEL_PARTNER);
//        }
//        Role role = roleOpt.get();
//        long roleId = role.getRoleId();
        Integer hirarchyLevelorderId = 1; // Assuming channel partner is always at level order 1, this can be dynamic based on your hierarchy design
        UserHierarchyLevel userhierarchylevel =  userHierarchyLevelService.findByByGivenLevelOrderIdAndTenantId(tenantId,hirarchyLevelorderId);
//        UserRoleHierarchyLevelMapping userRoleHierarchyLevelMappingobj = userroleHierarchyLevelMappingService.findUserRoleHierarchyForGivenRoleId(role.getRoleId());
        if (userhierarchylevel == null) {
            logger.warn("Hierarchy level id is null for given hierarchy level order id: {} and tenantId: {}", hirarchyLevelorderId, tenantId);
			 throw new IllegalStateException("Hierarchy level not found for order id: " + hirarchyLevelorderId);
           
        }

        //get all active commission configs for this hierarchy level and map by package type for easy lookup later.
        List<CommissionConfigRequest> configs = commissionConfigService.list(userhierarchylevel.getId(), null, Boolean.TRUE);
        Map<Integer, CommissionConfigRequest> configByPackageCategory = new HashMap<>();
        if (configs != null) {
            for (CommissionConfigRequest cfg : configs) {
                if (cfg.getPackageCategoryId() != null && cfg.getPackageCategoryId() > 0) {
                	configByPackageCategory.put(cfg.getPackageCategoryId(), cfg);
                }
            }
        }

        // Step 3: For each student, fetch their packages and group by package type and student.
        Map<Integer, Map<Long, List<PackageModel>>> packagesByCategoryAndStudent = new HashMap<>();
        if (students != null) {
            for (StudentDetailsBean s : students) {
                try {
                    Long student_id = s.getStudentId();
                    if (student_id == null) continue;
                    List<PackageModel> pkgs = studentService.getStudentPackages(student_id);
                    if (pkgs == null || pkgs.isEmpty()) continue;
                    for (PackageModel pkg : pkgs) {
                        Integer pTypeCategoryId = pkg.getPackageCategoryId();
                        if (pTypeCategoryId == null) pTypeCategoryId = 0; // treat null category as 0 or UNKNOWN
                        packagesByCategoryAndStudent
                                .computeIfAbsent(pTypeCategoryId, k -> new HashMap<>())
                                .computeIfAbsent(student_id, k -> new ArrayList<>())
                                .add(pkg);
                    }
                } catch (Exception ex) {
                    logger.error("Error fetching packages for student user {}: {}", s.getUserId(), ex.getMessage());
                }
            }
        }

        // Step 4: For each package type, calculate total package amount, determine applicable commission slab, and calculate commission.
        List<EarningBreakdownBean> breakdown = new ArrayList<>();
        BigDecimal totalEarning = BigDecimal.ZERO;
        
        ApplicationUser userObj = applicationUserRepository.findByUserId(userId).orElse(null);
        BigDecimal additionalcomissionpercent =  userObj.getAdditionalCommissionPercent();
        BigDecimal additionalCommissionAmount = BigDecimal.ZERO;
        for (Map.Entry<Integer, Map<Long, List<PackageModel>>> entry : packagesByCategoryAndStudent.entrySet()) {
            Integer packageCategoryId = entry.getKey();
            Map<Long, List<PackageModel>> byStudent = entry.getValue();
            int referralsCount = byStudent.size();

            BigDecimal totalPackageAmount = byStudent.values().stream()
                    .flatMap(Collection::stream)
                    .map(p -> p.getAmount() == null ? BigDecimal.ZERO : p.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            CommissionConfigRequest cfg = configByPackageCategory.get(packageCategoryId);
            Optional<PackageCategoryBean> packageCategoryobj  = packageCategoryService.getPackageCategoryById(hirarchyLevelorderId);
            EarningBreakdownBean eb = new EarningBreakdownBean();
            eb.setPackageType(packageCategoryobj.get().getName());
            eb.setReferrals(referralsCount);
            eb.setTotalPackageAmount(totalPackageAmount);

            if (cfg == null) {
                eb.setCommissionAmount(BigDecimal.ZERO);
                eb.setAppliedSlab("No commission config for package Category=" + packageCategoryobj.get().getName());
                breakdown.add(eb);
                continue;
            }

            CommissionSlab applied = null;
            if (cfg.getSlabs() != null) {
                for (CommissionSlab slab : cfg.getSlabs()) {
                    int from = slab.getFromStudentCount() == null ? Integer.MIN_VALUE : slab.getFromStudentCount();
                    int to = slab.getToStudentCount() == null ? Integer.MAX_VALUE : slab.getToStudentCount();
                    if (referralsCount >= from && referralsCount <= to) {
                        applied = slab;
                        break;
                    }
                }
            }

            if (applied == null) {
                eb.setCommissionAmount(BigDecimal.ZERO);
                eb.setAppliedSlab("No slab matched for count=" + referralsCount);
                breakdown.add(eb);
                continue;
            }

            BigDecimal commissionAmount = BigDecimal.ZERO;
            BigDecimal commissionTypeValue = BigDecimal.ZERO;
            if ("PERCENTAGE".equalsIgnoreCase(cfg.getCommissionType()) && applied.getPercentage() != null) {
                commissionAmount = totalPackageAmount.multiply(applied.getPercentage()).divide(new BigDecimal("100"));
                commissionTypeValue = applied.getPercentage();
            } else if ("FIXED".equalsIgnoreCase(cfg.getCommissionType()) && applied.getAmount() != null) {
                commissionAmount = applied.getAmount().multiply(new BigDecimal(referralsCount));
                commissionTypeValue = applied.getAmount();
            }
      	   if(additionalcomissionpercent != null && additionalcomissionpercent.compareTo(BigDecimal.ZERO) > 0) {
				additionalCommissionAmount = totalPackageAmount.multiply(additionalcomissionpercent).divide(new BigDecimal("100"));
				commissionAmount = commissionAmount.add(additionalCommissionAmount);
			}
            eb.setCommissionAmount(commissionAmount);
            eb.setAppliedSlab(String.format("from=%d,to=%d,commissionType=%s,rate=%f, additionalCommissionPercent=%f, additionalCommissionAmount=%f", 
                    applied.getFromStudentCount(), applied.getToStudentCount(), cfg.getCommissionType(), commissionTypeValue == null ? BigDecimal.ZERO : commissionTypeValue, additionalcomissionpercent == null ? BigDecimal.ZERO : additionalcomissionpercent, additionalCommissionAmount));

            totalEarning = totalEarning.add(commissionAmount == null ? BigDecimal.ZERO : commissionAmount);
            breakdown.add(eb);
        }

        EarningSummaryBean summary = new EarningSummaryBean();
        summary.setChannelPartnerId(userId);
        summary.setTotalReferrals(totalReferrals);
        summary.setTotalEarning(totalEarning);
        summary.setBreakdown(breakdown);

        return summary;
    }

    @Override
    public IndirectIncomeEarningBean computeIndirectEarning(Long userId, Long tenantId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }

        logger.info("Computing indirect earning for user ID: {}", userId);

        // Fetch user details
        Optional<ApplicationUser> userOpt = applicationUserRepository.findByUserId(userId);
        if (userOpt.isEmpty()) {
            logger.warn("User not found with ID: {}", userId);
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        ApplicationUser user = userOpt.get();
        
        // Step 1: Get direct earning for this user
        EarningSummaryBean directEarning = computeEarningSummary(userId,tenantId);
        ApplicationUser userObj = applicationUserRepository.findByUserId(userId).orElse(null);
        
        BigDecimal additionalcomissionpercent =  userObj.getAdditionalCommissionPercent();
       
        // Step 2: Fetch all downline levels
        List<ApplicationUser> downline1 = getAllDownlineLevel1(userId);
        List<ApplicationUser> downline2 = getAllDownlineLevel2(downline1);
        List<ApplicationUser> downline3 = getAllDownlineLevel3(downline2);
        List<ApplicationUser> downline4 = getAllDownlineLevel4(downline3);

        // Step 3: Process earnings for each downline level
        List<DownLineLevelEarningBean> downlineLevels = new ArrayList<>();
        
        BigDecimal totalIndirectEarning = BigDecimal.ZERO;
        BigDecimal totaladditionalCommissionAmount = BigDecimal.ZERO;

        // Process Level 1
        DownLineLevelEarningBean level1 = processDownlineLevel(1, downline1, additionalcomissionpercent);
        if (level1 != null) {
            downlineLevels.add(level1);
            totalIndirectEarning = totalIndirectEarning.add(level1.getTotalEarningForLevel());
            totaladditionalCommissionAmount = totaladditionalCommissionAmount.add(level1.getUserEarnings().stream()
					.map(IndirectEarningDetailBean::getTotalAdditionalCommissionAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        // Process Level 2
        DownLineLevelEarningBean level2 = processDownlineLevel(2, downline2, additionalcomissionpercent);
        if (level2 != null) {
            downlineLevels.add(level2);
            totalIndirectEarning = totalIndirectEarning.add(level2.getTotalEarningForLevel());
            totaladditionalCommissionAmount = totaladditionalCommissionAmount.add(level2.getUserEarnings().stream().map(IndirectEarningDetailBean::getTotalAdditionalCommissionAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        // Process Level 3
        DownLineLevelEarningBean level3 = processDownlineLevel(3, downline3, additionalcomissionpercent);
        if (level3 != null) {
            downlineLevels.add(level3);
            totalIndirectEarning = totalIndirectEarning.add(level3.getTotalEarningForLevel());
        }

        // Process Level 4
        DownLineLevelEarningBean level4 = processDownlineLevel(4, downline4, additionalcomissionpercent);
        if (level4 != null) {
            downlineLevels.add(level4);
            totalIndirectEarning = totalIndirectEarning.add(level4.getTotalEarningForLevel());
        }

        // Step 4: Build the result bean
        
        BigDecimal directTotal = directEarning.getTotalEarning() != null ? directEarning.getTotalEarning() : BigDecimal.ZERO;
        BigDecimal totalEarning = directTotal.add(totalIndirectEarning);

        IndirectIncomeEarningBean result = IndirectIncomeEarningBean.builder()
                .userId(userId)
                .userName(user.getUserName())
                .userFullName(user.getName())
                .directEarning(directTotal)
                .indirectEarning(totalIndirectEarning)
                .totalEarning(totalEarning)
                .downlineLevels(downlineLevels)
                .directEarningSummary(directEarning)
                .build();

        logger.info("Indirect earning computed successfully for user ID: {}", userId);
        return result;
    }

    /**
     * Fetch all downline users for Level 1 (direct children)
     */
    private List<ApplicationUser> getAllDownlineLevel1(Long userId) {
        logger.debug("Fetching downline level 1 for user ID: {}", userId);
        return applicationUserRepository.findByUserParentId(userId);
    }

    /**
     * Fetch all downline users for Level 2 (children of level 1)
     */
    private List<ApplicationUser> getAllDownlineLevel2(List<ApplicationUser> downline1) {
        List<ApplicationUser> downline2 = new ArrayList<>();
        if (downline1 == null || downline1.isEmpty()) {
            return downline2;
        }

        for (ApplicationUser user : downline1) {
            List<ApplicationUser> children = applicationUserRepository.findByUserParentId(user.getUserId());
            if (children != null && !children.isEmpty()) {
                downline2.addAll(children);
            }
        }

        logger.debug("Fetched {} users for downline level 2", downline2.size());
        return downline2;
    }

    /**
     * Fetch all downline users for Level 3 (children of level 2)
     */
    private List<ApplicationUser> getAllDownlineLevel3(List<ApplicationUser> downline2) {
        List<ApplicationUser> downline3 = new ArrayList<>();
        if (downline2 == null || downline2.isEmpty()) {
            return downline3;
        }

        for (ApplicationUser user : downline2) {
            List<ApplicationUser> children = applicationUserRepository.findByUserParentId(user.getUserId());
            if (children != null && !children.isEmpty()) {
                downline3.addAll(children);
            }
        }

        logger.debug("Fetched {} users for downline level 3", downline3.size());
        return downline3;
    }

    /**
     * Fetch all downline users for Level 4 (children of level 3)
     */
    private List<ApplicationUser> getAllDownlineLevel4(List<ApplicationUser> downline3) {
        List<ApplicationUser> downline4 = new ArrayList<>();
        if (downline3 == null || downline3.isEmpty()) {
            return downline4;
        }

        for (ApplicationUser user : downline3) {
            List<ApplicationUser> children = applicationUserRepository.findByUserParentId(user.getUserId());
            if (children != null && !children.isEmpty()) {
                downline4.addAll(children);
            }
        }

        logger.debug("Fetched {} users for downline level 4", downline4.size());
        return downline4;
    }

    /**
     * Process earnings for a specific downline level
     */
    private DownLineLevelEarningBean processDownlineLevel(Integer levelNumber, List<ApplicationUser> downlineUsers, BigDecimal additionalcomissionpercent) {
        if (downlineUsers == null || downlineUsers.isEmpty()) {
            return null;
        }

        logger.debug("Processing earnings for downline level {}", levelNumber);

        List<IndirectEarningDetailBean> userEarnings = new ArrayList<>();
        BigDecimal totalEarningForLevel = BigDecimal.ZERO;
        BigDecimal totalAdditionalCommissionAmountForLevel = BigDecimal.ZERO;
        BigDecimal totalPackageAmountForAllPackagesForLevel = BigDecimal.ZERO;
        int totalReferralsAcrossLevel = 0;

        for (ApplicationUser user : downlineUsers) {
            try {
                // Get students referred by this downline user
                List<StudentDetailsBean> students = studentService.getAllStudentsByChannelPartnerId(user.getUserId());
                int referralsCount = (students == null) ? 0 : students.size();
                totalReferralsAcrossLevel += referralsCount;

                // Fetch commission configs for this user's hierarchy level
                Map<Integer, CommissionConfigRequest> configByPackageCategory = getCommissionConfigsByPackageCategory(user.getUserId());

                // Process packages for all students
                EarningBreakdownBean breakdown = processUserPackagesAndCommission(students, configByPackageCategory, additionalcomissionpercent);

                // Create earning detail bean for this user
                List<EarningBreakdownBean> breakdownList = new ArrayList<>();
                breakdownList.add(breakdown);

                IndirectEarningDetailBean userEarning = IndirectEarningDetailBean.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName() != null ? user.getUserName() : "N/A")
                        .userFullName(user.getName())
                        .totalReferrals(referralsCount)
                        .totalEarning(breakdown.getCommissionAmount())
                        .totalAdditionalCommissionAmount(breakdown.getTotalAdditionalCommissionAmount())
                        .totalPackageAmountForAllPackages(breakdown.getTotalPackageAmountForAllPackages())
                        .breakdown(breakdownList)
                        .build();

                userEarnings.add(userEarning);
                totalEarningForLevel = totalEarningForLevel.add(breakdown.getCommissionAmount());
                totalAdditionalCommissionAmountForLevel = totalAdditionalCommissionAmountForLevel.add(breakdown.getTotalAdditionalCommissionAmount());
                totalPackageAmountForAllPackagesForLevel = totalPackageAmountForAllPackagesForLevel.add(breakdown.getTotalPackageAmountForAllPackages());
            } catch (Exception ex) {
                logger.error("Error processing earnings for user ID {} at level {}: {}", user.getUserId(), levelNumber, ex.getMessage());
            }
        }

        DownLineLevelEarningBean levelEarning = DownLineLevelEarningBean.builder()
                .levelNumber(levelNumber)
                .levelName("Level " + levelNumber)
                .totalDownlineUsers(downlineUsers.size())
                .totalReferralsAcrossLevel(totalReferralsAcrossLevel)
                .totalEarningForLevel(totalEarningForLevel)
                .userEarnings(userEarnings)
                .totalAdditionalCommissionAmount(totalAdditionalCommissionAmountForLevel)
                .totalPackageAmountForAllPackages(totalPackageAmountForAllPackagesForLevel)
                .build();

        logger.debug("Level {} earnings: totalUsers={}, totalReferrals={}, totalEarning={}", 
                levelNumber, downlineUsers.size(), totalReferralsAcrossLevel, totalEarningForLevel);
        
        return levelEarning;
    }

    /**
     * Get commission configs mapped by package type for a given user
     */
    private Map<Integer, CommissionConfigRequest> getCommissionConfigsByPackageCategory(Long userId) {
        Map<Integer, CommissionConfigRequest> configByPackageCategory = new HashMap<>();

        try {
            // Fetch user's tenant ID
            Optional<ApplicationUser> userOpt = applicationUserRepository.findByUserId(userId);
            if (userOpt.isEmpty()) {
                return configByPackageCategory;
            }

            ApplicationUser user = userOpt.get();
            Optional<UserBean> userBean =  userService.findById(userId, false); // fetch tenant ID from user service which might have caching
            Long tenantId = userBean.map(UserBean::getTenantId).orElse(null);

            // Get tenant ID from users table (need to query separately or via service)
            // For now, assuming we can get it from applicationUser service
            List<UserHierarchyLevel> hierarchyLevels = userHierarchyLevelService.findByTenantId(tenantId != null ? tenantId : 1L);

            if (hierarchyLevels != null && !hierarchyLevels.isEmpty()) {
                for (UserHierarchyLevel level : hierarchyLevels) {
                    List<CommissionConfigRequest> configs = commissionConfigService.list(level.getId(), null, Boolean.TRUE);
                    if (configs != null) {
                        for (CommissionConfigRequest cfg : configs) {
                            if (cfg.getPackageCategoryId() != null) {
                            	configByPackageCategory.put(cfg.getPackageCategoryId(), cfg);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error fetching commission configs for user ID {}: {}", userId, ex.getMessage());
        }

        return configByPackageCategory;
    }

    /**
     * Process user's packages and calculate commission for all package types
     */
    private EarningBreakdownBean processUserPackagesAndCommission(List<StudentDetailsBean> students, 
                                                                   Map<Integer, CommissionConfigRequest> configByPackageCategory,  BigDecimal additionalCommissionPercent) {
        // Step 1: Group packages by Category and student
        Map<Integer, Map<Long, List<PackageModel>>> packagesByCategoryAndStudent = new HashMap<>();
        if (students != null) {
            for (StudentDetailsBean s : students) {
                try {
                    Long student_id = s.getStudentId();
                    if (student_id == null) continue;
                    
                    List<PackageModel> pkgs = studentService.getStudentPackages(student_id);
                    if (pkgs == null || pkgs.isEmpty()) continue;
                    
                    for (PackageModel pkg : pkgs) {
                        Integer pCategoryId = pkg.getPackageCategoryId();
                        if (pCategoryId == null) pCategoryId = 0;
                        packagesByCategoryAndStudent
                                .computeIfAbsent(pCategoryId, k -> new HashMap<>())
                                .computeIfAbsent(student_id, k -> new ArrayList<>())
                                .add(pkg);
                    }
                } catch (Exception ex) {
                    logger.error("Error fetching packages for student: {}", ex.getMessage());
                }
            }
        }

        // Step 2: Calculate total commission across all package types
        BigDecimal totalCommission = BigDecimal.ZERO;
        BigDecimal	additionalCommissionAmount = BigDecimal.ZERO;
        BigDecimal totalPackageAmountForAllPackages = BigDecimal.ZERO;

        for (Map.Entry<Integer, Map<Long, List<PackageModel>>> entry : packagesByCategoryAndStudent.entrySet()) {
            Integer packageCategoryId = entry.getKey();
            Map<Long, List<PackageModel>> byStudent = entry.getValue();
            int referralsCount = byStudent.size();

            BigDecimal totalPackageAmount = byStudent.values().stream()
                    .flatMap(Collection::stream)
                    .map(p -> p.getAmount() == null ? BigDecimal.ZERO : p.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            CommissionConfigRequest cfg = configByPackageCategory.get(packageCategoryId);

            if (cfg == null || cfg.getSlabs() == null || cfg.getSlabs().isEmpty()) {
                continue;
            }

            CommissionSlab applied = null;
            for (CommissionSlab slab : cfg.getSlabs()) {
                int from = slab.getFromStudentCount() == null ? Integer.MIN_VALUE : slab.getFromStudentCount();
                int to = slab.getToStudentCount() == null ? Integer.MAX_VALUE : slab.getToStudentCount();
                if (referralsCount >= from && referralsCount <= to) {
                    applied = slab;
                    break;
                }
            }
            
            

            if (applied != null) {
                BigDecimal commissionAmount = BigDecimal.ZERO;
                
                if ("PERCENTAGE".equalsIgnoreCase(cfg.getCommissionType()) && applied.getPercentage() != null) {
                    commissionAmount = totalPackageAmount.multiply(applied.getPercentage()).divide(new BigDecimal("100"));
                } else if ("FIXED".equalsIgnoreCase(cfg.getCommissionType()) && applied.getAmount() != null) {
                    commissionAmount = applied.getAmount().multiply(new BigDecimal(referralsCount));
                }
                
                if(additionalCommissionPercent != null && additionalCommissionPercent.compareTo(BigDecimal.ZERO) > 0) {
                additionalCommissionAmount = totalPackageAmount.multiply(additionalCommissionPercent).divide(new BigDecimal("100"));				
                	
                	commissionAmount = commissionAmount.add(additionalCommissionAmount);
                }
                
                totalCommission = totalCommission.add(commissionAmount);
                additionalCommissionAmount = additionalCommissionAmount.add(additionalCommissionAmount);
                totalPackageAmountForAllPackages = totalPackageAmountForAllPackages.add(totalPackageAmount);
            }
        }

        EarningBreakdownBean breakdown = new EarningBreakdownBean();
        breakdown.setPackageType("CONSOLIDATED");
        breakdown.setReferrals(students != null ? students.size() : 0);
        breakdown.setTotalPackageAmount(BigDecimal.ZERO);
        breakdown.setCommissionAmount(totalCommission);
        breakdown.setTotalAdditionalCommissionAmount(additionalCommissionAmount);
        breakdown.setTotalPackageAmountForAllPackages(totalPackageAmountForAllPackages);
        breakdown.setAppliedSlab("Multi-package commission calculation");

        return breakdown;
    }
}