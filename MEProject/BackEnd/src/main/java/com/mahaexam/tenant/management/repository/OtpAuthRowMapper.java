package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.OtpAuth;

public class OtpAuthRowMapper implements RowMapper<OtpAuth> {
    @Override
    public OtpAuth mapRow(ResultSet rs, int rowNum) throws SQLException {
        OtpAuth otpAuth = new OtpAuth();
        otpAuth.setId(rs.getLong("id"));
        otpAuth.setMobile(rs.getString("mobile"));
        otpAuth.setEmail(rs.getString("email"));
        otpAuth.setOtp(rs.getString("otp"));
        otpAuth.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return otpAuth;
    }
}