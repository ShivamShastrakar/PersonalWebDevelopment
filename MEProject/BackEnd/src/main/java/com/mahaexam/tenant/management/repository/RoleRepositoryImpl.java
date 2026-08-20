package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.mahaexam.common.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.Role;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    Logger logger = LoggerFactory.getLogger(getClass());
	
	private final JdbcTemplate jdbcTemplate;

	public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Role save(Role role) {
		if (existsByNameIgnoreCaseAndTenantId(role.getName(), role.getTenantId(), null)) {
			throw new ValidationException("Role with the same name already exists for this tenant.");
		}
		String sql = "INSERT INTO role (tenant_id, name, description, is_active, is_assignable, created_at) VALUES (?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] { "role_id" });
			ps.setObject(1, role.getTenantId());
			ps.setString(2, role.getName());
			ps.setString(3, role.getDescription());
			ps.setBoolean(4, role.getIsActive() != null ? role.getIsActive() : true);
			ps.setBoolean(5, role.getIsAssignable() != null ? role.getIsAssignable() : true);
			ps.setObject(6, role.getCreatedAt() != null ? Timestamp.valueOf(role.getCreatedAt())
					: Timestamp.valueOf(LocalDateTime.now()));
			return ps;
		}, keyHolder);

		role.setRoleId(keyHolder.getKey().longValue());
		return role;
	}

	@Override
	public Optional<Role> findById(Long roleId) {
		String sql = "SELECT * FROM role WHERE role_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public Role update(Role role) {
		if (existsByNameIgnoreCaseAndTenantId(role.getName(), role.getTenantId(), role.getRoleId())) {
			throw new ValidationException("Another role with the same name already exists for this tenant.");
		}
		String sql = "UPDATE role SET tenant_id = ?, name = ?, description = ?, is_active = ?, is_assignable = ?, updated_at = CURRENT_TIMESTAMP WHERE role_id = ?";
		jdbcTemplate.update(sql, role.getTenantId(), role.getName(), role.getDescription(),
				role.getIsActive() != null ? role.getIsActive() : true,
				role.getIsAssignable() != null ? role.getIsAssignable() : true, role.getRoleId());
		return role;
	}

	@Override
	public void delete(Long roleId) {
		String sql = "update role set is_active=0 WHERE role_id = ?";
		jdbcTemplate.update(sql, roleId);
	}

	@Override
	public List<Role> findRolesByUserId(Long userId) {
		String sql = "SELECT r.* FROM role r JOIN user_role ur ON r.role_id = ur.role_id WHERE ur.user_id = ?";
		return jdbcTemplate.query(sql, new RoleRowMapper(), userId);
	}

	@Override
	public Optional<Role> findByName(String roleName) {
		String sql = "SELECT * FROM role WHERE name = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleName));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean hasRole(Long userId) {
		if (userId == null) {
			return false;
		}
		String sql = "SELECT COUNT(*) FROM user_role ur WHERE ur.user_id = ?";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
		return count != null && count > 0;
	}

	@Override
	public List<Role> findRolesByUserRoleIds(Long userId, List<Long> roleIds) {
		// Check if roleIds is empty to avoid invalid SQL
		if (roleIds == null || roleIds.isEmpty()) {
			return Collections.emptyList();
		}

		String inClause = String.join(",", Collections.nCopies(roleIds.size(), "?"));
	    String sql = "SELECT r.* FROM role r JOIN user_role ur ON r.role_id = ur.role_id WHERE ur.user_id = ? AND r.role_id IN (" + inClause + ")";
	    
	    // Prepare parameters: userId followed by roleIds
	    Object[] params = new Object[1 + roleIds.size()];
	    params[0] = userId;
	    for (int i = 0; i < roleIds.size(); i++) {
	        params[i + 1] = roleIds.get(i);
	    }

	    logger.debug("Executing SQL: {} with userId: {}, roleIds: {}", sql, userId, roleIds);
	    
	    try {
	        List<Role> roles = jdbcTemplate.query(sql, new RoleRowMapper(), params);
	        logger.info("Found {} roles for userId: {}", roles.size(), userId);
	        return roles;
	    } catch (Exception e) {
	        logger.error("Error executing query for userId: {}, roleIds: {}: {}", userId, roleIds, e.getMessage());
	        throw e; // Re-throw to allow caller to handle or return empty list if preferred
	    }
	}
	
	@Override
	public List<Role> findAll() {
        String sql = "SELECT * FROM role where is_active=1";
        return jdbcTemplate.query(sql, new RoleRowMapper());
    }

	@Override
	public boolean existsByNameIgnoreCaseAndTenantId(String roleName, Long tenantId, Long excludeRoleId) {
		String sql;
		Object[] params;

		if (excludeRoleId != null) {
			sql = "SELECT COUNT(*) FROM role WHERE LOWER(name) = LOWER(?) AND tenant_id = ? AND role_id != ?";
			params = new Object[]{roleName, tenantId, excludeRoleId};
		} else {
			sql = "SELECT COUNT(*) FROM role WHERE LOWER(name) = LOWER(?) AND tenant_id = ?";
			params = new Object[]{roleName, tenantId};
		}

		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, params);
		return count != null && count > 0;
	}
    @Override
    public List<Role> findRolesByNames(List<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return List.of();
        }
        // Build placeholders like (?, ?, ?, ...)
        String inSql = String.join(",", Collections.nCopies(roleNames.size(), "?"));
        String sql = "SELECT * FROM role WHERE name IN (" + inSql + ")";
        try {
            return jdbcTemplate.query(sql, new RoleRowMapper(), roleNames.toArray());
        } catch (Exception e) {
            return List.of();
        }
    }

}
