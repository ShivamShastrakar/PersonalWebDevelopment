package com.mahaexam.packagemanagment.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.FeeRuleModel;

public interface FeeRuleRepository {
	FeeRuleModel save(FeeRuleModel feeRule);

	Optional<FeeRuleModel> findById(Integer id);

	List<FeeRuleModel> findAll(UserBean user);

	void update(FeeRuleModel feeRule);

	void delete(Integer id);

	boolean existByRuleNameAndTenantId(String ruleName, Long tenantId);

	boolean existsByRuleNameAndTenantIdExceptId(String ruleName, Long tenantId, Integer excludeId);
}