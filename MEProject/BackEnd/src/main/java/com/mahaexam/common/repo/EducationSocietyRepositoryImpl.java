package com.mahaexam.common.repo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.EducationSociety;

@Repository
public class EducationSocietyRepositoryImpl implements EducationSocietyRepository {

    private final JdbcTemplate jdbcTemplate;

    public EducationSocietyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public EducationSociety findById(int id) {
        String sql = "SELECT * FROM education_society WHERE id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new EducationSocietyRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public List<EducationSociety> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM education_society WHERE deleted='0'",
            new EducationSocietyRowMapper()
        );
    }

    // save(), update(), delete() remain unchanged
    
    @Override
    public int save(EducationSociety society) {
        String sql = """
            INSERT INTO education_society (society_name, created_at, updated_at, deleted_at, deleted, is_disabled)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        return jdbcTemplate.update(sql,
            society.getSocietyName(),
            society.getCreatedAt(),
            society.getUpdatedAt(),
            society.getDeletedAt(),
            society.getDeleted(),
            society.isDisabled()
        );
    }

    @Override
    public int update(EducationSociety society) {
        String sql = """
            UPDATE education_society
            SET society_name = ?, updated_at = ?, deleted_at = ?, deleted = ?, is_disabled = ?
            WHERE id = ?
        """;

        return jdbcTemplate.update(sql,
            society.getSocietyName(),
            society.getUpdatedAt(),
            society.getDeletedAt(),
            society.getDeleted(),
            society.isDisabled(),
            society.getId()
        );
    }

    @Override
    public boolean existsBySocietyName(String societyName) {
        String sql = "SELECT COUNT(*) FROM education_society WHERE society_name = ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, societyName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsBySocietyNameExceptId(String societyName, int excludeId) {
        String sql = "SELECT COUNT(*) FROM education_society WHERE society_name = ? AND id != ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, societyName, excludeId);
        return count != null && count > 0;
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE education_society SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println(update);
    }
}