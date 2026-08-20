package com.mahaexam.papertemplate.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.common.bean.PaperTemplateResponse;
import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.papertemplate.model.PaperTemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaperTemplateRepositoryImpl implements PaperTemplateRepository {

	private final JdbcTemplate jdbcTemplate;
	private final ObjectMapper objectMapper;

	public PaperTemplateRepositoryImpl(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.objectMapper = objectMapper;
	}
	
	
	 @Override
	    public Long insert(PaperTemplate pt) {

	        String sql = "INSERT INTO paper_template (name, medium, board_id, class_id, total_duration, total_marks, part_display_name,"
	        		+ " number_of_parts, instructions, status, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	        KeyHolder keyHolder = new GeneratedKeyHolder();

	        jdbcTemplate.update(con -> {
	            PreparedStatement ps =
	                con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	            ps.setString(1, pt.getName());
	            ps.setString(2, pt.getMedium());
	            ps.setLong(3, pt.getBoardId());
	            ps.setLong(4, pt.getClassId());
	            ps.setInt(5, pt.getTotalDuration());
	            ps.setInt(6, pt.getTotalMarks());
	            ps.setString(7, pt.getPartDisplayName());
	            ps.setInt(8, pt.getNumberOfParts());
	            ps.setString(9, writeJson(pt.getInstructions()));
	            ps.setString(10, pt.getStatus());

	            if (pt.getTenantId() != null) {
	                ps.setLong(11, pt.getTenantId());
	            } else {
	                ps.setNull(11, java.sql.Types.BIGINT);
	            }
	            return ps;
	        }, keyHolder);

	        return keyHolder.getKey().longValue();
	    }

	    @Override
	    public void update(PaperTemplate pt, Long id) {

	        String sql = " UPDATE paper_template SET name=?, medium=?, board_id=?, class_id=?, total_duration=?,total_marks=?, "
	        		+ "part_display_name=?, number_of_parts=?, instructions=?, status=?, tenant_id=? WHERE id=? ";

	        jdbcTemplate.update(
	            sql,
	            pt.getName(),
	            pt.getMedium(),
	            pt.getBoardId(),
	            pt.getClassId(),
	            pt.getTotalDuration(),
	            pt.getTotalMarks(),
	            pt.getPartDisplayName(),
	            pt.getNumberOfParts(),
	            writeJson(pt.getInstructions()),
	            pt.getStatus(),
	            pt.getTenantId(),
	            id
	        );
	    }

	

	private String writeJson(List<String> instructions) {
		try {
			return objectMapper.writeValueAsString(instructions == null ? List.of() : instructions);
		} catch (Exception e) {
			throw new RuntimeException("Invalid JSON", e);
		}
	}

	@Override
	public PaperTemplate findById(Long id) {

		String sql = """
                SELECT pt.*,b.board_name,c.class_name  FROM paper_template pt
                inner join board b on pt.board_id =b.id
                inner join class c on pt.class_id = c.id
                WHERE pt.id = ? """;
		 return jdbcTemplate.queryForObject( sql, paperTemplateRowMapper(), id);
		
	}

	@Override
	public List<PaperTemplate> findAll(Long tenantId) {

		String sql = """
                SELECT pt.*,b.board_name,c.class_name  FROM paper_template pt
                inner join board b on pt.board_id =b.id
                inner join class c on pt.class_id = c.id
                where pt.tenant_id = ? and pt.status != 'InACTIVE'
                """;

		return jdbcTemplate.query(sql, new PaperTemplateRowMapper(), tenantId);
	}

	@Override
	public List<PaperTemplate> findAllByFilter(Long tenantId, Long boardId, Integer classId) {

		StringBuilder sql = new StringBuilder("""
                SELECT pt.*, b.board_name, c.class_name FROM paper_template pt
                INNER JOIN board b ON pt.board_id = b.id
                INNER JOIN class c ON pt.class_id = c.id
                WHERE pt.tenant_id = ? AND pt.status != 'InACTIVE'
                """);

		List<Object> params = new java.util.ArrayList<>();
		params.add(tenantId);

		if (boardId != null) {
			sql.append(" AND pt.board_id = ?");
			params.add(boardId);
		}
		if (classId != null) {
			sql.append(" AND pt.class_id = ?");
			params.add(classId);
		}

		sql.append(" ORDER BY pt.id DESC");

		return jdbcTemplate.query(sql.toString(), new PaperTemplateRowMapper(), params.toArray());
	}

	@Override
	public boolean existsByNameAndTenantId(String name, Long tenantId) {
		String sql = "SELECT COUNT(*) FROM paper_template WHERE LOWER(name) = LOWER(?) AND tenant_id = ? AND LOWER(status) != 'inactive'";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, tenantId);
		return count != null && count > 0;
	}

	@Override
	public boolean existsByNameAndTenantIdExcludingId(String name, Long tenantId, Long excludeId) {
		String sql = "SELECT COUNT(*) FROM paper_template WHERE LOWER(name) = LOWER(?) AND tenant_id = ? AND LOWER(status) != 'inactive' AND id != ?";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, tenantId, excludeId);
		return count != null && count > 0;
	}

	@Override
	public void deleteById(Long id) {
	    String deleteTemplateSql = "DELETE FROM paper_template WHERE id = ?";
	    jdbcTemplate.update(deleteTemplateSql, id);
	}
	
	private RowMapper<PaperTemplate> paperTemplateRowMapper() {
		return (rs, rowNum) -> {

			PaperTemplate pt = new PaperTemplate();
			pt.setId(rs.getLong("id"));
			pt.setName(rs.getString("name"));
			pt.setMedium(rs.getString("medium"));
			pt.setBoardId(rs.getLong("board_id"));
			pt.setClassId(rs.getLong("class_id"));
			pt.setTotalDuration(rs.getInt("total_duration"));
			pt.setTotalMarks(rs.getInt("total_marks"));
			pt.setPartDisplayName(rs.getString("part_display_name"));
			pt.setNumberOfParts(rs.getInt("number_of_parts"));
			pt.setStatus(rs.getString("status"));
//			pt.setTenantId(rs.getObject("tenant_id", Long.class));

			// ✅ JSON → List<String>
			String instructionsJson = rs.getString("instructions");
			pt.setInstructions(readJson(instructionsJson));

           pt.setBoardName(RepoUtil.getOptionalString(rs, "board_name"));
           pt.setClassName(RepoUtil.getOptionalString(rs, "class_name"));

			return pt;
		};
	}

	private List<String> readJson(String json) {
		if (json == null || json.trim().isEmpty()) {
			return new ArrayList<>();
		}

		try {
			return objectMapper.readValue(json, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {
			});
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse instructions JSON: " + json, e);
		}
	}

	@Override
	public BigDecimal getTotalMarks(Long paperTemplateId) {

		String sql = "SELECT total_marks FROM paper_template WHERE id = ?";
	        return jdbcTemplate.queryForObject(sql, BigDecimal.class, paperTemplateId);
	}
	
	@Override
	public PaperTemplateResponse findTemplate(Long templateId) {

	    String sql = "SELECT  pt.id, pt.name, pt.medium, pt.board_id, b.board_name, pt.class_id, c.class_name, "
	    		+ " pt.total_duration, pt.total_marks,pt.number_of_parts, pt.status, pt.tenant_id,instructions FROM paper_template pt "
	    		+ " INNER JOIN board b ON pt.board_id = b.id INNER JOIN class c ON pt.class_id = c.id "
	    		+ " WHERE pt.id = ? ";

	    return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
	        PaperTemplateResponse r = new PaperTemplateResponse();
	        r.setId(rs.getLong("id"));
	        r.setName(rs.getString("name"));
	        r.setMedium(rs.getString("medium"));
	        r.setBoardId(rs.getLong("board_id"));
	        r.setBoardName(rs.getString("board_name"));
	        r.setClassId(rs.getInt("class_id"));
	        r.setClassName(rs.getString("class_name"));
	        r.setTotalDuration(rs.getInt("total_duration"));
	        r.setTotalMarks(rs.getInt("total_marks"));
	        r.setNumberOfParts(rs.getInt("number_of_parts"));
	        r.setStatus(rs.getString("status"));
	        r.setTenantId(rs.getObject("tenant_id", Long.class));
			r.setInstructions(readJson(rs.getString("instructions")));
	        return r;
	    }, templateId);
	}

	@Override
	public List<PaperTemplateResponse> findTemplatesByIds(List<Long> templateIds) {
		if (templateIds == null || templateIds.isEmpty()) {
			return List.of();
		}

		String inClause = String.join(",", templateIds.stream().map(String::valueOf).toList());
		String sql = "SELECT  pt.id, pt.name, pt.medium, pt.board_id, b.board_name, pt.class_id, c.class_name, "
				+ " pt.total_duration, pt.total_marks,pt.number_of_parts, pt.status, pt.tenant_id FROM paper_template pt "
				+ "	INNER JOIN board b ON pt.board_id = b.id INNER JOIN class c ON pt.class_id = c.id " +
				"  WHERE pt.id IN (" + inClause + ")";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			PaperTemplateResponse r = new PaperTemplateResponse();
			r.setId(rs.getLong("id"));
			r.setName(rs.getString("name"));
			r.setMedium(rs.getString("medium"));
			r.setBoardId(rs.getLong("board_id"));
			r.setBoardName(rs.getString("board_name"));
			r.setClassId(rs.getInt("class_id"));
			r.setClassName(rs.getString("class_name"));
			r.setTotalDuration(rs.getInt("total_duration"));
			r.setTotalMarks(rs.getInt("total_marks"));
			r.setNumberOfParts(rs.getInt("number_of_parts"));
			r.setStatus(rs.getString("status"));
			r.setTenantId(rs.getObject("tenant_id", Long.class));
			return r;
		});
	}

	@Override
	public boolean existsByNameAndMediumAndBoardIdAndClassIdAndStatusAndTenantId(
			String name, String medium, Long boardId, Long classId, String status, Long tenantId) {

		String sql = "SELECT COUNT(*) FROM paper_template WHERE name = ? AND medium = ? AND board_id = ? AND class_id = ? AND status = ? AND tenant_id = ? ";

		Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
				name, medium, boardId, classId, status, tenantId);

		return count != null && count > 0;
	}

	@Override
	public boolean existsByNameAndMediumAndBoardIdAndClassIdAndStatusAndTenantIdExcludingId(
			String name, String medium, Long boardId, Long classId, String status, Long tenantId, Long excludeId) {

		String sql = "SELECT COUNT(*) FROM paper_template WHERE name = ?  AND medium = ? AND board_id = ? AND class_id = ? AND status = ? AND tenant_id = ? AND id != ? ";

		Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
				name, medium, boardId, classId, status, tenantId, excludeId);

		return count != null && count > 0;
	}


	@Override
	public void updateStatus(Long id) {
		String sql = "UPDATE paper_template SET status = 'inactive' WHERE id = ?";
		jdbcTemplate.update(sql, id);
		
	}

	@Override
	public boolean isTemplateReferencedInQuestionPaper(Long templateId) {
        String sql = "SELECT COUNT(*) FROM question_paper_template WHERE paper_template_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, templateId);
        return count != null && count > 0;
    }

}
