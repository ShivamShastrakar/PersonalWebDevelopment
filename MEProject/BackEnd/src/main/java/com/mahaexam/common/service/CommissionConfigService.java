package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.CommissionConfigRequest;

public interface CommissionConfigService {
	
	public CommissionConfigRequest create(CommissionConfigRequest request);
	public CommissionConfigRequest update(Long id, CommissionConfigRequest request);
	public void deactivate(Long id);
	public void delete(Long id);
	public List<CommissionConfigRequest> list(Integer hierarchyLevelId,String packageType,Boolean active);
	public Optional<CommissionConfigRequest> getCommissionSlabsByRoleId(Integer roleId);
	public Optional<CommissionConfigRequest> getCommissionSlabsByHierarchyLevelId(Long hierarchyLevelId);
}
