package com.mahaexam.common.repo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.State;

@Repository
public class StateRepositoryImpl implements StateRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<State> findAll(UserBean user) {
		String sql = "SELECT * FROM state WHERE deleted = '0'";
		if (!Objects.isNull(user)) {
			sql = sql + " AND tenant_id = ?";
			return jdbcTemplate.query(sql, new StateRowMapper(), user.getTenantId());
		}
		return jdbcTemplate.query(sql, new StateRowMapper());
	}

	@Override
	public Optional<State> findById(UserBean user, Integer id) {
		String sql = "SELECT * FROM state WHERE id = ? AND deleted = '0'";
		try {
			if (!Objects.isNull(user)) {
				sql = sql + " AND tenant_id = ?";
				State state = jdbcTemplate.queryForObject(sql, new StateRowMapper(), id, user.getTenantId());
				return Optional.ofNullable(state);
			}
			State state = jdbcTemplate.queryForObject(sql, new StateRowMapper(), id);
			return Optional.ofNullable(state);
		} catch (EmptyResultDataAccessException e) {
			// No record found
			return Optional.empty();
		}
	}

	@Override
	public int insert(UserBean user, State state) {
		String sql = "INSERT INTO state (state_name, state_alias_name, created_at, deleted, tenant_id) "
				+ "VALUES (?, ?, NOW(), '0', ?)";
		return jdbcTemplate.update(sql, state.getStateName(), state.getStateAliasName(), 
				state.getTenantId());
	}

	@Override
	public int update(UserBean user, State state) {
		String sql = "UPDATE state SET state_name = ?, state_alias_name = ?, updated_at = NOW() "
				+ "WHERE id = ? AND deleted = '0'";
		return jdbcTemplate.update(sql, state.getStateName(), state.getStateAliasName(),
				state.getId());
	}

	@Override
	public boolean existsByStateNameAndTenantIdExceptId(String stateName, Long tenantId, int excludeId) {
		String sql = "SELECT COUNT(*) FROM state WHERE state_name = ? AND (tenant_id = ? OR tenant_id is null) AND id <> ?";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, stateName, tenantId, excludeId);
		return count != null && count > 0;
	}

	@Override
	public void deleteState(Integer id) {
		String sql = "UPDATE state SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
