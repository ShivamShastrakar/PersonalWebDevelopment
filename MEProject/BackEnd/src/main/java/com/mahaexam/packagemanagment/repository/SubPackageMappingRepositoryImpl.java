package com.mahaexam.packagemanagment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.SubPackageMappingModel;

@Repository
public class SubPackageMappingRepositoryImpl implements SubPackageMappingRepository {

	private final JdbcTemplate jdbcTemplate;

	public SubPackageMappingRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public SubPackageMappingModel save(SubPackageMappingModel mapping) {
		// Validate foreign keys
		validatePackageIds(mapping.getParentPackageId(), mapping.getChildPackageId());
		String sql = "INSERT INTO sub_packages_mapping (parent_package_id, child_package_id) VALUES (?, ?)";
		jdbcTemplate.update(sql, mapping.getParentPackageId(), mapping.getChildPackageId());
		return mapping;
	}

	@Override
	public Optional<SubPackageMappingModel> findById(Integer id) {
		String sql = "SELECT * FROM sub_packages_mapping WHERE id = ?";
		try {
			SubPackageMappingModel mapping = jdbcTemplate.queryForObject(sql, new SubPackageMappingMapper(), id);
			return Optional.ofNullable(mapping);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<SubPackageMappingModel> findAll(UserBean user) {
		String sql = "SELECT spm.* FROM sub_packages_mapping spm " + "JOIN packages p ON spm.parent_package_id = p.id "
				+ "WHERE p.deleted = '0' AND (p.tenant_id IS NULL OR p.tenant_id = ?)";
		return jdbcTemplate.query(sql, new SubPackageMappingMapper(), user.getTenantId());
	}

	@Override
	public void update(SubPackageMappingModel mapping) {
		// Validate foreign keys
		validatePackageIds(mapping.getParentPackageId(), mapping.getChildPackageId());
		String sql = "UPDATE sub_packages_mapping SET parent_package_id = ?, child_package_id = ? WHERE id = ?";
		jdbcTemplate.update(sql, mapping.getParentPackageId(), mapping.getChildPackageId(), mapping.getId());
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM sub_packages_mapping WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	private void validatePackageIds(Integer parentPackageId, Integer childPackageId) {
		if (parentPackageId == null || childPackageId == null) {
			throw new IllegalArgumentException("Parent and child package IDs cannot be null");
		}
		String sql = "SELECT COUNT(*) FROM packages WHERE id = ? AND deleted = '0'";
		Integer parentCount = jdbcTemplate.queryForObject(sql, Integer.class, parentPackageId);
		Integer childCount = jdbcTemplate.queryForObject(sql, Integer.class, childPackageId);
		if (parentCount == null || parentCount == 0 || childCount == null || childCount == 0) {
			throw new IllegalArgumentException("Invalid parent or child package ID");
		}
	}
}