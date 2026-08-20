package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.Teacher;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository {
	private final JdbcTemplate jdbcTemplate;

	public TeacherRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Teacher save(Teacher teacher) {
		String sql = "INSERT INTO teacher (user_id, institute_index_number, in_service, subject_id, "
		           + "total_experience_years, area_of_interest, online_lecture_taken, qualification, "
		           + "teaching_experience, valuation_experience, moderation_experience, chef_moderation_experience, "
		           + "board_paper_setting_experience, MHT_CET_paper_setting_experience, NEET_paper_setting_experience, "
		           + "JEE_paper_setting_experience, KVPY_paper_setting_experience, specialty_topics_subjects, "
		           + "jee_exp, mht_cet_exp, neet_exp, total_exp, individual_ref_code, ref_code, pan_number) "
		           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
		    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    ps.setLong(1, teacher.getUserId());
		    ps.setString(2, teacher.getInstituteIndexNumber());
		    ps.setBoolean(3, teacher.getInService());
		    ps.setLong(4, teacher.getSubjectId());
		    ps.setInt(5, teacher.getTotalExperienceYears());
		    ps.setString(6, teacher.getAreaOfInterest());
		    ps.setBoolean(7, teacher.getOnlineLectureTaken());
		    ps.setString(8, teacher.getQualification());
		    ps.setInt(9, teacher.getTeachingExperience());
		    ps.setInt(10, teacher.getValuationExperience());
		    ps.setInt(11, teacher.getModerationExperience());
		    ps.setInt(12, teacher.getChefModerationExperience());
		    ps.setInt(13, teacher.getBoardPaperSettingExperience());
		    ps.setInt(14, teacher.getMhtCetPaperSettingExperience());
		    ps.setInt(15, teacher.getNeetPaperSettingExperience());
		    ps.setInt(16, teacher.getJeePaperSettingExperience());
		    ps.setInt(17, teacher.getKvpyPaperSettingExperience());
		    ps.setString(18, teacher.getSpecialtyTopicsSubjects());
		    ps.setInt(19, teacher.getJeeExp());
		    ps.setInt(20, teacher.getMhtCetExp());
		    ps.setInt(21, teacher.getNeetExp());
		    ps.setInt(22, teacher.getTotalExp());
		    ps.setString(23, teacher.getIndividualRefCode());
		    ps.setString(24, teacher.getRefCode());
		    ps.setString(25, teacher.getPanNumber());
		    return ps;
		}, keyHolder);

		// Set the generated teacher ID
		Number key = keyHolder.getKey();
		if (key != null) {
		    teacher.setTeacherId(key.longValue());
		}

		return teacher;

	}

	@Override
	public Optional<Teacher> findById(Long teacherId) {
		String sql = "SELECT * FROM teacher WHERE teacher_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new TeacherRowMapper(), teacherId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Teacher> findAll() {
		String sql = "SELECT * FROM teacher";
		return jdbcTemplate.query(sql, new TeacherRowMapper());
	}

	@Override
	public Teacher update(Teacher teacher) {
		String sql = "UPDATE teacher SET user_id = ?, institute_index_number = ?, in_service = ?, subject_id = ?, "
				+ "total_experience_years = ?, area_of_interest = ?, online_lecture_taken = ?, qualification = ?, "
				+ "teaching_experience = ?, valuation_experience = ?, moderation_experience = ?, "
				+ "chef_moderation_experience = ?, board_paper_setting_experience = ?, "
				+ "MHT_CET_paper_setting_experience = ?, NEET_paper_setting_experience = ?, "
				+ "JEE_paper_setting_experience = ?, KVPY_paper_setting_experience = ?, specialty_topics_subjects = ?, "
				+ "jee_exp = ?, mht_cet_exp = ?, neet_exp = ?, total_exp = ?, individual_ref_code = ?, ref_code = ? ,pan_number = ? "
				+ "WHERE teacher_id = ?";

		jdbcTemplate.update(sql, teacher.getUserId(), teacher.getInstituteIndexNumber(), teacher.getInService(),
				teacher.getSubjectId(), teacher.getTotalExperienceYears(), teacher.getAreaOfInterest(),
				teacher.getOnlineLectureTaken(), teacher.getQualification(), teacher.getTeachingExperience(),
				teacher.getValuationExperience(), teacher.getModerationExperience(),
				teacher.getChefModerationExperience(), teacher.getBoardPaperSettingExperience(),
				teacher.getMhtCetPaperSettingExperience(), teacher.getNeetPaperSettingExperience(),
				teacher.getJeePaperSettingExperience(), teacher.getKvpyPaperSettingExperience(),
				teacher.getSpecialtyTopicsSubjects(), teacher.getJeeExp(), teacher.getMhtCetExp(), teacher.getNeetExp(),
				teacher.getTotalExp(), teacher.getIndividualRefCode(), teacher.getRefCode(),teacher.getPanNumber(), teacher.getTeacherId());
		return teacher;
	}

	@Override
	public void delete(Long teacherId) {
		String sql = "DELETE FROM teacher WHERE teacher_id = ?";
		jdbcTemplate.update(sql, teacherId);
	}

	@Override
	public Optional<Teacher> findByUserId(Long userId) {
		String sql = "SELECT * FROM teacher WHERE user_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new TeacherRowMapper(), userId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}