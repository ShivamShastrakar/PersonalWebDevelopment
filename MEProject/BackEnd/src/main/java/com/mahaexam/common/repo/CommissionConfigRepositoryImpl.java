package com.mahaexam.common.repo;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.model.CommissionConfigRequest;
import com.mahaexam.common.model.CommissionSlab;

@Repository
public class CommissionConfigRepositoryImpl implements CommissionConfigRepository{
	
	private final JdbcTemplate jdbcTemplate;

    public CommissionConfigRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public Long insertConfig(CommissionConfigRequest req) {
		// TODO Auto-generated method stub
		
		String sql = """
	            INSERT INTO commission_config
	                (hierarchy_level_id, package_type, commission_type, is_active,tenant_id,created_date,created_by,updated_at,updated_by,package_category_id,exam_groupId)
	            VALUES (?, ?, ?, ?,?,?,?,?,?,?,?)
	            """;

	        KeyHolder keyHolder = new GeneratedKeyHolder();

	        jdbcTemplate.update(con -> {
	            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            // hierarchy_level_id
	            if (req.getHierarchyLevelId() != null) {
	                ps.setLong(1, req.getHierarchyLevelId());
	            } else {
	                ps.setNull(1, Types.BIGINT);
	            }
	            ps.setString(2, req.getPackageType());
	            ps.setString(3, req.getCommissionType());
	            ps.setBoolean(4, Boolean.TRUE.equals(req.getActive()));
	            // tenant_id (nullable)
	            if (req.getTenantId() != null) {
	                ps.setLong(5, req.getTenantId());
	            } else {
	                ps.setNull(5, Types.BIGINT);
	            }
	            // created_date
	            Timestamp createdTs = req.getCreated_date() != null ? Timestamp.valueOf(req.getCreated_date()) : Timestamp.valueOf(LocalDateTime.now());
	            ps.setTimestamp(6, createdTs);
	            // created_by (nullable)
	            if (req.getCreated_by() != null) {
	                ps.setLong(7, req.getCreated_by());
	            } else {
	                ps.setNull(7, Types.BIGINT);
	            }
	            // Correct order: 8 = updated_at (Timestamp), 9 = updated_by (long/null-safe)
	            Timestamp updatedTs = req.getUpdated_at() != null ? Timestamp.valueOf(req.getUpdated_at()) : Timestamp.valueOf(LocalDateTime.now());
	            ps.setTimestamp(8, updatedTs);
	            if (req.getUpdated_by() != null) {
	                ps.setLong(9, req.getUpdated_by());
	            } else {
	                ps.setNull(9, Types.BIGINT);
	            }
	            // package_category_id (nullable)
	            if (req.getPackageCategoryId() != null) {
	                ps.setInt(10, req.getPackageCategoryId());
	            } else {
	                ps.setNull(10, Types.INTEGER);
	            }
	            // exam_groupId (nullable, default 0)
	            if (req.getExamGroupId() != null) {
	                ps.setInt(11, req.getExamGroupId());
	            } else {
	                ps.setInt(11, 0);
	            }
	            return ps;
	        }, keyHolder);                                    // use KeyHolder for auto id[web:83][web:87]

	        return keyHolder.getKey().longValue();
		
	}

	@Override
	public void updateConfig(CommissionConfigRequest req) {
		// TODO Auto-generated method stub
		String sql = """
	            UPDATE commission_config
	               SET hierarchy_level_id = ?, package_type = ?, commission_type = ?, is_active = ?,updated_at=?,updated_by=?,package_category_id=?,exam_groupId=?
	             WHERE id = ?""";
	        jdbcTemplate.update(sql,
	        	req.getHierarchyLevelId(),
            req.getPackageType(),
            req.getCommissionType(),
            Boolean.TRUE.equals(req.getActive()),
            Timestamp.valueOf(LocalDateTime.now()),
            req.getUpdated_by(),
            req.getPackageCategoryId(),
            req.getExamGroupId() != null ? req.getExamGroupId() : 0,
            req.getId());
		
	}
	
	@Override
	public void deleteSlabsByConfigId(Long configId) {
        jdbcTemplate.update("DELETE FROM commission_slab WHERE commission_config_id = ?", configId);
    }

	@Override
	public void deleteConfigById(Long id) {
        jdbcTemplate.update("DELETE FROM commission_config WHERE id = ?", id);
    }
	
