package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.StudentPackageMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentPackageMappingRepositoryImpl implements StudentPackageMappingRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentPackageMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StudentPackageMapping save(StudentPackageMapping mapping) {
        String sql = "INSERT INTO student_package_mapping (package_id, student_id, subscription_type, next_invoice_date, status, created_date, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, mapping.getPackageId());
            ps.setLong(2, mapping.getStudentId());
            ps.setString(3, mapping.getSubscriptionType());
            if (mapping.getNextInvoiceDate() != null) {
                ps.setDate(4, new java.sql.Date(mapping.getNextInvoiceDate().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setString(5, mapping.getStatus() != null ? mapping.getStatus() : "Active");
            ps.setTimestamp(6, mapping.getCreatedDate() != null ? Timestamp.valueOf(mapping.getCreatedDate()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(7, mapping.getCreatedBy());
            return ps;
        }, keyHolder);
        mapping.setId(keyHolder.getKey().intValue());
        return mapping;
    }

    @Override
    public StudentPackageMapping findById(Integer id) {
        String sql = "SELECT * FROM student_package_mapping WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToMapping, id);
    }

    @Override
    public List<StudentPackageMapping> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM student_package_mapping WHERE student_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToMapping, studentId);
    }

    @Override
    public void updateStatus(Integer id, String status) {
        String sql = "UPDATE student_package_mapping SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }

    @Override
    @Transactional
    public List<StudentPackageMapping> saveMultiple(List<StudentPackageMapping> mappings) {
        if (mappings == null || mappings.isEmpty()) {
            return new ArrayList<>();
        }

        String sql = "INSERT INTO student_package_mapping " +
                "(package_id, student_id, subscription_type, next_invoice_date, status, created_date, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        List<StudentPackageMapping> validMappings = new ArrayList<>();

        for (StudentPackageMapping mapping : mappings) {
            if (mapping == null) continue;

            // Required fields
            if (mapping.getPackageId() == null || mapping.getStudentId() == null) {
                throw new IllegalArgumentException("packageId and studentId cannot be null in StudentPackageMapping");
            }

            batchArgs.add(new Object[]{
                    mapping.getPackageId(),
                    mapping.getStudentId(),
                    mapping.getSubscriptionType(), // can be null
                    mapping.getNextInvoiceDate() != null
                            ? new java.sql.Date(mapping.getNextInvoiceDate().getTime())
                            : null,
                    mapping.getStatus() != null ? mapping.getStatus() : "Active",
                    mapping.getCreatedDate() != null
                            ? Timestamp.valueOf(mapping.getCreatedDate())
                            : Timestamp.valueOf(LocalDateTime.now()),
                    mapping.getCreatedBy() // can be null
            });

            validMappings.add(mapping);
        }

        if (batchArgs.isEmpty()) {
            return new ArrayList<>();
        }

        int[] updateCounts = jdbcTemplate.batchUpdate(sql, batchArgs);

        if (updateCounts.length != batchArgs.size()) {
            throw new IllegalStateException(
                    "Batch insert failed: expected " + batchArgs.size() + " updates, but got " + updateCounts.length
            );
        }

        return validMappings;
    }


    private StudentPackageMapping mapRowToMapping(ResultSet rs, int rowNum) throws SQLException {
        StudentPackageMapping mapping = StudentPackageMapping.builder().build();
        mapping.setId(rs.getInt("id"));
        mapping.setPackageId(rs.getInt("package_id"));
        mapping.setStudentId(rs.getLong("student_id"));
        mapping.setSubscriptionType(rs.getString("subscription_type"));
        mapping.setNextInvoiceDate(rs.getDate("next_invoice_date"));
        mapping.setStatus(rs.getString("status"));
        mapping.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        mapping.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        mapping.setCreatedBy(rs.getLong("created_by"));
        return mapping;
    }
}