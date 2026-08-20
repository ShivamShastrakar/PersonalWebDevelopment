package com.mahaexam.papertemplate.repository;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.papertemplate.model.QuestionPaperTemplate;

@Repository
public class QuestionPaperTemplateRepositoryImpl
        implements QuestionPaperTemplateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public QuestionPaperTemplateRepositoryImpl(
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(
            Long questionPaperId,
            Long paperTemplateId,
            Integer sequence,
            Long tenantId) {

        String sql = " INSERT INTO question_paper_template (question_paper_id, paper_template_id, sequence, tenant_id)" +
                     " VALUES (:questionPaperId, :paperTemplateId, :sequence, :tenantId) ";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("questionPaperId", questionPaperId)
                .addValue("paperTemplateId", paperTemplateId)
                .addValue("sequence", sequence)
                .addValue("tenantId", tenantId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
    }

    @Override
    public void saveBatch(Long questionPaperId, List<Long> paperTemplateIds, Long tenantId) {
        if (paperTemplateIds == null || paperTemplateIds.isEmpty()) {
            return;
        }

        String sql = " INSERT INTO question_paper_template (question_paper_id, paper_template_id, sequence, tenant_id) " +
                     " VALUES (:questionPaperId, :paperTemplateId, :sequence, :tenantId) ";

        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[paperTemplateIds.size()];

        for (int i = 0; i < paperTemplateIds.size(); i++) {
            batchParams[i] = new MapSqlParameterSource()
                    .addValue("questionPaperId", questionPaperId)
                    .addValue("paperTemplateId", paperTemplateIds.get(i))
                    .addValue("sequence", i + 1)
                    .addValue("tenantId", tenantId);
        }

        jdbcTemplate.batchUpdate(sql, batchParams);
    }

    @Override
    public void deleteByQuestionPaperId(Long questionPaperId) {
        String sql = " DELETE FROM question_paper_template WHERE question_paper_id = :questionPaperId";
        jdbcTemplate.update(sql, new MapSqlParameterSource("questionPaperId", questionPaperId));
    }

    @Override
    public List<QuestionPaperTemplate> findAllByTenantId(Long tenantId) {
        String sql = "SELECT qpt.id, qpt.question_paper_id, qpt.paper_template_id, qpt.sequence, qpt.tenant_id, pt.name as template_name " +
                     "FROM question_paper_template qpt " +
                     "LEFT JOIN paper_template pt ON qpt.paper_template_id = pt.id " +
                     "WHERE qpt.tenant_id = :tenantId " +
                     "ORDER BY qpt.question_paper_id, qpt.sequence";
        return jdbcTemplate.query(sql, new MapSqlParameterSource("tenantId", tenantId), rowMapper());
    }

    @Override
    public List<QuestionPaperTemplate> findByQuestionPaperId(Long questionPaperId) {
        String sql = "SELECT qpt.id, qpt.question_paper_id, qpt.paper_template_id, qpt.sequence, qpt.tenant_id, pt.name as template_name " +
                     "FROM question_paper_template qpt " +
                     "LEFT JOIN paper_template pt ON qpt.paper_template_id = pt.id " +
                     "WHERE qpt.question_paper_id = :questionPaperId " +
                     "ORDER BY qpt.sequence";
        return jdbcTemplate.query(sql, new MapSqlParameterSource("questionPaperId", questionPaperId), rowMapper());
    }

    private RowMapper<QuestionPaperTemplate> rowMapper() {
        return (rs, rowNum) -> {
            QuestionPaperTemplate qpt = new QuestionPaperTemplate();
            qpt.setId(rs.getLong("id"));
            qpt.setQuestionPaperId(rs.getLong("question_paper_id"));
            qpt.setPaperTemplateId(rs.getLong("paper_template_id"));
            qpt.setSequence(rs.getInt("sequence"));
            qpt.setPaperTemplateName(rs.getString("template_name"));
            qpt.setTenantId(rs.getObject("tenant_id") != null ? rs.getLong("tenant_id") : null);
            return qpt;
        };
    }
}
