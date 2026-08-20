package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.Zone;

public class ZoneRowMapper implements RowMapper<Zone> {
    @Override
    public Zone mapRow(ResultSet rs, int rowNum) throws SQLException {
        Zone zone = new Zone();
        zone.setId(rs.getInt("id"));
        zone.setZoneName(rs.getString("zone_name"));
        zone.setStateId(rs.getInt("state_id"));

        zone.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        zone.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        zone.setTenantId(rs.getObject("tenant_id") != null ? rs.getInt("tenant_id") : null);

        return zone;
    }
}
