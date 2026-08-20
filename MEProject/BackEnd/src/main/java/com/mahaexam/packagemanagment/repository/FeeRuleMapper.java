package com.mahaexam.packagemanagment.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.packagemanagment.model.FeeRuleModel;

public class FeeRuleMapper implements RowMapper<FeeRuleModel> {
	@Override
	public FeeRuleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeeRuleModel feeRule = new FeeRuleModel();
		feeRule.setId(rs.getInt("id"));
		feeRule.setRuleName(rs.getString("rule_name"));
		feeRule.setRuleTypeId(rs.getObject("rule_type_id", Integer.class));
		feeRule.setStartDate(
				rs.getTimestamp("start_date") != null ? rs.getTimestamp("start_date").toLocalDateTime() : null);
		feeRule.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date").toLocalDateTime() : null);
		feeRule.setCreatedAt(
				rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
		feeRule.setUpdatedAt(
				rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
		feeRule.setDeletedAt(
				rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
		feeRule.setDeleted(rs.getString("deleted"));
		feeRule.setAmount(rs.getBigDecimal("amount"));
		feeRule.setDueDate(rs.getTimestamp("due_date") != null ? rs.getTimestamp("due_date").toLocalDateTime() : null);
		feeRule.setPackageId(rs.getObject("package_id", Integer.class));
		feeRule.setInstituteId(rs.getObject("institute_id", Integer.class));
		feeRule.setTalukaId(rs.getObject("taluka_id", Integer.class));
		feeRule.setDistrictId(rs.getObject("district_id", Integer.class));
		feeRule.setStateId(rs.getObject("state_id", Integer.class));
		feeRule.setDivisionId(rs.getObject("division_id", Integer.class));
		feeRule.setAmountType(rs.getString("amount_type"));
		feeRule.setRuleCode(rs.getString("rule_code"));
		feeRule.setRoleId(rs.getObject("role_id", Long.class));
		feeRule.setIncentiveCap(rs.getObject("incentive_cap", Integer.class));
		feeRule.setRulesAmount(rs.getString("rules_amount"));
		feeRule.setPackageType(rs.getString("package_type"));
		feeRule.setQuantity(rs.getObject("quantity", Integer.class));
		feeRule.setParentPackageIds(rs.getString("parent_package_ids"));
		feeRule.setTenantId(rs.getObject("tenant_id", Long.class));
		return feeRule;
	}
}