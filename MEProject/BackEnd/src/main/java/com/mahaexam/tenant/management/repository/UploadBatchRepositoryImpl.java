package com.mahaexam.tenant.management.repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.tenant.management.model.UploadBatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UploadBatchRepositoryImpl implements UploadBatchRepository {
    private static final Logger logger = LogManager.getLogger(UploadBatchRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UploadBatchRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<UploadBatch> rowMapper = (rs, rowNum) -> {
        UploadBatch batch = new UploadBatch();
        batch.setBatchId(rs.getLong("batch_id"));
        Timestamp timestamp = rs.getTimestamp("upload_time");
        LocalDateTime uploadTime = timestamp != null ? timestamp.toLocalDateTime() : null;
        logger.info("Mapping upload_time: " + uploadTime);
        batch.setUploadTime(uploadTime);
        batch.setEntityType(rs.getString("entity_type"));
        batch.setStatus(rs.getString("status"));
        batch.setOriginalFilePath(rs.getString("original_file_path"));
        batch.setErrorFilePath(rs.getString("error_file_path"));
        batch.setCreatedBy(rs.getLong("created_by"));

        // Handle nullable payment_transaction_id
        Long paymentTransactionId = rs.getLong("payment_transaction_id");
        batch.setPaymentTransactionId(rs.wasNull() ? null : paymentTransactionId);

        batch.setTotalCount(rs.getInt("total_count"));
        batch.setSuccessCount(rs.getInt("success_count"));

        // Convert integer flag to boolean for isOffline
        Integer offlineInt = RepoUtil.getOptionalInteger(rs, "has_offline_payment");
        batch.setIsOffline(offlineInt != null && offlineInt != 0);

        return batch;
    };

    @Override
    public UploadBatch save(UploadBatch batch) {
        if (batch.getBatchId() == null) {
            String sql = "INSERT INTO upload_batch (entity_type, status, original_file_path, error_file_path, created_by, tenant_id, payment_transaction_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, batch.getEntityType());
                ps.setString(2, batch.getStatus());
                ps.setString(3, batch.getOriginalFilePath());
                ps.setString(4, batch.getErrorFilePath());
                ps.setLong(5, batch.getCreatedBy());
                ps.setLong(6, batch.getTenantId());
                if (batch.getPaymentTransactionId() != null) {
                    ps.setLong(7, batch.getPaymentTransactionId());
                } else {
                    ps.setNull(7, Types.BIGINT);
                }
                return ps;
            }, keyHolder);
            var key = keyHolder.getKey();
            if (key != null) {
                batch.setBatchId(key.longValue());
            }
        } else {
            String sql = "UPDATE upload_batch SET entity_type=?, status=?, original_file_path=?, error_file_path=?, total_count=?, success_count=?, payment_transaction_id=? WHERE batch_id=?";
            jdbcTemplate.update(sql,
                    batch.getEntityType(),
                    batch.getStatus(),
                    batch.getOriginalFilePath(),
                    batch.getErrorFilePath(),
                    batch.getTotalCount(),
                    batch.getSuccessCount(),
                    batch.getPaymentTransactionId(),
                    batch.getBatchId());
        }
        return batch;
    }

    @Override
    public Optional<UploadBatch> findById(Long id) {
        String sql = """
                SELECT ub.*,IF(op.id IS NULL, FALSE, TRUE) AS has_offline_payment FROM upload_batch ub\s
                left join offline_payment op on ub.batch_id=op.batch_id  WHERE ub.batch_id = ?
                """;
        List<UploadBatch> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.stream().findFirst();
    }

    @Override
    public List<UploadBatch> findAllOrderedByUploadTimeDesc(UserBean userBean) {
        String sql = """
                SELECT ub.*,IF(op.id IS NULL, FALSE, TRUE) AS has_offline_payment FROM upload_batch ub
                left join offline_payment op on ub.batch_id=op.batch_id
                """;
        Object[] params = new Object[]{};

        if (userBean != null && userBean.getTenantId() != null) {
            sql += " WHERE ub.tenant_id = ? ";
            params = new Object[]{userBean.getTenantId()};
        }
        if (userBean != null && Objects.nonNull(userBean.getApplicationUser())
                && !(userBean.getApplicationUser().isAdmin())) {
            if (sql.contains("WHERE")) {
                sql += " AND ub.created_by = ? ";
            } else {
                sql += " WHERE ub.created_by = ? ";
            }
            params = new Object[]{userBean.getTenantId(), userBean.getApplicationUser().getUserId()};
        }
        sql += " ORDER BY ub.upload_time DESC limit 25";
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    @Override
    public Long findBtachIdByInvoiceNumber(String invoiceNumber) {
        String sql = """
                select ub.batch_id from  upload_batch ub
                inner join payment_transactions pt on pt.transaction_id = ub.payment_transaction_id
                where pt.payu_transaction_id  = ? and ub.entity_type = 'Student' LIMIT 1
                """;
        return jdbcTemplate.queryForObject(sql, Long.class, invoiceNumber);
    }
}
