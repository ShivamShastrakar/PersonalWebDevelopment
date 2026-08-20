package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.PaymentTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PaymentTransactionRepositoryImpl implements PaymentTransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public PaymentTransactionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PaymentTransaction save(PaymentTransaction transaction) {
        String sql = "INSERT INTO payment_transactions (selection_summary_id, batch_id, payu_transaction_id, total_amount, payment_status, payment_link, payment_link_id, remark, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"transaction_id"});
            ps.setObject(1, transaction.getSelectionSummaryId()); // Use setObject to handle null values
            ps.setObject(2, transaction.getBatchId()); // Add batch_id parameter
            ps.setString(3, transaction.getPayuTransactionId());
            ps.setBigDecimal(4, transaction.getTotalAmount());
            ps.setString(5, transaction.getPaymentStatus());
            ps.setString(6, transaction.getPaymentLink());
            ps.setString(7, transaction.getPaymentLinkId()); // Nullable payment_link_id
            ps.setString(8, transaction.getRemark()); // Nullable remark
            ps.setTimestamp(9, java.sql.Timestamp.valueOf(transaction.getCreatedAt()));
            ps.setTimestamp(10, transaction.getUpdatedAt() != null ? java.sql.Timestamp.valueOf(transaction.getUpdatedAt()) : null);
            return ps;
        }, keyHolder);
        transaction.setTransactionId(keyHolder.getKey().longValue());
        return transaction;
    }

    @Override
    public void updatePaymentStatus(String payuTransactionId, String status) {
        String sql = "UPDATE payment_transactions SET payment_status = ?, updated_at = NOW() WHERE payu_transaction_id = ?";
        jdbcTemplate.update(sql, status, payuTransactionId);
    }

    /**
     * Returns the payment_status for the given transaction_id, or null if no such transaction exists.
     */
    public String getPaymentStatusByTransactionId(Long transactionId) {
        String sql = "SELECT payment_status FROM payment_transactions WHERE transaction_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, transactionId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private PaymentTransaction mapRowToTransaction(ResultSet rs, int rowNum) throws SQLException {
        PaymentTransaction transaction = PaymentTransaction.builder().build();
        transaction.setTransactionId(rs.getLong("transaction_id"));

        // Handle nullable selection_summary_id
        Long selectionSummaryId = rs.getLong("selection_summary_id");
        transaction.setSelectionSummaryId(rs.wasNull() ? null : selectionSummaryId);

        // Handle nullable batch_id
        Long batchId = rs.getLong("batch_id");
        transaction.setBatchId(rs.wasNull() ? null : batchId);

        transaction.setPayuTransactionId(rs.getString("payu_transaction_id"));
        transaction.setTotalAmount(rs.getBigDecimal("total_amount"));
        transaction.setPaymentStatus(rs.getString("payment_status"));
        transaction.setPaymentLink(rs.getString("payment_link"));

        // Handle nullable payment_link_id
        String paymentLinkId = rs.getString("payment_link_id");
        transaction.setPaymentLinkId(rs.wasNull() ? null : paymentLinkId);

        // Handle nullable remark
        String remark = rs.getString("remark");
        transaction.setRemark(rs.wasNull() ? null : remark);

        transaction.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        if (rs.getTimestamp("updated_at") != null) {
            transaction.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return transaction;
    }


}