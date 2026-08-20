package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.AcademicExperience;

@Repository
public class AcademicExperienceRepositoryImpl implements AcademicExperienceRepository{

	private final JdbcTemplate jdbcTemplate;

	public AcademicExperienceRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public AcademicExperience createAcademicExperience(AcademicExperience academicExperience) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO acadamic_experience (user_id, class_id, board_id, subject_id, chapters "
				+ ") VALUES (?, ?, ?, ?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, academicExperience.getUserId());
			ps.setLong(2, academicExperience.getClassId());
			ps.setLong(3, academicExperience.getBoardId());
			ps.setInt(4, academicExperience.getSubjectId());
			ps.setString(5, academicExperience.getChapters());
			return ps;
		}, keyHolder);

		// Retrieve and set the generated ID
		Number key = keyHolder.getKey();
		if (key != null) {
			academicExperience.setAcademicId(key.longValue());
		}

		return academicExperience;

	}

	@Override
	public AcademicExperience updateAcademicExperience(Long academicId, AcademicExperience academicExperience) {
		String sql = "UPDATE acadamic_experience SET chapters = ? WHERE acadamic_id = ?";
		jdbcTemplate.update(sql, academicExperience.getChapters(),academicId);
		academicExperience.setAcademicId(academicId);
		return academicExperience;
	}

	@Override
	public void deleteAcademicExperience(Long academicId) {
		String sql = "DELETE FROM acadamic_experience WHERE acadamic_id = ?";
        jdbcTemplate.update(sql, academicId);
		
	}

	@Override
	public List<AcademicExperience> getAcademicExperiencesByUserId(Long userId) {
		 String sql = "SELECT * FROM acadamic_experience WHERE user_id = ?";
	        return jdbcTemplate.query(sql, new AcademicExperienceRowMapper(), userId);
	}

}
