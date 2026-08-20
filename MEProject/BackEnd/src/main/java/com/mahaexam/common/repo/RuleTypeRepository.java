package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.RuleTypeModel;

public interface RuleTypeRepository {
	RuleTypeModel save(RuleTypeModel ruleType);

	Optional<RuleTypeModel> findById(Integer id);

	List<RuleTypeModel> findAll(UserBean user);

	void update(RuleTypeModel ruleType);

	void delete(Integer id);
}