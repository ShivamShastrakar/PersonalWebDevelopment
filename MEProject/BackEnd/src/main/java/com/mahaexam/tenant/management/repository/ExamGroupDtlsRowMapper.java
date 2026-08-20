package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.ExamGroupDtls;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamGroupDtlsRowMapper implements RowMapper<ExamGroupDtls> {
    @Override
    public ExamGroupDtls mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ExamGroupDtls.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
    }
}
