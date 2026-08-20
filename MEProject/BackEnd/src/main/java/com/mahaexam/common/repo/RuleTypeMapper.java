package com.mahaexam.common.repo;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.RuleTypeModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleTypeMapper implements RowMapper<RuleTypeModel> {
	@Override
	public RuleTypeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		RuleTypeModel ruleType = new RuleTypeModel();
		ruleType.setId(rs.getInt("id"));
		ruleType.setRuleType(rs.getString("rule_type"));
		ruleType.setCreatedAt(
				rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
		ruleType.setCreatedBy(rs.getObject("created_by", Integer.class));
		ruleType.setUpdatedAt(
				rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
		ruleType.setDeletedAt(
				rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
		ruleType.setDeleted(rs.getString("deleted"));
		ruleType.setUpdatedBy(rs.getObject("updated_by", Integer.class));
		ruleType.setTenantId(rs.getObject("tenant_id", Long.class));
		return ruleType;
	}
}