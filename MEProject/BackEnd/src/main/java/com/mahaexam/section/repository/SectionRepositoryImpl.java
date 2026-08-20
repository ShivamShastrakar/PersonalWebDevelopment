package com.mahaexam.section.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.SectionResponse;
import com.mahaexam.common.model.Section;
@Repository
public class SectionRepositoryImpl implements SectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SectionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void insert(Section section, Long partId) {

        String sql = "INSERT INTO sections (part_id, name, display_name, question_type, number_of_questions, marks_per_question, "
        		+" negative_marks, total_marks, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        jdbcTemplate.update(
            sql,
            partId,
            section.getName(),
            section.getDisplayName(),
            section.getQuestionType(),
            section.getNumberOfQuestions(),
            section.getMarksPerQuestion(),
            section.getNegativeMarks(),
            section.getTotalMarks(),
            section.getStatus()
        );
    }

    @Override
    public void save(Section section) {

    	
        String sql = "INSERT INTO sections(part_id, name, display_name, question_type, number_of_questions, "
        		+ " marks_per_question, negative_marks, total_marks,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        jdbcTemplate.update(
                sql,
                section.getPartId(),
                section.getName(),
                section.getDisplayName(),
                section.getQuestionType(),
                section.getNumberOfQuestions(),
                section.getMarksPerQuestion(),
                section.getNegativeMarks(),
                section.getTotalMarks(),
                section.getStatus()
        );
    }

    @Override
    public List<Section> findByPartId(Long partId) {

        String sql = """
            SELECT * FROM sections
            WHERE part_id = ?
              AND status = 'ACTIVE'
        """;

        return jdbcTemplate.query(sql, sectionRowMapper(), partId);
    }

    @Override
    public boolean existsByPartIdAndName(Long partId, String name) {

        String sql = "SELECT COUNT(*) FROM sections WHERE part_id = ? AND name = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, partId, name);

        return count != null && count > 0;
    }

    @Override
    public BigDecimal getTotalMarksByPart(Long partId) {

        String sql = "SELECT COALESCE(SUM(total_marks), 0) FROM sections WHERE part_id = ? AND status = 'ACTIVE' ";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, partId);
    }
    
    private RowMapper<Section> sectionRowMapper() {
		return (rs, rowNum) -> {
			Section s = new Section();
	        s.setId(rs.getLong("id"));
	        s.setPartId(rs.getLong("part_id"));
	        s.setName(rs.getString("name"));
	        s.setDisplayName(rs.getBoolean("display_name"));
	        s.setQuestionType(rs.getString("question_type"));
	        s.setNumberOfQuestions(rs.getInt("number_of_questions"));
	        s.setMarksPerQuestion(rs.getBigDecimal("marks_per_question"));
	        s.setNegativeMarks(rs.getBigDecimal("negative_marks"));
	        s.setTotalMarks(rs.getBigDecimal("total_marks"));
	        s.setStatus(rs.getString("status"));
	        return s;
		};
	}

	@Override
	public void deleteByTemplateId(Long templateId) {
		String sql = "DELETE s FROM sections s JOIN part p ON s.part_id = p.id WHERE p.paper_template_id = ?";
		        jdbcTemplate.update(sql, templateId);
		
	}
	
	@Override
	public List<SectionResponse> byPartId(Long partId) {

	    String sql = " SELECT id, name, display_name, question_type, number_of_questions, marks_per_question, negative_marks, total_marks "
	    		+ " FROM sections WHERE part_id = ? ORDER BY id ";

	    return jdbcTemplate.query(sql, (rs, rowNum) -> {
	        SectionResponse s = new SectionResponse();
	        s.setId(rs.getLong("id"));
	        s.setName(rs.getString("name"));
	        s.setDisplayName(rs.getBoolean("display_name"));
	        s.setQuestionType(rs.getString("question_type"));
	        s.setNumberOfQuestions(rs.getInt("number_of_questions"));
	        s.setMarksPerQuestion(rs.getDouble("marks_per_question"));
	        s.setNegativeMarks(rs.getDouble("negative_marks"));
	        s.setTotalMarks(rs.getInt("total_marks"));
	        return s;
	    }, partId);
	}

	@Override
	public List<SectionResponse> byPartIds(List<Long> partIds) {
		if (partIds == null || partIds.isEmpty()) {
			return List.of();
		}

		String inClause = String.join(",", partIds.stream().map(String::valueOf).toList());
		String sql = " SELECT id, part_id, name, display_name, question_type, number_of_questions, " +
				"marks_per_question, negative_marks, total_marks " +
				" FROM sections WHERE part_id IN (" + inClause + ") ORDER BY part_id, id ";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			SectionResponse s = new SectionResponse();
			s.setId(rs.getLong("id"));
			s.setPartId(rs.getLong("part_id"));
			s.setName(rs.getString("name"));
			s.setDisplayName(rs.getBoolean("display_name"));
			s.setQuestionType(rs.getString("question_type"));
			s.setNumberOfQuestions(rs.getInt("number_of_questions"));
			s.setMarksPerQuestion(rs.getDouble("marks_per_question"));
			s.setNegativeMarks(rs.getDouble("negative_marks"));
			s.setTotalMarks(rs.getInt("total_marks"));
			return s;
		});
	}

	@Override
	public void deleteByPartIds(List<Long> partIds) {
        if (partIds == null || partIds.isEmpty()) {
            return;
        }
        String inClause = String.join(",", partIds.stream().map(String::valueOf).toList());
        String sql = "DELETE FROM sections WHERE part_id IN (" + inClause + ")";
        jdbcTemplate.update(sql);
    }
}
