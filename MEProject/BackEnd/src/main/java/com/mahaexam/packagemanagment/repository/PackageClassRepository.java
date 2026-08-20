package com.mahaexam.packagemanagment.repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageClassModel;

import java.util.List;
import java.util.Optional;

public interface PackageClassRepository {
	PackageClassModel save(PackageClassModel mapping);

	Optional<PackageClassModel> findById(Integer id);

	List<PackageClassModel> findAll(UserBean user);

	void update(PackageClassModel mapping);

	void delete(Integer id);
	void deletebyGivenPackageId(Integer packageId);
}