package com.mahaexam.common.repo;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Topic;
import com.mahaexam.common.util.RepoUtil;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TopicRepositoryImpl implements TopicRepository {

    private final JdbcTemplate jdbcTemplate;

    public TopicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Topic> TOPIC_ROW_MAPPER = new RowMapper<>() {
        @Override
        public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
            Topic topic = new Topic();
            topic.setTopicId(rs.getInt("topic_id"));
            topic.setTopicName(rs.getString("topic_name"));
            topic.setChapterId(rs.getObject("chapter_id", Integer.class));
            topic.setSubjectId(rs.getObject("subject_id", Integer.class));
            topic.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            topic.setCreatedBy(rs.getObject("created_by", Integer.class));
            topic.setUpdatedBy(rs.getObject("updated_by", Integer.class));
            topic.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
            topic.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));
            topic.setClassId(rs.getObject("class_id", Integer.class));
            topic.setBoardId(rs.getObject("board_id", Integer.class));
            topic.setTenantId(rs.getObject("tenant_id", Long.class));
            topic.setChapterName(RepoUtil.getOptionalString(rs,"chapter_name"));
            topic.setSubjectName(RepoUtil.getOptionalString(rs,"subject_name"));
            topic.setClassName(RepoUtil.getOptionalString(rs,"class_name"));
            topic.setBoardName(RepoUtil.getOptionalString(rs,"board_name"));
            return topic;
        }
    };

    @Override
    public List<Topic> findAll(UserBean user) {
        String sql= "SELECT t.*, c.chapter_name,s.subject_name, cls.class_name,b.board_name FROM topics t " +
        		" inner join chapters c on c.id = t.chapter_id inner join subject s on s.subject_id =t.subject_id " +
        		" inner join class cls on cls.id  = t.class_id inner join board b on b.id  = t.board_id  WHERE t.deleted_at IS NULL and (t.tenant_id = ? OR t.tenant_id is null)";
        return jdbcTemplate.query(sql, TOPIC_ROW_MAPPER, user.getTenantId());

    }

    @Override
    public Optional<Topic> findById(int id) {
        List<Topic> topics = jdbcTemplate.query(
                " SELECT t.*, c.chapter_name,s.subject_name, cls.class_name,b.board_name FROM topics t " +
                " inner join chapters c on c.id = t.chapter_id inner join subject s on s.subject_id =t.subject_id " +
                " inner join class cls on cls.id  = t.class_id inner join board b on b.id  = t.board_id  WHERE topic_id = ? AND deleted_at IS NULL",
                TOPIC_ROW_MAPPER, id);
        return topics.isEmpty() ? Optional.empty() : Optional.of(topics.get(0));
    }

    @Override
    public Topic save(Topic topic) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO topics (topic_name, chapter_id, subject_id, created_at, created_by, class_id, board_id, tenant_id) " +
                            "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, topic.getTopicName());
            ps.setObject(2, topic.getChapterId());
            ps.setObject(3, topic.getSubjectId());
            ps.setObject(4, topic.getCreatedBy());
            ps.setObject(5, topic.getClassId());
            ps.setObject(6, topic.getBoardId());
            ps.setObject(7, topic.getTenantId());
            return ps;
        }, keyHolder);

        // Retrieve the generated ID
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            topic.setTopicId(generatedId.intValue());
        }
        return topic;
    }

    @Override
    public Topic update(Topic topic) {
        jdbcTemplate.update(
                "UPDATE topics SET topic_name = ?, chapter_id = ?, subject_id = ?, updated_by = ?, updated_at = CURRENT_TIMESTAMP, " +
                        "class_id = ?, board_id = ?   WHERE topic_id = ? AND deleted_at IS NULL",
                topic.getTopicName(), topic.getChapterId(), topic.getSubjectId(), topic.getUpdatedBy(),
                topic.getClassId(), topic.getBoardId(), topic.getTopicId());
        return topic;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                "UPDATE topics SET deleted_at = CURRENT_TIMESTAMP WHERE topic_id = ?",
                id);
    }

    @Override
    public List<Topic> findByBoardClassSubjectAndChapter(Integer boardId, Integer classId, Integer subjectId, Integer chapterId, Long tenantId) {
        String sql = "SELECT t.*, c.chapter_name, s.subject_name, cls.class_name, b.board_name " +
                     "FROM topics t " +
                     "INNER JOIN chapters c ON c.id = t.chapter_id " +
                     "INNER JOIN subject s ON s.subject_id = t.subject_id " +
                     "INNER JOIN class cls ON cls.id = t.class_id " +
                     "INNER JOIN board b ON b.id = t.board_id " +
                     "WHERE t.board_id = ? AND t.class_id = ? AND t.subject_id = ? AND t.chapter_id = ? " +
                     "AND (t.tenant_id = ? OR t.tenant_id IS NULL) " +
                     "AND t.deleted_at IS NULL " +
                     "ORDER BY t.topic_name";
        return jdbcTemplate.query(sql, TOPIC_ROW_MAPPER, boardId, classId, subjectId, chapterId, tenantId);
    }
}
