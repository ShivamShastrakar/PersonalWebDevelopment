package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.Division;

public class DivisionRowMapper implements RowMapper<Division> {
    public Division mapRow(ResultSet rs, int rowNum) throws SQLException {
        Division division = new Division();
        division.setId(rs.getInt("id"));
        division.setDivisionName(rs.getString("division_name"));
        division.setDivisionCode(rs.getInt("division_code"));
        division.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        division.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        division.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        division.setDeleted(rs.getString("deleted"));
        division.setStateId(rs.getInt("state_id"));
        division.setTenantId(rs.getInt("tenant_id"));
        return division;
    }
}
