package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.ExamGroupPackageCategoryMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamGroupPackageCategoryMapperRowMapper implements RowMapper<ExamGroupPackageCategoryMapper> {
    @Override
    public ExamGroupPackageCategoryMapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ExamGroupPackageCategoryMapper.builder()
                .id(rs.getInt("id"))
                .examGroupId(rs.getInt("exam_group_id"))
                .packageCategoryId(rs.getInt("package_category_id"))
                .build();
    }
}
