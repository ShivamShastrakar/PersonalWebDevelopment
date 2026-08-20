package com.mahaexam.tenant.management.repository;

import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public UserBean save(UserBean user) {
		String sql = "INSERT INTO users (tenant_id, username, password_hash, is_active, is_salt, created_at) "
				+ "VALUES (?, ?, ?,?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, user.getTenantId());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getPassword());
			ps.setBoolean(4, user.getIsActive() != null ? user.getIsActive() : true);
			ps.setBoolean(5, user.getIsSalt() != null ? user.getIsSalt() : false);
			ps.setTimestamp(6,
					Timestamp.valueOf(user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now()));
			return ps;
		}, keyHolder);

		// Set generated key to the user
		Number key = keyHolder.getKey();
		if (key != null) {
			user.setUserId(key.longValue());
		}

		return user;
	}

	@Override
	public Optional<UserBean> findById(Long userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<UserBean> findAll() {
		String sql = "SELECT * FROM users";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public UserBean update(UserBean user) {
		String sql = "UPDATE users SET tenant_id = ?, username = ?, password_hash = ?,  created_at = ?,is_salt =? "
				+ " WHERE user_id = ?";

		jdbcTemplate.update(sql, user.getTenantId(), user.getUserName(), user.getPassword(),
				user.getCreatedAt(),user.getIsSalt(), user.getUserId());
		return user;
	}

	@Override
	public void delete(Long userId) {
		String sql = "UPDATE users SET  is_active = '0' WHERE user_id = ?";
		jdbcTemplate.update(sql, userId);
	}

	@Override
	public Optional<UserBean> findByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRowMapper(), username));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

    @Override
    public Optional<UserBean> findByMobileNo(String mobileNo) {
        String sql = """
                     
                select u.* from users u
                     inner join application_user au
                     on u.user_id = au.user_id
                     where au.registered_mobile_number =?
                     limit 1
                     """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRowMapper(), mobileNo));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
