package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;

public interface UserService {
	UserBean save(UserBean user);

	Optional<UserBean> findById(Long userId, boolean fullData);

	List<UserBean> findAll();

	UserBean update(UserBean user);

	void delete(Long userId);

	Optional<UserBean> findByUsername(String username, boolean fullData);

    Optional<UserBean> findByMoblieNo(String mobileNo, boolean fullData);

	void assignRole(Long userId, Long roleId);

	void unassignRole(Long userId, Long roleId);

	void assignRoles(Long userId, List<Long> roleIds);

    void assignRolesByName(Long userId, List<String> roleIds);

}
