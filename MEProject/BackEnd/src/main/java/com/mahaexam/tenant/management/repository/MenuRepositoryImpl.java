package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.Menu;
import com.mahaexam.tenant.management.model.Menu.Permission;

@Repository
public class MenuRepositoryImpl implements MenuRepository {

	private final JdbcTemplate jdbcTemplate;

	public MenuRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Menu> findMenusByUserId(Long userId, Long roleId) {
		String sql = """
				   SELECT DISTINCT m.*, p.permission_id, p.name AS permission_name, p.type permission_type
					FROM user_role ur
					JOIN role_menu_permission rmp ON ur.role_id = rmp.role_id
					JOIN menus m ON m.menu_id = rmp.menu_id
					LEFT JOIN menu_feature_toggle mft ON mft.menu_id = m.menu_id
					
					LEFT JOIN permission p ON rmp.permission_id = p.permission_id
					WHERE ur.user_id = ?
					  AND rmp.role_id = ?
					  AND m.is_active = TRUE and mft.menu_id is null
					ORDER BY parent_Id, m.order_index
					""";
		/*
		 * 					  
		 * LEFT JOIN feature_toggles ft ON ft.feature_id = mft.feature_id
		 * AND (
					      ft.feature_id IS NULL OR
					      (
					          (ft.enabled = TRUE OR (ft.activation_date IS NOT NULL AND CURDATE() >= ft.activation_date))
					          AND (ft.expiration_date IS NULL OR CURDATE() <= ft.expiration_date)
					      )
					  )
		 */
		// Query and map rows
        List<Menu> menus = jdbcTemplate.query(sql, new MenuRowPermissionMapper(),userId, roleId);

        // Group by menu_id to aggregate permissions
        Map<Integer, Menu> menuMap = menus.stream()
                .collect(Collectors.toMap(
                        Menu::getMenuId,
                        menu -> menu,
                        (existing, replacement) -> {
                            List<Permission> existingPerms = new ArrayList<>(existing.getPermissions());
                            existingPerms.addAll(replacement.getPermissions());
                            existing.setPermissions(existingPerms);
                            return existing;
                        }
                ));

        // Convert map values to list and sort by orderIndex
        return menuMap.values().stream()
//                .sorted((m1, m2) -> Integer.compare(m1.getOrderIndex(), m2.getOrderIndex()))
                .collect(Collectors.toList());

//		return jdbcTemplate.query(sql, new MenuRowMapper(), userId, roleId);
	}

	@Override
	public List<Menu> findAll() {
		return jdbcTemplate.query("SELECT * FROM menus ORDER BY order_index", new MenuRowMapper());
	}

	@Override
	public Optional<Menu> findById(Integer menuId) {
		String sql = "SELECT * FROM menus WHERE menu_id = ?";
		List<Menu> result = jdbcTemplate.query(sql, new MenuRowMapper(), menuId);
		return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	}

	@Override
	public Integer save(Menu menu) {
		String sql = """
				    INSERT INTO menus (parent_id, name, path, icon, order_index, is_active)
				    VALUES (?, ?, ?, ?, ?, ?)
				""";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (menu.getParentId() != null)
				ps.setInt(1, menu.getParentId());
			else
				ps.setNull(1, java.sql.Types.INTEGER);
			ps.setString(2, menu.getName());
			ps.setString(3, menu.getPath());
			ps.setString(4, menu.getIcon());
			ps.setInt(5, menu.getOrderIndex());
			ps.setBoolean(6, menu.getIsActive());
			return ps;
		}, keyHolder);

		return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
	}

	@Override
	public int update(Menu menu) {
		String sql = """
				    UPDATE menus SET
				      parent_id = ?,
				      name = ?,
				      path = ?,
				      icon = ?,
				      order_index = ?,
				      is_active = ?
				    WHERE menu_id = ?
				""";

		return jdbcTemplate.update(sql, menu.getParentId(), menu.getName(), menu.getPath(), menu.getIcon(),
				menu.getOrderIndex(), menu.getIsActive(), menu.getMenuId());
	}

	@Override
	public int deleteById(Integer menuId) {
		return jdbcTemplate.update("DELETE FROM menus WHERE menu_id = ?", menuId);
	}
}