package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserEarningTransactionsDtls;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEarningTransactionsDtlsRowMapper implements RowMapper<UserEarningTransactionsDtls> {
    @Override
    public UserEarningTransactionsDtls mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserEarningTransactionsDtls.builder()
                .id(rs.getLong("id"))
                .referralUserId(rs.getObject("referral_user_id") != null ? rs.getLong("referral_user_id") : null)
                .studentId(rs.getLong("student_id"))
                .studentPackageId(rs.getInt("student_package_id"))
                .commisionConfigId(rs.getObject("commision_config_id") != null ? rs.getLong("commision_config_id") : null)
                .commisionType(rs.getObject("commision_type") != null ? rs.getInt("commision_type") : null)
                .levelOrderId(rs.getObject("level_order_id") != null ? rs.getLong("level_order_id") : null)
                .earnedAmount(rs.getBigDecimal("earned_amount"))
                .earnedDate(rs.getDate("earned_date") != null ? rs.getDate("earned_date").toLocalDate() : null)
                .eligibleCommisionSlab(rs.getObject("eligible_commision_slab") != null ? rs.getLong("eligible_commision_slab") : null)
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                .build();
    }
}
