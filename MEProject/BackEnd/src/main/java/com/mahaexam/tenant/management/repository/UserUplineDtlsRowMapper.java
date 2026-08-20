package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.UserUplineDtls;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUplineDtlsRowMapper implements RowMapper<UserUplineDtls> {
    @Override
    public UserUplineDtls mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserUplineDtls.builder()
                .id(rs.getLong("id"))
                .userLevel1Id(rs.getObject("user_level1_id") != null ? rs.getLong("user_level1_id") : null)
                .userLevel2Id(rs.getObject("user_level2_id") != null ? rs.getLong("user_level2_id") : null)
                .userLevel3Id(rs.getObject("user_level3_id") != null ? rs.getLong("user_level3_id") : null)
                .userLevel4Id(rs.getObject("user_level4_id") != null ? rs.getLong("user_level4_id") : null)
                .userLevel5Id(rs.getObject("user_level5_id") != null ? rs.getLong("user_level5_id") : null)
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                .build();
    }
}
