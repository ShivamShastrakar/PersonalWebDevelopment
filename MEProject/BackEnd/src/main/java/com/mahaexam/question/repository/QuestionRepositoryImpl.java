package com.mahaexam.question.repository;

import com.mahaexam.question.model.QuestionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of QuestionRepository using JdbcTemplate
 */
@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuestionRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public QuestionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<QuestionEntity> QUESTION_ROW_MAPPER = new RowMapper<QuestionEntity>() {
        @Override
        public QuestionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Helper method to safely convert BigInteger/Long
            Long id = null;
            Object idObj = rs.getObject("id");
            if (idObj != null) {
                if (idObj instanceof Long) {
                    id = (Long) idObj;
                } else if (idObj instanceof java.math.BigInteger) {
                    id = ((java.math.BigInteger) idObj).longValue();
                } else {
                    id = ((Number) idObj).longValue();
                }
            }

            Long createdBy = null;
            Object createdByObj = rs.getObject("created_by");
            if (createdByObj != null) {
                if (createdByObj instanceof Long) {
                    createdBy = (Long) createdByObj;
                } else if (createdByObj instanceof java.math.BigInteger) {
                    createdBy = ((java.math.BigInteger) createdByObj).longValue();
                } else {
                    createdBy = ((Number) createdByObj).longValue();
                }
            }

            return QuestionEntity.builder()
                    .id(id)
                    .subjectId(rs.getInt("subject_id"))
                    .classId(rs.getInt("class_id"))
                    .boardId((Integer) rs.getObject("board_id"))
                    .medium(rs.getString("medium"))
                    .chapterId((Integer) rs.getObject("chapter_id"))
                    .topicId((Integer) rs.getObject("topic_id"))
                    .questionType(rs.getString("question_type"))
                    .questionText(rs.getString("question_text"))
                    .questionImageUrl(rs.getString("question_image_url"))
                    .options(rs.getString("options"))
                    .correctAnswer(rs.getString("correct_answer"))
                    .answerExplanation(rs.getString("answer_explanation"))
                    .answerExplanationImageUrl(rs.getString("answer_explanation_image_url"))
                    .skillLevel(rs.getString("skill_level"))
                    .difficultyLevel(rs.getString("difficulty_level"))
                    .aiPromptHash(rs.getString("ai_prompt_hash"))
                    .createdAt(rs.getTimestamp("created_at") != null ?
                              rs.getTimestamp("created_at").toLocalDateTime() : null)
                    .createdBy(createdBy)
                    .paragraphId(rs.getString("paragraph_id"))
                    .paragraphText(rs.getString("paragraph_text"))
                    .tenantId(rs.getObject("tenant_id") != null ? rs.getLong("tenant_id") : null)
                    .build();
        }
    };

    @Override
    public QuestionEntity save(QuestionEntity question) {
        String sql = "INSERT INTO questions (subject_id, class_id, board_id, medium, chapter_id, topic_id, " +
                     "question_type, question_text, question_image_url, options, correct_answer, answer_explanation, " +
                     "answer_explanation_image_url, skill_level, difficulty_level, ai_prompt_hash, created_at, created_by, paragraph_id, paragraph_text, tenant_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, question.getSubjectId());
            ps.setObject(2, question.getClassId());
            ps.setObject(3, question.getBoardId());
            ps.setString(4, question.getMedium());
            ps.setObject(5, question.getChapterId());
            ps.setObject(6, question.getTopicId());
            ps.setString(7, question.getQuestionType());
            ps.setString(8, question.getQuestionText());
            ps.setString(9, question.getQuestionImageUrl());
            ps.setString(10, question.getOptions());
            ps.setString(11, question.getCorrectAnswer());
            ps.setString(12, question.getAnswerExplanation());
            ps.setString(13, question.getAnswerExplanationImageUrl());
            ps.setString(14, question.getSkillLevel());
            ps.setString(15, question.getDifficultyLevel());
            ps.setString(16, question.getAiPromptHash());
            ps.setTimestamp(17, question.getCreatedAt() != null ?
                           Timestamp.valueOf(question.getCreatedAt()) : new Timestamp(System.currentTimeMillis()));
            ps.setObject(18, question.getCreatedBy());
            ps.setString(19, question.getParagraphId());
            ps.setString(20, question.getParagraphText());
            ps.setObject(21, question.getTenantId());
            return ps;
        }, keyHolder);

        question.setId(keyHolder.getKey().longValue());
        return question;
    }

    @Override
    public List<QuestionEntity> saveAll(List<QuestionEntity> questions) {
        String sql = "INSERT INTO questions (subject_id, class_id, board_id, medium, chapter_id, topic_id, " +
                     "question_type, question_text, question_image_url, options, correct_answer, answer_explanation, " +
                     "answer_explanation_image_url, skill_level, difficulty_level, ai_prompt_hash, created_at, created_by, paragraph_id, paragraph_text, tenant_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Use batch update for better performance
        jdbcTemplate.batchUpdate(sql, questions, questions.size(), (PreparedStatement ps, QuestionEntity question) -> {
            ps.setObject(1, question.getSubjectId());
            ps.setObject(2, question.getClassId());
            ps.setObject(3, question.getBoardId());
            ps.setString(4, question.getMedium());
            ps.setObject(5, question.getChapterId());
            ps.setObject(6, question.getTopicId());
            ps.setString(7, question.getQuestionType());
            ps.setString(8, question.getQuestionText());
            ps.setString(9, question.getQuestionImageUrl());
            ps.setString(10, question.getOptions());
            ps.setString(11, question.getCorrectAnswer());
            ps.setString(12, question.getAnswerExplanation());
            ps.setString(13, question.getAnswerExplanationImageUrl());
            ps.setString(14, question.getSkillLevel());
            ps.setString(15, question.getDifficultyLevel());
            ps.setString(16, question.getAiPromptHash());
            ps.setTimestamp(17, question.getCreatedAt() != null ?
                           Timestamp.valueOf(question.getCreatedAt()) : new Timestamp(System.currentTimeMillis()));
            ps.setObject(18, question.getCreatedBy());
            ps.setString(19, question.getParagraphId());
            ps.setString(20, question.getParagraphText());
            ps.setObject(21, question.getTenantId());
        });

        // Note: Batch insert doesn't return generated IDs easily
        // If you need the IDs, you can query them back using ai_prompt_hash or other unique identifiers
        return questions;
    }

    @Override
    public QuestionEntity update(QuestionEntity question) {
        String sql = "UPDATE questions SET subject_id = ?, class_id = ?, board_id = ?, medium = ?, chapter_id = ?, " +
                     "topic_id = ?, question_type = ?, question_text = ?, question_image_url = ?, options = ?, " +
                     "correct_answer = ?, answer_explanation = ?, answer_explanation_image_url = ?, skill_level = ?, " +
                     "difficulty_level = ?, ai_prompt_hash = ?, paragraph_id = ?, paragraph_text = ?, tenant_id = ? WHERE id = ?";

        jdbcTemplate.update(sql,
            question.getSubjectId(),
            question.getClassId(),
            question.getBoardId(),
            question.getMedium(),
            question.getChapterId(),
            question.getTopicId(),
            question.getQuestionType(),
            question.getQuestionText(),
            question.getQuestionImageUrl(),
            question.getOptions(),
            question.getCorrectAnswer(),
            question.getAnswerExplanation(),
            question.getAnswerExplanationImageUrl(),
            question.getSkillLevel(),
            question.getDifficultyLevel(),
            question.getAiPromptHash(),
            question.getParagraphId(),
            question.getParagraphText(),
            question.getTenantId(),
            question.getId()
        );

        return question;
    }

    @Override
    public Optional<QuestionEntity> findById(Long id) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        List<QuestionEntity> results = jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }


    @Override
    public List<QuestionEntity> findAllByTenantId(Long tenantId) {
        String sql = "SELECT * FROM questions WHERE tenant_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, tenantId);
    }

    @Override
    public List<QuestionEntity> findBySubjectAndClass(Integer subjectId, Integer classId) {
        String sql = "SELECT * FROM questions WHERE subject_id = ? AND class_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, subjectId, classId);
    }

    @Override
    public List<QuestionEntity> findByMetadata(Integer boardId, Integer classId, Integer subjectId, Integer topicId,
                                               String questionType, String skillLevel, String difficultyLevel) {
        StringBuilder sql = new StringBuilder("SELECT * FROM questions WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (boardId != null) {
            sql.append(" AND board_id = ?");
            params.add(boardId);
        }
        if (classId != null) {
            sql.append(" AND class_id = ?");
            params.add(classId);
        }
        if (subjectId != null) {
            sql.append(" AND subject_id = ?");
            params.add(subjectId);
        }
        if (topicId != null) {
            sql.append(" AND topic_id = ?");
            params.add(topicId);
        }
        if (questionType != null && !questionType.isEmpty()) {
            sql.append(" AND LOWER(question_type) = LOWER(?)");
            params.add(questionType);
        }
        if (skillLevel != null && !skillLevel.isEmpty()) {
            sql.append(" AND LOWER(skill_level) = LOWER(?)");
            params.add(skillLevel);
        }
        if (difficultyLevel != null && !difficultyLevel.isEmpty()) {
            sql.append(" AND LOWER(difficulty_level) = LOWER(?)");
            params.add(difficultyLevel);
        }

        sql.append(" ORDER BY created_at DESC");

        return jdbcTemplate.query(sql.toString(), QUESTION_ROW_MAPPER, params.toArray());
    }

    @Override
    public List<QuestionEntity> findByChapter(Integer chapterId) {
        String sql = "SELECT * FROM questions WHERE chapter_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, chapterId);
    }

    @Override
    public List<QuestionEntity> findByTopic(Integer topicId) {
        String sql = "SELECT * FROM questions WHERE topic_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, topicId);
    }

    @Override
    public boolean existsByAiPromptHash(String aiPromptHash) {
        String sql = "SELECT COUNT(*) FROM questions WHERE ai_prompt_hash = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, aiPromptHash);
        return count != null && count > 0;
    }

    @Override
    public Optional<QuestionEntity> findByAiPromptHash(String aiPromptHash) {
        String sql = "SELECT * FROM questions WHERE ai_prompt_hash = ?";
        List<QuestionEntity> results = jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, aiPromptHash);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM questions";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    @Override
    public long countByMetadata(Integer classId, Integer subjectId, Integer topicId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM questions WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (classId != null) {
            sql.append(" AND class_id = ?");
            params.add(classId);
        }
        if (subjectId != null) {
            sql.append(" AND subject_id = ?");
            params.add(subjectId);
        }
        if (topicId != null) {
            sql.append(" AND topic_id = ?");
            params.add(topicId);
        }

        Long count = jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
        return count != null ? count : 0;
    }

    @Override
    public List<QuestionEntity> findRandomQuestions(Integer boardId, Integer classId, Integer subjectId,
                                                    Integer chapterId, Integer topicId, String questionType,
                                                    String skillLevel, String difficultyLevel, String medium, int limit) {
        // First, get random questions matching the criteria
        // For paragraph questions, we select distinct paragraph_id to avoid selecting multiple questions from same paragraph
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT COALESCE(paragraph_id, CAST(id AS CHAR)) as group_id, " +
            "id, subject_id, class_id, board_id, medium, chapter_id, topic_id, question_type, " +
            "question_text, options, correct_answer, answer_explanation, skill_level, difficulty_level, " +
            "ai_prompt_hash, created_at, created_by, paragraph_id, paragraph_text,tenant_id,question_image_url,answer_explanation_image_url " +
            "FROM questions WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (boardId != null) {
            sql.append(" AND board_id = ?");
            params.add(boardId);
        }
        if (classId != null) {
            sql.append(" AND class_id = ?");
            params.add(classId);
        }
        if (subjectId != null) {
            sql.append(" AND subject_id = ?");
            params.add(subjectId);
        }
        if (chapterId != null) {
            sql.append(" AND chapter_id = ?");
            params.add(chapterId);
        }
        if (topicId != null) {
            sql.append(" AND topic_id = ?");
            params.add(topicId);
        }
        if (questionType != null && !questionType.isEmpty()) {
            sql.append(" AND LOWER(question_type) = LOWER(?)");
            params.add(questionType);
        }
        if (skillLevel != null && !skillLevel.isEmpty()) {
            sql.append(" AND LOWER(skill_level) = LOWER(?)");
            params.add(skillLevel);
        }
        if (difficultyLevel != null && !difficultyLevel.isEmpty()) {
            sql.append(" AND LOWER(difficulty_level) = LOWER(?)");
            params.add(difficultyLevel);
        }
        if (medium != null && !medium.isEmpty()) {
            sql.append(" AND LOWER(medium) = LOWER(?)");
            params.add(medium);
        }

        sql.append(" ORDER BY RAND() LIMIT ?");
        params.add(limit);

        List<QuestionEntity> randomQuestions = jdbcTemplate.query(sql.toString(), QUESTION_ROW_MAPPER, params.toArray());

        // Now, for any question with paragraph_id, fetch all questions in that paragraph
        List<QuestionEntity> finalQuestions = new ArrayList<>();
        java.util.Set<String> processedParagraphIds = new java.util.HashSet<>();

        for (QuestionEntity question : randomQuestions) {
            if (question.getParagraphId() != null && !question.getParagraphId().isEmpty()) {
                // This is a paragraph question
                if (!processedParagraphIds.contains(question.getParagraphId())) {
                    // Fetch all questions with this paragraph_id
                    String paragraphSql = "SELECT * FROM questions WHERE paragraph_id = ? ORDER BY id";
                    List<QuestionEntity> paragraphQuestions = jdbcTemplate.query(
                        paragraphSql,
                        QUESTION_ROW_MAPPER,
                        question.getParagraphId()
                    );
                    finalQuestions.addAll(paragraphQuestions);
                    processedParagraphIds.add(question.getParagraphId());
                }
            } else {
                // Regular question (not part of a paragraph)
                finalQuestions.add(question);
            }
        }

        return finalQuestions;
    }

    @Override
    public List<QuestionEntity> findRandomQuestionsForParagraph(Integer boardId, Integer classId, Integer subjectId,
                                                                String skillLevel, String difficultyLevel, String medium, Set<Long> paragraphQuestionIds , int limit) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT q.*, COALESCE(q.paragraph_id, CAST(q.id AS CHAR)) AS group_id\n" +
            "FROM questions q\n" +
            "INNER JOIN (\n" +
            "    SELECT paragraph_id\n" +
            "    FROM questions\n" +
            "    WHERE question_type = 'paragraph-based-mcq'\n" +
            "      AND paragraph_id IS NOT NULL\n");

        List<Object> innerParams = new ArrayList<>();
        if (subjectId != null) {
            sql.append("      AND subject_id = ?\n");
            innerParams.add(subjectId);
        }
        if (medium != null && !medium.isEmpty()) {
            sql.append("      AND medium = ?\n");
            innerParams.add(medium);
        }
