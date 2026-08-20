package com.mahaexam.common.repo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.District;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

    private final JdbcTemplate jdbcTemplate;

    public DistrictRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String COLUMNS = "id, district_name, district_code, created_at, updated_at, deleted_at, deleted, tenant_id, state_id, zone_id, division_id";

    @Override
    public int save(UserBean user,District district) {
        String sql = """
            INSERT INTO district (district_name, district_code, state_id, zone_id, division_id)
            VALUES (?,  ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, district.getDistrictName());
            ps.setObject(2, district.getDistrictCode(), java.sql.Types.INTEGER);
//            ps.setObject(3, district.getTenantId(), java.sql.Types.BIGINT);
            ps.setObject(3, district.getStateId(), java.sql.Types.INTEGER);
            ps.setObject(4, district.getZoneId(), java.sql.Types.INTEGER);
            ps.setObject(5, district.getDivisionId(), java.sql.Types.INTEGER);
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            district.setId(keyHolder.getKey().intValue());
        }
        return district.getId();
    }

    @Override
    public Optional<District> findById(UserBean user, int id) {
        String sql = "SELECT " + COLUMNS + " FROM district WHERE id = ?";
        List<District> list = jdbcTemplate.query(sql, new DistrictRowMapper(), id);
        return list.stream().findFirst();
    }

    @Override
    public List<District> findAll(UserBean user) {
        String sql = "SELECT " + COLUMNS + " FROM district WHERE deleted = '0'";
        return jdbcTemplate.query(sql, new DistrictRowMapper());
    }

    @Override
    public List<District> findByTenant(UserBean user, Long tenantId) {
        String sql = "SELECT " + COLUMNS + " FROM district WHERE tenant_id = ? AND deleted = '0'";
        return jdbcTemplate.query(sql, new DistrictRowMapper(), tenantId);
    }
    
    @Override
    public List<District> findByStateId(UserBean user, Integer stateId) {
        String sql = """
            SELECT id, district_name, district_code, created_at, updated_at,
                   deleted_at, deleted, tenant_id, state_id, zone_id, division_id
            FROM district
            WHERE state_id = ? AND deleted = '0' AND (tenant_id = ? OR tenant_id IS NULL)
        """;
        Long tenantId = Objects.nonNull(user) ? user.getTenantId():null;
        return jdbcTemplate.query(sql, new DistrictRowMapper(), stateId, tenantId);
    }


    @Override
    public int update(UserBean user, District district) {
        String sql = """
            UPDATE district
            SET district_name = ?, district_code = ?, tenant_id = ?, state_id = ?, zone_id = ?, division_id = ?, updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
        """;
        return jdbcTemplate.update(sql,
            district.getDistrictName(),
            district.getDistrictCode(),
            district.getTenantId(),
            district.getStateId(),
            district.getZoneId(),
            district.getDivisionId(),
            district.getId()
        );
    }

    @Override
    public int deleteById(UserBean user, int id) {
        String sql = "UPDATE district SET deleted = '1', deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    
    @Override
    public boolean existsByDistrictNameAndTenantId(String districtName, Long tenantId) {
        String sql = """
            SELECT COUNT(*) FROM district
            WHERE district_name = ? AND (tenant_id = ? OR tenant_id is null) AND deleted = '0'
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, districtName, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByDistrictNameAndTenantIdExceptId(String districtName, Long tenantId, int excludeId) {
        String sql = """
            SELECT COUNT(*) FROM district
            WHERE district_name = ? AND tenant_id = ? AND id != ? AND deleted = '0'
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, districtName, tenantId, excludeId);
        return count != null && count > 0;
    }

}

