package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.UserTenant;

@Repository
public class UserTenantRepositoryImpl implements UserTenantRepository {
	private final JdbcTemplate jdbcTemplate;

	public UserTenantRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public UserTenant save(UserTenant userTenant) {
		String sql = "INSERT INTO user_tenant (user_id, tenant_id, created_at) VALUES (?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, userTenant.getUserId());
			ps.setLong(2, userTenant.getTenantId());
			ps.setTimestamp(3, Timestamp
					.valueOf(userTenant.getCreatedAt() != null ? userTenant.getCreatedAt() : LocalDateTime.now()));
			return ps;
		}, keyHolder);

		// Retrieve and set the generated ID
		Number key = keyHolder.getKey();
		if (key != null) {
			userTenant.setId(key.longValue());
		}

		return userTenant;

	}

	@Override
	public Optional<UserTenant> findById(Long id) {
		String sql = "SELECT * FROM user_tenant WHERE id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserTenantRowMapper(), id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<UserTenant> findAll() {
		String sql = "SELECT * FROM user_tenant";
		return jdbcTemplate.query(sql, new UserTenantRowMapper());
	}

	@Override
	public UserTenant update(UserTenant userTenant) {
		String sql = "UPDATE user_tenant SET user_id = ?, tenant_id = ?, created_at = ? WHERE id = ?";

		jdbcTemplate.update(sql, userTenant.getUserId(), userTenant.getTenantId(), userTenant.getCreatedAt(),
				userTenant.getId());
		return userTenant;
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM user_tenant WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<UserTenant> findByUserId(Long userId) {
		String sql = "SELECT * FROM user_tenant WHERE user_id = ?";
		return jdbcTemplate.query(sql, new UserTenantRowMapper(), userId);
	}
}