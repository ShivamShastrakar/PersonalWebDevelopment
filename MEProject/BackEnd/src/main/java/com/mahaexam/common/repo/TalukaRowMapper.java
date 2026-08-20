package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.Taluka;

public class TalukaRowMapper implements RowMapper<Taluka> {

    @Override
    public Taluka mapRow(ResultSet rs, int rowNum) throws SQLException {
        Taluka taluka = new Taluka();

        taluka.setId(rs.getInt("id"));
        taluka.setTalukaName(rs.getString("taluka_name"));
        taluka.setDistrictId(rs.getInt("district_id"));

        if (rs.getTimestamp("created_at") != null) {
            taluka.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }

        if (rs.getTimestamp("updated_at") != null) {
            taluka.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        if (rs.getTimestamp("deleted_at") != null) {
            taluka.setDeletedAt(rs.getTimestamp("deleted_at").toLocalDateTime());
        }

        taluka.setDeleted(rs.getString("deleted"));
        taluka.setTenantId(rs.getInt("tenant_id"));

        taluka.setDistrictName(RepoUtil.getOptionalString(rs,"district_name"));
        taluka.setDivisionId(RepoUtil.getOptionalInteger(rs,"division_id"));
        taluka.setDivisionName(RepoUtil.getOptionalString(rs,"division_name"));
        taluka.setStateId(RepoUtil.getOptionalInteger(rs,"state_id"));
        taluka.setStateName(RepoUtil.getOptionalString(rs,"state_name"));
        return taluka;
    }
}
