package com.mahaexam.packagemanagment.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.packagemanagment.model.PackageCourseModel;

public class PackageCourseMapper implements RowMapper<PackageCourseModel> {
	@Override
	public PackageCourseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		PackageCourseModel mapping = new PackageCourseModel();
		mapping.setId(rs.getInt("id"));
		mapping.setPackageId(rs.getObject("package_id", Integer.class));
		mapping.setCourseId(rs.getObject("course_id", Integer.class));
		return mapping;
	}
}