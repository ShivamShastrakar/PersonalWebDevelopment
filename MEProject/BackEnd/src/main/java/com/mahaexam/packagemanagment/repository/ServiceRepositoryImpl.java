package com.mahaexam.packagemanagment.repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.ServiceModel;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public ServiceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ServiceModel save(ServiceModel service) {
        String sql = "INSERT INTO services (service_name, created_at, updated_at, deleted_at, deleted, updated_by, " +
                "service_details, service_type, options, tenant_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                service.getServiceName(),
                service.getCreatedAt(),
                service.getUpdatedAt(),
                service.getDeletedAt(),
                service.getDeleted(),
                service.getUpdatedBy(),
                service.getServiceDetails(),
                service.getServiceType(),
                service.getOptions(),
                service.getTenantId());
        return service;
    }

    @Override
    public Optional<ServiceModel> findById(Integer id) {
        String sql = "SELECT * FROM services WHERE id = ? AND deleted = '0'";
        try {
            ServiceModel service = jdbcTemplate.queryForObject(sql, new ServiceMapper(), id);
            return Optional.ofNullable(service);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ServiceModel> findAll(UserBean user) {
        String sql = "SELECT * FROM services WHERE deleted = '0' AND (tenant_id IS NULL OR tenant_id = ?)";
        return jdbcTemplate.query(sql, new ServiceMapper(), user.getTenantId());
    }
    
    @Override
    public List<ServiceModel> findAllByPackageIds(List<Integer> packageIds) {
        if (packageIds == null || packageIds.isEmpty()) {
            return Collections.emptyList(); // Return empty list for invalid input
        }
        // Create placeholders for each package ID
        String placeholders = String.join(",", Collections.nCopies(packageIds.size(), "?"));
        String sql = "SELECT s.*,ps.package_id FROM services s " +
                     "INNER JOIN package_services ps ON s.id = ps.service_id " +
                     "WHERE s.deleted = '0' AND ps.package_id IN (" + placeholders + ")";

        // Convert List<Integer> to array for jdbcTemplate
        return jdbcTemplate.query(sql, new ServiceMapper(), packageIds.toArray());
    }

    @Override
    public boolean existsByServiceName(String serviceName) {
        String sql = "SELECT COUNT(*) FROM services WHERE LOWER(service_name) = LOWER(?) AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, serviceName.trim());
        return count != null && count > 0;
    }

    @Override
    public boolean existsByServiceNameExcludingId(String serviceName, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM services WHERE LOWER(service_name) = LOWER(?) AND id <> ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, serviceName.trim(), excludeId);
        return count != null && count > 0;
    }

    @Override
    public void update(ServiceModel service) {
        String sql = "UPDATE services SET service_name = ?, updated_at = ?, updated_by = ?, service_details = ?, " +
                "service_type = ?, options = ?, tenant_id = ? WHERE id = ? AND deleted = '0'";
        jdbcTemplate.update(sql,
                service.getServiceName(),
                service.getUpdatedAt(),
                service.getUpdatedBy(),
                service.getServiceDetails(),
                service.getServiceType(),
                service.getOptions(),
                service.getTenantId(),
                service.getId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE services SET deleted = '1', deleted_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, LocalDateTime.now(), id);
    }
}