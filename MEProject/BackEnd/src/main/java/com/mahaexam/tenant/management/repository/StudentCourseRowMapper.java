package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.tenant.management.model.StudentCourse;

public class StudentCourseRowMapper implements RowMapper<StudentCourse> {
	@Override
	public StudentCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setId(rs.getLong("id"));
		studentCourse.setStudentId(rs.getLong("student_id"));
		studentCourse.setCourseId(rs.getObject("course_id") != null ? rs.getLong("course_id") : null);

		studentCourse.setCourseName(RepoUtil.getOptionalString(rs, "course_name"));
		studentCourse.setCourseDetails(RepoUtil.getOptionalString(rs, "course_details"));
		return studentCourse;
	}
}