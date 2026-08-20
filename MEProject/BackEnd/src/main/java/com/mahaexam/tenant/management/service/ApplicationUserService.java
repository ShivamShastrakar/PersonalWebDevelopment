package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.ApplicationUser;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserService {
    ApplicationUser save(ApplicationUser user);

    Optional<ApplicationUser> findById(Long id);

    List<ApplicationUser> findAll();

    ApplicationUser update(ApplicationUser user);

    Optional<ApplicationUser> findByUserId(Long userId);

    Optional<ApplicationUser> findByEmailId(String emailId);

    Optional<ApplicationUser> findByMobileNo(String mobileNo);


    Optional<ApplicationUser> findByEmailIdAndMobileNo(String emailId, String mobileNo);

    ApplicationUser updateByUserId(ApplicationUser user);

    List<ApplicationUser> findByFirstOrLastName(String name, Long tenantId, List<String> profileTypes);
}