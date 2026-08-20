package com.mahaexam.common.repo;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.Config;

@Repository
public class ConfigRepositoryImpl implements ConfigRepository {
    private final JdbcTemplate jdbcTemplate;

    public ConfigRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Config save(Config config) {
        String sql = "INSERT INTO config (name, value, created_at, deleted) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, config.getName());
            ps.setString(2, config.getValue());
            ps.setObject(3, config.getCreatedAt() != null ? Timestamp.valueOf(config.getCreatedAt()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4, config.getDeleted() != null ? config.getDeleted() : "0");
            return ps;
        });

        return config;
    }

    @Override
    public Optional<Config> findByName(String name) {
        String sql = "SELECT * FROM config WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ConfigRowMapper(), name));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Config> findAllActive() {
        String sql = "SELECT * FROM config WHERE deleted = '0'";
        return jdbcTemplate.query(sql, new ConfigRowMapper());
    }

    @Override
    public List<Config> findAllDeleted() {
        String sql = "SELECT * FROM config WHERE deleted = '1'";
        return jdbcTemplate.query(sql, new ConfigRowMapper());
    }

    @Override
    public Config update(Config config) {
        String sql = "UPDATE config SET value = ?, updated_at = ?, deleted = ?, deleted_at = ? WHERE name = ?";

        jdbcTemplate.update(sql,
            config.getValue(),
            Timestamp.valueOf(LocalDateTime.now()),
            config.getDeleted() != null ? config.getDeleted() : "0",
            config.getDeletedAt() != null ? Timestamp.valueOf(config.getDeletedAt()) : null,
            config.getName()
        );
        return config;
    }

    @Override
    public void softDelete(String name) {
        String sql = "UPDATE config SET deleted = '1', deleted_at = ?, updated_at = ? WHERE name = ?";
        jdbcTemplate.update(sql, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), name);
    }
}