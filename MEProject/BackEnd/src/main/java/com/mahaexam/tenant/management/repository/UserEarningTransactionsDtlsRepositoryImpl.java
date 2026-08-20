package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserEarningTransactionsDtls;
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
public class UserEarningTransactionsDtlsRepositoryImpl implements UserEarningTransactionsDtlsRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserEarningTransactionsDtlsRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UserEarningTransactionsDtlsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserEarningTransactionsDtls save(UserEarningTransactionsDtls dto) {
        String sql = "INSERT INTO user_earning_transactions_dtls (referral_user_id, student_id, student_package_id, commision_config_id, commision_type, level_order_id, earned_amount, earned_date, eligible_commision_slab) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (dto.getReferralUserId() != null) {
                ps.setLong(1, dto.getReferralUserId());
            } else {
                ps.setNull(1, Types.BIGINT);
            }
            ps.setLong(2, dto.getStudentId());
            ps.setInt(3, dto.getStudentPackageId());
            if (dto.getCommisionConfigId() != null) {
                ps.setLong(4, dto.getCommisionConfigId());
            } else {
                ps.setNull(4, Types.BIGINT);
            }
            if (dto.getCommisionType() != null) {
                ps.setInt(5, dto.getCommisionType());
            } else {
                ps.setNull(5, Types.TINYINT);
            }
            if (dto.getLevelOrderId() != null) {
                ps.setLong(6, dto.getLevelOrderId());
            } else {
                ps.setNull(6, Types.BIGINT);
            }
            if (dto.getEarnedAmount() != null) {
                ps.setBigDecimal(7, dto.getEarnedAmount());
            } else {
                ps.setNull(7, Types.DECIMAL);
            }
            if (dto.getEarnedDate() != null) {
                ps.setDate(8, Date.valueOf(dto.getEarnedDate()));
            } else {
                ps.setNull(8, Types.DATE);
            }
            if (dto.getEligibleCommisionSlab() != null) {
                ps.setLong(9, dto.getEligibleCommisionSlab());
            } else {
                ps.setNull(9, Types.BIGINT);
            }
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            dto.setId(key.longValue());
        }

        logger.info("UserEarningTransactionsDtls saved with ID: {}", dto.getId());
        return dto;
    }

    @Override
    public Optional<UserEarningTransactionsDtls> findById(Long id) {
        String sql = "SELECT * FROM user_earning_transactions_dtls WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserEarningTransactionsDtlsRowMapper(), id));
        } catch (Exception e) {
            logger.error("Error finding UserEarningTransactionsDtls by ID: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<UserEarningTransactionsDtls> findByReferralUserId(Long referralUserId) {
        String sql = "SELECT * FROM user_earning_transactions_dtls WHERE referral_user_id = ?";
        try {
            return jdbcTemplate.query(sql, new UserEarningTransactionsDtlsRowMapper(), referralUserId);
        } catch (Exception e) {
            logger.error("Error finding UserEarningTransactionsDtls by referralUserId: {}", referralUserId, e);
            return List.of();
        }
    }

    @Override
    public List<UserEarningTransactionsDtls> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM user_earning_transactions_dtls WHERE student_id = ?";
        try {
            return jdbcTemplate.query(sql, new UserEarningTransactionsDtlsRowMapper(), studentId);
        } catch (Exception e) {
            logger.error("Error finding UserEarningTransactionsDtls by studentId: {}", studentId, e);
            return List.of();
        }
    }

    @Override
    public UserEarningTransactionsDtls update(UserEarningTransactionsDtls dto) {
        String sql = "UPDATE user_earning_transactions_dtls SET referral_user_id = ?, student_id = ?, student_package_id = ?, commision_config_id = ?, commision_type = ?, level_order_id = ?, earned_amount = ?, earned_date = ?, eligible_commision_slab = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                dto.getReferralUserId(),
                dto.getStudentId(),
                dto.getStudentPackageId(),
                dto.getCommisionConfigId(),
                dto.getCommisionType(),
                dto.getLevelOrderId(),
                dto.getEarnedAmount(),
                dto.getEarnedDate() != null ? Date.valueOf(dto.getEarnedDate()) : null,
                dto.getEligibleCommisionSlab(),
                dto.getId());

        logger.info("UserEarningTransactionsDtls updated with ID: {}", dto.getId());
        return dto;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM user_earning_transactions_dtls WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("UserEarningTransactionsDtls deleted with ID: {}", id);
    }

    @Override
    public List<UserEarningTransactionsDtls> findAll() {
        String sql = "SELECT * FROM user_earning_transactions_dtls";
        try {
            return jdbcTemplate.query(sql, new UserEarningTransactionsDtlsRowMapper());
        } catch (Exception e) {
            logger.error("Error finding all UserEarningTransactionsDtls", e);
            return List.of();
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM user_earning_transactions_dtls WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
