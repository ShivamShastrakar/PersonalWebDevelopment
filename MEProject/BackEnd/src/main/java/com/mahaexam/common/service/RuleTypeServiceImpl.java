package com.mahaexam.common.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.RuleTypeBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.RuleTypeModel;
import com.mahaexam.common.repo.RuleTypeRepository;

@Service
public class RuleTypeServiceImpl implements RuleTypeService {

	private final RuleTypeRepository ruleTypeRepository;

	public RuleTypeServiceImpl(RuleTypeRepository ruleTypeRepository) {
		this.ruleTypeRepository = ruleTypeRepository;
	}

	// Convert RuleTypeBean to RuleTypeModel
	private RuleTypeModel toModel(RuleTypeBean bean) {
		RuleTypeModel model = new RuleTypeModel();
		model.setId(bean.getId());
		model.setRuleType(bean.getRuleType());
		model.setCreatedBy(bean.getCreatedBy());
		model.setUpdatedBy(bean.getUpdatedBy());
		model.setTenantId(bean.getTenantId());
		return model;
	}

	// Convert RuleTypeModel to RuleTypeBean
	private RuleTypeBean toBean(RuleTypeModel model) {
		RuleTypeBean bean = new RuleTypeBean();
		bean.setId(model.getId());
		bean.setRuleType(model.getRuleType());
		bean.setCreatedBy(model.getCreatedBy());
		bean.setUpdatedBy(model.getUpdatedBy());
		bean.setTenantId(model.getTenantId());
		return bean;
	}

	@Override
	public RuleTypeBean createRuleType(RuleTypeBean ruleType) {
		if (ruleType.getRuleType() == null || ruleType.getRuleType().isBlank()) {
			throw new IllegalArgumentException("Rule type cannot be null or empty");
		}
		RuleTypeModel model = toModel(ruleType);
		model.setCreatedAt(LocalDateTime.now());
		model.setDeleted("0");
		RuleTypeModel savedModel = ruleTypeRepository.save(model);
		return toBean(savedModel);
	}

	@Override
	public Optional<RuleTypeBean> getRuleTypeById(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid rule type ID");
		}
		return ruleTypeRepository.findById(id).map(this::toBean);
	}

	@Override
	public List<RuleTypeBean> getAllRuleTypes(UserBean user) {
		if (user == null || user.getTenantId() == null) {
			throw new IllegalArgumentException("User or tenant ID cannot be null");
		}
		return ruleTypeRepository.findAll(user).stream().map(this::toBean).collect(Collectors.toList());
	}

	@Override
	public RuleTypeBean updateRuleType(Integer id, RuleTypeBean ruleType) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid rule type ID");
		}
		if (ruleType.getRuleType() == null || ruleType.getRuleType().isBlank()) {
			throw new IllegalArgumentException("Rule type cannot be null or empty");
		}
		Optional<RuleTypeModel> existing = ruleTypeRepository.findById(id);
		if (existing.isEmpty()) {
			throw new IllegalArgumentException("Rule type with ID " + id + " not found");
		}
		RuleTypeModel model = toModel(ruleType);
		model.setId(id);
		model.setUpdatedAt(LocalDateTime.now());
		ruleTypeRepository.update(model);
		return toBean(model);
	}

	@Override
	public void deleteRuleType(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid rule type ID");
		}
		Optional<RuleTypeModel> existing = ruleTypeRepository.findById(id);
		if (existing.isEmpty()) {
			throw new IllegalArgumentException("Rule type with ID " + id + " not found");
		}
		ruleTypeRepository.delete(id);
	}
}