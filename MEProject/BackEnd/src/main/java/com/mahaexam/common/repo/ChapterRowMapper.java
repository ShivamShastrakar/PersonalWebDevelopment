package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.Chapter;

public class ChapterRowMapper implements RowMapper<Chapter> {
    @Override
    public Chapter mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Chapter chapter = new Chapter();
    	chapter.setId(rs.getInt("id"));
    	chapter.setChapterName(rs.getString("chapter_name"));
    	chapter.setUnit(rs.getString("unit"));
    	chapter.setClassName(rs.getString("class"));
    	chapter.setExamType(rs.getString("exam_type"));
    	chapter.setSubjectId(rs.getInt("subject_id"));
    	chapter.setStatus(rs.getString("status"));
    	chapter.setInstituteId(rs.getInt("institute_id"));
    	chapter.setCreatedDate(rs.getTimestamp("created_date") != null ? rs.getTimestamp("created_date").toLocalDateTime() : null);
    	chapter.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
    	chapter.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
      chapter.setTenantId(rs.getObject("tenant_id", Long.class));

      chapter.setCoveragePercentage(RepoUtil.getOptionalInteger(rs,"coverage_percentage"));

      chapter.setClassName(RepoUtil.getOptionalString(rs,"class_name"));
      chapter.setBoardName(RepoUtil.getOptionalString(rs,"board_name"));
	  chapter.setSubjectName(RepoUtil.getOptionalString(rs,"subject_name"));

      return chapter;
    }
}