//        if (skillLevel != null && !skillLevel.isEmpty()) {
//            sql.append("      AND skill_level = ?\n");
//            innerParams.add(skillLevel);
//        }
//        if (difficultyLevel != null && !difficultyLevel.isEmpty()) {
//            sql.append("      AND difficulty_level = ?\n");
//            innerParams.add(difficultyLevel);
//        }


        if (boardId != null) {
            sql.append("      AND board_id = ?\n");
            innerParams.add(boardId);
        }
        if (classId != null) {
            sql.append("      AND class_id = ?\n");
            innerParams.add(classId);
        }

        sql.append("    GROUP BY paragraph_id having count(1)>1" +
            "    ORDER BY RAND()\n" +
            "    LIMIT 1\n" +
            ") AS chosen ON q.paragraph_id = chosen.paragraph_id\n" +
            "WHERE q.question_type = 'paragraph-based-mcq'\n");

        List<Object> outerParams = new ArrayList<>(innerParams);
        if (subjectId != null) {
            sql.append("  AND q.subject_id = ?\n");
            outerParams.add(subjectId);
        }
        if (medium != null && !medium.isEmpty()) {
            sql.append("  AND q.medium = ?\n");
            outerParams.add(medium);
        }

//        if (skillLevel != null && !skillLevel.isEmpty()) {
//            sql.append("  AND q.skill_level = ?\n");
//            outerParams.add(skillLevel);
//        }
//        if (difficultyLevel != null && !difficultyLevel.isEmpty()) {
//            sql.append("  AND q.difficulty_level = ?\n");
//            outerParams.add(difficultyLevel);
//        }

        if (boardId != null) {
            sql.append("  AND q.board_id = ?\n");
            outerParams.add(boardId);
        }
        if (classId != null) {
            sql.append("  AND q.class_id = ?\n");
            outerParams.add(classId);
        }
        // Exclude paragraphQuestionIds if provided
        if (paragraphQuestionIds != null && !paragraphQuestionIds.isEmpty()) {
            String inClause = paragraphQuestionIds.stream().map(id -> "?").collect(java.util.stream.Collectors.joining(", "));
            sql.append("  AND q.paragraph_id NOT IN (" + inClause + ")\n");
            for (Long id : paragraphQuestionIds) {
                outerParams.add(id);
            }
        }
        sql.append("ORDER BY q.id ");
