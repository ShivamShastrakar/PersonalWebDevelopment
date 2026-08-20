package com.mahaexam.packagemanagment.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.packagemanagment.model.PackageClassModel;

public class PackageClassMapper implements RowMapper<PackageClassModel> {
    @Override
    public PackageClassModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PackageClassModel mapping = new PackageClassModel();
        mapping.setId(rs.getInt("id"));
        mapping.setPackageId(rs.getObject("package_id", Integer.class));
        mapping.setClassId(rs.getObject("class_id", Integer.class));
        return mapping;
    }
}
