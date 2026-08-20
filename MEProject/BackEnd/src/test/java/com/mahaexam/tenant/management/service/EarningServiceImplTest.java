package com.mahaexam.tenant.management.service;

import com.mahaexam.common.model.CommissionConfigRequest;
import com.mahaexam.common.model.CommissionSlab;
import com.mahaexam.packagemanagment.bean.PackageCategoryBean;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.packagemanagment.service.PackageCategoryService;
import com.mahaexam.tenant.management.bean.EarningSummaryBean;
import com.mahaexam.tenant.management.bean.StudentDetailsBean;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.UserHierarchyLevel;
import com.mahaexam.tenant.management.repository.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mahaexam.common.service.CommissionConfigService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EarningServiceImplTest {

    @Mock
    private StudentService studentService;

    @Mock
    private CommissionConfigService commissionConfigService;

    @Mock
    private RoleService roleService;

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Mock
    private UserHierarchyLevelService userHierarchyLevelService;

    @Mock
    private ApplicationUserService applicationUserService;

    @Mock
    private UserRoleHierarchyLevelMappingService userroleHierarchyLevelMappingService;

    @Mock
    private UserService userService;

    @Mock
    private PackageCategoryService packageCategoryService;

    @InjectMocks
    private EarningServiceImpl earningService;

    private final Long channelPartnerId = 100L;
    private final Long tenantId = 1L;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testZeroReferralsReturnsEmptySummary() {
        when(studentService.getAllStudentsByChannelPartnerId(channelPartnerId)).thenReturn(List.of());

        // Mock the UserHierarchyLevel lookup
        UserHierarchyLevel hierarchyLevel = UserHierarchyLevel.builder()
                .id(1)
                .levelName("Channel Partner")
                .levelOrder(1)
                .tenantId(tenantId)
                .build();
        when(userHierarchyLevelService.findByByGivenLevelOrderIdAndTenantId(tenantId, 1))
                .thenReturn(hierarchyLevel);
        
        when(commissionConfigService.list(ArgumentMatchers.anyInt(), ArgumentMatchers.any(), ArgumentMatchers.anyBoolean()))
                .thenReturn(List.of());

        // Mock ApplicationUser for additionalCommissionPercent lookup
        ApplicationUser mockUser = new ApplicationUser();
        mockUser.setUserId(channelPartnerId);
        mockUser.setAdditionalCommissionPercent(BigDecimal.ZERO);
        when(applicationUserRepository.findByUserId(channelPartnerId)).thenReturn(Optional.of(mockUser));

        EarningSummaryBean summary = earningService.computeEarningSummary(channelPartnerId, tenantId);

        assertNotNull(summary);
        assertEquals(0, summary.getTotalReferrals());
        assertEquals(0, summary.getTotalEarning().intValue());
        assertTrue(summary.getBreakdown().isEmpty());
    }

    @Test
    public void testNoConfigForPackageType() {
        // one referred student with one package
        StudentDetailsBean student = new StudentDetailsBean();
        student.setUserId(200L);
        student.setStudentId(200L);
        when(studentService.getAllStudentsByChannelPartnerId(channelPartnerId)).thenReturn(List.of(student));

        PackageModel pkg = new PackageModel();
        pkg.setPackageType("Prepare");
        pkg.setAmount(new BigDecimal("100"));
        pkg.setPackageCategoryId(1);
        when(studentService.getStudentPackages(200L)).thenReturn(List.of(pkg));

        // No role/config found
        UserHierarchyLevel hierarchyLevel = UserHierarchyLevel.builder()
                .id(1)
                .levelName("Channel Partner")
                .levelOrder(1)
                .tenantId(tenantId)
                .build();
        when(userHierarchyLevelService.findByByGivenLevelOrderIdAndTenantId(tenantId, 1))
                .thenReturn(hierarchyLevel);
        
        when(commissionConfigService.list(ArgumentMatchers.anyInt(), ArgumentMatchers.any(), ArgumentMatchers.anyBoolean()))
                .thenReturn(List.of());

        // Mock PackageCategoryService
        PackageCategoryBean categoryBean = PackageCategoryBean.builder().id(1).name("Prepare").build();
        when(packageCategoryService.getPackageCategoryById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(categoryBean));

        // Mock ApplicationUser for additionalCommissionPercent lookup
        ApplicationUser mockUser = new ApplicationUser();
        mockUser.setUserId(channelPartnerId);
        mockUser.setAdditionalCommissionPercent(BigDecimal.ZERO);
        when(applicationUserRepository.findByUserId(channelPartnerId)).thenReturn(Optional.of(mockUser));

        EarningSummaryBean summary = earningService.computeEarningSummary(channelPartnerId, tenantId);
        assertNotNull(summary);
        assertEquals(1, summary.getTotalReferrals());
        assertEquals(1, summary.getBreakdown().size());
        assertEquals(BigDecimal.ZERO, summary.getBreakdown().get(0).getCommissionAmount());
    }

    @Test
    public void testPercentageCommissionApplied() {
        StudentDetailsBean s1 = new StudentDetailsBean();
        s1.setUserId(201L);
        s1.setStudentId(201L);
        when(studentService.getAllStudentsByChannelPartnerId(channelPartnerId)).thenReturn(List.of(s1));

        PackageModel pk = new PackageModel();
        pk.setPackageType("Prepare");
        pk.setAmount(new BigDecimal("200.00"));
        pk.setPackageCategoryId(1);
        when(studentService.getStudentPackages(201L)).thenReturn(List.of(pk));

        // Mock the UserHierarchyLevel lookup
        UserHierarchyLevel hierarchyLevel = UserHierarchyLevel.builder()
                .id(5)
                .levelName("Channel Partner")
                .levelOrder(1)
                .tenantId(tenantId)
                .build();
        when(userHierarchyLevelService.findByByGivenLevelOrderIdAndTenantId(tenantId, 1))
                .thenReturn(hierarchyLevel);

        CommissionSlab slab = new CommissionSlab();
        slab.setFromStudentCount(1);
        slab.setToStudentCount(10);
        slab.setPercentage(new BigDecimal("10"));

        CommissionConfigRequest cfg = CommissionConfigRequest.builder()
                .hierarchyLevelId(5L)
                .packageType("Prepare")
                .packageCategoryId(1)
                .commissionType("PERCENTAGE")
                .slabs(List.of(slab))
                .build();

        when(commissionConfigService.list(ArgumentMatchers.eq(5), ArgumentMatchers.any(), ArgumentMatchers.eq(Boolean.TRUE)))
                .thenReturn(List.of(cfg));

        // Mock PackageCategoryService
        PackageCategoryBean categoryBean = PackageCategoryBean.builder().id(1).name("Prepare").build();
        when(packageCategoryService.getPackageCategoryById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(categoryBean));

        // Mock ApplicationUser for additionalCommissionPercent lookup
        ApplicationUser mockUser = new ApplicationUser();
        mockUser.setUserId(channelPartnerId);
        mockUser.setAdditionalCommissionPercent(BigDecimal.ZERO);
        when(applicationUserRepository.findByUserId(channelPartnerId)).thenReturn(Optional.of(mockUser));

        EarningSummaryBean summary = earningService.computeEarningSummary(channelPartnerId, tenantId);
        assertNotNull(summary);
        assertEquals(1, summary.getTotalReferrals());
        // commission = 200 * 10% = 20
        assertEquals(0, new BigDecimal("20.00").compareTo(summary.getTotalEarning()));
        assertEquals(1, summary.getBreakdown().size());
        assertEquals(0, new BigDecimal("20.00").compareTo(summary.getBreakdown().get(0).getCommissionAmount()));
    }

    @Test
    public void testFixedCommissionAppliedForMultipleReferrals() {
        StudentDetailsBean s1 = new StudentDetailsBean();
        s1.setUserId(301L);
        s1.setStudentId(301L);
        StudentDetailsBean s2 = new StudentDetailsBean();
        s2.setUserId(302L);
        s2.setStudentId(302L);
        when(studentService.getAllStudentsByChannelPartnerId(channelPartnerId)).thenReturn(List.of(s1, s2));

        PackageModel p1 = new PackageModel();
        p1.setPackageType("Practice");
        p1.setAmount(new BigDecimal("100"));
        p1.setPackageCategoryId(2);
        when(studentService.getStudentPackages(301L)).thenReturn(List.of(p1));

        PackageModel p2 = new PackageModel();
        p2.setPackageType("Practice");
        p2.setAmount(new BigDecimal("150"));
        p2.setPackageCategoryId(2);
        when(studentService.getStudentPackages(302L)).thenReturn(List.of(p2));

        // Mock the UserHierarchyLevel lookup
        UserHierarchyLevel hierarchyLevel = UserHierarchyLevel.builder()
                .id(6)
                .levelName("Channel Partner")
                .levelOrder(1)
                .tenantId(tenantId)
                .build();
        when(userHierarchyLevelService.findByByGivenLevelOrderIdAndTenantId(tenantId, 1))
                .thenReturn(hierarchyLevel);

        CommissionSlab slab = new CommissionSlab();
        slab.setFromStudentCount(1);
        slab.setToStudentCount(5);
        slab.setAmount(new BigDecimal("50"));

        CommissionConfigRequest cfg = CommissionConfigRequest.builder()
                .hierarchyLevelId(6L)
                .packageType("Practice")
                .packageCategoryId(2)
                .commissionType("FIXED")
                .slabs(List.of(slab))
                .build();

        when(commissionConfigService.list(ArgumentMatchers.eq(6), ArgumentMatchers.any(), ArgumentMatchers.eq(Boolean.TRUE)))
                .thenReturn(List.of(cfg));

        // Mock PackageCategoryService
        PackageCategoryBean categoryBean = PackageCategoryBean.builder().id(2).name("Practice").build();
        when(packageCategoryService.getPackageCategoryById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(categoryBean));

        // Mock ApplicationUser for additionalCommissionPercent lookup
        ApplicationUser mockUser = new ApplicationUser();
        mockUser.setUserId(channelPartnerId);
        mockUser.setAdditionalCommissionPercent(BigDecimal.ZERO);
        when(applicationUserRepository.findByUserId(channelPartnerId)).thenReturn(Optional.of(mockUser));

        EarningSummaryBean summary = earningService.computeEarningSummary(channelPartnerId, tenantId);
        assertNotNull(summary);
        assertEquals(2, summary.getTotalReferrals());
        // commission = 50 * 2 = 100
        assertEquals(0, new BigDecimal("100").compareTo(summary.getTotalEarning()));
        assertEquals(1, summary.getBreakdown().size());
        assertEquals(0, new BigDecimal("100").compareTo(summary.getBreakdown().get(0).getCommissionAmount()));
    }
}