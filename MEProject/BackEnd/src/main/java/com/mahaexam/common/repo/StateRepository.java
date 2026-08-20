package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.State;

public interface StateRepository {
	List<State> findAll(UserBean user);

	Optional<State> findById(UserBean user, Integer id);

	int insert(UserBean user, State state);

	int update(UserBean user, State state);

	boolean existsByStateNameAndTenantIdExceptId(String stateName, Long tenantId, int excludeId);

	void deleteState(Integer id);
}