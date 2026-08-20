package com.mahaexam.tenant.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.tenant.management.bean.MenuBean;
import com.mahaexam.tenant.management.service.MenuService;

@RestController
@RequestMapping("/api/menus")
public class MenuController extends BaseController {
	 private final MenuService menuService;

	    public MenuController(MenuService menuService) {
	        this.menuService = menuService;
	    }
	    
	    // 👤 Get Menus for User (Role + Feature Toggle Logic)
	    @GetMapping("/user/{roleId}")
	    public List<MenuBean> getMenusByUser(@PathVariable Long roleId) {
	    	UserBean user =  getUser();
	        return menuService.getMenusForUser(user.getUserId(),roleId,false);
	    }

    @GetMapping("/WithHierarchy/user/{roleId}")
    public List<MenuBean> getMenusByUserWithHierarchy(@PathVariable Long roleId) {
        UserBean user =  getUser();
        return menuService.getMenusForUser(user.getUserId(),roleId,true);
    }
}
