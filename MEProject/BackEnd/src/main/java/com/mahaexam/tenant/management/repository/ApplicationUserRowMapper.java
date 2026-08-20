package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.ApplicationUser;

public class ApplicationUserRowMapper implements RowMapper<ApplicationUser> {
    @Override
    public ApplicationUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        ApplicationUser user = new ApplicationUser();
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
        user.setAddressId(rs.getLong("address_id"));
        user.setUserParentId(RepoUtil.getOptionalLong(rs, "user_parent_id"));
        user.setAdditionalCommissionPercent(rs.getBigDecimal("additional_commission_percent"));
        user.setPhotoUrl(rs.getString("photo_url"));
        user.setUserName(RepoUtil.getOptionalString(rs,"username"));
        user.setRole(RepoUtil.getOptionalString(rs,"roleNames"));
        user.setUserParentId(rs.getLong("user_parent_id"));
        return user;
    }
}