package com.mahaexam.packagemanagment.repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.SubPackageMappingModel;

import java.util.List;
import java.util.Optional;

public interface SubPackageMappingRepository {
    SubPackageMappingModel save(SubPackageMappingModel mapping);
    Optional<SubPackageMappingModel> findById(Integer id);
    List<SubPackageMappingModel> findAll(UserBean user);
    void update(SubPackageMappingModel mapping);
    void delete(Integer id);
}