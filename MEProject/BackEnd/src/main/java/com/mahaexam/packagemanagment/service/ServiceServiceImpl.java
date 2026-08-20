package com.mahaexam.packagemanagment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.ServiceBean;
import com.mahaexam.packagemanagment.model.ServiceModel;
import com.mahaexam.packagemanagment.repository.ServiceRepository;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    // Convert ServiceBean to ServiceModel
    private ServiceModel toModel(ServiceBean bean) {
        ServiceModel model = new ServiceModel();
        model.setId(bean.getId());
        model.setServiceName(bean.getServiceName());
        model.setServiceDetails(bean.getServiceDetails());
        model.setServiceType(bean.getServiceType());
        model.setOptions(bean.getOptions());
        model.setUpdatedBy(bean.getUpdatedBy());
        model.setTenantId(bean.getTenantId());
        model.setPackageId(bean.getPackageId());
        return model;
    }

    // Convert ServiceModel to ServiceBean
    private ServiceBean toBean(ServiceModel model) {
        ServiceBean bean = new ServiceBean();
        bean.setId(model.getId());
        bean.setServiceName(model.getServiceName());
        bean.setServiceDetails(model.getServiceDetails());
        bean.setServiceType(model.getServiceType());
        bean.setCreatedAt(model.getCreatedAt());
        bean.setUpdatedAt(model.getUpdatedAt());
        bean.setOptions(model.getOptions());
        bean.setUpdatedBy(model.getUpdatedBy());
        bean.setTenantId(model.getTenantId());
        bean.setPackageId(model.getPackageId());
        return bean;
    }

    @Override
    public ServiceBean createService(ServiceBean service) {
        if (service.getServiceName() == null || service.getServiceName().isBlank()) {
            throw new IllegalArgumentException("Service name cannot be null or empty");
        }
        if (serviceRepository.existsByServiceName(service.getServiceName().trim())) {
            throw new ValidationException("Service name already exists: " + service.getServiceName());
        }
        ServiceModel model = toModel(service);
        model.setCreatedAt(LocalDateTime.now());
        model.setDeleted("0");
        ServiceModel savedModel = serviceRepository.save(model);
        return toBean(savedModel);
    }

    @Override
    public Optional<ServiceBean> getServiceById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid service ID");
        }
        return serviceRepository.findById(id).map(this::toBean);
    }

    @Override
    public List<ServiceBean> getAllServices(UserBean user) {
        if (user == null || user.getTenantId() == null) {
            throw new IllegalArgumentException("User or tenant ID cannot be null");
        }
        return serviceRepository.findAll(user).stream()
                .map(this::toBean)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceBean updateService(Integer id, ServiceBean service) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid service ID");
        }
        if (service.getServiceName() == null || service.getServiceName().isBlank()) {
            throw new IllegalArgumentException("Service name cannot be null or empty");
        }
        if (serviceRepository.existsByServiceNameExcludingId(service.getServiceName().trim(), id)) {
            throw new ValidationException("Service name already exists: " + service.getServiceName());
        }
        Optional<ServiceModel> existing = serviceRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Service with ID " + id + " not found");
        }
        ServiceModel model = toModel(service);
        model.setId(id);
        model.setUpdatedAt(LocalDateTime.now());
        serviceRepository.update(model);
        return toBean(model);
    }

    @Override
    public void deleteService(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid service ID");
        }
        Optional<ServiceModel> existing = serviceRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Service with ID " + id + " not found");
        }
        serviceRepository.delete(id);
    }
    
    @Override
    public List<ServiceBean> findAllByPackageIds(List<Integer> packageIds){
    	List<ServiceModel> serviceModels= serviceRepository.findAllByPackageIds(packageIds);
    	return serviceModels.stream().map(this::toBean).collect(Collectors.toList());
    }
    
}
