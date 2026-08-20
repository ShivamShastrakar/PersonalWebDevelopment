package com.mahaexam.packagemanagment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.FeeRuleBean;
import com.mahaexam.packagemanagment.model.FeeRuleModel;
import com.mahaexam.packagemanagment.repository.FeeRuleRepository;

@Service
public class FeeRuleServiceImpl implements FeeRuleService {

	private final FeeRuleRepository feeRuleRepository;

	public FeeRuleServiceImpl(FeeRuleRepository feeRuleRepository) {
		this.feeRuleRepository = feeRuleRepository;
	}

	// Convert FeeRuleBean to FeeRuleModel
	private FeeRuleModel toModel(FeeRuleBean bean) {
		FeeRuleModel model = new FeeRuleModel();
		model.setId(bean.getId());
		model.setRuleName(bean.getRuleName());
		model.setRuleTypeId(bean.getRuleTypeId());
		model.setStartDate(bean.getStartDate());
		model.setEndDate(bean.getEndDate());
		model.setAmount(bean.getAmount());
		model.setDueDate(bean.getDueDate());
		model.setPackageId(bean.getPackageId());
		model.setInstituteId(bean.getInstituteId());
		model.setTalukaId(bean.getTalukaId());
		model.setDistrictId(bean.getDistrictId());
		model.setStateId(bean.getStateId());
		model.setDivisionId(bean.getDivisionId());
		model.setAmountType(bean.getAmountType());
		model.setRuleCode(bean.getRuleCode());
		model.setRoleId(bean.getRoleId());
		model.setIncentiveCap(bean.getIncentiveCap());
		model.setRulesAmount(bean.getRulesAmount());
		model.setPackageType(bean.getPackageType());
		model.setQuantity(bean.getQuantity());
		model.setParentPackageIds(bean.getParentPackageIds());
		model.setTenantId(bean.getTenantId());
		return model;
	}

	// Convert FeeRuleModel to FeeRuleBean
	private FeeRuleBean toBean(FeeRuleModel model) {
		FeeRuleBean bean = new FeeRuleBean();
		bean.setId(model.getId());
		bean.setRuleName(model.getRuleName());
		bean.setRuleTypeId(model.getRuleTypeId());
		bean.setStartDate(model.getStartDate());
		bean.setEndDate(model.getEndDate());
		bean.setAmount(model.getAmount());
		bean.setDueDate(model.getDueDate());
		bean.setPackageId(model.getPackageId());
		bean.setInstituteId(model.getInstituteId());
		bean.setTalukaId(model.getTalukaId());
		bean.setDistrictId(model.getDistrictId());
		bean.setStateId(model.getStateId());
		bean.setDivisionId(model.getDivisionId());
		bean.setAmountType(model.getAmountType());
		bean.setRuleCode(model.getRuleCode());
		bean.setRoleId(model.getRoleId());
		bean.setIncentiveCap(model.getIncentiveCap());
		bean.setRulesAmount(model.getRulesAmount());
		bean.setPackageType(model.getPackageType());
		bean.setQuantity(model.getQuantity());
		bean.setParentPackageIds(model.getParentPackageIds());
		bean.setTenantId(model.getTenantId());
		return bean;
	}

	@Override
	public FeeRuleBean createFeeRule(FeeRuleBean feeRule) {
		if (feeRule.getRuleName() == null || feeRule.getRuleName().isBlank()) {
			throw new IllegalArgumentException("Rule name cannot be null or empty");
		}
		boolean exists = feeRuleRepository.existByRuleNameAndTenantId(feeRule.getRuleName(), feeRule.getTenantId());
		if (exists) {
			throw new ValidationException("Fee rule name already exists for the tenant.");
		}
		FeeRuleModel model = toModel(feeRule);
		model.setCreatedAt(LocalDateTime.now());
		model.setDeleted("0");
		FeeRuleModel savedModel = feeRuleRepository.save(model);
		return toBean(savedModel);
	}

	@Override
	public Optional<FeeRuleBean> getFeeRuleById(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid fee rule ID");
		}
		return feeRuleRepository.findById(id).map(this::toBean);
	}

	@Override
	public List<FeeRuleBean> getAllFeeRules(UserBean user) {
		if (user == null || user.getTenantId() == null) {
			throw new IllegalArgumentException("User or tenant ID cannot be null");
		}
		return feeRuleRepository.findAll(user).stream().map(this::toBean).collect(Collectors.toList());
	}

	@Override
	public FeeRuleBean updateFeeRule(Integer id, FeeRuleBean feeRule) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid fee rule ID");
		}
		if (feeRule.getRuleName() == null || feeRule.getRuleName().isBlank()) {
			throw new IllegalArgumentException("Rule name cannot be null or empty");
		}
		Optional<FeeRuleModel> existing = feeRuleRepository.findById(id);
		if (existing.isEmpty()) {
			throw new IllegalArgumentException("Fee rule with ID " + id + " not found");
		}
		boolean exists = feeRuleRepository.existsByRuleNameAndTenantIdExceptId(
				feeRule.getRuleName(), feeRule.getTenantId(), id
		);
		if (exists) {
			throw new ValidationException("Another fee rule with the same name already exists for this tenant.");
		}
		FeeRuleModel model = toModel(feeRule);
		model.setId(id);
		model.setUpdatedAt(LocalDateTime.now());
		feeRuleRepository.update(model);
		return toBean(model);
	}

	@Override
	public void deleteFeeRule(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid fee rule ID");
		}
		Optional<FeeRuleModel> existing = feeRuleRepository.findById(id);
		if (existing.isEmpty()) {
			throw new IllegalArgumentException("Fee rule with ID " + id + " not found");
		}
		feeRuleRepository.delete(id);
	}
}