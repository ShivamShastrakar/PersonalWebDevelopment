package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.Config;

public class ConfigRowMapper implements RowMapper<Config> {
    @Override
    public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
        Config config = new Config();
        config.setName(rs.getString("name"));
        config.setValue(rs.getString("value"));
        config.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        config.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        config.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        config.setDeleted(rs.getString("deleted"));
        return config;
    }
}