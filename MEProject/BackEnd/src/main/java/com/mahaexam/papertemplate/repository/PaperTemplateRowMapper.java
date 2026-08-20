package com.mahaexam.papertemplate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.papertemplate.model.PaperTemplate;

public class PaperTemplateRowMapper implements RowMapper<PaperTemplate> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public PaperTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {

		PaperTemplate t = new PaperTemplate();

		t.setId(rs.getLong("id"));
		t.setName(rs.getString("name"));
		t.setMedium(rs.getString("medium"));
		t.setBoardId(rs.getLong("board_id"));
		t.setClassId(rs.getLong("class_id"));
		t.setTotalDuration(rs.getInt("total_duration"));
		t.setTotalMarks(rs.getInt("total_marks"));
		t.setPartDisplayName(rs.getString("part_display_name"));
		t.setNumberOfParts(rs.getInt("number_of_parts"));
		t.setStatus(rs.getString("status"));

		try {
			String json = rs.getString("instructions");
			if (json != null) {
				t.setInstructions(mapper.readValue(json, new TypeReference<List<String>>() {
				}));
			}
		} catch (Exception e) {
			throw new RuntimeException("JSON parse error", e);
		}
       t.setBoardName(RepoUtil.getOptionalString(rs, "board_name"));
       t.setClassName(RepoUtil.getOptionalString(rs, "class_name"));

		return t;
	}
}
