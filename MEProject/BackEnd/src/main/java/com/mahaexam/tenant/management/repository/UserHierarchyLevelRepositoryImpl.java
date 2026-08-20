package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserHierarchyLevel;
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
public class UserHierarchyLevelRepositoryImpl implements UserHierarchyLevelRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserHierarchyLevelRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UserHierarchyLevelRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserHierarchyLevel save(UserHierarchyLevel userHierarchyLevel) {
        String sql = "INSERT INTO user_hierarchy_level (level_name, description, level_order, tenant_id, created_at) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userHierarchyLevel.getLevelName());
            ps.setString(2, userHierarchyLevel.getDescription());
            ps.setInt(3, userHierarchyLevel.getLevelOrder());
            ps.setLong(4, userHierarchyLevel.getTenantId());
            ps.setObject(5, userHierarchyLevel.getCreatedAt() != null ? Timestamp.valueOf(userHierarchyLevel.getCreatedAt())
                    : Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            userHierarchyLevel.setId(key.intValue());
        }

        logger.info("UserHierarchyLevel saved with ID: {}", userHierarchyLevel.getId());
        return userHierarchyLevel;
    }

    @Override
    public Optional<UserHierarchyLevel> findById(Integer id) {
        String sql = "SELECT * FROM user_hierarchy_level WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserHierarchyLevelRowMapper(), id));
        } catch (Exception e) {
            logger.error("Error finding UserHierarchyLevel by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserHierarchyLevel> findByLevelName(String levelName) {
        String sql = "SELECT * FROM user_hierarchy_level WHERE level_name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserHierarchyLevelRowMapper(), levelName));
        } catch (Exception e) {
            logger.error("Error finding UserHierarchyLevel by level name: {}", levelName, e);
            return Optional.empty();
        }
    }

    @Override
    public List<UserHierarchyLevel> findByTenantId(Long tenantId) {
        String sql = "SELECT * FROM user_hierarchy_level WHERE tenant_id = ? ORDER BY level_order ASC";
        try {
            return jdbcTemplate.query(sql, new UserHierarchyLevelRowMapper(), tenantId);
        } catch (Exception e) {
            logger.error("Error finding UserHierarchyLevel by tenant ID: {}", tenantId, e);
            return List.of();
        }
    }
    
    @Override
    public UserHierarchyLevel findByByGivenLevelOrderIdAndTenantId(Long tenantId, Integer levelOrderId){
    		String sql = "SELECT * FROM user_hierarchy_level WHERE tenant_id = ? AND level_order = ? ORDER BY level_order ASC";
		try {
			List<UserHierarchyLevel> results = jdbcTemplate.query(sql, new UserHierarchyLevelRowMapper(), tenantId, levelOrderId);
			return results.isEmpty() ? null : results.get(0);
		} catch (Exception e) {
			logger.error("Error finding UserHierarchyLevel by tenant ID: {} and level order ID: {}", tenantId, levelOrderId, e);
			return null;
		}
    }

    @Override
    public UserHierarchyLevel update(UserHierarchyLevel userHierarchyLevel) {
        String sql = "UPDATE user_hierarchy_level SET level_name = ?, description = ?, level_order = ?, tenant_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        jdbcTemplate.update(sql, userHierarchyLevel.getLevelName(), userHierarchyLevel.getDescription(),
                userHierarchyLevel.getLevelOrder(), userHierarchyLevel.getTenantId(), userHierarchyLevel.getId());

        logger.info("UserHierarchyLevel updated with ID: {}", userHierarchyLevel.getId());
        return userHierarchyLevel;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM user_hierarchy_level WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("UserHierarchyLevel deleted with ID: {}", id);
    }

    @Override
    public List<UserHierarchyLevel> findAll() {
        String sql = "SELECT * FROM user_hierarchy_level ORDER BY level_order ASC";
        return jdbcTemplate.query(sql, new UserHierarchyLevelRowMapper());
    }

    @Override
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM user_hierarchy_level WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
