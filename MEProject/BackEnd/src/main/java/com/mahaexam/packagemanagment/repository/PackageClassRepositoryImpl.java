package com.mahaexam.packagemanagment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageClassModel;

@Repository
public class PackageClassRepositoryImpl implements PackageClassRepository {

    private final JdbcTemplate jdbcTemplate;

    public PackageClassRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PackageClassModel save(PackageClassModel mapping) {
        // Validate foreign keys
        validateForeignKeys(mapping.getPackageId(), mapping.getClassId());
        String sql = "INSERT INTO package_classes (package_id, class_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, mapping.getPackageId(), mapping.getClassId());
        return mapping;
    }

    @Override
    public Optional<PackageClassModel> findById(Integer id) {
        String sql = "SELECT * FROM package_classes WHERE id = ?";
        try {
            PackageClassModel mapping = jdbcTemplate.queryForObject(sql, new PackageClassMapper(), id);
            return Optional.ofNullable(mapping);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PackageClassModel> findAll(UserBean user) {
        String sql = "SELECT pc.* FROM package_classes pc " +
                "JOIN packages p ON pc.package_id = p.id " +
                "WHERE p.deleted = '0' AND (p.tenant_id IS NULL OR p.tenant_id = ?)";
        return jdbcTemplate.query(sql, new PackageClassMapper(), user.getTenantId());
    }

    @Override
    public void update(PackageClassModel mapping) {
        // Validate foreign keys
        validateForeignKeys(mapping.getPackageId(), mapping.getClassId());
        String sql = "UPDATE package_classes SET package_id = ?, class_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, mapping.getPackageId(), mapping.getClassId(), mapping.getId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM package_classes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public void deletebyGivenPackageId(Integer packageId) {
    	  String sql = "DELETE FROM package_classes WHERE package_id = ?";
          jdbcTemplate.update(sql, packageId);
	}

    private void validateForeignKeys(Integer packageId, Integer classId) {
        if (packageId == null || classId == null) {
            throw new IllegalArgumentException("Package and class IDs cannot be null");
        }
        String packageSql = "SELECT COUNT(*) FROM packages WHERE id = ? AND deleted = '0'";
        Integer packageCount = jdbcTemplate.queryForObject(packageSql, Integer.class, packageId);
        String classSql = "SELECT COUNT(*) FROM class WHERE id = ?";
        Integer classCount = jdbcTemplate.queryForObject(classSql, Integer.class, classId);
        if (packageCount == null || packageCount == 0 || classCount == null || classCount == 0) {
            throw new IllegalArgumentException("Invalid package or class ID");
        }
    }
}
