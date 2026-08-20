package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.StudentPackageSelectionSummary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class StudentPackageSelectionSummaryRepositoryImpl implements StudentPackageSelectionSummaryRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentPackageSelectionSummaryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StudentPackageSelectionSummary save(StudentPackageSelectionSummary summary) {
        String sql = "INSERT INTO student_package_selection_summary (student_id, total_amount, selected_at, status) " +
                "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"selection_summary_id"});
            ps.setLong(1, summary.getStudentId());
            ps.setBigDecimal(2, summary.getTotalAmount());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(summary.getSelectedAt()));
            ps.setString(4, summary.getStatus());
            return ps;
        }, keyHolder);
        summary.setSelectionSummaryId(keyHolder.getKey().longValue());
        return summary;
    }

    @Override
    public void updateStatus(Long selectionSummaryId, String status) {
        String sql = "UPDATE student_package_selection_summary SET status = ? WHERE selection_summary_id = ?";
        jdbcTemplate.update(sql, status, selectionSummaryId);
    }

    private StudentPackageSelectionSummary mapRowToSummary(ResultSet rs, int rowNum) throws SQLException {
        StudentPackageSelectionSummary summary = new StudentPackageSelectionSummary();
        summary.setSelectionSummaryId(rs.getLong("selection_summary_id"));
        summary.setStudentId(rs.getLong("student_id"));
        summary.setTotalAmount(rs.getBigDecimal("total_amount"));
        summary.setSelectedAt(rs.getTimestamp("selected_at").toLocalDateTime());
        summary.setStatus(rs.getString("status"));
        return summary;
    }
}