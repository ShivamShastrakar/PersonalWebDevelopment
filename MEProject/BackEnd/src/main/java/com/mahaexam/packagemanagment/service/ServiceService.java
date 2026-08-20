package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.ServiceBean;

public interface ServiceService {
    ServiceBean createService(ServiceBean service);
    Optional<ServiceBean> getServiceById(Integer id);
    List<ServiceBean> getAllServices(UserBean user);
    ServiceBean updateService(Integer id, ServiceBean service);
    void deleteService(Integer id);
	List<ServiceBean> findAllByPackageIds(List<Integer> packageIds);
}	