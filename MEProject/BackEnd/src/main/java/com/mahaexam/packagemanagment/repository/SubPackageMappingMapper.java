package com.mahaexam.packagemanagment.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.packagemanagment.model.SubPackageMappingModel;

public class SubPackageMappingMapper implements RowMapper<SubPackageMappingModel> {
    @Override
    public SubPackageMappingModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubPackageMappingModel mapping = new SubPackageMappingModel();
        mapping.setId(rs.getInt("id"));
        mapping.setParentPackageId(rs.getObject("parent_package_id", Integer.class));
        mapping.setChildPackageId(rs.getObject("child_package_id", Integer.class));
        return mapping;
    }
}