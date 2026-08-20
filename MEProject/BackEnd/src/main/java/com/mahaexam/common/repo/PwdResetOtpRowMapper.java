package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.PwdResetOtp;

public class PwdResetOtpRowMapper implements RowMapper<PwdResetOtp> {
    @Override
    public PwdResetOtp mapRow(ResultSet rs, int rowNum) throws SQLException {
        PwdResetOtp pwdResetOtp = new PwdResetOtp();
        pwdResetOtp.setId(rs.getLong("id"));
        pwdResetOtp.setUserId(rs.getLong("user_id"));
        pwdResetOtp.setOtp(rs.getString("otp"));
        pwdResetOtp.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        pwdResetOtp.setExpiresAt(rs.getTimestamp("expires_at") != null ? rs.getTimestamp("expires_at").toLocalDateTime() : null);
        return pwdResetOtp;
    }
}
