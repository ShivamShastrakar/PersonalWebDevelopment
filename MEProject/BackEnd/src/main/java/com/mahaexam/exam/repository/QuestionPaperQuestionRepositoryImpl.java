package com.mahaexam.exam.repository;

import com.mahaexam.exam.model.QuestionPaperQuestion;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class QuestionPaperQuestionRepositoryImpl implements QuestionPaperQuestionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public QuestionPaperQuestionRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(QuestionPaperQuestion questionPaperQuestion) {
        if (questionPaperQuestion.getId() == null) {
            insert(questionPaperQuestion);
        } else {
            update(questionPaperQuestion);
        }
    }

    @Override
    public void batchSave(List<QuestionPaperQuestion> questionPaperQuestions) {
        if (questionPaperQuestions == null || questionPaperQuestions.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO question_paper_questions " +
                "(question_paper_id, subject_id, part_id, section_id, question_id, sequence_number, created_at, created_by, updated_by) " +
                "VALUES (:questionPaperId, :subjectId, :partId, :sectionId, :questionId, :sequenceNumber, :createdAt, :createdBy, :updatedBy)";

        MapSqlParameterSource[] batchParams = questionPaperQuestions.stream()
                .map(qpq -> new MapSqlParameterSource()
                        .addValue("questionPaperId", qpq.getQuestionPaperId())
                        .addValue("subjectId", qpq.getSubjectId())
                        .addValue("partId", qpq.getPartId())
                        .addValue("sectionId", qpq.getSectionId())
                        .addValue("questionId", qpq.getQuestionId())
                        .addValue("sequenceNumber", qpq.getSequenceNumber())
                        .addValue("createdAt", Timestamp.valueOf(LocalDateTime.now()))
                        .addValue("createdBy", qpq.getCreatedBy())
                        .addValue("updatedBy", qpq.getUpdatedBy()))
                .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(sql, batchParams);
    }

    private void insert(QuestionPaperQuestion qpq) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO question_paper_questions " +
                "(question_paper_id, subject_id, part_id, section_id, question_id, sequence_number, created_at, created_by, updated_by) " +
                "VALUES (:questionPaperId, :subjectId, :partId, :sectionId, :questionId, :sequenceNumber, :createdAt, :createdBy, :updatedBy)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("questionPaperId", qpq.getQuestionPaperId())
                .addValue("subjectId", qpq.getSubjectId())
                .addValue("partId", qpq.getPartId())
                .addValue("sectionId", qpq.getSectionId())
                .addValue("questionId", qpq.getQuestionId())
                .addValue("sequenceNumber", qpq.getSequenceNumber())
                .addValue("createdAt", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("createdBy", qpq.getCreatedBy())
                .addValue("updatedBy", qpq.getUpdatedBy());

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        qpq.setId(keyHolder.getKey().longValue());
    }

    private void update(QuestionPaperQuestion qpq) {
        String sql = "UPDATE question_paper_questions SET " +
                "question_paper_id = :questionPaperId, subject_id = :subjectId, part_id = :partId, section_id = :sectionId, question_id = :questionId, " +
                "sequence_number = :sequenceNumber, updated_by = :updatedBy " +
                "WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", qpq.getId())
                .addValue("questionPaperId", qpq.getQuestionPaperId())
                .addValue("subjectId", qpq.getSubjectId())
                .addValue("partId", qpq.getPartId())
                .addValue("sectionId", qpq.getSectionId())
                .addValue("questionId", qpq.getQuestionId())
                .addValue("sequenceNumber", qpq.getSequenceNumber())
                .addValue("updatedBy", qpq.getUpdatedBy());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<QuestionPaperQuestion> findByQuestionPaperIdOrderBySequenceNumber(Long questionPaperId) {
        String sql = "SELECT * FROM question_paper_questions WHERE question_paper_id = :questionPaperId ORDER BY sequence_number";
        return jdbcTemplate.query(sql, new MapSqlParameterSource("questionPaperId", questionPaperId), questionPaperQuestionRowMapper());
    }

    @Override
    public Long countByQuestionPaperId(Long questionPaperId) {
        String sql = "SELECT COUNT(*) FROM question_paper_questions WHERE question_paper_id = :questionPaperId";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("questionPaperId", questionPaperId), Long.class);
    }

    @Override
    public void deleteByQuestionPaperId(Long questionPaperId) {
        String sql = "DELETE FROM question_paper_questions WHERE question_paper_id = :questionPaperId";
        jdbcTemplate.update(sql, new MapSqlParameterSource("questionPaperId", questionPaperId));
    }

    @Override
    public boolean existsByQuestionPaperIdAndQuestionId(Long questionPaperId, Long questionId) {
        String sql = "SELECT COUNT(*) FROM question_paper_questions WHERE question_paper_id = :questionPaperId AND question_id = :questionId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("questionPaperId", questionPaperId)
                .addValue("questionId", questionId);
        Long count = jdbcTemplate.queryForObject(sql, params, Long.class);
        return count != null && count > 0;
    }

    @Override
    public Integer getMaxSequenceNumber(Long questionPaperId) {
        String sql = "SELECT COALESCE(MAX(sequence_number), 0) FROM question_paper_questions WHERE question_paper_id = :questionPaperId";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("questionPaperId", questionPaperId), Integer.class);
    }

    private RowMapper<QuestionPaperQuestion> questionPaperQuestionRowMapper() {
        return (rs, rowNum) -> {
            QuestionPaperQuestion qpq = new QuestionPaperQuestion();
            qpq.setId(rs.getLong("id"));
            qpq.setQuestionPaperId(rs.getLong("question_paper_id"));
            qpq.setSubjectId(rs.getObject("subject_id") != null ? rs.getLong("subject_id") : null);
            qpq.setPartId(rs.getObject("part_id") != null ? rs.getLong("part_id") : null);
            qpq.setSectionId(rs.getObject("section_id") != null ? rs.getLong("section_id") : null);
            qpq.setQuestionId(rs.getLong("question_id"));
            qpq.setSequenceNumber(rs.getInt("sequence_number"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            qpq.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);

            Long createdBy = rs.getLong("created_by");
            qpq.setCreatedBy(rs.wasNull() ? null : createdBy);

            Long updatedBy = rs.getLong("updated_by");
            qpq.setUpdatedBy(rs.wasNull() ? null : updatedBy);

            return qpq;
        };
    }
}
