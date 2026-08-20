package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.MyEarningStats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyEarningStatsRowMapper implements RowMapper<MyEarningStats> {
    @Override
    public MyEarningStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MyEarningStats.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .levelOrderId(rs.getObject("level_order_id") != null ? rs.getLong("level_order_id") : null)
                .earningPeriodEndDt(rs.getDate("earning_period_end_dt") != null ? rs.getDate("earning_period_end_dt").toLocalDate() : null)
                .totalDirectStudentCount(rs.getObject("total_direct_student_count") != null ? rs.getInt("total_direct_student_count") : null)
                .totalDirectEarningAmt(rs.getBigDecimal("total_direct_earning_amt"))
                .totalIndirectStudentCount(rs.getObject("total_indirect_student_count") != null ? rs.getInt("total_indirect_student_count") : null)
                .totalIndirectEarningAmt(rs.getBigDecimal("total_indirect_earning_amt"))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                .build();
    }
}
