package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.OfflinePaymentModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OfflinePaymentRepositoryImpl implements OfflinePaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public OfflinePaymentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<OfflinePaymentModel> OFFLINE_PAYMENT_ROW_MAPPER = new RowMapper<OfflinePaymentModel>() {
        @Override
        public OfflinePaymentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            return OfflinePaymentModel.builder()
                    .id(rs.getLong("id"))
                    .amount(rs.getBigDecimal("amount"))
                    .paymentMode(rs.getString("payment_mode"))
                    .paymentDate(rs.getDate("payment_date") != null ?
                                 rs.getDate("payment_date").toLocalDate() : null)
                    .remarks(rs.getString("remarks"))
                    .chequeNumber(rs.getString("cheque_number"))
                    .bankName(rs.getString("bank_name"))
                    .chequeDate(rs.getDate("cheque_date") != null ?
                                rs.getDate("cheque_date").toLocalDate() : null)
                    .receivedBy(rs.getString("received_by"))
                    .batchId((Long) rs.getObject("batch_id"))
                    .transactionId((Long) rs.getObject("transaction_id"))
                    .status(rs.getString("status"))
                    .createdAt(rs.getTimestamp("created_at") != null ?
                              rs.getTimestamp("created_at").toLocalDateTime() : null)
                    .updatedAt(rs.getTimestamp("updated_at") != null ?
                              rs.getTimestamp("updated_at").toLocalDateTime() : null)
                    .build();
        }
    };

    @Override
    public OfflinePaymentModel save(OfflinePaymentModel offlinePayment) {
        String sql = """
            INSERT INTO offline_payment (
                amount, payment_mode, payment_date, remarks,
                cheque_number, bank_name, cheque_date,
                received_by, batch_id, transaction_id, status
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, offlinePayment.getAmount());
            ps.setString(2, offlinePayment.getPaymentMode());
            ps.setDate(3, offlinePayment.getPaymentDate() != null ?
                       Date.valueOf(offlinePayment.getPaymentDate()) : null);
            ps.setString(4, offlinePayment.getRemarks());
            ps.setString(5, offlinePayment.getChequeNumber());
            ps.setString(6, offlinePayment.getBankName());
            ps.setDate(7, offlinePayment.getChequeDate() != null ?
                       Date.valueOf(offlinePayment.getChequeDate()) : null);
            ps.setString(8, offlinePayment.getReceivedBy());
            ps.setObject(9, offlinePayment.getBatchId());
            ps.setObject(10, offlinePayment.getTransactionId());
            ps.setString(11, offlinePayment.getStatus());
            return ps;
        }, keyHolder);

        offlinePayment.setId(keyHolder.getKey().longValue());
        return offlinePayment;
    }

    @Override
    public List<OfflinePaymentModel> saveAll(List<OfflinePaymentModel> offlinePayments) {
        offlinePayments.forEach(this::save);
        return offlinePayments;
    }

    @Override
    public int[] batchInsert(List<OfflinePaymentModel> offlinePayments) {
        if (offlinePayments == null || offlinePayments.isEmpty()) {
            return new int[0];
        }

        String sql = """
            INSERT INTO offline_payment (
                amount, payment_mode, payment_date, remarks,
                cheque_number, bank_name, cheque_date,
                received_by, batch_id, transaction_id, status
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        // Process in chunks for large datasets
        int chunkSize = 1000;
        List<Integer> allResults = new ArrayList<>();

        for (int i = 0; i < offlinePayments.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, offlinePayments.size());
            List<OfflinePaymentModel> chunk = offlinePayments.subList(i, endIndex);

            int[] chunkResult = jdbcTemplate.batchUpdate(sql,
                new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        OfflinePaymentModel payment = chunk.get(i);
                        ps.setBigDecimal(1, payment.getAmount());
                        ps.setString(2, payment.getPaymentMode());
                        ps.setDate(3, payment.getPaymentDate() != null ?
                                   Date.valueOf(payment.getPaymentDate()) : null);
                        ps.setString(4, payment.getRemarks());
                        ps.setString(5, payment.getChequeNumber());
                        ps.setString(6, payment.getBankName());
                        ps.setDate(7, payment.getChequeDate() != null ?
                                   Date.valueOf(payment.getChequeDate()) : null);
                        ps.setString(8, payment.getReceivedBy());
                        ps.setObject(9, payment.getBatchId());
                        ps.setObject(10, payment.getTransactionId());
                        ps.setString(11, payment.getStatus());
                    }

                    @Override
                    public int getBatchSize() {
                        return chunk.size();
                    }
                });

            for (int result : chunkResult) {
                allResults.add(result);
            }
        }

        return allResults.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public OfflinePaymentModel update(OfflinePaymentModel offlinePayment) {
        String sql = """
            UPDATE offline_payment SET
                amount = ?, payment_mode = ?, payment_date = ?, remarks = ?,
                cheque_number = ?, bank_name = ?, cheque_date = ?,
                received_by = ?, batch_id = ?, transaction_id = ?, status = ?
            WHERE id = ?
        """;

        jdbcTemplate.update(sql,
                offlinePayment.getAmount(),
                offlinePayment.getPaymentMode(),
                offlinePayment.getPaymentDate() != null ?
                    Date.valueOf(offlinePayment.getPaymentDate()) : null,
                offlinePayment.getRemarks(),
                offlinePayment.getChequeNumber(),
                offlinePayment.getBankName(),
                offlinePayment.getChequeDate() != null ?
                    Date.valueOf(offlinePayment.getChequeDate()) : null,
                offlinePayment.getReceivedBy(),
                offlinePayment.getBatchId(),
                offlinePayment.getTransactionId(),
                offlinePayment.getStatus(),
                offlinePayment.getId()
        );

        return offlinePayment;
    }

    @Override
    public Optional<OfflinePaymentModel> findById(Long id) {
        String sql = "SELECT * FROM offline_payment WHERE id = ?";
        List<OfflinePaymentModel> results = jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<OfflinePaymentModel> findAll() {
        String sql = "SELECT * FROM offline_payment ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER);
    }

    @Override
    public List<OfflinePaymentModel> findByBatchId(Long batchId) {
        String sql = "SELECT * FROM offline_payment WHERE batch_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER, batchId);
    }

    @Override
    public List<OfflinePaymentModel> findByTransactionId(Long transactionId) {
        String sql = "SELECT * FROM offline_payment WHERE transaction_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER, transactionId);
    }

    @Override
    public List<OfflinePaymentModel> findByStatus(String status) {
        String sql = "SELECT * FROM offline_payment WHERE status = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER, status);
    }

    @Override
    public List<OfflinePaymentModel> findByPaymentMode(String paymentMode) {
        String sql = "SELECT * FROM offline_payment WHERE payment_mode = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER, paymentMode);
    }

    @Override
    public List<OfflinePaymentModel> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        String sql = """
            SELECT * FROM offline_payment 
            WHERE payment_date BETWEEN ? AND ? 
            ORDER BY payment_date DESC
        """;
        return jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER,
                                  Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @Override
    public Optional<OfflinePaymentModel> findByChequeNumber(String chequeNumber) {
        String sql = "SELECT * FROM offline_payment WHERE cheque_number = ?";
        List<OfflinePaymentModel> results = jdbcTemplate.query(sql, OFFLINE_PAYMENT_ROW_MAPPER, chequeNumber);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM offline_payment WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteByBatchId(Long batchId) {
        String sql = "DELETE FROM offline_payment WHERE batch_id = ?";
        jdbcTemplate.update(sql, batchId);
    }

    @Override
    public int countByBatchId(Long batchId) {
        String sql = "SELECT COUNT(*) FROM offline_payment WHERE batch_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, batchId);
    }

    @Override
    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM offline_payment WHERE status = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, status);
    }

    @Override
    public int updateStatus(Long id, String status) {
        String sql = "UPDATE offline_payment SET status = ?, updated_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, Timestamp.valueOf(java.time.LocalDateTime.now()), id);
    }

    @Override
    public int[] batchUpdateStatus(List<Long> ids, String status) {
        if (ids == null || ids.isEmpty()) {
            return new int[0];
        }

        String sql = "UPDATE offline_payment SET status = ?, updated_at = ? WHERE id = ?";

        // Process in chunks
        int chunkSize = 1000;
        List<Integer> allResults = new ArrayList<>();

        for (int i = 0; i < ids.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, ids.size());
            List<Long> chunk = ids.subList(i, endIndex);

            int[] chunkResult = jdbcTemplate.batchUpdate(sql,
                new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, status);
                        ps.setTimestamp(2, Timestamp.valueOf(java.time.LocalDateTime.now()));
                        ps.setLong(3, chunk.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return chunk.size();
                    }
                });

            for (int result : chunkResult) {
                allResults.add(result);
            }
        }

        return allResults.stream().mapToInt(Integer::intValue).toArray();
    }
}

