package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.Permission;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {
	private final JdbcTemplate jdbcTemplate;

	public PermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Permission save(Permission permission) {
		String sql = "INSERT INTO permission (name, description, created_at,type) VALUES (?, ?, ?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] { "permission_id" });
			ps.setString(1, permission.getName());
			ps.setString(2, permission.getDescription());
			ps.setObject(3, permission.getCreatedAt() != null ? Timestamp.valueOf(permission.getCreatedAt())
					: Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(4, permission.getType());
			return ps;
		}, keyHolder);

		permission.setPermissionId(keyHolder.getKey().longValue());
		return permission;
	}

	@Override
	public Optional<Permission> findById(Long permissionId) {
		String sql = "SELECT * FROM permission WHERE permission_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new PermissionRowMapper(), permissionId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Permission> findByName(String name) {
		String sql = "SELECT * FROM permission WHERE name = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new PermissionRowMapper(), name));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Permission> findAll() {
		String sql = "SELECT * FROM permission";
		return jdbcTemplate.query(sql, new PermissionRowMapper());
	}

	@Override
	public Permission update(Permission permission) {
		String sql = "UPDATE permission SET name = ?, description = ?, type = ? WHERE permission_id = ?";
		jdbcTemplate.update(sql, permission.getName(), permission.getDescription(), permission.getType(), permission.getPermissionId());
		return permission;
	}

	@Override
	public void delete(Long permissionId) {
		String sql = "DELETE FROM permission WHERE permission_id = ?";
		jdbcTemplate.update(sql, permissionId);
	}

	@Override
	public List<Permission> findPermissionsByRoleId(Long roleId) {
		String sql = "SELECT p.* FROM permission p JOIN role_permission rp ON p.permission_id = rp.permission_id WHERE rp.role_id = ?";
		return jdbcTemplate.query(sql, new PermissionRowMapper(), roleId);
	}
}