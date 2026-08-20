package com.mahaexam.packagemanagment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageCourseModel;

@Repository
public class PackageCourseRepositoryImpl implements PackageCourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public PackageCourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PackageCourseModel save(PackageCourseModel mapping) {
        // Validate foreign keys
        validateForeignKeys(mapping.getPackageId(), mapping.getCourseId());
        String sql = "INSERT INTO package_courses (package_id, course_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, mapping.getPackageId(), mapping.getCourseId());
        return mapping;
    }

    @Override
    public Optional<PackageCourseModel> findById(Integer id) {
        String sql = "SELECT * FROM package_courses WHERE id = ?";
        try {
            PackageCourseModel mapping = jdbcTemplate.queryForObject(sql, new PackageCourseMapper(), id);
            return Optional.ofNullable(mapping);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PackageCourseModel> findAll(UserBean user) {
        String sql = "SELECT pc.* FROM package_courses pc " +
                "JOIN packages p ON pc.package_id = p.id " +
                "WHERE p.deleted = '0' AND p.tenant_id = ? ";
        return jdbcTemplate.query(sql, new PackageCourseMapper(), user.getTenantId());
    }

    @Override
    public void update(PackageCourseModel mapping) {
        // Validate foreign keys
        validateForeignKeys(mapping.getPackageId(), mapping.getCourseId());
        String sql = "UPDATE package_courses SET package_id = ?, course_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, mapping.getPackageId(), mapping.getCourseId(), mapping.getId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM package_courses WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public void deletebyGivenPackageId(Integer packageId) {
    	  String sql = "DELETE FROM package_courses WHERE package_id = ?";
          jdbcTemplate.update(sql, packageId);
    }

    private void validateForeignKeys(Integer packageId, Integer courseId) {
        if (packageId == null || courseId == null) {
            throw new IllegalArgumentException("Package and course IDs cannot be null");
        }
        String packageSql = "SELECT COUNT(*) FROM packages WHERE id = ? AND deleted = '0'";
        Integer packageCount = jdbcTemplate.queryForObject(packageSql, Integer.class, packageId);
        String courseSql = "SELECT COUNT(*) FROM course WHERE id = ?";
        Integer courseCount = jdbcTemplate.queryForObject(courseSql, Integer.class, courseId);
        if (packageCount == null || packageCount == 0 || courseCount == null || courseCount == 0) {
            throw new IllegalArgumentException("Invalid package or course ID");
        }
    }
}