//        sql.append("ORDER BY q.id LIMIT ?\n");
//        outerParams.add(limit);
        try {
            return jdbcTemplate.query(sql.toString(), QUESTION_ROW_MAPPER, outerParams.toArray());
        } catch (Exception e) {
            logger.error("Error fetching random questions for paragraph: {}", e.getMessage(), e);
            throw e;
        }

        /*
        // Now, for any question with paragraph_id, fetch all questions in that paragraph
        List<QuestionEntity> finalQuestions = new ArrayList<>();
        java.util.Set<String> processedParagraphIds = new java.util.HashSet<>();

        for (QuestionEntity question : randomQuestions) {
            if (question.getParagraphId() != null && !question.getParagraphId().isEmpty()) {
                // This is a paragraph question
                if (!processedParagraphIds.contains(question.getParagraphId())) {
                    // Fetch all questions with this paragraph_id
                    String paragraphSql = "SELECT * FROM questions WHERE paragraph_id = ? ORDER BY id";
                    List<QuestionEntity> paragraphQuestions = jdbcTemplate.query(
                            paragraphSql,
                            QUESTION_ROW_MAPPER,
                            question.getParagraphId()
                    );
                    finalQuestions.addAll(paragraphQuestions);
                    processedParagraphIds.add(question.getParagraphId());
                }
            } else {
                // Regular question (not part of a paragraph)
                finalQuestions.add(question);
            }
        }
         return finalQuestions;
         */


    }

    @Override
    public List<QuestionEntity> findByQuestionPaperId(Long questionPaperId) {
        String sql = "SELECT DISTINCT COALESCE(paragraph_id, CAST(q.id AS CHAR)) as group_id, " +
                "q.id, q.subject_id, class_id, board_id, medium, chapter_id, topic_id, question_type, " +
                "question_text, options, correct_answer, answer_explanation, skill_level, difficulty_level, " +
                "ai_prompt_hash, q.created_at, q.created_by, paragraph_id, paragraph_text,tenant_id , " +
                "question_image_url," +
                "answer_explanation_image_url " +
                "FROM questions q " +
                "INNER JOIN question_paper_questions qpq ON q.id = qpq.question_id " +
                "WHERE qpq.question_paper_id = ?";
        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, questionPaperId);
    }

    @Override
    public List<QuestionEntity> findByQuestionPaperIdPaginated(Long questionPaperId, int page, int size) {
        String sql = "SELECT DISTINCT COALESCE(paragraph_id, CAST(q.id AS CHAR)) as group_id, " +
                "q.id, subject_id, class_id, board_id, medium, chapter_id, topic_id, question_type, " +
                "question_text, options, correct_answer, answer_explanation, skill_level, difficulty_level, " +
                "ai_prompt_hash, q.created_at, q.created_by, paragraph_id, paragraph_text,tenant_id ," +
                "question_image_url,answer_explanation_image_url " +
                "FROM questions q " +
                "INNER JOIN question_paper_questions qpq ON q.id = qpq.question_id " +
                "WHERE qpq.question_paper_id = ? " +
                "ORDER BY q.id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, questionPaperId, size, page * size);
    }

    @Override
    public List<QuestionEntity> findByQuestions(Long questionPaperId, Integer subjectId, Long partId, Integer sectionId, int numberOfQuestions) {
        String sql = """
                    
                SELECT
                    	DISTINCT COALESCE(paragraph_id, CAST(q.id AS CHAR)) as group_id,
                    	q.id,
                    	q.subject_id,
                    	class_id,
                    	board_id,
                    	medium,
                    	chapter_id,
                    	topic_id,
                    	question_type,
                    	question_text,
                    	options,
                    	correct_answer,
                    	answer_explanation,
                    	skill_level,
                    	difficulty_level,
                    	ai_prompt_hash,
                    	q.created_at,
                    	q.created_by,
                    	paragraph_id,
                    	paragraph_text,
                    	tenant_id,
                    	question_image_url,
                    	answer_explanation_image_url
                    FROM
                    	questions q
                    INNER JOIN question_paper_questions qpq ON
                    	q.id = qpq.question_id
                    WHERE
                    	qpq.question_paper_id = ?
                    	and qpq.subject_id = ?
                    	and qpq.part_id = ?
                    	and qpq.section_id = ?
                    """;
        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER, questionPaperId, subjectId,  partId,  sectionId);
    }
}