	@Override
	public void insertSlabs(Long configId, List<CommissionSlab> slabs) {
        String sql = """
            INSERT INTO commission_slab
                (commission_config_id, from_student_count, to_student_count, percentage, amount)
            VALUES (?, ?, ?, ?, ?)
            """;
        jdbcTemplate.batchUpdate(sql, slabs, slabs.size(), (ps, slab) -> {
            ps.setLong(1, configId);
            ps.setInt(2, slab.getFromStudentCount());
            ps.setInt(3, slab.getToStudentCount());
            if (slab.getPercentage() != null) {
                ps.setBigDecimal(4, slab.getPercentage());
            } else {
                ps.setNull(4, Types.DECIMAL);
            }
            if (slab.getAmount() != null) {
                ps.setBigDecimal(5, slab.getAmount());
            } else {
                ps.setNull(5, Types.DECIMAL);
            }
        });                                                // batch insert slabs[web:96][web:98]
    }
	
	public List<CommissionConfigRequest> findConfigs(Integer hierarchyLevelId, String packageType, Boolean active) {
		StringBuilder sql = new StringBuilder("""
		SELECT c.id, c.hierarchy_level_id, c.package_type, c.commission_type, c.is_active, c.package_category_id, c.exam_groupId
		FROM commission_config c
		WHERE 1=1
		""");
		
		List<Object> params = new ArrayList<>();
		
		if (hierarchyLevelId != null) {
		sql.append(" AND c.hierarchy_level_id = ?");
		params.add(hierarchyLevelId);
		}
		if (packageType != null) {
		sql.append(" AND c.package_type = ?");
		params.add(packageType);
		}
		if (active != null) {
		sql.append(" AND c.is_active = ?");
		params.add(active);
		}
		
		List<CommissionConfigRequest> configs = jdbcTemplate.query(
		sql.toString(),
		params.toArray(),
		(rs, rowNum) -> {
		CommissionConfigRequest dto = new CommissionConfigRequest();
		dto.setId(rs.getLong("id"));
		dto.setHierarchyLevelId(rs.getLong("hierarchy_level_id"));
		dto.setPackageType(rs.getString("package_type"));
		dto.setCommissionType(rs.getString("commission_type"));
		dto.setActive(rs.getBoolean("is_active"));
		dto.setPackageCategoryId(rs.getInt("package_category_id"));
		dto.setExamGroupId(rs.getInt("exam_groupId"));
		return dto;
		});                                        // RowMapper-style mapping[web:85][web:94]
		
		if (configs.isEmpty()) {
		return configs;
		}
		
		// load slabs for all configs
		String inClause = configs.stream()
		.map(c -> "?")
		.collect(Collectors.joining(", "));
		String slabSql = """
		SELECT id, commission_config_id, from_student_count, to_student_count, percentage, amount
		FROM commission_slab
		WHERE commission_config_id IN (%s)
		""".formatted(inClause);
		
		Object[] ids = configs.stream().map(CommissionConfigRequest::getId).toArray();
		List<CommissionSlab> slabs = jdbcTemplate.query(
		slabSql,
		ids,
		(rs, rowNum) -> {
			CommissionSlab s = new CommissionSlab();
		s.setId(rs.getLong("id"));
		s.setFromStudentCount(rs.getInt("from_student_count"));
		s.setToStudentCount(rs.getInt("to_student_count"));
		BigDecimal pct = rs.getBigDecimal("percentage");
		BigDecimal amt = rs.getBigDecimal("amount");
		s.setPercentage(pct);
		s.setAmount(amt);
		return s;
		});
		
		Map<Long, List<CommissionSlab>> slabMap = slabs.stream()
		.collect(Collectors.groupingBy(s -> s.getId() == null
		? -1L
		: s.getId())); // will adjust below if needed
		
		// Better: group by commission_config_id using custom DTO or map
		Map<Long, List<CommissionSlab>> byConfig = new HashMap<>();
		jdbcTemplate.query(
		slabSql,
		ids,
		(ResultSet rs) -> {
		Long cfgId = rs.getLong("commission_config_id");
		CommissionSlab s = new CommissionSlab();
		s.setId(rs.getLong("id"));
		s.setFromStudentCount(rs.getInt("from_student_count"));
		s.setToStudentCount(rs.getInt("to_student_count"));
		s.setPercentage(rs.getBigDecimal("percentage"));
		s.setAmount(rs.getBigDecimal("amount"));
		byConfig.computeIfAbsent(cfgId, k -> new ArrayList<>()).add(s);
		});
		
		configs.forEach(c -> c.setSlabs(byConfig.getOrDefault(c.getId(), List.of())));
		return configs;
}
	
