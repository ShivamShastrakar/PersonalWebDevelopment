package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.PackageCategoryModel;
import java.util.List;
import java.util.Optional;

public interface PackageCategoryRepository {
    PackageCategoryModel save(PackageCategoryModel model);
    
    Optional<PackageCategoryModel> findById(Integer id);
    
    List<PackageCategoryModel> findAll(Long tenantId);
    
    void update(PackageCategoryModel model);
    
    void delete(Integer id);
    
    boolean existsByName(String name, Long tenantId);
    
    boolean existsByNameExcludingId(String name, Integer excludeId, Long tenantId);
}
