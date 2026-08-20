package com.mahaexam.packagemanagment.repository;


import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageSearchRequest;
import com.mahaexam.packagemanagment.model.PackageModel;

public interface PackageRepository {
	PackageModel save(PackageModel pkg);
    Optional<PackageModel> findById(Integer id);
    List<PackageModel> findAll(UserBean user, String type);
    void update(PackageModel pkg);
    void delete(Integer id);
    PaginatedResponse<PackageModel> search(UserBean user, PackageSearchRequest request);
    boolean existsByPackageName(String packageName);
    boolean existsByPackageNameExcludingId(String packageName, Integer excludeId);
    List<PackageModel> findAllByUserId(Long userId, UserBean user);
    List<PackageModel> findAllByStudentIds(UserBean user, List<Long> studentIds);
	List<PackageModel> findAll(UserBean user, String type, String targetYear);

	/**
	 * Find suggested packages based on student criteria
	 * @param classId Student's current class ID
	 * @param subjectGroupId Student's subject group ID
	 * @param targetYear Student's target exam year
	 * @return List of packages matching the criteria
	 */
	List<PackageModel> findSuggestedPackages(Integer classId, Integer subjectGroupId, Integer targetYear);
}