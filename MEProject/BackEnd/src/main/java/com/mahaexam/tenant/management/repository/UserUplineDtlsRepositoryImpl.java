package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserUplineDtls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class UserUplineDtlsRepositoryImpl implements UserUplineDtlsRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserUplineDtlsRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UserUplineDtlsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserUplineDtls save(UserUplineDtls userUplineDtls) {
        String sql = "INSERT INTO user_upline_dtls (user_level1_id, user_level2_id, user_level3_id, user_level4_id, user_level5_id) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userUplineDtls.getUserLevel1Id());
            if (userUplineDtls.getUserLevel2Id() != null) {
                ps.setLong(2, userUplineDtls.getUserLevel2Id());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            if (userUplineDtls.getUserLevel3Id() != null) {
                ps.setLong(3, userUplineDtls.getUserLevel3Id());
            } else {
                ps.setNull(3, Types.BIGINT);
            }
            if (userUplineDtls.getUserLevel4Id() != null) {
                ps.setLong(4, userUplineDtls.getUserLevel4Id());
            } else {
                ps.setNull(4, Types.BIGINT);
            }
            if (userUplineDtls.getUserLevel5Id() != null) {
                ps.setLong(5, userUplineDtls.getUserLevel5Id());
            } else {
                ps.setNull(5, Types.BIGINT);
            }
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            userUplineDtls.setId(key.longValue());
        }

        logger.info("UserUplineDtls saved with ID: {}", userUplineDtls.getId());
        return userUplineDtls;
    }

    @Override
    public Optional<UserUplineDtls> findById(Long id) {
        String sql = "SELECT * FROM user_upline_dtls WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserUplineDtlsRowMapper(), id));
        } catch (Exception e) {
            logger.error("Error finding UserUplineDtls by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<UserUplineDtls> findByUserLevel1Id(Long userLevel1Id) {
        String sql = "SELECT * FROM user_upline_dtls WHERE user_level1_id = ?";
        try {
            return jdbcTemplate.query(sql, new UserUplineDtlsRowMapper(), userLevel1Id);
        } catch (Exception e) {
            logger.error("Error finding UserUplineDtls by userLevel1Id: {}", userLevel1Id, e);
            return List.of();
        }
    }

    @Override
    public UserUplineDtls update(UserUplineDtls userUplineDtls) {
        String sql = "UPDATE user_upline_dtls SET user_level1_id = ?, user_level2_id = ?, user_level3_id = ?, user_level4_id = ?, user_level5_id = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                userUplineDtls.getUserLevel1Id(),
                userUplineDtls.getUserLevel2Id(),
                userUplineDtls.getUserLevel3Id(),
                userUplineDtls.getUserLevel4Id(),
                userUplineDtls.getUserLevel5Id(),
                userUplineDtls.getId());

        logger.info("UserUplineDtls updated with ID: {}", userUplineDtls.getId());
        return userUplineDtls;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM user_upline_dtls WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("UserUplineDtls deleted with ID: {}", id);
    }

    @Override
    public List<UserUplineDtls> findAll() {
        String sql = "SELECT * FROM user_upline_dtls";
        try {
            return jdbcTemplate.query(sql, new UserUplineDtlsRowMapper());
        } catch (Exception e) {
            logger.error("Error finding all UserUplineDtls", e);
            return List.of();
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM user_upline_dtls WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
