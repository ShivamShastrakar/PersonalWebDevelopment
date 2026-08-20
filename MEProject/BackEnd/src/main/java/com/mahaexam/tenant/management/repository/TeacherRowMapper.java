package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.Teacher;

public class TeacherRowMapper implements RowMapper<Teacher> {
    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(rs.getLong("teacher_id"));
        teacher.setUserId(rs.getLong("user_id"));
        teacher.setInstituteIndexNumber(rs.getString("institute_index_number"));
        teacher.setInService(rs.getObject("in_service") != null ? rs.getBoolean("in_service") : null);
        teacher.setSubjectId(rs.getInt("subject_id"));
        teacher.setTotalExperienceYears(rs.getInt("total_experience_years"));
        teacher.setAreaOfInterest(rs.getString("area_of_interest"));
        teacher.setOnlineLectureTaken(rs.getObject("online_lecture_taken") != null ? 
                                     rs.getBoolean("online_lecture_taken") : null);
        teacher.setQualification(rs.getString("qualification"));
        teacher.setTeachingExperience(rs.getInt("teaching_experience"));
        teacher.setValuationExperience(rs.getInt("valuation_experience"));
        teacher.setModerationExperience(rs.getInt("moderation_experience"));
        teacher.setChefModerationExperience(rs.getInt("chef_moderation_experience"));
        teacher.setBoardPaperSettingExperience(rs.getInt("board_paper_setting_experience"));
        teacher.setMhtCetPaperSettingExperience(rs.getInt("MHT_CET_paper_setting_experience"));
        teacher.setNeetPaperSettingExperience(rs.getInt("NEET_paper_setting_experience"));
        teacher.setJeePaperSettingExperience(rs.getInt("JEE_paper_setting_experience"));
        teacher.setKvpyPaperSettingExperience(rs.getInt("KVPY_paper_setting_experience"));
        teacher.setSpecialtyTopicsSubjects(rs.getString("specialty_topics_subjects"));
        teacher.setJeeExp(rs.getObject("jee_exp") != null ? rs.getInt("jee_exp") : null);
        teacher.setMhtCetExp(rs.getObject("mht_cet_exp") != null ? rs.getInt("mht_cet_exp") : null);
        teacher.setNeetExp(rs.getObject("neet_exp") != null ? rs.getInt("neet_exp") : null);
        teacher.setTotalExp(rs.getInt("total_exp"));
        teacher.setIndividualRefCode(rs.getString("individual_ref_code"));
        teacher.setRefCode(rs.getString("ref_code"));
        teacher.setPanNumber(rs.getString("pan_number"));
        return teacher;
    }
}