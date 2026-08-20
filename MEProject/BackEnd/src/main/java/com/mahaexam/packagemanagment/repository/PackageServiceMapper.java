package com.mahaexam.packagemanagment.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.packagemanagment.model.PackageServiceModel;

public class PackageServiceMapper implements RowMapper<PackageServiceModel> {
	@Override
	public PackageServiceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		PackageServiceModel mapping = new PackageServiceModel();
		mapping.setId(rs.getInt("id"));
		mapping.setPackageId(rs.getObject("package_id", Integer.class));
		mapping.setServiceId(rs.getObject("service_id", Integer.class));
		mapping.setCreatedDate(
				rs.getTimestamp("created_date") != null ? rs.getTimestamp("created_date").toLocalDateTime() : null);
		mapping.setCreatedBy(rs.getObject("created_by", Integer.class));
		return mapping;
	}
}