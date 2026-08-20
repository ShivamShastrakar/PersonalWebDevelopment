package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.EducationSociety;

public class EducationSocietyRowMapper implements RowMapper<EducationSociety> {

    @Override
    public EducationSociety mapRow(ResultSet rs, int rowNum) throws SQLException {
        EducationSociety s = new EducationSociety();
        s.setId(rs.getInt("id"));
        s.setSocietyName(rs.getString("society_name"));
        s.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        s.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        s.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));
        s.setDeleted(rs.getString("deleted"));
        s.setDisabled(rs.getBoolean("is_disabled"));
        return s;
    }
}
