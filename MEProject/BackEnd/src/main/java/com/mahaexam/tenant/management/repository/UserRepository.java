package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;

public interface UserRepository {
	UserBean save(UserBean user);

	Optional<UserBean> findById(Long userId);

	List<UserBean> findAll();

	UserBean update(UserBean user);

	void delete(Long userId);

	Optional<UserBean> findByUsername(String username);

    Optional<UserBean> findByMobileNo(String username);
}
