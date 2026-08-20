package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.util.RepoUtil;

public class ClassRowMapper implements RowMapper<ClassEntity> {
    @Override
    public ClassEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClassEntity clazz = new ClassEntity();
        clazz.setId(rs.getInt("id"));
        clazz.setTenantId(rs.getLong("tenant_id"));
        clazz.setClassName(rs.getString("class_name"));
        clazz.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        clazz.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        clazz.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        clazz.setDeleted(rs.getString("deleted"));
        
        clazz.setPackageId(RepoUtil.getOptionalInteger(rs, "package_id"));
        clazz.setIsExamGroupRequired(RepoUtil.getOptionalBoolean(rs, "is_exam_group_required"));
        return clazz;
    }
}