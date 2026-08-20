package com.mahaexam.tenant.management.service;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.tenant.management.bean.ApplicationUserProfileBean;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.ApplicationUserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserProfileService {

    ApplicationUserProfile updateProfile(Long id, ApplicationUserProfileBean updateDTO);

    ApplicationUserProfile getUserProfileDetails(Long userId);

    PaginatedResponse<ApplicationUser> findByUserType(Long tenantId, String userType, Boolean isDeleted, Pageable pageable);

    void deleteByUserId(Long userId);

    Integer countByUserType(Long tenantId, String userType);

    List<ApplicationUser> findByFirstOrLastName(String query, Long tenantId, List<String> profileTypes);
    PaginatedResponse<ApplicationUser> findAllUsersForGivenTenantId(Long tenantId, Long user_id, Pageable pageable);
    void updateUserParentId(Long userParentId, Long userId);
    
    PaginatedResponse<ApplicationUser> getAllUsersprofilesForGivenTenantId(Long tenantId, Pageable pageable);
}
