package com.mahaexam.common.repo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Taluka;

@Repository
public class TalukaRepositoryImpl implements TalukaRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Taluka> findAll(UserBean user) {
		String sql = " SELECT t.*, d.district_name, s.state_name , d2.division_name FROM taluka t "
				+ " inner join district d ON d.id = t.district_id "
				+ " inner join state s on s.id  =d.state_id"
				+ " inner join division d2 on d2.state_id = s.id WHERE t.deleted = '0' ";
		return jdbcTemplate.query(sql, new TalukaRowMapper());
	}

	@Override
	public Optional<Taluka> findById(UserBean user, int id) {
		String sql = "SELECT t.*, d.district_name, s.state_name , d2.division_name FROM taluka t "
				+ " inner join district d ON d.id = t.district_id "
				+ "	inner join state s on s.id  =d.state_id "
				+ "	inner join division d2 on d2.state_id = s.id WHERE t.id = ?";
		try {
			Taluka taluka = jdbcTemplate.queryForObject(sql, new TalukaRowMapper(), id);
			return Optional.ofNullable(taluka);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Taluka> findByDistrictId(UserBean user, int districtId) {
		String sql = "SELECT * FROM taluka WHERE district_id = ?";
		return jdbcTemplate.query(sql, new TalukaRowMapper(), districtId);
	}

	@Override
	public int insert(UserBean user, Taluka taluka) {
		if (existsByTalukaNameAndTenantId(taluka.getTalukaName(), Objects.nonNull(taluka.getTenantId()) ? Long.valueOf(taluka.getTenantId()) : null)) {
			throw new ValidationException("Taluka name already exists for this tenant.");
		}
		String sql = "INSERT INTO taluka (taluka_name, created_at, updated_at, deleted, district_id, tenant_id) "
				+ "VALUES (?, NOW(), NULL, '0', ?, ?)";
		return jdbcTemplate.update(sql, taluka.getTalukaName(), taluka.getDistrictId(),
				 taluka.getTenantId());
	}

	@Override
	public int update(UserBean user, Taluka taluka) {
		if (existsByTalukaNameAndTenantIdExceptId(taluka.getTalukaName(),  Objects.nonNull(taluka.getTenantId()) ? Long.valueOf(taluka.getTenantId()) : null, taluka.getId())) {
			throw new ValidationException("Taluka name already exists for this tenant.");
		}
		String sql = "UPDATE taluka SET taluka_name = ?, updated_at = NOW(), district_id = ? "
				+ "WHERE id = ?";
		return jdbcTemplate.update(sql, taluka.getTalukaName(), taluka.getDistrictId(),
				taluka.getId());
	}

	@Override
	public void softDelete(Integer id) {
		String sql = "UPDATE taluka SET deleted = '1', deleted_at = NOW() WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	

    @Override
    public List<Taluka> getTalukasByStateId(UserBean user, int stateId) {
        String sql = """
        
                SELECT DISTINCT  t.*,d.district_name,div1.id division_id, div1.division_name, d.state_id, s.state_name
        FROM taluka t
        inner join district d on t.district_id = d.id
        inner join division div1 on div1.id = d.division_id
        inner join state s on s.id = d.state_id
        where s.id = ?
        """;
        return jdbcTemplate.query(sql, new TalukaRowMapper(), stateId);
    }

    public boolean existsByTalukaNameAndTenantId(String talukaName, Long tenantId) {
		String sql = """
        SELECT COUNT(*) FROM taluka
        WHERE taluka_name = ? AND tenant_id = ? AND deleted = '0'
    """;
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, talukaName, tenantId);
		return count != null && count > 0;
	}

	public boolean existsByTalukaNameAndTenantIdExceptId(String talukaName, Long tenantId, int excludeId) {
		String sql = """
        SELECT COUNT(*) FROM taluka
        WHERE taluka_name = ? AND tenant_id = ? AND id != ? AND deleted = '0'
    """;
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, talukaName, tenantId, excludeId);
		return count != null && count > 0;
	}

}
