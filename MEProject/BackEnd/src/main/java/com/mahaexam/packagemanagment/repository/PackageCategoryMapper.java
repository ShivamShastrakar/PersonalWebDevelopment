package com.mahaexam.packagemanagment.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.packagemanagment.model.PackageCategoryModel;

public class PackageCategoryMapper implements RowMapper<PackageCategoryModel> {
    @Override
    public PackageCategoryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PackageCategoryModel model = new PackageCategoryModel();
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setDescription(rs.getString("description"));
        model.setTenantId(rs.getObject("tenant_id", Long.class));
        model.setCreatedDate(rs.getTimestamp("created_date") != null ? 
            rs.getTimestamp("created_date").toLocalDateTime() : null);
        model.setCreatedBy(rs.getObject("created_by", Integer.class));
        return model;
    }
}
