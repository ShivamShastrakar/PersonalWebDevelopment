package com.mahaexam.exam.repository;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.common.bean.QuestionPaperMetaData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.papertemplate.model.QuestionPaper;

@Repository
public class QuestionPaperRepositoryImpl implements QuestionPaperRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final ObjectMapper objectMapper;

	public QuestionPaperRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public QuestionPaper save(QuestionPaper questionPaper) {
		return questionPaper.getId() == null ? insert(questionPaper) : update(questionPaper);
	}

	private QuestionPaper insert(QuestionPaper questionPaper) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = " INSERT INTO question_paper (question_paper_name, academic_year, status, start_date, end_date, description, meta_data, tenant_id) VALUES (:questionPaperName, :academicYear, :status, :startDate, :endDate, :description, :metaData, :tenantId) ";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("questionPaperName", questionPaper.getQuestionPaperName())
				.addValue("academicYear", questionPaper.getAcademicYear())
				.addValue("status", questionPaper.getStatus())
				.addValue("startDate", questionPaper.getStartDate())
				.addValue("endDate", questionPaper.getEndDate())
				.addValue("description", questionPaper.getDescription())
				.addValue("metaData", serializeMetaData(questionPaper.getMetaData()))
				.addValue("tenantId", questionPaper.getTenantId());

		jdbcTemplate.update(sql, params, keyHolder, new String[] { "id" });

		Long id = keyHolder.getKey().longValue();

		questionPaper.setId(id);
		return questionPaper;
	}

	private QuestionPaper update(QuestionPaper questionPaper) {

		String sql = " UPDATE question_paper SET question_paper_name = :questionPaperName, academic_year = :academicYear, status = :status, start_date = :startDate, end_date = :endDate, description = :description, meta_data = :metaData, tenant_id = :tenantId WHERE id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", questionPaper.getId())
				.addValue("questionPaperName", questionPaper.getQuestionPaperName())
				.addValue("academicYear", questionPaper.getAcademicYear())
				.addValue("status", questionPaper.getStatus())
				.addValue("startDate", questionPaper.getStartDate())
				.addValue("endDate", questionPaper.getEndDate())
				.addValue("description", questionPaper.getDescription())
				.addValue("metaData", serializeMetaData(questionPaper.getMetaData()))
				.addValue("tenantId", questionPaper.getTenantId());

		jdbcTemplate.update(sql, params);
		return questionPaper;
	}

	@Override
	public Optional<QuestionPaper> findById(Long id) {

		String sql = "SELECT * FROM question_paper WHERE id = :id";

		List<QuestionPaper> list = jdbcTemplate.query(sql, new MapSqlParameterSource("id", id), questionPaperRowMapper());

		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}

	@Override
	public List<QuestionPaper> findAll() {

		String sql = "SELECT * FROM question_paper ORDER BY created_at DESC";

		return jdbcTemplate.query(sql, questionPaperRowMapper());
	}
	
	@Override
	public List<QuestionPaper> findByClassId(Integer classId) {
		String sql = "SELECT qp.*,pt.class_id FROM question_paper_template qpt " +
				" JOIN question_paper qp ON qp.id = qpt.question_paper_id " +
				" JOIN paper_template pt ON pt.id = qpt.paper_template_id WHERE pt.class_id = :classId and qp.status ='active' ORDER BY created_at DESC";
		return jdbcTemplate.query(sql, new MapSqlParameterSource("classId", classId), questionPaperRowMapper());
	}

	@Override
	public List<QuestionPaper> findAllByTenantId(Long tenantId) {

		String sql = "SELECT * FROM question_paper WHERE tenant_id = :tenantId ORDER BY created_at DESC";

		return jdbcTemplate.query(sql, new MapSqlParameterSource("tenantId", tenantId), questionPaperRowMapper());
	}

	@Override
	public List<QuestionPaper> findAllByTenantIdAndFilter(Long tenantId, Long boardId, Integer classId) {

		StringBuilder sql = new StringBuilder("""
				SELECT DISTINCT qp.* FROM question_paper qp
				JOIN question_paper_template qpt ON qpt.question_paper_id = qp.id
				JOIN paper_template pt ON pt.id = qpt.paper_template_id
				WHERE qp.tenant_id = :tenantId
				""");

		MapSqlParameterSource params = new MapSqlParameterSource("tenantId", tenantId);

		if (boardId != null) {
			sql.append(" AND pt.board_id = :boardId");
			params.addValue("boardId", boardId);
		}
		if (classId != null) {
			sql.append(" AND pt.class_id = :classId");
			params.addValue("classId", classId);
		}

		sql.append(" ORDER BY qp.created_at DESC");

		return jdbcTemplate.query(sql.toString(), params, questionPaperRowMapper());
	}

	@Override
	public void updateStatus(Long id, Boolean status) {

		String sql = " UPDATE question_paper SET status = :status WHERE id = :id ";

		jdbcTemplate.update(sql, new MapSqlParameterSource().addValue("id", id).addValue("status", status));
	}

	@Override
	public boolean existsByName(String questionPaperName) {
		String sql = "SELECT COUNT(*) FROM question_paper WHERE LOWER(question_paper_name) = LOWER(:questionPaperName)";
		Integer count = jdbcTemplate.queryForObject(sql,
				new MapSqlParameterSource("questionPaperName", questionPaperName), Integer.class);
		return count != null && count > 0;
	}

	@Override
	public boolean existsByNameAndTenantId(String questionPaperName, Long tenantId) {
		String sql = "SELECT COUNT(*) FROM question_paper WHERE LOWER(question_paper_name) = LOWER(:questionPaperName) AND tenant_id = :tenantId";
		Integer count = jdbcTemplate.queryForObject(sql,
				new MapSqlParameterSource("questionPaperName", questionPaperName).addValue("tenantId", tenantId),
				Integer.class);
		return count != null && count > 0;
	}

	@Override
	public List<QuestionPaper> findExamsByStudentPackageAndMedium(Long studentUserId, Long tenantId) {
		String sql = """
				SELECT DISTINCT
				    qp.*,
				    pt.total_duration,
				    pt.total_marks                                              AS exam_total_marks,
				    CASE WHEN sss.question_paper_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_taken,
				    sss.summary_id,
				    sss.marks_obtained,
				    sss.max_marks,
				    sss.total_questions,
				    sss.correct,
				    sss.wrong,
				    sss.not_answered,
				    sss.attempted_at
				FROM question_paper qp
				JOIN package_question_paper_mapping pqpm
				    ON pqpm.question_paper_id = qp.id
				JOIN packages p
				    ON p.id       = pqpm.package_id
				    AND p.deleted = '0'
				JOIN student_package_mapping spm
				    ON spm.package_id = p.id
				    AND spm.status    = 'Active'
				JOIN student s
				    ON s.student_id = spm.student_id
				    AND s.user_id   = :studentUserId
				JOIN question_paper_template qpt
				    ON qpt.question_paper_id = qp.id
				JOIN paper_template pt
				    ON pt.id        = qpt.paper_template_id
				    AND pt.class_id = s.current_class_id
				    AND pt.medium   = s.medium
				LEFT JOIN (
				    SELECT question_paper_id,
				           MIN(id)              AS summary_id,
				           SUM(marks_obtained)  AS marks_obtained,
				           SUM(max_marks)       AS max_marks,
				           SUM(total_questions) AS total_questions,
				           SUM(correct)         AS correct,
				           SUM(wrong)           AS wrong,
				           SUM(not_answered)    AS not_answered,
				           MAX(attempted_at)    AS attempted_at
				    FROM student_subject_summary
				    WHERE student_user_id = :studentUserId
				      AND tenant_id       = :tenantId
				    GROUP BY question_paper_id
				) sss ON sss.question_paper_id = qp.id
				WHERE qp.tenant_id = :tenantId
				  AND qp.status    = 'ACTIVE'
				ORDER BY qp.start_date DESC
				""";

		return jdbcTemplate.query(sql,
				new MapSqlParameterSource("studentUserId", studentUserId).addValue("tenantId", tenantId),
				examRowMapper());
	}

	private RowMapper<QuestionPaper> examRowMapper() {
		return (rs, rowNum) -> {
			QuestionPaper questionPaper = questionPaperRowMapper().mapRow(rs, rowNum);
			if (questionPaper != null) {
				questionPaper.setIsTaken(rs.getBoolean("is_taken"));
				questionPaper.setSummaryId(rs.getObject("summary_id") != null ? rs.getLong("summary_id") : null);
				questionPaper.setMarksObtained(rs.getBigDecimal("marks_obtained"));
				questionPaper.setMaxMarks(rs.getBigDecimal("max_marks"));
				questionPaper.setTotalDuration(rs.getObject("total_duration") != null ? rs.getInt("total_duration") : null);
				questionPaper.setExamTotalMarks(rs.getObject("exam_total_marks") != null ? rs.getInt("exam_total_marks") : null);
				questionPaper.setTotalQuestions(rs.getObject("total_questions") != null ? rs.getInt("total_questions") : null);
				questionPaper.setCorrectAnswers(rs.getObject("correct") != null ? rs.getInt("correct") : null);
				questionPaper.setWrongAnswers(rs.getObject("wrong") != null ? rs.getInt("wrong") : null);
				questionPaper.setNotAnswered(rs.getObject("not_answered") != null ? rs.getInt("not_answered") : null);
				questionPaper.setAttemptedAt(rs.getTimestamp("attempted_at") != null
						? rs.getTimestamp("attempted_at").toLocalDateTime() : null);
			}
			return questionPaper;
		};
	}

	private RowMapper<QuestionPaper> questionPaperRowMapper() {
		return (rs, rowNum) -> {
			QuestionPaper questionPaper = new QuestionPaper();
			questionPaper.setId(rs.getLong("id"));
			questionPaper.setQuestionPaperName(rs.getString("question_paper_name"));
			questionPaper.setAcademicYear(rs.getString("academic_year"));
			questionPaper.setStatus(rs.getString("status"));
			questionPaper.setStartDate(rs.getTimestamp("start_date") != null ? rs.getTimestamp("start_date").toLocalDateTime() : null);
			questionPaper.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date").toLocalDateTime() : null);
			questionPaper.setDescription(rs.getString("description"));
			questionPaper.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			questionPaper.setMetaData(deserializeMetaData(rs.getString("meta_data")));
			long tenantId = rs.getLong("tenant_id");
			questionPaper.setTenantId(rs.wasNull() ? null : tenantId);
			return questionPaper;
		};
	}

	private String serializeMetaData(QuestionPaperMetaData metaData) {
		if (metaData == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(metaData);
		} catch (Exception e) {
			throw new RuntimeException("Error serializing metadata to JSON", e);
		}
	}

	private QuestionPaperMetaData deserializeMetaData(String json) {
		if (json == null || json.trim().isEmpty()) {
			return null;
		}
		try {
			return objectMapper.readValue(json, QuestionPaperMetaData.class);
		} catch (Exception e) {
			throw new RuntimeException("Error deserializing metadata from JSON", e);
		}
	}
}
