package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.ApplicationUserProfile;

public class ApplicationUserProfileRowMapper implements RowMapper<ApplicationUserProfile> {
    @Override
    public ApplicationUserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ApplicationUserProfile user = new ApplicationUserProfile();
        user.setId(rs.getLong("id"));
        user.setUserId(rs.getLong("user_id"));
        user.setUserType(rs.getString("user_type"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setMiddleName(rs.getString("middle_name"));
        user.setGender(rs.getString("gender"));
        user.setDateOfBirth(rs.getDate("date_of_birth") != null ? 
                            rs.getDate("date_of_birth").toLocalDate() : null);
        user.setAadharNumber(rs.getString("aadhar_number"));
        user.setRegisteredMobileNumber(rs.getString("registered_mobile_number"));
        user.setWhatsappNumber(rs.getString("whatsapp_number"));
        user.setEmail(rs.getString("email"));
        user.setCreatedAt(rs.getTimestamp("created_at") != null ? 
                         rs.getTimestamp("created_at").toLocalDateTime() : null);
        user.setUpdatedAt(rs.getTimestamp("updated_at") != null ? 
                         rs.getTimestamp("updated_at").toLocalDateTime() : null);
        return user;
    }
}