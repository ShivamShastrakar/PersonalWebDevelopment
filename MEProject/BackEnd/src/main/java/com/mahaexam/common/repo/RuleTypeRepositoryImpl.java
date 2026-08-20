package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.RuleTypeModel;

@Repository
public class RuleTypeRepositoryImpl implements RuleTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public RuleTypeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RuleTypeModel save(RuleTypeModel ruleType) {
        // Validate tenant_id and unique rule_type
        validateTenantId(ruleType.getTenantId());
        validateUniqueRuleType(ruleType.getRuleType(), null);
        String sql = "INSERT INTO rule_types (rule_type, created_at, created_by, updated_at, deleted_at, deleted, updated_by, tenant_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                ruleType.getRuleType(),
                ruleType.getCreatedAt(),
                ruleType.getCreatedBy(),
                ruleType.getUpdatedAt(),
                ruleType.getDeletedAt(),
                ruleType.getDeleted(),
                ruleType.getUpdatedBy(),
                ruleType.getTenantId());
        return ruleType;
    }

    @Override
    public Optional<RuleTypeModel> findById(Integer id) {
        String sql = "SELECT * FROM rule_types WHERE id = ? AND deleted = '0'";
        try {
            RuleTypeModel ruleType = jdbcTemplate.queryForObject(sql,  new RuleTypeMapper(), id);
            return Optional.ofNullable(ruleType);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<RuleTypeModel> findAll(UserBean user) {
        String sql = "SELECT * FROM rule_types WHERE deleted = '0' AND (tenant_id IS NULL OR tenant_id = ?)";
        return jdbcTemplate.query(sql, new RuleTypeMapper(), user.getTenantId());
    }

    @Override
    public void update(RuleTypeModel ruleType) {
        // Validate tenant_id and unique rule_type
        validateTenantId(ruleType.getTenantId());
        validateUniqueRuleType(ruleType.getRuleType(), ruleType.getId());
        String sql = "UPDATE rule_types SET rule_type = ?, updated_at = ?, updated_by = ?, tenant_id = ? WHERE id = ? AND deleted = '0'";
        jdbcTemplate.update(sql,
                ruleType.getRuleType(),
                ruleType.getUpdatedAt(),
                ruleType.getUpdatedBy(),
                ruleType.getTenantId(),
                ruleType.getId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE rule_types SET deleted = '1', deleted_at = ? WHERE id = ? AND deleted = '0'";
        jdbcTemplate.update(sql, java.time.LocalDateTime.now(), id);
    }

    private void validateTenantId(Long tenantId) {
        if (tenantId != null) {
            String sql = "SELECT COUNT(*) FROM tenant WHERE tenant_id = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tenantId);
            if (count == null || count == 0) {
                throw new IllegalArgumentException("Invalid tenant ID");
            }
        }
    }

    private void validateUniqueRuleType(String ruleType, Integer excludeId) {
        if (ruleType == null || ruleType.isBlank()) {
            throw new IllegalArgumentException("Rule type cannot be null or empty");
        }
        String sql = "SELECT COUNT(*) FROM rule_types WHERE rule_type = ? AND deleted = '0'";
        if (excludeId != null) {
            sql += " AND id != ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ruleType, excludeId);
            if (count != null && count > 0) {
                throw new IllegalArgumentException("Rule type '" + ruleType + "' already exists");
            }
        } else {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ruleType);
            if (count != null && count > 0) {
                throw new IllegalArgumentException("Rule type '" + ruleType + "' already exists");
            }
        }
    }
}