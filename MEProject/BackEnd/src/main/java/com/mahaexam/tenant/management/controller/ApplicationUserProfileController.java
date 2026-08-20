package com.mahaexam.tenant.management.controller;


import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.tenant.management.bean.ApplicationUserProfileBean;
import com.mahaexam.tenant.management.bean.ChannelPartnerUpdate;
import com.mahaexam.tenant.management.bean.NetworkPartnerUpdate;
import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.model.ApplicationUserProfile;
import com.mahaexam.tenant.management.service.ApplicationUserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ApplicationUserProfileController extends BaseController {

    @Autowired
    private ApplicationUserProfileService profileService;

    @PostMapping("/{id}")
    public ResponseEntity<ApplicationUserProfile> updateProfile(@PathVariable Long id, @Valid @RequestBody ApplicationUserProfileBean updateDTO) {
        ApplicationUserProfile updatedProfile = profileService.updateProfile(id, updateDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApplicationUserProfile> getUserProfileDetails(@PathVariable Long userId) {
        ApplicationUserProfile updatedProfile = profileService.getUserProfileDetails(userId);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/userType/{userType}")
    public ResponseEntity<PaginatedResponse<ApplicationUser>> findByUserType(@PathVariable String userType,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(profileService.findByUserType(user.getTenantId(), userType, false, pageable));
    }

    @GetMapping("total-count/userType/{userType}")
    public ResponseEntity<Integer> countByUserType(@PathVariable String userType) {
        UserBean user = getUser();
        return ResponseEntity.ok(profileService.countByUserType(user.getTenantId(), userType));
    }

    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        profileService.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("cpUpdate/{userId}")
    public ResponseEntity<ApplicationUserProfile> updateCPProfile(@PathVariable Long userId, @Valid @RequestBody ChannelPartnerUpdate channelPartnerUpdate) {
        ApplicationUserProfileBean updateDTO = ApplicationUserProfileConverter.toBean(channelPartnerUpdate , null);
        ApplicationUserProfile updatedProfile = profileService.updateProfile(userId, updateDTO);
        return ResponseEntity.ok(updatedProfile);
    }
    
    @PostMapping("npUpdate/{userId}")
    public ResponseEntity<ApplicationUserProfile> updateNPProfile(@PathVariable Long userId, @Valid @RequestBody NetworkPartnerUpdate networkPartnerUpdate) {
        ApplicationUserProfileBean updateDTO = ApplicationUserProfileConverter.toBean(null, networkPartnerUpdate);
        ApplicationUserProfile updatedProfile = profileService.updateProfile(userId, updateDTO);
        return ResponseEntity.ok(updatedProfile);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ApplicationUser>> searchUsers(
            @RequestParam String query,
            @RequestParam(required = false) List<String> profileTypes) {
        UserBean user = getUser();
        List<ApplicationUser> users = profileService.findByFirstOrLastName(query, user.getTenantId(), profileTypes);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/searchUsersExlucdingcurrentuserId")
    public ResponseEntity<PaginatedResponse<ApplicationUser>> findAllUsersForGivenTenantId(
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "100000") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(profileService.findAllUsersForGivenTenantId(user.getTenantId(), user.getUserId(), pageable));
    }
    
    @PostMapping("/updateUserParentId/{userParentId}")
    public ResponseEntity<Void> updateUserParentId(@PathVariable Long userParentId) {
        UserBean user = getUser();
        profileService.updateUserParentId(userParentId, user.getUserId());
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/getAllUsersprofilesForGivenTenantId")
    public ResponseEntity<PaginatedResponse<ApplicationUser>> getAllUsersProfiles(
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10000") int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserBean user = getUser();
        return ResponseEntity.ok(profileService.getAllUsersprofilesForGivenTenantId(user.getTenantId(), pageable));
    }
    

}
