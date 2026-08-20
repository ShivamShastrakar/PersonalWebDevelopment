package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.District;

public class DistrictRowMapper implements RowMapper<District> {

	@Override
    public District mapRow(ResultSet rs, int rowNum) throws SQLException {
        District district = new District();
        district.setId(rs.getInt("id"));
        district.setDistrictName(rs.getString("district_name"));
        district.setDistrictCode(rs.getInt("district_code"));
        district.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        district.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        district.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        district.setDeleted(rs.getString("deleted"));
        district.setTenantId(rs.getObject("tenant_id") != null ? rs.getLong("tenant_id") : null);
        district.setStateId(rs.getObject("state_id") != null ? rs.getInt("state_id") : null);
        district.setZoneId(rs.getObject("zone_id") != null ? rs.getInt("zone_id") : null);
        district.setDivisionId(rs.getObject("division_id") != null ? rs.getInt("division_id") : null);
        return district;
    }
}
