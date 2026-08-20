package com.mahaexam.common.repo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.PwdResetOtp;

@Repository
public class PwdResetOtpRepositoryImpl implements PwdResetOtpRepository {

    private final JdbcTemplate jdbcTemplate;

    public PwdResetOtpRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PwdResetOtp save(PwdResetOtp pwdResetOtp) {
        String sql = "INSERT INTO pwd_reset_otp (user_id, otp, created_at, expires_at) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, pwdResetOtp.getUserId());
            ps.setString(2, pwdResetOtp.getOtp());
            ps.setTimestamp(3, pwdResetOtp.getCreatedAt() != null ?
                Timestamp.valueOf(pwdResetOtp.getCreatedAt()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(4, pwdResetOtp.getExpiresAt() != null ?
                Timestamp.valueOf(pwdResetOtp.getExpiresAt()) : null);
            return ps;
        }, keyHolder);

        // Set the generated ID
        if (keyHolder.getKey() != null) {
            pwdResetOtp.setId(keyHolder.getKey().longValue());
        }

        return pwdResetOtp;
    }

    @Override
    public Optional<PwdResetOtp> findById(Long id) {
        String sql = "SELECT * FROM pwd_reset_otp WHERE id = ?";
        try {
            PwdResetOtp pwdResetOtp = jdbcTemplate.queryForObject(sql, new PwdResetOtpRowMapper(), id);
            return Optional.ofNullable(pwdResetOtp);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PwdResetOtp> findByUserIdAndOtp(Long userId, String otp) {
        String sql = "SELECT * FROM pwd_reset_otp WHERE user_id = ? AND otp = ? AND expires_at > NOW()";
        try {
            PwdResetOtp pwdResetOtp = jdbcTemplate.queryForObject(sql, new PwdResetOtpRowMapper(), userId, otp);
            return Optional.ofNullable(pwdResetOtp);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PwdResetOtp> findByUserId(Long userId) {
        String sql = "SELECT * FROM pwd_reset_otp WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new PwdResetOtpRowMapper(), userId);
    }

    @Override
    public List<PwdResetOtp> findValidOtpsByUserId(Long userId) {
        String sql = "SELECT * FROM pwd_reset_otp WHERE user_id = ? AND expires_at > NOW() ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new PwdResetOtpRowMapper(), userId);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM pwd_reset_otp WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        String sql = "DELETE FROM pwd_reset_otp WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void deleteExpiredOtps() {
        String sql = "DELETE FROM pwd_reset_otp WHERE expires_at <= NOW()";
        jdbcTemplate.update(sql);
    }

    @Override
    public int countValidOtpsByUserId(Long userId) {
        String sql = "SELECT COUNT(*) FROM pwd_reset_otp WHERE user_id = ? AND expires_at > NOW()";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null ? count : 0;
    }
}
