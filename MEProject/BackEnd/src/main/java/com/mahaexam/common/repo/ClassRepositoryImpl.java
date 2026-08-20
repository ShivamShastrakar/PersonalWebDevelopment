package com.mahaexam.common.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mahaexam.common.bean.ClassesDeleteBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.packagemanagment.repository.ServiceMapper;

@Repository
public class ClassRepositoryImpl implements ClassRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClassRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ClassEntity> findAllByTenant(Long tenantId) {
        String sql = "SELECT * FROM class WHERE (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
        return jdbcTemplate.query(sql, new ClassRowMapper(), tenantId);
    }

    @Override
    public ClassEntity findById(int id) {
        String sql = "SELECT * FROM class WHERE id = ? AND deleted = '0'";
        try {
            return jdbcTemplate.queryForObject(sql, new ClassRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Or throw a custom exception like EntityNotFoundException
        }
    }

    @Override
    public int save(ClassEntity clazz) {
        String sql = "INSERT INTO class (tenant_id, class_name, is_exam_group_required) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, clazz.getTenantId(), clazz.getClassName(), clazz.getIsExamGroupRequired());
    }

    @Override
    public int update(ClassEntity clazz) {
        String sql = "UPDATE class SET class_name = ?, is_exam_group_required = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql, clazz.getClassName(), clazz.getIsExamGroupRequired(), clazz.getId());
    }

    @Override
    public int[] softDelete(ClassesDeleteBean deleteBean) {
        String sql = String.format("UPDATE class SET deleted = 1, deleted_at = CURRENT_TIMESTAMP WHERE id = ?",
                deleteBean.getIdsToDelete().stream().map(id -> "?").collect(Collectors.joining(",")));
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, deleteBean.getIdsToDelete().get(i));
            }
            @Override
            public int getBatchSize() {
                return deleteBean.getIdsToDelete().size();
            }
        });
    }

    @Override
    public boolean existsByClassNameAndTenantId(String className, Long tenantId) {
        String sql = "SELECT COUNT(*) FROM class WHERE class_name = ? AND (tenant_id = ? OR tenant_id is null) AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, className, tenantId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByClassNameAndTenantIdExceptId(String className, Long tenantId, int excludeId) {
        String sql = "SELECT COUNT(1) FROM class WHERE class_name = ? AND (tenant_id = ? OR tenant_id is null) AND id != ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, className, tenantId, excludeId);
        return count != null && count > 0;
    }
    
    @Override
    public List<ClassEntity> findAllByPackageIds(List<Integer> packageIds) {
        
        if (packageIds == null || packageIds.isEmpty()) {
            return Collections.emptyList(); // Return empty list for invalid input
        }
        // Create placeholders for each package ID
        String placeholders = String.join(",", Collections.nCopies(packageIds.size(), "?"));
        String sql = "SELECT c.*,pc.package_id FROM class c "
        		+ "inner join package_classes pc on pc.class_id = c.id "
        		+ "WHERE deleted = '0' and  pc.package_id in ("+placeholders +") order by c.class_name";

        // Convert List<Integer> to array for jdbcTemplate
        return jdbcTemplate.query(sql, new ClassRowMapper(), packageIds.toArray());
    }

    @Override
    public Optional<ClassEntity> findClassByName(String className) {
        String sql = "SELECT * FROM class WHERE class_name = ? AND deleted = '0'";
        try {
            ClassEntity result = jdbcTemplate.queryForObject(sql, new ClassRowMapper(), className);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ClassEntity> findByBoardAndTenant(Integer boardId, Long tenantId) {
        String sql = "SELECT DISTINCT c.* FROM class c " +
                     "INNER JOIN board_class_mapping bcm ON c.id = bcm.class_id " +
                     "WHERE bcm.board_id = ? AND (c.tenant_id = ? OR c.tenant_id IS NULL) AND c.deleted = '0' " +
                     "ORDER BY c.class_name";
        return jdbcTemplate.query(sql, new ClassRowMapper(), boardId, tenantId);
    }

}
