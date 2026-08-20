package com.mahaexam.common.repo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Division;

@Repository
public class DivisionRepositoryImpl implements DivisionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Division> findAll(UserBean user) {
		String sql = "SELECT * FROM division WHERE deleted = '0'";
		if (!Objects.isNull(user)) {
			sql = sql + " AND tenant_id = ?";
			return jdbcTemplate.query(sql, new DivisionRowMapper(), user.getTenantId());
		}
		return jdbcTemplate.query(sql, new DivisionRowMapper());
	}

	@Override
	public Optional<Division> findById(UserBean user, Integer id) {
		String sql = "SELECT * FROM division WHERE id = ?";
		try {
			if (!Objects.isNull(user)) {
				sql = sql + " AND tenant_id = ?";
				Division region = jdbcTemplate.queryForObject(sql, new DivisionRowMapper(), id, user.getTenantId());
				return Optional.ofNullable(region);
			}
			Division region = jdbcTemplate.queryForObject(sql, new DivisionRowMapper(), id);
			return Optional.ofNullable(region);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

    @Override
    public Optional<Division> findByName(UserBean user, String name) {
        String sql = "SELECT * FROM division WHERE division_name = ?";
        try {
            if (!Objects.isNull(user)) {
                sql = sql + " AND (tenant_id = ? OR tenant_id is null) limit 1";
                Division region = jdbcTemplate.queryForObject(sql, new DivisionRowMapper(), name, user.getTenantId());
                return Optional.ofNullable(region);
            }
            Division region = jdbcTemplate.queryForObject(sql, new DivisionRowMapper(), name);
            return Optional.ofNullable(region);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

	@Override
	public List<Division> findByStateId(UserBean user, int stateId) {
		String sql = "SELECT * FROM division WHERE state_id = ? AND deleted = '0'";
		if (!Objects.isNull(user)) {
			sql = sql + " AND tenant_id = ?";
			return jdbcTemplate.query(sql, new DivisionRowMapper(), user.getTenantId());
		}
		return jdbcTemplate.query(sql, new DivisionRowMapper(), stateId);
	}

	@Override
	public int insert(UserBean user, Division region) {
		String sql = "INSERT INTO division (division_name, division_code, created_at, deleted, state_id, tenant_id) "
				+ "VALUES (?, ?, NOW(), '0', ?, ?)";
		return jdbcTemplate.update(sql, region.getDivisionName(), region.getDivisionCode(), region.getStateId(),
				region.getTenantId());
	}

	@Override
	public int update(UserBean user, Division region) {
		String sql = "UPDATE division SET division_name = ?, division_code = ?, updated_at = NOW(), "
				+ "state_id = ? WHERE id = ? AND deleted = '0'";
		return jdbcTemplate.update(sql, region.getDivisionName(), region.getDivisionCode(), region.getStateId(),
				 region.getId());
	}

	@Override
	public void delete(Integer id){
		String sql = "UPDATE division SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

    @Override
    public boolean existsByNameAndIdNot(UserBean user, String name, Long divisionId) {
        String sql = "SELECT COUNT(*) FROM division WHERE division_name = ? AND id != ?";
        Object[] params;

        if (!Objects.isNull(user)) {
            sql += " AND (tenant_id = ? OR tenant_id is null)";
            params = new Object[]{name, divisionId, user.getTenantId()};
        } else {
            params = new Object[]{name, divisionId};
        }

        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

}