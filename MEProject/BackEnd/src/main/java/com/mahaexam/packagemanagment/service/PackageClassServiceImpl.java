package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageClassBean;
import com.mahaexam.packagemanagment.model.PackageClassModel;
import com.mahaexam.packagemanagment.repository.PackageClassRepository;

@Service
public class PackageClassServiceImpl implements PackageClassService {

    private final PackageClassRepository mappingRepository;

    public PackageClassServiceImpl(PackageClassRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    // Convert PackageClassBean to PackageClassModel
    private PackageClassModel toModel(PackageClassBean bean) {
        PackageClassModel model = new PackageClassModel();
        model.setId(bean.getId());
        model.setPackageId(bean.getPackageId());
        model.setClassId(bean.getClassId());
        return model;
    }

    // Convert PackageClassModel to PackageClassBean
    private PackageClassBean toBean(PackageClassModel model) {
        PackageClassBean bean = new PackageClassBean();
        bean.setId(model.getId());
        bean.setPackageId(model.getPackageId());
        bean.setClassId(model.getClassId());
        return bean;
    }

    @Override
    public PackageClassBean createMapping(PackageClassBean mapping) {
        if (mapping.getPackageId() == null || mapping.getClassId() == null) {
            throw new IllegalArgumentException("Package and class IDs cannot be null");
        }
        PackageClassModel model = toModel(mapping);
        PackageClassModel savedModel = mappingRepository.save(model);
        return toBean(savedModel);
    }

    @Override
    public Optional<PackageClassBean> getMappingById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        return mappingRepository.findById(id).map(this::toBean);
    }

    @Override
    public List<PackageClassBean> getAllMappings(UserBean user) {
        if (user == null || user.getTenantId() == null) {
            throw new IllegalArgumentException("User or tenant ID cannot be null");
        }
        return mappingRepository.findAll(user).stream()
                .map(this::toBean)
                .collect(Collectors.toList());
    }

    @Override
    public PackageClassBean updateMapping(Integer id, PackageClassBean mapping) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        if (mapping.getPackageId() == null || mapping.getClassId() == null) {
            throw new IllegalArgumentException("Package and class IDs cannot be null");
        }
        Optional<PackageClassModel> existing = mappingRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
        }
        PackageClassModel model = toModel(mapping);
        model.setId(id);
        mappingRepository.update(model);
        return toBean(model);
    }

    @Override
    public void deleteMapping(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        Optional<PackageClassModel> existing = mappingRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
        }
        mappingRepository.delete(id);
    }
    
    @Override
    public void deletebyGivenPackageId(Integer packageId) {
    	if (packageId == null || packageId <= 0) {
            throw new IllegalArgumentException("Invalid package Id");
        }
//        Optional<PackageClassModel> existing = mappingRepository.findById(id);
//        if (existing.isEmpty()) {
//            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
//        }
        mappingRepository.deletebyGivenPackageId(packageId);
    	
    }
    
    
}