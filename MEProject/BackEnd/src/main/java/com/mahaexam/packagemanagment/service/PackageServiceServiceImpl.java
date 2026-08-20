package com.mahaexam.packagemanagment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageServiceBean;
import com.mahaexam.packagemanagment.model.PackageServiceModel;
import com.mahaexam.packagemanagment.repository.PackageServiceRepository;

@Service
public class PackageServiceServiceImpl implements PackageServiceService {

    private final PackageServiceRepository mappingRepository;

    public PackageServiceServiceImpl(PackageServiceRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    // Convert PackageServiceBean to PackageServiceModel
    private PackageServiceModel toModel(PackageServiceBean bean) {
        PackageServiceModel model = new PackageServiceModel();
        model.setId(bean.getId());
        model.setPackageId(bean.getPackageId());
        model.setServiceId(bean.getServiceId());
        model.setCreatedBy(bean.getCreatedBy());
        model.setCreatedDate(LocalDateTime.now());
        return model;
    }

    // Convert PackageServiceModel to PackageServiceBean
    private PackageServiceBean toBean(PackageServiceModel model) {
        PackageServiceBean bean = new PackageServiceBean();
        bean.setId(model.getId());
        bean.setPackageId(model.getPackageId());
        bean.setServiceId(model.getServiceId());
        bean.setCreatedBy(model.getCreatedBy());
        return bean;
    }

    @Override
    public PackageServiceBean createMapping(PackageServiceBean mapping) {
        if (mapping.getPackageId() == null || mapping.getServiceId() == null) {
            throw new IllegalArgumentException("Package and service IDs cannot be null");
        }
        PackageServiceModel model = toModel(mapping);
        model.setCreatedDate(LocalDateTime.now());
        PackageServiceModel savedModel = mappingRepository.save(model);
        return toBean(savedModel);
    }

    @Override
    public List<PackageServiceBean> createMappings(List<PackageServiceBean> mappings) {
        if (mappings == null || mappings.isEmpty()) {
            return Collections.emptyList();
        }

        List<PackageServiceModel> models = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (PackageServiceBean mapping : mappings) {
            if (mapping.getPackageId() == null || mapping.getServiceId() == null) {
                throw new IllegalArgumentException("Package and service IDs cannot be null");
            }
            PackageServiceModel model = toModel(mapping);
            model.setCreatedDate(now);
            models.add(model);
        }

        List<PackageServiceModel> savedModels = mappingRepository.saveBatch(models);
        return savedModels.stream()
                .map(this::toBean)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PackageServiceBean> getMappingById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        return mappingRepository.findById(id).map(this::toBean);
    }

    @Override
    public List<PackageServiceBean> getAllMappings(UserBean user) {
        if (user == null || user.getTenantId() == null) {
            throw new IllegalArgumentException("User or tenant ID cannot be null");
        }
        return mappingRepository.findAll(user).stream()
                .map(this::toBean)
                .collect(Collectors.toList());
    }

    @Override
    public PackageServiceBean updateMapping(Integer id, PackageServiceBean mapping) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        if (mapping.getPackageId() == null || mapping.getServiceId() == null) {
            throw new IllegalArgumentException("Package and service IDs cannot be null");
        }
        Optional<PackageServiceModel> existing = mappingRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
        }
        PackageServiceModel model = toModel(mapping);
        model.setId(id);
        mappingRepository.update(model);
        return toBean(model);
    }

    @Override
    public void deleteMapping(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        Optional<PackageServiceModel> existing = mappingRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
        }
        mappingRepository.delete(id);
    }
    
    @Override
    public void deletebyGivenPackageId(Integer packageId) {
    	
    	if (packageId == null || packageId <= 0) {
            throw new IllegalArgumentException("Invalid package ID");
        }
//        Optional<PackageServiceModel> existing = mappingRepository.findById(id);
//        if (existing.isEmpty()) {
//            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
//        }
        mappingRepository.deletebyGivenPackageId(packageId);
    	
    }
}