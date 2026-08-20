package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.SubPackageMappingBean;

public interface SubPackageMappingService {
    SubPackageMappingBean createMapping(SubPackageMappingBean mapping);
    Optional<SubPackageMappingBean> getMappingById(Integer id);
    List<SubPackageMappingBean> getAllMappings(UserBean user);
    SubPackageMappingBean updateMapping(Integer id, SubPackageMappingBean mapping);
    void deleteMapping(Integer id);
}