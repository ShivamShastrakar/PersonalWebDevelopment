package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.CommissionConfigRequest;
import com.mahaexam.common.model.CommissionSlab;

public interface CommissionConfigRepository {
	
	public Long insertConfig(CommissionConfigRequest req);
	public void updateConfig(CommissionConfigRequest req);
	public void deleteSlabsByConfigId(Long configId);
	public void deleteConfigById(Long id);
	public void insertSlabs(Long configId, List<CommissionSlab> slabs);
	public List<CommissionConfigRequest> findConfigs(Integer hierarchyLevelId, String packageType,Boolean active);
	public Optional<CommissionConfigRequest> findById(Long id);
	public Optional<CommissionConfigRequest> findByRoleId(Integer roleId);
	public Optional<CommissionConfigRequest> findByHierarchyLevelId(Long hierarchyLevelId);
}
