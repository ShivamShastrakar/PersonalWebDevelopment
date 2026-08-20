package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.SubPackageMappingBean;
import com.mahaexam.packagemanagment.model.SubPackageMappingModel;
import com.mahaexam.packagemanagment.repository.SubPackageMappingRepository;

@Service
public class SubPackageMappingServiceImpl implements SubPackageMappingService {

    private final SubPackageMappingRepository mappingRepository;

    public SubPackageMappingServiceImpl(SubPackageMappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    // Convert SubPackageMappingBean to SubPackageMappingModel
    private SubPackageMappingModel toModel(SubPackageMappingBean bean) {
        SubPackageMappingModel model = new SubPackageMappingModel();
        model.setId(bean.getId());
        model.setParentPackageId(bean.getParentPackageId());
        model.setChildPackageId(bean.getChildPackageId());
        return model;
    }

    // Convert SubPackageMappingModel to SubPackageMappingBean
    private SubPackageMappingBean toBean(SubPackageMappingModel model) {
        SubPackageMappingBean bean = new SubPackageMappingBean();
        bean.setId(model.getId());
        bean.setParentPackageId(model.getParentPackageId());
        bean.setChildPackageId(model.getChildPackageId());
        return bean;
    }

    @Override
    public SubPackageMappingBean createMapping(SubPackageMappingBean mapping) {
        if (mapping.getParentPackageId() == null || mapping.getChildPackageId() == null) {
            throw new IllegalArgumentException("Parent and child package IDs cannot be null");
        }
        SubPackageMappingModel model = toModel(mapping);
        SubPackageMappingModel savedModel = mappingRepository.save(model);
        return toBean(savedModel);
    }

    @Override
    public Optional<SubPackageMappingBean> getMappingById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        return mappingRepository.findById(id).map(this::toBean);
    }

    @Override
    public List<SubPackageMappingBean> getAllMappings(UserBean user) {
        if (user == null || user.getTenantId() == null) {
            throw new IllegalArgumentException("User or tenant ID cannot be null");
        }
        return mappingRepository.findAll(user).stream()
                .map(this::toBean)
                .collect(Collectors.toList());
    }

    @Override
    public SubPackageMappingBean updateMapping(Integer id, SubPackageMappingBean mapping) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        if (mapping.getParentPackageId() == null || mapping.getChildPackageId() == null) {
            throw new IllegalArgumentException("Parent and child package IDs cannot be null");
        }
        Optional<SubPackageMappingModel> existing = mappingRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
        }
        SubPackageMappingModel model = toModel(mapping);
        model.setId(id);
        mappingRepository.update(model);
        return toBean(model);
    }

    @Override
    public void deleteMapping(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid mapping ID");
        }
        Optional<SubPackageMappingModel> existing = mappingRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mapping with ID " + id + " not found");
        }
        mappingRepository.delete(id);
    }
}