package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.bean.UserBean;

public class UserRowMapper implements RowMapper<UserBean> {
	@Override
	public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBean user = new UserBean();
		user.setUserId(rs.getLong("user_id"));
		user.setTenantId(rs.getLong("tenant_id"));
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password_hash"));
		user.setIsActive(rs.getBoolean("is_active"));
		user.setIsSalt(rs.getBoolean("is_salt"));
		
		user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		return user;
	}
}