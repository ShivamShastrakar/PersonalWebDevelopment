package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.Student;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getLong("student_id"));
        student.setUserId(rs.getLong("user_id"));
        student.setCurrentClassId(rs.getObject("current_class_id") != null ? 
                                 rs.getInt("current_class_id") : null);
        student.setCurrentSubjectGroupId(rs.getObject("current_subject_group_id") != null ? 
                                        rs.getInt("current_subject_group_id") : null);
        student.setTargetFinalExamYear(rs.getObject("target_final_exam_year") != null ? 
                                      rs.getInt("target_final_exam_year") : null);
        student.setMedium(rs.getString("medium"));
        student.setBoardId(RepoUtil.getOptionalInteger(rs, "board_id"));
        student.setSchoolName(rs.getString("school_name"));
        student.setSchoolAddress(rs.getString("school_address"));
        return student;
    }
}
