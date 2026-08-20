package com.mahaexam.packagemanagment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.FeeRuleModel;

@Repository
public class FeeRuleRepositoryImpl implements FeeRuleRepository {

	private final JdbcTemplate jdbcTemplate;

	public FeeRuleRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public FeeRuleModel save(FeeRuleModel feeRule) {
		// Validate foreign keys
		validateForeignKeys(feeRule);
		String sql = "INSERT INTO fee_rules (rule_name, rule_type_id, start_date, end_date, created_at, updated_at, deleted_at, deleted, amount, due_date, package_id, institute_id, taluka_id, district_id, state_id, division_id, amount_type, rule_code, role_id, incentive_cap, rules_amount, package_type, quantity, parent_package_ids, tenant_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, feeRule.getRuleName(), feeRule.getRuleTypeId(), feeRule.getStartDate(),
				feeRule.getEndDate(), feeRule.getCreatedAt(), feeRule.getUpdatedAt(), feeRule.getDeletedAt(),
				feeRule.getDeleted(), feeRule.getAmount(), feeRule.getDueDate(), feeRule.getPackageId(),
				feeRule.getInstituteId(), feeRule.getTalukaId(), feeRule.getDistrictId(), feeRule.getStateId(),
				feeRule.getDivisionId(), feeRule.getAmountType(), feeRule.getRuleCode(), feeRule.getRoleId(),
				feeRule.getIncentiveCap(), feeRule.getRulesAmount(), feeRule.getPackageType(), feeRule.getQuantity(),
				feeRule.getParentPackageIds(), feeRule.getTenantId());
		return feeRule;
	}

	@Override
	public Optional<FeeRuleModel> findById(Integer id) {
		String sql = "SELECT * FROM fee_rules WHERE id = ? AND deleted = '0'";
		try {
			FeeRuleModel feeRule = jdbcTemplate.queryForObject(sql, new FeeRuleMapper(), id);
			return Optional.ofNullable(feeRule);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<FeeRuleModel> findAll(UserBean user) {
		String sql = "SELECT * FROM fee_rules WHERE deleted = '0' AND (tenant_id IS NULL OR tenant_id = ?)";
		return jdbcTemplate.query(sql, new FeeRuleMapper(), user.getTenantId());
	}

	@Override
	public void update(FeeRuleModel feeRule) {
		// Validate foreign keys
		validateForeignKeys(feeRule);
		String sql = "UPDATE fee_rules SET rule_name = ?, rule_type_id = ?, start_date = ?, end_date = ?, updated_at = ?, amount = ?, due_date = ?, package_id = ?, institute_id = ?, taluka_id = ?, district_id = ?, state_id = ?, division_id = ?, amount_type = ?, rule_code = ?, role_id = ?, incentive_cap = ?, rules_amount = ?, package_type = ?, quantity = ?, parent_package_ids = ?, tenant_id = ? WHERE id = ? AND deleted = '0'";
		jdbcTemplate.update(sql, feeRule.getRuleName(), feeRule.getRuleTypeId(), feeRule.getStartDate(),
				feeRule.getEndDate(), feeRule.getUpdatedAt(), feeRule.getAmount(), feeRule.getDueDate(),
				feeRule.getPackageId(), feeRule.getInstituteId(), feeRule.getTalukaId(), feeRule.getDistrictId(),
				feeRule.getStateId(), feeRule.getDivisionId(), feeRule.getAmountType(), feeRule.getRuleCode(),
				feeRule.getRoleId(), feeRule.getIncentiveCap(), feeRule.getRulesAmount(), feeRule.getPackageType(),
				feeRule.getQuantity(), feeRule.getParentPackageIds(), feeRule.getTenantId(), feeRule.getId());
	}

	@Override
	public void delete(Integer id) {
		String sql = "UPDATE fee_rules SET deleted = '1', deleted_at = ? WHERE id = ? AND deleted = '0'";
		jdbcTemplate.update(sql, java.time.LocalDateTime.now(), id);
	}

	@Override
	public boolean existByRuleNameAndTenantId(String ruleName, Long tenantId) {
		String sql = "SELECT COUNT(*) FROM fee_rules WHERE rule_name = ? AND (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ruleName, tenantId);
		return count != null && count > 0;
	}

	@Override
	public boolean existsByRuleNameAndTenantIdExceptId(String ruleName, Long tenantId, Integer excludeId) {
		String sql = "SELECT COUNT(*) FROM fee_rules WHERE rule_name = ? AND (tenant_id = ? OR tenant_id is null) AND id != ? AND deleted = '0'";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ruleName, tenantId, excludeId);
		return count != null && count > 0;
	}

	private void validateForeignKeys(FeeRuleModel feeRule) {
		if (feeRule.getRuleName() == null || feeRule.getRuleName().isBlank()) {
			throw new IllegalArgumentException("Rule name cannot be null or empty");
		}
		if (feeRule.getRuleTypeId() != null) {
			String sql = "SELECT COUNT(*) FROM rule_types WHERE id = ? AND deleted = '0'";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getRuleTypeId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid rule type ID");
			}
		}
		if (feeRule.getPackageId() != null) {
			String sql = "SELECT COUNT(*) FROM packages WHERE id = ? AND deleted = '0'";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getPackageId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid package ID");
			}
		}
		if (feeRule.getInstituteId() != null) {
			String sql = "SELECT COUNT(*) FROM institutes WHERE id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getInstituteId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid institute ID");
			}
		}
		if (feeRule.getTalukaId() != null) {
			String sql = "SELECT COUNT(*) FROM taluka WHERE id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getTalukaId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid taluka ID");
			}
		}
		if (feeRule.getDistrictId() != null) {
			String sql = "SELECT COUNT(*) FROM district WHERE id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getDistrictId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid district ID");
			}
		}
		if (feeRule.getStateId() != null) {
			String sql = "SELECT COUNT(*) FROM state WHERE id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getStateId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid state ID");
			}
		}
		if (feeRule.getDivisionId() != null) {
			String sql = "SELECT COUNT(*) FROM division WHERE id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getDivisionId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid division ID");
			}
		}
		if (feeRule.getRoleId() != null) {
			String sql = "SELECT COUNT(*) FROM role WHERE role_id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getRoleId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid role ID");
			}
		}
		if (feeRule.getTenantId() != null) {
			String sql = "SELECT COUNT(*) FROM tenant WHERE tenant_id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, feeRule.getTenantId());
			if (count == null || count == 0) {
				throw new IllegalArgumentException("Invalid tenant ID");
			}
		}
	}
}