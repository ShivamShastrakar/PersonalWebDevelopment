package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.SubjectBoardClassMapping;

public class SubjectBoardClassMappingRowMapper implements RowMapper<SubjectBoardClassMapping> {
	@Override
	public SubjectBoardClassMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubjectBoardClassMapping mapping = new SubjectBoardClassMapping();
		mapping.setId(rs.getInt("id"));
		mapping.setSubjectId(rs.getInt("subject_id"));
		mapping.setClassId(rs.getInt("class_id"));
		mapping.setBoardId(rs.getInt("board_id"));
		mapping.setMedium(RepoUtil.getOptionalString(rs, "medium"));
		mapping.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		mapping.setUpdatedAt(
				rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
		mapping.setDeletedAt(
				rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
		mapping.setDeleted(rs.getString("deleted"));
        mapping.setClassName(RepoUtil.getOptionalString(rs, "class_name"));
        mapping.setBoardName(RepoUtil.getOptionalString(rs, "board_name"));

		return mapping;
	}
}