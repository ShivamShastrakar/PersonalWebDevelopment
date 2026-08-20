package com.mahaexam.question.repository;

import com.mahaexam.model.StudentQuestionAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentQuestionAttemptRepositoryImpl implements StudentQuestionAttemptRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<StudentQuestionAttempt> rowMapper = (rs, rowNum) -> {
        StudentQuestionAttempt attempt = new StudentQuestionAttempt();
        attempt.setId(rs.getLong("id"));
        attempt.setQuestionPaperId(rs.getLong("question_paper_id"));
        attempt.setStudentUserId(rs.getLong("student_user_id"));
        attempt.setQuestionId(rs.getLong("question_id"));
        attempt.setSubjectId(rs.getInt("subject_id"));
        attempt.setAnswerGiven(rs.getString("answer_given"));
        attempt.setIsCorrect(rs.getObject("is_correct") != null ? rs.getBoolean("is_correct") : null);
        attempt.setMarksObtained(rs.getBigDecimal("marks_obtained"));
        attempt.setAttemptedAt(rs.getTimestamp("attempted_at") != null ? rs.getTimestamp("attempted_at").toLocalDateTime() : null);
        attempt.setSummaryId(rs.getObject("summary_id") != null ? rs.getLong("summary_id") : null);
        attempt.setTenantId(rs.getObject("tenant_id") != null ? rs.getLong("tenant_id") : null);
        return attempt;
    };

    @Override
    public StudentQuestionAttempt findById(Long id) {
        String sql = "SELECT * FROM student_question_attempt WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<StudentQuestionAttempt> findAllByTenantId(Long tenantId) {
        String sql = "SELECT * FROM student_question_attempt WHERE tenant_id = ?";
        return jdbcTemplate.query(sql, rowMapper, tenantId);
    }

    @Override
    public List<StudentQuestionAttempt> findByQuestionPaperIdAndStudentUserIdAndTenantId(Long questionPaperId, Long studentUserId, Long tenantId) {
        String sql = "SELECT * FROM student_question_attempt WHERE question_paper_id = ? AND student_user_id = ? AND tenant_id = ?";
        return jdbcTemplate.query(sql, rowMapper, questionPaperId, studentUserId, tenantId);
    }

    @Override
    public List<StudentQuestionAttempt> findBySummaryIdAndTenantId(Long summaryId, Long tenantId) {
        String sql = "SELECT * FROM student_question_attempt WHERE summary_id = ? AND tenant_id = ?";
        return jdbcTemplate.query(sql, rowMapper, summaryId, tenantId);
    }

    @Override
    public int save(StudentQuestionAttempt attempt) {
        String sql = "INSERT INTO student_question_attempt (question_paper_id, student_user_id, question_id, subject_id, answer_given, is_correct, marks_obtained, summary_id, attempted_at, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                attempt.getQuestionPaperId(),
                attempt.getStudentUserId(),
                attempt.getQuestionId(),
                attempt.getSubjectId(),
                attempt.getAnswerGiven(),
                attempt.getIsCorrect(),
                attempt.getMarksObtained(),
                attempt.getSummaryId(),
                attempt.getAttemptedAt(),
                attempt.getTenantId()
        );
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM student_question_attempt WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int[] batchSave(List<StudentQuestionAttempt> attempts) {
        String sql = "INSERT INTO student_question_attempt (question_paper_id, student_user_id, question_id, subject_id, answer_given, is_correct, marks_obtained, summary_id, attempted_at, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
            @Override
            public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
                StudentQuestionAttempt attempt = attempts.get(i);
                ps.setLong(1, attempt.getQuestionPaperId());
                ps.setLong(2, attempt.getStudentUserId());
                ps.setLong(3, attempt.getQuestionId());
                ps.setInt(4, attempt.getSubjectId());
                ps.setString(5, attempt.getAnswerGiven());
                if (attempt.getIsCorrect() != null) {
                    ps.setBoolean(6, attempt.getIsCorrect());
                } else {
                    ps.setNull(6, java.sql.Types.BOOLEAN);
                }
                ps.setBigDecimal(7, attempt.getMarksObtained());
                if (attempt.getSummaryId() != null) {
                    ps.setLong(8, attempt.getSummaryId());
                } else {
                    ps.setNull(8, java.sql.Types.BIGINT);
                }
                ps.setTimestamp(9, attempt.getAttemptedAt() != null ? java.sql.Timestamp.valueOf(attempt.getAttemptedAt()) : null);
                if (attempt.getTenantId() != null) {
                    ps.setLong(10, attempt.getTenantId());
                } else {
                    ps.setNull(10, java.sql.Types.BIGINT);
                }
            }

            @Override
            public int getBatchSize() {
                return attempts.size();
            }
        });
    }
}
