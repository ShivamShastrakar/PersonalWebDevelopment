package com.mahaexam.packagemanagment.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageCourseModel;

public interface PackageCourseRepository {
	PackageCourseModel save(PackageCourseModel mapping);

	Optional<PackageCourseModel> findById(Integer id);

	List<PackageCourseModel> findAll(UserBean user);

	void update(PackageCourseModel mapping);

	void delete(Integer id);
	void deletebyGivenPackageId(Integer packageId);
}