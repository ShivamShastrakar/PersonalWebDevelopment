package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.District;

public interface DistrictRepository {
	int save(UserBean user,District district);

	Optional<District> findById(UserBean user, int id);

	List<District> findAll(UserBean user);

	List<District> findByTenant(UserBean user, Long tenantId);
	
	List<District> findByStateId(UserBean user, Integer stateId);

	int update(UserBean user, District district);

	int deleteById(UserBean user, int id);
	
	boolean existsByDistrictNameAndTenantId(String districtName, Long tenantId);

	boolean existsByDistrictNameAndTenantIdExceptId(String districtName, Long tenantId, int excludeId);

}
