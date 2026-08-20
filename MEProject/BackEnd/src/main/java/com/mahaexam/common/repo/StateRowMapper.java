package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.State;

public class StateRowMapper implements RowMapper<State> {
    @Override
    public State mapRow(ResultSet rs, int rowNum) throws SQLException {
        State state = new State();
        state.setId(rs.getInt("id"));
        state.setStateName(rs.getString("state_name"));
        state.setStateAliasName(rs.getString("state_alias_name"));
        state.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        state.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        state.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        state.setDeleted(rs.getString("deleted"));
        state.setTenantId(rs.getInt("tenant_id"));
        return state;
    }
}

