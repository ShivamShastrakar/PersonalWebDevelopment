package com.mahaexam.packagemanagment.repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageServiceModel;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PackageServiceRepositoryImpl implements PackageServiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public PackageServiceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PackageServiceModel save(PackageServiceModel mapping) {
        // Validate foreign keys
        validateForeignKeys(mapping.getPackageId(), mapping.getServiceId());
        String sql = "INSERT INTO package_services (package_id, service_id, created_date, created_by) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, mapping.getPackageId(), mapping.getServiceId(), mapping.getCreatedDate(), mapping.getCreatedBy());
        return mapping;
    }

    @Override
    public List<PackageServiceModel> saveBatch(List<PackageServiceModel> mappings) {
        if (mappings == null || mappings.isEmpty()) {
            return Collections.emptyList();
        }

        // Validate foreign keys for each mapping
        for (PackageServiceModel mapping : mappings) {
            validateForeignKeys(mapping.getPackageId(), mapping.getServiceId());
        }

        String sql = "INSERT INTO package_services (package_id, service_id, created_date, created_by) VALUES (?, ?, ?, ?)";

        // Prepare batch arguments
        List<Object[]> batchArgs = mappings.stream()
                .map(mapping -> new Object[]{
                        mapping.getPackageId(),
                        mapping.getServiceId(),
                        mapping.getCreatedDate(),
                        mapping.getCreatedBy()
                })
                .collect(Collectors.toList());

        // Perform batch update
        jdbcTemplate.batchUpdate(sql, batchArgs);

        return mappings;
    }

    @Override
    public Optional<PackageServiceModel> findById(Integer id) {
        String sql = "SELECT * FROM package_services WHERE id = ?";
        try {
            PackageServiceModel mapping = jdbcTemplate.queryForObject(sql, new PackageServiceMapper(), id);
            return Optional.ofNullable(mapping);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PackageServiceModel> findAll(UserBean user) {
        String sql = "SELECT ps.* FROM package_services ps " +
                "JOIN packages p ON ps.package_id = p.id " +
                "WHERE p.deleted = '0' AND p.tenant_id = ?";
        return jdbcTemplate.query(sql, new PackageServiceMapper(), user.getTenantId());
    }

    @Override
    public void update(PackageServiceModel mapping) {
        // Validate foreign keys
        validateForeignKeys(mapping.getPackageId(), mapping.getServiceId());
        String sql = "UPDATE package_services SET package_id = ?, service_id = ?, created_date = ?, created_by = ? WHERE id = ?";
        jdbcTemplate.update(sql, mapping.getPackageId(), mapping.getServiceId(), mapping.getCreatedDate(), mapping.getCreatedBy(), mapping.getId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM package_services WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public void deletebyGivenPackageId(Integer packageId) {
    	String sql = "DELETE FROM package_services WHERE package_id = ?";
        jdbcTemplate.update(sql, packageId);
    }

    private void validateForeignKeys(Integer packageId, Integer serviceId) {
        if (packageId == null || serviceId == null) {
            throw new IllegalArgumentException("Package and service IDs cannot be null");
        }
        String packageSql = "SELECT COUNT(*) FROM packages WHERE id = ? AND deleted = '0'";
        Integer packageCount = jdbcTemplate.queryForObject(packageSql, Integer.class, packageId);
        String serviceSql = "SELECT COUNT(*) FROM services WHERE id = ? AND deleted = '0'";
        Integer serviceCount = jdbcTemplate.queryForObject(serviceSql, Integer.class, serviceId);
        if (packageCount == null || packageCount == 0 || serviceCount == null || serviceCount == 0) {
            throw new IllegalArgumentException("Invalid package or service ID");
        }
    }
}