package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.bean.MenuBean;
import com.mahaexam.tenant.management.model.Menu;

public interface MenuService {
    List<Menu> getAllMenus();

    Optional<Menu> getMenuById(Integer menuId);

    List<MenuBean> getMenusForUser(Long userId, Long roleId, boolean withHierarchy);

    Integer createMenu(Menu menu);

    int updateMenu(Menu menu);

    int deleteMenu(Integer menuId);
}