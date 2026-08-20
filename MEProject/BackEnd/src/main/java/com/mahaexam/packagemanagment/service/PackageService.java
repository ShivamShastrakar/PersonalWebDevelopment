package com.mahaexam.packagemanagment.service;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageBean;
import com.mahaexam.packagemanagment.bean.PackageSearchRequest;
import com.mahaexam.packagemanagment.model.PackageModel;

import java.util.List;
import java.util.Optional;

public interface PackageService {
    PackageBean createPackage(UserBean user, PackageBean pkg);

    Optional<PackageBean> getPackageById(Integer id, boolean fetchImage);

    List<PackageBean> getAllPackages(UserBean user, String type, String targetYear);

    PackageBean updatePackage(UserBean user,Integer id, PackageBean pkg);

    void deletePackage(Integer id);

    PaginatedResponse<PackageBean> searchPackages(UserBean user, PackageSearchRequest request);

    PackageBean getPackageImageById(Integer id);

    List<PackageBean> findAllByUserId(Long userId, UserBean user);

    List<PackageBean>  findAllByUserId(UserBean user, List<Long> studentIds);

    /**
     * Get suggested packages based on student's current details
     * @param user of the student user
     * @return List of suggested packages matching the student's criteria
     */
    List<PackageBean> getSuggestedPackages(UserBean user);
}