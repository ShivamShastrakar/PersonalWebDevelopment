package com.mahaexam.packagemanagment.repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.ServiceModel;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    ServiceModel save(ServiceModel service);
    Optional<ServiceModel> findById(Integer id);
    List<ServiceModel> findAll(UserBean user);
    void update(ServiceModel service);
    void delete(Integer id);
	List<ServiceModel> findAllByPackageIds(List<Integer> packageIds);
    boolean existsByServiceName(String serviceName);
    boolean existsByServiceNameExcludingId(String serviceName, Integer excludeId);
}