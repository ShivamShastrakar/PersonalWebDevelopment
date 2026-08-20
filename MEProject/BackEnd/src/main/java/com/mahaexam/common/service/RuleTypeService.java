package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.RuleTypeBean;
import com.mahaexam.common.bean.UserBean;

public interface RuleTypeService {
	RuleTypeBean createRuleType(RuleTypeBean ruleType);

	Optional<RuleTypeBean> getRuleTypeById(Integer id);

	List<RuleTypeBean> getAllRuleTypes(UserBean user);

	RuleTypeBean updateRuleType(Integer id, RuleTypeBean ruleType);

	void deleteRuleType(Integer id);
}