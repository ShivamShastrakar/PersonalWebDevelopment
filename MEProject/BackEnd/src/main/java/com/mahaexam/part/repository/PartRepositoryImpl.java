package com.mahaexam.part.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.PartResponse;
import com.mahaexam.papertemplate.model.Part;

@Repository
public class PartRepositoryImpl implements PartRepository {

	private final JdbcTemplate jdbcTemplate;

	public PartRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long insert(Part part, Long templateId) {

		String sql = """
				  INSERT INTO part
				  (name, display_name, display_subject,
				   number_of_sections, paper_template_id, subject_id)
				  VALUES (?, ?, ?, ?, ?, ?)
				""";

		KeyHolder kh = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, part.getName());
			ps.setBoolean(2, part.getDisplayName());
			ps.setBoolean(3, part.getDisplaySubject());
			ps.setInt(4, part.getNumberOfSections());
			ps.setLong(5, templateId);

            ps.setInt(6, part.getSubjectId());

			return ps;
		}, kh);

		return kh.getKey().longValue();
	}

	public Part save(Part part) {

		String sql = """
				    INSERT INTO part (
				        name,
				        display_name,
				        display_subject,
				        number_of_sections,
				        paper_template_id,
				        subject_id
				    ) VALUES (
				        :name,
				        :displayName,
				        :displaySubject,
				        :numberOfSections,
				        :paperTemplateId,
				        :subjectId
				    )
				""";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", part.getName())
				.addValue("displayName", part.getDisplayName()).addValue("displaySubject", part.getDisplaySubject())
				.addValue("numberOfSections", part.getNumberOfSections())
				.addValue("paperTemplateId", part.getPaperTemplateId())
				.addValue("subjectId", part.getSubjectId());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, params, keyHolder, new String[] { "id" });

		part.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

		return findById(part.getId());
	}

	public Part update(Part part) {

		String sql = """
				    UPDATE part
				    SET name = :name,
				        display_name = :displayName,
				        display_subject = :displaySubject,
				        number_of_sections = :numberOfSections,
				        subject_id = :subjectId
				    WHERE id = :id
				""";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", part.getId())
				.addValue("name", part.getName()).addValue("displayName", part.getDisplayName())
				.addValue("displaySubject", part.getDisplaySubject())
				.addValue("numberOfSections", part.getNumberOfSections())
				.addValue("subjectId", part.getSubjectId());

		jdbcTemplate.update(sql, params);
		return part;
	}

	/*
	 * ------------------------------------------------- DELETE
	 * -------------------------------------------------
	 */
	@Override
	public void deleteByPaperTemplateId(Long templateId) {

		String sql = "DELETE FROM part WHERE paper_template_id = ?";

		jdbcTemplate.update(sql, templateId);
	}

	/*
	 * ------------------------------------------------- FIND (already discussed *
	 * earlier) -------------------------------------------------
	 */
	@Override
	public List<Part> findByPaperTemplateId(Long templateId) {

		String sql = """
				  SELECT
                    id,
                    name,
                    display_name,
                    display_subject,
                    number_of_sections,
                    paper_template_id,
                    p.subject_id,
                    s.subject_name
                 FROM
                    part p
                 inner join subject s on
                    p.subject_id = s.subject_id
				 WHERE paper_template_id = ?
				 ORDER BY id
				""";

		return jdbcTemplate.query(sql, partRowMapper(), templateId);

	}

	@Override
	public Part findById(Long id) {

		String sql = """
				  SELECT
                    id,
                    name,
                    display_name,
                    display_subject,
                    number_of_sections,
                    paper_template_id,
                    p.subject_id,
                    s.subject_name
                 FROM
                    part p
                 inner join subject s on
                    p.subject_id = s.subject_id
				 WHERE id = ?
				""";
		return jdbcTemplate.queryForObject(sql, partRowMapper(), id);

	}

	@Override
	public Long getPaperTemplateId(Long partId) {
		String sql = " SELECT paper_template_id FROM part WHERE id = ? ";
		return jdbcTemplate.queryForObject(sql, Long.class, partId);

	}

	/*
	 * ------------------------------------------------- ROW MAPPER
	 * -------------------------------------------------
	 */
	private RowMapper<Part> partRowMapper() {
		return (rs, rowNum) -> {

			/* ---------------- PART ---------------- */
			Part part = new Part();
			part.setId(rs.getLong("id"));
			part.setName(rs.getString("name"));
			part.setDisplayName(rs.getBoolean("display_name"));
			part.setDisplaySubject(rs.getBoolean("display_subject"));
			part.setNumberOfSections(rs.getInt("number_of_sections"));

			// Set paper_template_id
			Long paperTemplateId = rs.getLong("paper_template_id");
			if (!rs.wasNull()) {
				part.setPaperTemplateId(paperTemplateId);
			}

			// Set subject_id
			Integer subjectId = rs.getInt("subject_id");
			if (!rs.wasNull()) {
				part.setSubjectId(subjectId);
			}
            part.setSubjectName(RepoUtil.getOptionalString(rs,"subject_name"));
			return part;
		};
	}
	
	
	@Override
	public List<PartResponse> byPaperTemplateId(Long templateId) {

	    String sql = """
                 SELECT
                    id,
                    name,
                    display_name,
                    display_subject,
                    number_of_sections,
                    paper_template_id,
                    p.subject_id,
                    s.subject_name
                 FROM
                    part p
                 inner join subject s on
                    p.subject_id = s.subject_id
                 WHERE paper_template_id = ? ORDER BY id
                """;
	    return jdbcTemplate.query(sql, (rs, rowNum) -> {
	        PartResponse p = new PartResponse();
	        p.setId(rs.getLong("id"));
	        p.setName(rs.getString("name"));
	        p.setSubjectId(rs.getLong("subject_id"));
	        p.setDisplayName(rs.getBoolean("display_name"));
	        p.setDisplaySubject(rs.getBoolean("display_subject"));
	        p.setNumberOfSections(rs.getInt("number_of_sections"));
            p.setSubjectName(RepoUtil.getOptionalString(rs, "subject_name"));
	        return p;
	    }, templateId);
	}

	@Override
	public List<PartResponse> byPaperTemplateIds(List<Long> templateIds) {
		if (templateIds == null || templateIds.isEmpty()) {
			return List.of();
		}

		String inClause = String.join(",", templateIds.stream().map(String::valueOf).toList());
		String sql = "SELECT " +
				"id, name, display_name, display_subject, number_of_sections, " +
				"paper_template_id, p.subject_id, s.subject_name " +
				"FROM part p " +
				"INNER JOIN subject s ON p.subject_id = s.subject_id " +
				"WHERE paper_template_id IN (" + inClause + ") " +
				"ORDER BY paper_template_id, id";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			PartResponse p = new PartResponse();
			p.setId(rs.getLong("id"));
			p.setName(rs.getString("name"));
			p.setSubjectId(rs.getLong("subject_id"));
			p.setDisplayName(rs.getBoolean("display_name"));
			p.setDisplaySubject(rs.getBoolean("display_subject"));
			p.setNumberOfSections(rs.getInt("number_of_sections"));
			p.setPaperTemplateId(rs.getLong("paper_template_id"));
			p.setSubjectName(RepoUtil.getOptionalString(rs, "subject_name"));
			return p;
		});
	}

}
