package com.mahaexam.packagemanagment.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.packagemanagment.model.PackageCategoryModel;

@Repository
public class PackageCategoryRepositoryImpl implements PackageCategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public PackageCategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PackageCategoryModel save(PackageCategoryModel model) {
        String sql = "INSERT INTO `package_category` (`name`, `description`, `tenant_id`, `created_date`, `created_by`) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getName());
            ps.setString(2, model.getDescription());
            ps.setObject(3, model.getTenantId(), java.sql.Types.BIGINT);
            ps.setObject(4, model.getCreatedDate(), java.sql.Types.TIMESTAMP);
            ps.setObject(5, model.getCreatedBy(), java.sql.Types.INTEGER);
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            model.setId(generatedId.intValue());
        }
        return model;
    }

    @Override
    public Optional<PackageCategoryModel> findById(Integer id) {
        String sql = "SELECT * FROM `package_category` WHERE `id` = ?";
        try {
            PackageCategoryModel model = jdbcTemplate.queryForObject(sql, new PackageCategoryMapper(), id);
            return Optional.ofNullable(model);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PackageCategoryModel> findAll(Long tenantId) {
        String sql = "SELECT * FROM `package_category` WHERE `tenant_id` = ? ORDER BY `name` ASC";
        return jdbcTemplate.query(sql, new PackageCategoryMapper(), tenantId);
    }

    @Override
    public void update(PackageCategoryModel model) {
        String sql = "UPDATE `package_category` SET `name` = ?, `description` = ?, `tenant_id` = ?, `created_by` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql,
                model.getName(),
                model.getDescription(),
                model.getTenantId(),
                model.getCreatedBy(),
                model.getId()
        );
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM `package_category` WHERE `id` = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByName(String name, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM `package_category` WHERE `name` = ? AND `tenant_id` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByNameExcludingId(String name, Integer excludeId, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM `package_category` WHERE `name` = ? AND `id` != ? AND `tenant_id` = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, excludeId, tenantId);
        return count != null && count > 0;
    }
}
