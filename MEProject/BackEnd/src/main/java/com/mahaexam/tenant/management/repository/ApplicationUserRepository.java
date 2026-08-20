package com.mahaexam.tenant.management.repository;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.tenant.management.model.ApplicationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserRepository {
    ApplicationUser save(ApplicationUser user);

    Optional<ApplicationUser> findById(Long id);

    List<ApplicationUser> findAll();

    ApplicationUser update(ApplicationUser user);

    void deleteByUserId(Long id);

    Optional<ApplicationUser> findByUserId(Long userId);

    Optional<ApplicationUser> findByEmailId(String emailId);

    Optional<ApplicationUser> findByMobileNo(String mobileNo);

    List<ApplicationUser> findByFirstOrLastName(String name, Long tenantId, List<String> profileTypes);

    Optional<ApplicationUser> findByEmailIdAndMobileNo(String emailId, String mobileNo);

    ApplicationUser updateByUserId(ApplicationUser user);

    PaginatedResponse<ApplicationUser> findByUserType(Long tenantId, String userType, Boolean isDeleted, Pageable pageable);

    Integer countByUserType(Long tenantId,String userType);
    PaginatedResponse<ApplicationUser> findAllUsersForGivenTenantId(Long tenantId, Long user_id, Pageable pageable);
    void updateUserParentId(Long userParentId, Long userId);
    
    List<ApplicationUser> findByUserParentId(Long userParentId);
    
    PaginatedResponse<ApplicationUser> getAllUsersprofilesForGivenTenantId(Long tenantId, Pageable pageable);
}
