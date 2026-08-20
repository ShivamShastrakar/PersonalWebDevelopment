package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageCourseBean;

public interface PackageCourseService {
	PackageCourseBean createMapping(PackageCourseBean mapping);

	Optional<PackageCourseBean> getMappingById(Integer id);

	List<PackageCourseBean> getAllMappings(UserBean user);

	PackageCourseBean updateMapping(Integer id, PackageCourseBean mapping);

	void deleteMapping(Integer id);
	void deletebyGivenPackageId(Integer packageId);
}