package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.tenant.management.model.StudentSubjectGroup;

public class StudentSubjectGroupRowMapper implements RowMapper<StudentSubjectGroup> {
	@Override
	public StudentSubjectGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		StudentSubjectGroup studentSubjectGroup = new StudentSubjectGroup();
		studentSubjectGroup.setId(rs.getLong("id"));
		studentSubjectGroup.setStudentId(rs.getLong("student_id"));
		studentSubjectGroup
				.setSubjectGroupId(rs.getObject("subject_group_id") != null ? rs.getInt("subject_group_id") : null);
		studentSubjectGroup.setGroupName(RepoUtil.getOptionalString(rs, "group_name"));
		studentSubjectGroup.setDescription(RepoUtil.getOptionalString(rs, "description"));
		return studentSubjectGroup;
	}
}