package com.mahaexam.common.repo;

import com.mahaexam.common.model.MessageTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class MessageTemplateRepositoryImpl implements MessageTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public MessageTemplateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MessageTemplate> rowMapper = new RowMapper<>() {
        @Override
        public MessageTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
            MessageTemplate template = new MessageTemplate();
            template.setTemplateId(rs.getInt("template_id"));
            template.setSmsTemplateId(rs.getString("sms_template_id"));
            template.setTemplateName(rs.getString("template_name"));
            template.setTemplateType(rs.getString("template_type"));
            template.setSubject(rs.getString("subject"));
            template.setContent(rs.getString("content"));
            template.setStatus(rs.getString("status"));
            template.setDeleted(rs.getBoolean("deleted"));
            Timestamp createdAtTs = rs.getTimestamp("created_at");
            template.setCreatedAt(createdAtTs != null ? createdAtTs.toLocalDateTime() : null);

            Timestamp updatedAtTs = rs.getTimestamp("updated_at");
            template.setUpdatedAt(updatedAtTs != null ? updatedAtTs.toLocalDateTime() : null);
            ;
            return template;
        }
    };

    @Override
    public int save(MessageTemplate template) {
        String sql = "INSERT INTO message_templates (template_name, template_type, subject, content, status, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        return jdbcTemplate.update(sql,
                template.getTemplateName(),
                template.getTemplateType(),
                template.getSubject(),
                template.getContent(),
                template.getStatus());
    }

    @Override
    public MessageTemplate getTemplateByNameAndType(String templateName, String templateType) {
        String sql = "SELECT * FROM message_templates WHERE template_name = ? AND template_type = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper,templateName, templateType);
    }

    @Override
    public int update(MessageTemplate template) {
        String sql = "UPDATE message_templates SET template_name = ?, template_type = ?, subject = ?, content = ?, status = ?, updated_at = NOW() " +
                "WHERE template_id = ?";
        return jdbcTemplate.update(sql,
                template.getTemplateName(),
                template.getTemplateType(),
                template.getSubject(),
                template.getContent(),
                template.getStatus(),
                template.getTemplateId());
    }

    @Override
    public int delete(int templateId) {
        String sql = "UPDATE message_templates set deleted ='1' WHERE template_id = ?";
        return jdbcTemplate.update(sql, templateId);
    }

    @Override
    public MessageTemplate findById(int templateId) {
        String sql = "SELECT * FROM message_templates WHERE template_id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper,templateId);
    }

    @Override
    public List<MessageTemplate> findAll() {
        String sql = "SELECT * FROM message_templates";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
