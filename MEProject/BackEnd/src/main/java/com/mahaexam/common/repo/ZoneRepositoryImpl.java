package com.mahaexam.common.repo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.Zone;

@Repository
public class ZoneRepositoryImpl implements ZoneRepository {

    private final JdbcTemplate jdbcTemplate;

    public ZoneRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String BASE_COLUMNS = "id, zone_name, state_id, created_at, updated_at, tenant_id";

    @Override
    public Zone save(Zone zone) {
        String sql = """
            INSERT INTO zone (zone_name, state_id, tenant_id)
            VALUES (?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, zone.getZoneName());
            ps.setInt(2, zone.getStateId());
            if (zone.getTenantId() != null) {
                ps.setLong(3, zone.getTenantId());
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);
            }
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            zone.setId(keyHolder.getKey().intValue());
        }
        return zone;
    }

    @Override
    public Optional<Zone> findById(int id) {
        String sql = "SELECT " + BASE_COLUMNS + " FROM zone WHERE id = ?";
        List<Zone> result = jdbcTemplate.query(sql, new ZoneRowMapper(), id);
        return result.stream().findFirst();
    }

    @Override
    public List<Zone> findAll() {
        String sql = "SELECT " + BASE_COLUMNS + " FROM zone";
        return jdbcTemplate.query(sql, new ZoneRowMapper());
    }

    @Override
    public List<Zone> findByTenant(Long tenantId) {
        String sql = "SELECT " + BASE_COLUMNS + " FROM zone WHERE tenant_id = ?";
        return jdbcTemplate.query(sql, new ZoneRowMapper(), tenantId);
    }

    @Override
    public int update(Zone zone) {
        String sql = """
            UPDATE zone
            SET zone_name = ?, state_id = ?, updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
        """;

        return jdbcTemplate.update(sql,
            zone.getZoneName(),
            zone.getStateId(),
            zone.getId()
        );
    }

    @Override
    public int deleteById(int id) {
        String sql = "DELETE FROM zone WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

