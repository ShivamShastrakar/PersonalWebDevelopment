package com.mahaexam.tenant.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mahaexam.tenant.management.bean.MenuBean;
import com.mahaexam.tenant.management.model.Menu;
import com.mahaexam.tenant.management.model.Menu.Permission;
import com.mahaexam.tenant.management.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;

	public MenuServiceImpl(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Override
	public List<Menu> getAllMenus() {
		return menuRepository.findAll();
	}

	@Override
	public Optional<Menu> getMenuById(Integer menuId) {
		return menuRepository.findById(menuId);
	}

	@Override
	public List<MenuBean> getMenusForUser(Long userId, Long roleId, boolean withHierarchy) {
		List<Menu> menus = menuRepository.findMenusByUserId(userId, roleId);
		List<MenuBean> menuBeans = new ArrayList<>();
		if (Objects.nonNull(menus) && !menus.isEmpty()) {
			for (Menu menu : menus) {
				MenuBean menuBean = new MenuBean();
				menuBean.setMenuId(menu.getMenuId());
				menuBean.setParentId(menu.getParentId());
				menuBean.setName(menu.getName());
				menuBean.setPath(menu.getPath());
				menuBean.setOrderIndex(menu.getOrderIndex());
				menuBean.setPermissions(menu.getPermissions());
				menuBeans.add(menuBean);
			}
		}
        if(withHierarchy){
            return MenuBuilder.buildMenuHierarchy(menuBeans);
        }
        return menuBeans;
	}
	public String getCommaSeparatedPermissionNames(List<Permission> permissions) {
	    if (permissions == null || permissions.isEmpty()) {
	        return "";  // Return empty string for null or empty list
	    }

	    return permissions.stream()
	            .map(Permission::getPermissionName)  // Extract permissionName
	            .filter(name -> name != null && !name.trim().isEmpty())  // Filter out null/empty names
	            .map(String::trim)  // Trim spaces
	            .collect(Collectors.joining(","));  // Join with commas
	}
	@Override
	public Integer createMenu(Menu menu) {
		return menuRepository.save(menu);
	}

	@Override
	public int updateMenu(Menu menu) {
		return menuRepository.update(menu);
	}

	@Override
	public int deleteMenu(Integer menuId) {
		return menuRepository.deleteById(menuId);
	}
}