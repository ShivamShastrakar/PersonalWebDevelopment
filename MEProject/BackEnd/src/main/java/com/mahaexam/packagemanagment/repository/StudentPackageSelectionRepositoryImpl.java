package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.StudentPackageSelection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentPackageSelectionRepositoryImpl implements StudentPackageSelectionRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentPackageSelectionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StudentPackageSelection save(StudentPackageSelection selection) {
        String sql = "INSERT INTO student_package_selection (package_id, student_id, selection_summary_id, amount, selected_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"selection_id"});
            ps.setInt(1, selection.getPackageId());
            ps.setLong(2, selection.getStudentId());
            ps.setLong(3, selection.getSelectionSummaryId());
            ps.setBigDecimal(4, selection.getAmount());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(selection.getSelectedAt()));
            return ps;
        }, keyHolder);
        selection.setSelectionId(keyHolder.getKey().longValue());
        return selection;
    }

    @Override
    public List<StudentPackageSelection> save(List<StudentPackageSelection> selections) {
        if (selections == null || selections.isEmpty()) {
            return new ArrayList<>();
        }

        String sql = "INSERT INTO student_package_selection (package_id, student_id, selection_summary_id, amount, selected_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        // Prepare batch arguments
        List<Object[]> batchArgs = new ArrayList<>();
        for (StudentPackageSelection selection : selections) {
            if (selection == null) continue;
            if (selection.getPackageId() == null || selection.getStudentId() == null ||
                    selection.getSelectionSummaryId() == null || selection.getAmount() == null ||
                    selection.getSelectedAt() == null) {
                throw new IllegalArgumentException("Required fields cannot be null in StudentPackageSelection");
            }
            batchArgs.add(new Object[]{
                    selection.getPackageId(),
                    selection.getStudentId(),
                    selection.getSelectionSummaryId(),
                    selection.getAmount(),
                    java.sql.Timestamp.valueOf(selection.getSelectedAt())
            });
        }

        // Perform batch update
        int[] updateCounts = jdbcTemplate.batchUpdate(sql, batchArgs);

        // Verify all records were inserted
        if (batchArgs.size() != updateCounts.length) {
            throw new IllegalStateException("Batch insert failed: expected " + batchArgs.size() + " updates, but got " + updateCounts.length);
        }

        // Return input list unchanged (no selection_id set)
        return selections;
    }

    @Override
    public List<StudentPackageSelection> findBySelectionSummaryId(Long selectionSummaryId) {
        String sql = "SELECT * FROM student_package_selection WHERE selection_summary_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSelection, selectionSummaryId);
    }

    @Override
    public StudentPackageSelection findById(Long selectionId) {
        String sql = "SELECT * FROM student_package_selection WHERE selection_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToSelection, selectionId);
    }

    @Override
    public List<StudentPackageSelection> findByInvoiceNumber(String invoiceNumber) {
        String sql = "SELECT s.* FROM student_package_selection s " +
                "JOIN student_package_selection_summary sum ON s.selection_summary_id = sum.selection_summary_id " +
                "JOIN payment_transactions p ON p.selection_summary_id = sum.selection_summary_id " +
                "WHERE p.payu_transaction_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSelection, invoiceNumber);
    }

    private StudentPackageSelection mapRowToSelection(ResultSet rs, int rowNum) throws SQLException {
        StudentPackageSelection selection = new StudentPackageSelection();
        selection.setSelectionId(rs.getLong("selection_id"));
        selection.setPackageId(rs.getInt("package_id"));
        selection.setStudentId(rs.getLong("student_id"));
        selection.setSelectionSummaryId(rs.getLong("selection_summary_id"));
        selection.setAmount(rs.getBigDecimal("amount"));
        selection.setSelectedAt(rs.getTimestamp("selected_at").toLocalDateTime());
        return selection;
    }
}