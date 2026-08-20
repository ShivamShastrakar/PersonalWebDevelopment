package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.StudentClass;

public class StudentClassRowMapper implements RowMapper<StudentClass> {
    @Override
    public StudentClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentClass studentClass = new StudentClass();
        studentClass.setId(rs.getLong("id"));
        studentClass.setStudentId(rs.getLong("student_id"));
        studentClass.setClassId(rs.getObject("class_id") != null ? 
                               rs.getInt("class_id") : null);
        return studentClass;
    }
}