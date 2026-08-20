package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.Menu;

public class MenuRowMapper implements RowMapper<Menu> {
	
	@Override
	public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Menu.builder().menuId(rs.getInt("menu_id"))
				.parentId(rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null).name(rs.getString("name"))
				.path(rs.getString("path")).icon(rs.getString("icon")).orderIndex(rs.getInt("order_index"))
				.isActive(rs.getBoolean("is_active")).build();
	}
}