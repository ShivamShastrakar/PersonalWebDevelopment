package com.mahaexam.tenant.management.bean;

import java.util.List;

import com.mahaexam.tenant.management.model.Menu.Permission;

import lombok.Data;

@Data
public class MenuBean {
	Integer menuId;
    Integer parentId;
    String name;
    String path;
    Integer orderIndex;
    List<MenuBean> subMenues;
    List<Permission> permissions;
}
