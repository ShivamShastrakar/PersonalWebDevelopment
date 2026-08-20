package com.mahaexam.common.repo;

import com.mahaexam.common.model.ChapterBoardClassMapping;
import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChapterBoardClassMappingRowMapper implements RowMapper<ChapterBoardClassMapping> {
    @Override
    public ChapterBoardClassMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChapterBoardClassMapping mapping = new ChapterBoardClassMapping();
        mapping.setId(rs.getInt("id"));
        mapping.setChapterId(getNullableInt(rs, "chapter_id"));
        mapping.setClassId(getNullableInt(rs, "class_id"));
        mapping.setBoardId(getNullableInt(rs, "board_id"));
        if (hasColumn(rs, "created_at") && rs.getTimestamp("created_at") != null) {
            mapping.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (hasColumn(rs, "updated_at") && rs.getTimestamp("updated_at") != null) {
            mapping.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        if (hasColumn(rs, "deleted_at") && rs.getTimestamp("deleted_at") != null) {
            mapping.setDeletedAt(rs.getTimestamp("deleted_at").toLocalDateTime());
        }
        if (hasColumn(rs, "deleted")) {
            mapping.setDeleted(rs.getString("deleted"));
        }
        mapping.setClassName(RepoUtil.getOptionalString(rs, "class_name"));
        mapping.setBoardName(RepoUtil.getOptionalString(rs, "board_name"));
        mapping.setChapterName(RepoUtil.getOptionalString(rs, "chapter_name"));
        return mapping;
    }

    private Integer getNullableInt(ResultSet rs, String column) throws SQLException {
        try {
            int value = rs.getInt(column);
            return rs.wasNull() ? null : value;
        } catch (SQLException e) {
            return null; // column might not exist in select
        }
    }

    private boolean hasColumn(ResultSet rs, String column) {
        try {
            rs.findColumn(column);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
