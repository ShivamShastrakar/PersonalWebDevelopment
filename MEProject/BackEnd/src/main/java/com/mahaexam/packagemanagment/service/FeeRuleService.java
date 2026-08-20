package com.mahaexam.packagemanagment.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.FeeRuleBean;

public interface FeeRuleService {
	FeeRuleBean createFeeRule(FeeRuleBean feeRule);

	Optional<FeeRuleBean> getFeeRuleById(Integer id);

	List<FeeRuleBean> getAllFeeRules(UserBean user);

	FeeRuleBean updateFeeRule(Integer id, FeeRuleBean feeRule);

	void deleteFeeRule(Integer id);
}