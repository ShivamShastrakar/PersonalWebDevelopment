package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserRoleHierarchyLevelMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRoleHierarchyLevelMappingRepositoryImpl implements UserRoleHierarchyLevelMappingRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleHierarchyLevelMappingRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UserRoleHierarchyLevelMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserRoleHierarchyLevelMapping save(UserRoleHierarchyLevelMapping mapping) {
        String sql = "INSERT INTO user_role_hierarchy_level_mappings (role_id, user_hierarchy_level_id, created_at) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, mapping.getUserRoleId());
            ps.setInt(2, mapping.getUserHierarchyLevelId());
            ps.setObject(3, mapping.getCreatedAt() != null ? Timestamp.valueOf(mapping.getCreatedAt())
                    : Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            mapping.setId(key.intValue());
        }

        logger.info("UserRoleHierarchyLevelMapping saved with ID: {}", mapping.getId());
        return mapping;
    }

    @Override
    public Optional<UserRoleHierarchyLevelMapping> findById(Integer id) {
        String sql = "SELECT * FROM user_role_hierarchy_level_mappings WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRoleHierarchyLevelMappingRowMapper(), id));
        } catch (Exception e) {
            logger.error("Error finding UserRoleHierarchyLevelMapping by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public UserRoleHierarchyLevelMapping update(UserRoleHierarchyLevelMapping mapping) {
        String sql = "UPDATE user_role_hierarchy_level_mappings SET role_id = ?, user_hierarchy_level_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        jdbcTemplate.update(sql, mapping.getUserRoleId(), mapping.getUserHierarchyLevelId(), mapping.getId());

        logger.info("UserRoleHierarchyLevelMapping updated with ID: {}", mapping.getId());
        return mapping;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM user_role_hierarchy_level_mappings WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("UserRoleHierarchyLevelMapping deleted with ID: {}", id);
    }

    @Override
    public List<UserRoleHierarchyLevelMapping> findAll() {
        String sql = "SELECT * FROM user_role_hierarchy_level_mappings";
        return jdbcTemplate.query(sql, new UserRoleHierarchyLevelMappingRowMapper());
    }

    @Override
    public List<UserRoleHierarchyLevelMapping> findByUserRoleId(Long userRoleId) {
        String sql = "SELECT * FROM user_role_hierarchy_level_mappings WHERE role_id = ?";
        return jdbcTemplate.query(sql, new UserRoleHierarchyLevelMappingRowMapper(), userRoleId);
    }

    @Override
    public List<UserRoleHierarchyLevelMapping> findByUserHierarchyLevelId(Integer hierarchyLevelId) {
        String sql = "SELECT * FROM user_role_hierarchy_level_mappings WHERE user_hierarchy_level_id = ?";
        return jdbcTemplate.query(sql, new UserRoleHierarchyLevelMappingRowMapper(), hierarchyLevelId);
    }

    @Override
    public Optional<UserRoleHierarchyLevelMapping> findByUserRoleIdAndHierarchyLevelId(Long userRoleId, Integer hierarchyLevelId) {
        String sql = "SELECT * FROM user_role_hierarchy_level_mappings WHERE role_id = ? AND user_hierarchy_level_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRoleHierarchyLevelMappingRowMapper(), userRoleId, hierarchyLevelId));
        } catch (Exception e) {
            logger.error("Error finding UserRoleHierarchyLevelMapping by role ID and hierarchy level ID", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM user_role_hierarchy_level_mappings WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
