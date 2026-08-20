package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.Institute;

@Repository
public class InstituteRepositoryImpl implements InstituteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Institute i) {
        String sql = "INSERT INTO institutes (index_number, udi_number, institute_name, state_id, district_id ,taluka_id, total_intake, created_at, is_disabled) VALUES (?, ?, ?, ?,  ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            i.getIndexNumber(),
            i.getUdiNumber(),
            i.getInstituteName(),
            i.getStateId(),
            i.getDistrictId(),
            i.getTalukaId(),
            i.getTotalIntake(),
            i.getCreatedAt(),
            i.isDisabled()
        );
    }

    @Override
    public Institute findById(int id) {
        String sql = "SELECT * FROM institutes WHERE id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new InstituteRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public List<Institute> findAll() {
        return jdbcTemplate.query(
            "SELECT i.*, s.state_name, d.district_name,t.taluka_name FROM institutes i LEFT JOIN state s ON i.state_id = s.id "
            + " LEFT JOIN district d ON i.district_id = d.id LEFT JOIN taluka t ON i.taluka_id = t.id;",
            new InstituteRowMapper()
        );
    }

    @Override
    public int update(Institute i) {
        String sql = "UPDATE institutes SET institute_name = ?, state_id = ?, district_id  = ?, taluka_id = ?, total_intake = ?, updated_at = ?, is_disabled = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
            i.getInstituteName(),
            i.getStateId(),
            i.getDistrictId(),
            i.getTalukaId(),
            i.getTotalIntake(),
            i.getUpdatedAt(),
            i.isDisabled(),
            i.getId()
        );
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM institutes WHERE id = ?", id);
    }

    @Override
    public boolean existsByInstituteName(String instituteName) {
        String sql = "SELECT COUNT(*) FROM institutes WHERE institute_name = ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, instituteName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByInstituteNameExceptId(String instituteName, int excludeId) {
        String sql = "SELECT COUNT(*) FROM institutes WHERE institute_name = ? AND id != ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, instituteName, excludeId);
        return count != null && count > 0;
    }

	@Override
	public Optional<Institute> searchByIndexNumber(String indexNumber) {
		 String sql = "SELECT * FROM institutes WHERE index_number = ? AND deleted = '0'";
	        try {
	        	Institute ruleType = jdbcTemplate.queryForObject(sql,  new InstituteRowMapper(), indexNumber);
	            return Optional.ofNullable(ruleType);
	        } catch (Exception e) {
	            return Optional.empty();
	        }
	}

	
}