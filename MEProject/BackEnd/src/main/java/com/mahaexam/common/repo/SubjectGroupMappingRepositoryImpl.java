package com.mahaexam.common.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.SubjectGroupMapping;

@Repository
public class SubjectGroupMappingRepositoryImpl implements SubjectGroupMappingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SubjectGroupMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SubjectGroupMapping mapping) {
        String sql = "INSERT INTO subject_group_mapping (group_id, subject_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, mapping.getGroupId(), mapping.getSubjectId());
    }

    @Override
    public int softDelete(int mappingId) {
        String sql = "UPDATE subject_group_mapping SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE mapping_id = ?";
        return jdbcTemplate.update(sql, mappingId);
    }

    @Override
    public List<SubjectGroupMapping> findAll() {
        String sql = "SELECT * FROM subject_group_mapping WHERE deleted = '0'";
        return jdbcTemplate.query(sql, new SubjectGroupMappingRowMapper());
    }

    @Override
    public SubjectGroupMapping findById(int mappingId) {
        String sql = "SELECT * FROM subject_group_mapping WHERE mapping_id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new SubjectGroupMappingRowMapper(), mappingId);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }
}
