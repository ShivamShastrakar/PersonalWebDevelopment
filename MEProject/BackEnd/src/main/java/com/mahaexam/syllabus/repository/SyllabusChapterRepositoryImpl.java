package com.mahaexam.syllabus.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.papertemplate.model.SyllabusChapter;

@Repository
public class SyllabusChapterRepositoryImpl
        implements SyllabusChapterRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SyllabusChapterRepositoryImpl(
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(SyllabusChapter sc) {

        String sql = "INSERT INTO syllabus_chapter (syllabus_id, chapter_id, number_of_questions, marks,coverage_percentage)  VALUES (:syllabusId, :chapterId, :questions, :marks, :coverage) ";

        jdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("syllabusId", sc.getSyllabusId())
                        .addValue("chapterId", sc.getChapterId())
                        .addValue("questions", sc.getNumberOfQuestions())
                        .addValue("marks", sc.getMarks())
                        .addValue("coverage", sc.getCoveragePercentage())
        );
    }

    @Override
    public List<SyllabusChapter> findBySyllabusId(Long syllabusId) {
        String sql = "SELECT sc.id, sc.syllabus_id, sc.chapter_id, c.chapter_name AS chapter_name, sc.number_of_questions, sc.coverage_percentage, sc.marks "
        		+ "FROM syllabus_chapter sc JOIN chapters c ON sc.chapter_id = c.id WHERE syllabus_id = :syllabusId ORDER BY id";

        return jdbcTemplate.query(
                sql,
                new MapSqlParameterSource("syllabusId", syllabusId),
                (rs, rowNum) -> {
                    SyllabusChapter sc = new SyllabusChapter();
                    sc.setId(rs.getLong("id"));
                    sc.setSyllabusId(rs.getLong("syllabus_id"));
                    sc.setChapterId(rs.getLong("chapter_id"));
                    sc.setChapterName("chapter_name");
                    sc.setNumberOfQuestions(rs.getInt("number_of_questions"));
                    sc.setMarks(rs.getInt("marks"));
                    sc.setCoveragePercentage(BigDecimal.valueOf(rs.getDouble("coverage_percentage")));
                    return sc;
                }
        );
    }

    @Override
    public void deleteBySyllabusId(Long syllabusId) {

        jdbcTemplate.update(
                "DELETE FROM syllabus_chapter WHERE syllabus_id = :id",
                new MapSqlParameterSource("id", syllabusId)
        );
    }
}
