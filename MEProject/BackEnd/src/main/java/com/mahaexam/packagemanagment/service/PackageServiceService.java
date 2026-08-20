package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageServiceBean;

public interface PackageServiceService {
    PackageServiceBean createMapping(PackageServiceBean mapping);
    List<PackageServiceBean> createMappings(List<PackageServiceBean> mappings);
    Optional<PackageServiceBean> getMappingById(Integer id);
    List<PackageServiceBean> getAllMappings(UserBean user);
    PackageServiceBean updateMapping(Integer id, PackageServiceBean mapping);
    void deleteMapping(Integer id);
    void deletebyGivenPackageId(Integer packageId);
}
