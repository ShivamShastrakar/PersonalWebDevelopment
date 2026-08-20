package com.mahaexam.packagemanagment.service;

import com.mahaexam.packagemanagment.bean.PackageCategoryBean;
import java.util.List;
import java.util.Optional;

public interface PackageCategoryService {
    PackageCategoryBean createPackageCategory(PackageCategoryBean bean);
    
    Optional<PackageCategoryBean> getPackageCategoryById(Integer id);
    
    List<PackageCategoryBean> getAllPackageCategories(Long tenantId);
    
    PackageCategoryBean updatePackageCategory(Integer id, PackageCategoryBean bean);
    
    void deletePackageCategory(Integer id);
}
