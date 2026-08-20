package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageCourseBean;
import com.mahaexam.packagemanagment.model.PackageCourseModel;
import com.mahaexam.packagemanagment.repository.PackageCourseRepository;

@Service
public class PackageCourseServiceImpl implements PackageCourseService {

	private final PackageCourseRepository mappingRepository;

	public PackageCourseServiceImpl(PackageCourseRepository mappingRepository) {
		this.mappingRepository = mappingRepository;
	}

	// Convert PackageCourseBean to PackageCourseModel
	private PackageCourseModel toModel(PackageCourseBean bean) {
		PackageCourseModel model = new PackageCourseModel();
		model.setId(bean.getId());
		model.setPackageId(bean.getPackageId());
		model.setCourseId(bean.getCourseId());
		return model;
	}

	// Convert PackageCourseModel to PackageCourseBean
	private PackageCourseBean toBean(PackageCourseModel model) {
		PackageCourseBean bean = new PackageCourseBean();
		bean.setId(model.getId());
		bean.setPackageId(model.getPackageId());
		bean.setCourseId(model.getCourseId());
		return bean;
	}

	@Override
	public PackageCourseBean createMapping(PackageCourseBean mapping) {
		if (mapping.getPackageId() == null || mapping.getCourseId() == null) {
			throw new IllegalArgumentException("Package and course IDs cannot be null");
		}
		PackageCourseModel model = toModel(mapping);
		PackageCourseModel savedModel = mappingRepository.save(model);
		return toBean(savedModel);
	}

	@Override
	public Optional<PackageCourseBean> getMappingById(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid mapping ID");
		}
		return mappingRepository.findById(id).map(this::toBean);
	}

	@Override
	public List<PackageCourseBean> getAllMappings(UserBean user) {
		if (user == null || user.getTenantId() == null) {
			throw new IllegalArgumentException("User or tenant ID cannot be null");
		}
		return mappingRepository.findAll(user).stream().map(this::toBean).collect(Collectors.toList());
	}

	@Override
	public PackageCourseBean updateMapping(Integer id, PackageCourseBean mapping) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid mapping ID");
		}
		if (mapping.getPackageId() == null || mapping.getCourseId() == null) {
			throw new IllegalArgumentException("Package and course IDs cannot be null");
		}
		Optional<PackageCourseModel> existing = mappingRepository.findById(id);
		if (existing.isEmpty()) {
			throw new IllegalArgumentException("Mapping with ID " + id + " not found");
		}
		PackageCourseModel model = toModel(mapping);
		model.setId(id);
		mappingRepository.update(model);
		return toBean(model);
	}

	@Override
	public void deleteMapping(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid mapping ID");
		}
		Optional<PackageCourseModel> existing = mappingRepository.findById(id);
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
//		Optional<PackageCourseModel> existing = mappingRepository.findById(id);
//		if (existing.isEmpty()) {
//			throw new IllegalArgumentException("Mapping with ID " + id + " not found");
//		}
		mappingRepository.deletebyGivenPackageId(packageId);
		
	}
}