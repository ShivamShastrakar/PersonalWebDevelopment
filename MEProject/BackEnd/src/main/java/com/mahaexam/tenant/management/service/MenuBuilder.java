package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.bean.MenuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuBuilder {

    /**
     * Builds a hierarchical menu from a flat list of MenuBean objects.
     *
     * @param flatMenuList Flat list of menus with parentId references.
     * @return List of root MenuBean objects with subMenues populated and sorted.
     */
    public static List<MenuBean> buildMenuHierarchy(List<MenuBean> flatMenuList) {
        // Map menus by menuId for quick lookup
        Map<Integer, MenuBean> menuMap = new HashMap<>();
        for (MenuBean menu : flatMenuList) {
            if (menu.getSubMenues() == null) {
                menu.setSubMenues(new ArrayList<>()); // Initialize if null
            }
            menuMap.put(menu.getMenuId(), menu);
        }

        // List for root menus (those with parentId == null)
        List<MenuBean> rootMenus = new ArrayList<>();

        // Build hierarchy by attaching children to parents
        for (MenuBean menu : menuMap.values()) {
            if (menu.getParentId() == null) {
                rootMenus.add(menu);
            } else {
                MenuBean parent = menuMap.get(menu.getParentId());
                if (parent != null) {
                    parent.getSubMenues().add(menu);
                }
            }
        }

        // Sort all levels by orderIndex
        sortMenus(rootMenus);

        return rootMenus;
    }

    /**
     * Recursively sorts menus and their subMenues by orderIndex (null treated as 0).
     */
    private static void sortMenus(List<MenuBean> menus) {
        menus.sort((m1, m2) -> {
            int o1 = m1.getOrderIndex() != null ? m1.getOrderIndex() : 0;
            int o2 = m2.getOrderIndex() != null ? m2.getOrderIndex() : 0;
            return Integer.compare(o1, o2);
        });

        for (MenuBean menu : menus) {
            if (menu.getSubMenues() != null && !menu.getSubMenues().isEmpty()) {
                sortMenus(menu.getSubMenues());
            }
        }
    }
}

