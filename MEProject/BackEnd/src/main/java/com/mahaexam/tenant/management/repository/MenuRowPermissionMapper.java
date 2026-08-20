package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.exception.ServiceException;
import com.mahaexam.tenant.management.model.Menu;

public class MenuRowPermissionMapper implements RowMapper<Menu> {
	@Override
	public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Check if permission columns exist and are not null
		Long permissionId = null;
		String permissionName = null;
		String permissionType = null;
		try {
			if (rs.getObject("permission_id") != null) {
				permissionId = rs.getLong("permission_id");
				permissionName = rs.getString("permission_name");
				permissionType = rs.getString("permission_type");
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}

		// Build Permission object if available
		List<Menu.Permission> permissions = (permissionId != null && permissionName != null)
				? Collections.singletonList(Menu.Permission.builder().permissionId(permissionId)
						.permissionName(permissionName).permissionType(permissionType).build())
				: Collections.emptyList();

		return Menu.builder().menuId(rs.getInt("menu_id"))
				.parentId(rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null).name(rs.getString("name"))
				.path(rs.getString("path")).icon(rs.getString("icon")).orderIndex(rs.getInt("order_index"))
				.isActive(rs.getBoolean("is_active")).permissions(permissions).build();
	}

}
