package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.MyEarningStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class MyEarningStatsRepositoryImpl implements MyEarningStatsRepository {
    private static final Logger logger = LoggerFactory.getLogger(MyEarningStatsRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public MyEarningStatsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MyEarningStats save(MyEarningStats dto) {
        String sql = "INSERT INTO my_earning_stats (user_id, level_order_id, earning_period_end_dt, total_direct_student_count, total_direct_earning_amt, total_indirect_student_count, total_indirect_earning_amt) VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getUserId());
            if (dto.getLevelOrderId() != null) {
                ps.setLong(2, dto.getLevelOrderId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            if (dto.getEarningPeriodEndDt() != null) {
                ps.setDate(3, Date.valueOf(dto.getEarningPeriodEndDt()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            if (dto.getTotalDirectStudentCount() != null) {
                ps.setInt(4, dto.getTotalDirectStudentCount());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            if (dto.getTotalDirectEarningAmt() != null) {
                ps.setBigDecimal(5, dto.getTotalDirectEarningAmt());
            } else {
                ps.setNull(5, Types.DECIMAL);
            }
            if (dto.getTotalIndirectStudentCount() != null) {
                ps.setInt(6, dto.getTotalIndirectStudentCount());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            if (dto.getTotalIndirectEarningAmt() != null) {
                ps.setBigDecimal(7, dto.getTotalIndirectEarningAmt());
            } else {
                ps.setNull(7, Types.DECIMAL);
            }
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            dto.setId(key.longValue());
        }

        logger.info("MyEarningStats saved with ID: {}", dto.getId());
        return dto;
    }

    @Override
    public Optional<MyEarningStats> findById(Long id) {
        String sql = "SELECT * FROM my_earning_stats WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new MyEarningStatsRowMapper(), id));
        } catch (Exception e) {
            logger.error("Error finding MyEarningStats by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<MyEarningStats> findByUserId(Long userId) {
        String sql = "SELECT * FROM my_earning_stats WHERE user_id = ?";
        try {
            return jdbcTemplate.query(sql, new MyEarningStatsRowMapper(), userId);
        } catch (Exception e) {
            logger.error("Error finding MyEarningStats by userId: {}", userId, e);
            return List.of();
        }
    }

    @Override
    public MyEarningStats update(MyEarningStats dto) {
        String sql = "UPDATE my_earning_stats SET user_id = ?, level_order_id = ?, earning_period_end_dt = ?, total_direct_student_count = ?, total_direct_earning_amt = ?, total_indirect_student_count = ?, total_indirect_earning_amt = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                dto.getUserId(),
                dto.getLevelOrderId(),
                dto.getEarningPeriodEndDt() != null ? Date.valueOf(dto.getEarningPeriodEndDt()) : null,
                dto.getTotalDirectStudentCount(),
                dto.getTotalDirectEarningAmt(),
                dto.getTotalIndirectStudentCount(),
                dto.getTotalIndirectEarningAmt(),
                dto.getId());

        logger.info("MyEarningStats updated with ID: {}", dto.getId());
        return dto;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM my_earning_stats WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("MyEarningStats deleted with ID: {}", id);
    }

    @Override
    public List<MyEarningStats> findAll() {
        String sql = "SELECT * FROM my_earning_stats";
        try {
            return jdbcTemplate.query(sql, new MyEarningStatsRowMapper());
        } catch (Exception e) {
            logger.error("Error finding all MyEarningStats", e);
            return List.of();
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM my_earning_stats WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
