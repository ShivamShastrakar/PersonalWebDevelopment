package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.SubjectGroup;

@Repository
public class SubjectGroupRepositoryImpl implements SubjectGroupRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubjectGroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SubjectGroup> findAllByTenant(Long tenantId) {
        String sql = "SELECT * FROM subject_group WHERE (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
        return jdbcTemplate.query(sql, new SubjectGroupRowMapper(), tenantId);
    }

    @Override
    public SubjectGroup findById(int id) {
        String sql = "SELECT * FROM subject_group WHERE group_id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new SubjectGroupRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public int save(SubjectGroup group) {
        String sql = "INSERT INTO subject_group (tenant_id, group_name, description) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, group.getTenantId(), group.getGroupName(), group.getDescription());
    }

    @Override
    public int update(SubjectGroup group) {
        String sql = "UPDATE subject_group SET group_name = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE group_id = ?";
        return jdbcTemplate.update(sql, group.getGroupName(), group.getDescription(), group.getGroupId());
    }

    @Override
    public int softDelete(int id) {
        String sql = "UPDATE subject_group SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE group_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByGroupNameAndTenantId(String groupName, Long tenantId) {
        String sql = """
        SELECT COUNT(*) FROM subject_group
        WHERE group_name = ? AND (tenant_id = ? OR tenant_id is null) AND deleted = '0'
    """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, groupName, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByGroupNameAndTenantIdExceptId(String groupName, Long tenantId, int excludeId) {
        String sql = """
        SELECT COUNT(*) FROM subject_group
        WHERE group_name = ? AND (tenant_id = ? OR tenant_id is null) AND group_id != ? AND deleted = '0'
    """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, groupName, tenantId, excludeId);
        return count != null && count > 0;
    }

    @Override
    public Optional<SubjectGroup> findGroupByName(String subjectGroupName) {
        String sql = "SELECT * FROM subject_group WHERE group_name = ? AND deleted = '0'";
        try {
            SubjectGroup result = jdbcTemplate.queryForObject(sql, new SubjectGroupRowMapper(), subjectGroupName);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
