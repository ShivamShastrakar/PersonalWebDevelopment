package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.OtpAuth;

@Repository
public class OtpAuthRepositoryImpl implements OtpAuthRepository {
	private static final Logger logger = LogManager.getLogger(OtpAuthRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public OtpAuthRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OtpAuth save(OtpAuth otpAuth) {
        String sql = "INSERT INTO otp_auth (mobile, email, otp, created_at) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, otpAuth.getMobile());
            ps.setString(2, otpAuth.getEmail());
            ps.setString(3, otpAuth.getOtp());
            ps.setObject(4, otpAuth.getCreatedAt() != null ? otpAuth.getCreatedAt() : LocalDateTime.now());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        otpAuth.setId(generatedId);
        return otpAuth;
    }

    @Override
    public Optional<OtpAuth> findById(Long id) {
        String sql = "SELECT * FROM otp_auth WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new OtpAuthRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<OtpAuth> findAll() {
        String sql = "SELECT * FROM otp_auth";
        return jdbcTemplate.query(sql, new OtpAuthRowMapper());
    }

    @Override
    public OtpAuth update(OtpAuth otpAuth) {
        String sql = "UPDATE otp_auth SET mobile = ?, email = ?, otp = ?, created_at = ? WHERE id = ?";

        jdbcTemplate.update(sql,
            otpAuth.getMobile(),
            otpAuth.getEmail(),
            otpAuth.getOtp(),
            otpAuth.getCreatedAt(),
            otpAuth.getId()
        );
        return otpAuth;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM otp_auth WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<OtpAuth> findByEmail(String email) {
        String sql = "SELECT * FROM otp_auth WHERE email = ? ORDER BY created_at DESC LIMIT 1";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new OtpAuthRowMapper(), email));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<OtpAuth> findByMobile(String mobile) {
        String sql = "SELECT * FROM otp_auth WHERE mobile = ? ORDER BY created_at DESC LIMIT 1";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{mobile}, new OtpAuthRowMapper()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<OtpAuth> findByEmailAndMobile(String email, String mobile) {
        String sql = "SELECT * FROM otp_auth WHERE email = ? and mobile = ? ORDER BY created_at DESC LIMIT 1";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new OtpAuthRowMapper(), email,mobile));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    @Override
    public void delete(String email, String mobile) {
        String sql = "DELETE FROM otp_auth WHERE email = ? AND mobile = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, email, mobile);
            if (rowsAffected > 0) {
            	logger.info("Deleted " + rowsAffected + " record(s) for email: " + email + ", mobile: " + mobile);
            } else {
            	logger.info("No records found to delete for email: " + email + ", mobile: " + mobile);
            }
        } catch (Exception e) {
        	logger.error("Error deleting record for email: " + email + ", mobile: " + mobile + ": " + e.getMessage(),e);
            throw new RuntimeException("Failed to delete OTP auth record", e);
        }
    }
}