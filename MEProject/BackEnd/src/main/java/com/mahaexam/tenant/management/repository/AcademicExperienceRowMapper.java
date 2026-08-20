package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.AcademicExperience;

public class AcademicExperienceRowMapper implements RowMapper<AcademicExperience> {
    @Override
    public AcademicExperience mapRow(ResultSet rs, int rowNum) throws SQLException {
    	AcademicExperience academicExperience = new AcademicExperience();
    	academicExperience.setAcademicId(rs.getLong("acadamic_id"));
    	academicExperience.setUserId(rs.getLong("user_id"));
    	academicExperience.setBoardId(rs.getObject("board_id") != null ? 
                rs.getInt("board_id") : null);
    	academicExperience.setSubjectId(rs.getObject("subject_id") != null ? 
                rs.getInt("subject_id") : null);
    	academicExperience.setClassId(rs.getObject("class_id") != null ? 
                               rs.getInt("class_id") : null);
    	academicExperience.setChapters(rs.getString("chapters"));
        return academicExperience;
    }
}
