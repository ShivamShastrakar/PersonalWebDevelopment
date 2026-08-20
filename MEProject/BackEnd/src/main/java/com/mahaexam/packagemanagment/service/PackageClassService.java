package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageClassBean;

public interface PackageClassService {
    PackageClassBean createMapping(PackageClassBean mapping);
    Optional<PackageClassBean> getMappingById(Integer id);
    List<PackageClassBean> getAllMappings(UserBean user);
    PackageClassBean updateMapping(Integer id, PackageClassBean mapping);
    void deleteMapping(Integer id);
    void deletebyGivenPackageId(Integer packageId);
}