	public Optional<CommissionConfigRequest> findById(Long id) {
        String sql = """
            SELECT c.id, c.hierarchy_level_id, c.package_type, c.commission_type, c.is_active, c.package_category_id, c.exam_groupId
            FROM commission_config c
            WHERE c.id = ?
            """;

        List<CommissionConfigRequest> list = jdbcTemplate.query(sql, new Object[]{id},
                (rs, rowNum) -> {
                	CommissionConfigRequest dto = new CommissionConfigRequest();
                    dto.setId(rs.getLong("id"));
                    // corrected column name
                    dto.setHierarchyLevelId(rs.getLong("hierarchy_level_id"));
                    dto.setPackageType(rs.getString("package_type"));
                    dto.setCommissionType(rs.getString("commission_type"));
                    dto.setActive(rs.getBoolean("is_active"));
                    dto.setPackageCategoryId(rs.getInt("package_category_id"));
                    dto.setExamGroupId(rs.getInt("exam_groupId"));
                    return dto;
                });

        if (list.isEmpty()) {
            return Optional.empty();
        }
        CommissionConfigRequest config = list.get(0);

        List<CommissionSlab> slabs = jdbcTemplate.query("""
                SELECT id, from_student_count, to_student_count, percentage, amount
                FROM commission_slab
                WHERE commission_config_id = ?
                """,
                new Object[]{id},
                (rs, rowNum) -> {
                	CommissionSlab s = new CommissionSlab();
                    s.setId(rs.getLong("id"));
                    s.setFromStudentCount(rs.getInt("from_student_count"));
                    s.setToStudentCount(rs.getInt("to_student_count"));
                    s.setPercentage(rs.getBigDecimal("percentage"));
                    s.setAmount(rs.getBigDecimal("amount"));
                    return s;
                });

        config.setSlabs(slabs);
        return Optional.of(config);
    }

    @Override
    public Optional<CommissionConfigRequest> findByRoleId(Integer roleId) {
        // This method is deprecated. Use findByHierarchyLevelId instead
        // Keeping for backward compatibility but returns empty
        return Optional.empty();
    }

    public Optional<CommissionConfigRequest> findByHierarchyLevelId(Long hierarchyLevelId) {
        String sql = """
            SELECT c.id, c.hierarchy_level_id, c.package_type, c.commission_type, c.is_active, c.package_category_id, c.exam_groupId
            FROM commission_config c
            WHERE c.hierarchy_level_id = ? AND c.is_active = TRUE
            """;

        List<CommissionConfigRequest> list = jdbcTemplate.query(sql, new Object[]{hierarchyLevelId},
                (rs, rowNum) -> {
                	CommissionConfigRequest dto = new CommissionConfigRequest();
                    dto.setId(rs.getLong("id"));
                    dto.setHierarchyLevelId(rs.getLong("hierarchy_level_id"));
                    dto.setPackageType(rs.getString("package_type"));
                    dto.setCommissionType(rs.getString("commission_type"));
                    dto.setActive(rs.getBoolean("is_active"));
                    dto.setPackageCategoryId(rs.getInt("package_category_id"));
                    dto.setExamGroupId(rs.getInt("exam_groupId"));
                    return dto;
                });

        if (list.isEmpty()) {
            return Optional.empty();
        }
        CommissionConfigRequest config = list.get(0);

        List<CommissionSlab> slabs = jdbcTemplate.query("""
                SELECT id, from_student_count, to_student_count, percentage, amount
                FROM commission_slab
                WHERE commission_config_id = ?
                """,
                new Object[]{config.getId()},
                (rs, rowNum) -> {
                	CommissionSlab s = new CommissionSlab();
                    s.setId(rs.getLong("id"));
                    s.setFromStudentCount(rs.getInt("from_student_count"));
                    s.setToStudentCount(rs.getInt("to_student_count"));
                    s.setPercentage(rs.getBigDecimal("percentage"));
                    s.setAmount(rs.getBigDecimal("amount"));
                    return s;
                });

        config.setSlabs(slabs);
        return Optional.of(config);
    }
}
