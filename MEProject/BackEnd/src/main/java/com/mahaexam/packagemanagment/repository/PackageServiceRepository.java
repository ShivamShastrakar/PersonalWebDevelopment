package com.mahaexam.packagemanagment.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageServiceModel;

public interface PackageServiceRepository {
	PackageServiceModel save(PackageServiceModel mapping);

    List<PackageServiceModel> saveBatch(List<PackageServiceModel> mappings);

	Optional<PackageServiceModel> findById(Integer id);

	List<PackageServiceModel> findAll(UserBean user);

	void update(PackageServiceModel mapping);

	void delete(Integer id);
	void deletebyGivenPackageId(Integer packageId);
}