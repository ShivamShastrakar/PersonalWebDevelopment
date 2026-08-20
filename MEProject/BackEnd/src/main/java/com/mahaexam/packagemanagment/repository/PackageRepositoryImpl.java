package com.mahaexam.packagemanagment.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

import com.mahaexam.common.constants.AppConstants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.model.PackageModel;
import com.mahaexam.packagemanagment.bean.PackageSearchRequest;
import com.mahaexam.common.bean.PaginatedResponse;

@Repository
public class PackageRepositoryImpl implements PackageRepository {

    private final JdbcTemplate jdbcTemplate;

    public PackageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 

    @Override
    public PackageModel save(PackageModel pkg) {
    	 String sql = "INSERT INTO packages (package_name, package_details, amount, package_for, package_type, " +
                 "package_target_students, package_mode, flag, package_type_name, pkg_exam_group, is_archived, " +
                 "archived_by, start_date, end_date, target_year, show_strike_price, strike_price, is_testing_package, " +
                 "created_at, updated_at, deleted_at, deleted, updated_by, package_img_url, tenant_id, subscriptiontype,no_of_mock_exams, no_of_pactice_exams, no_of_bonus_exams, package_category_id) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
         
         KeyHolder keyHolder = new GeneratedKeyHolder();
         jdbcTemplate.update(connection -> {
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ps.setString(1, pkg.getPackageName());
             ps.setString(2, pkg.getPackageDetails());
             ps.setBigDecimal(3, pkg.getAmount());
             ps.setString(4, pkg.getPackageFor());
             ps.setString(5, pkg.getPackageType());
             ps.setString(6, pkg.getPackageTargetStudents());
             ps.setString(7, pkg.getPackageMode());
             ps.setBoolean(8, pkg.getFlag());
             ps.setString(9, pkg.getPackageTypeName());
             ps.setObject(10, pkg.getPkgExamGroup(), java.sql.Types.INTEGER);
             ps.setInt(11, pkg.getIsArchived());
             ps.setObject(12, pkg.getArchivedBy(), java.sql.Types.INTEGER);
             ps.setObject(13, pkg.getStartDate(), java.sql.Types.TIMESTAMP);
             ps.setObject(14, pkg.getEndDate(), java.sql.Types.TIMESTAMP);
             ps.setString(15, pkg.getTargetYear());
             ps.setInt(16, pkg.getShowStrikePrice());
             ps.setBigDecimal(17, pkg.getStrikePrice());
             ps.setInt(18, pkg.getIsTestingPackage());
             ps.setObject(19, pkg.getCreatedAt(), java.sql.Types.TIMESTAMP);
             ps.setObject(20, pkg.getUpdatedAt(), java.sql.Types.TIMESTAMP);
             ps.setObject(21, pkg.getDeletedAt(), java.sql.Types.TIMESTAMP);
             ps.setString(22, pkg.getDeleted());
             ps.setObject(23, pkg.getUpdatedBy(), java.sql.Types.INTEGER);
             ps.setString(24, pkg.getPackageImgUrl());
             ps.setObject(25, pkg.getTenantId(), java.sql.Types.BIGINT);
             ps.setString(26, pkg.getSubscriptiontype());
             ps.setObject(27, pkg.getNo_of_mock_exams(), java.sql.Types.INTEGER);
             ps.setObject(28, pkg.getNo_of_pactice_exams(), java.sql.Types.INTEGER);
             ps.setObject(29, pkg.getNo_of_bonus_exams(), java.sql.Types.INTEGER);
             ps.setObject(30, pkg.getPackageCategoryId(), java.sql.Types.INTEGER);
             return ps;
         }, keyHolder);
         
     

         // Set the generated ID on the PackageModel
         Number generatedId = keyHolder.getKey();
         if (generatedId != null) {
             pkg.setId(generatedId.intValue());
         }
         return pkg;
    }

    @Override
    public Optional<PackageModel> findById(Integer id) {
//        String sql = "SELECT * FROM packages WHERE id = ? AND deleted = '0'";
    	String sql = """
                SELECT  p.*, pc.class_id as class_id, pco.course_id as course_id
                from packages p
                inner join package_classes pc on p.id = pc.package_id
                inner join package_courses pco on p.id = pco.package_id
                Where p.id = ?
                """;
        try {
            PackageModel pkg = jdbcTemplate.queryForObject(sql, new PackageMapper(), id);
            return Optional.ofNullable(pkg);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PackageModel> findAll(UserBean user, String type) {
        StringBuilder sql = new StringBuilder("SELECT * FROM packages WHERE deleted = '0' AND tenant_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(user.getTenantId());

        // Add type filter if provided
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND package_type = ?");
            params.add(type);
        }

        sql.append(" ORDER BY end_date DESC");

        return jdbcTemplate.query(sql.toString(), new PackageMapper(), params.toArray());
    }

    @Override
    public PaginatedResponse<PackageModel> search(UserBean user, PackageSearchRequest request) {
        // Build the base SQL for counting total records
        StringBuilder countSql = new StringBuilder(
            "SELECT COUNT(DISTINCT p.id) FROM packages p");
        List<Object> countParams = new ArrayList<>();
        
        // Build the search SQL
        StringBuilder searchSql = new StringBuilder(
            "SELECT DISTINCT p.* FROM packages p");
        List<Object> searchParams = new ArrayList<>();
        
        // Add JOINs and WHERE conditions
        boolean hasClassFilter = request.getClassId() != null;
        boolean hasServiceFilter = request.getServiceId() != null;
        
        if (hasClassFilter) {
            countSql.append(" JOIN package_classes pc ON p.id = pc.package_id");
            searchSql.append(" JOIN package_classes pc ON p.id = pc.package_id");
        }
        
        if (hasServiceFilter) {
            countSql.append(" JOIN package_services ps ON p.id = ps.package_id");
            searchSql.append(" JOIN package_services ps ON p.id = ps.package_id");
        }
        
        // Add WHERE clause
        countSql.append(" WHERE p.deleted = '0' AND p.tenant_id = ?");
        searchSql.append(" WHERE p.deleted = '0' AND p.tenant_id = ?");
        countParams.add(user.getTenantId());
        searchParams.add(user.getTenantId());
        
        // Add filters
        if (hasClassFilter) {
            countSql.append(" AND pc.class_id = ?");
            searchSql.append(" AND pc.class_id = ?");
            countParams.add(request.getClassId());
            searchParams.add(request.getClassId());
        }
        
        if (hasServiceFilter) {
            countSql.append(" AND ps.service_id = ?");
            searchSql.append(" AND ps.service_id = ?");
            countParams.add(request.getServiceId());
            searchParams.add(request.getServiceId());
        }
        
        // Execute count query
        Long totalElements = jdbcTemplate.queryForObject(countSql.toString(), Long.class, countParams.toArray());
        
        // Add pagination to search query
        searchSql.append(" ORDER BY p.end_date DESC LIMIT ? OFFSET ?");
        int offset = request.getPage() * request.getSize();
        searchParams.add(request.getSize());
        searchParams.add(offset);
        
        // Execute search query
        List<PackageModel> content = jdbcTemplate.query(searchSql.toString(), new PackageMapper(), searchParams.toArray());
        
        // Calculate pagination info
        int totalPages = (int) Math.ceil((double) totalElements / request.getSize());
        
        return PaginatedResponse.<PackageModel>builder()
                .content(content)
                .page(request.getPage())
                .size(request.getSize())
                .totalElements(totalElements != null ? totalElements : 0L)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public boolean existsByPackageName(String packageName) {
        String sql = "SELECT COUNT(*) FROM packages WHERE package_name = ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, packageName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByPackageNameExcludingId(String packageName, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM packages WHERE package_name = ? AND id != ? AND deleted = '0'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, packageName, excludeId);
        return count != null && count > 0;
    }

    @Override
    public List<PackageModel> findAllByUserId(Long userId, UserBean user) {
        String sql = """
                select
                    distinct p.*
                from
                    packages p
                inner join student_package_mapping spm on
                    p.id = spm.package_id
                inner join student s on
                    s.student_id = spm.student_id
                where
                    s.user_id = ?
                order by
                    p.end_date desc
                """;
        return jdbcTemplate.query(sql, new PackageMapper(), userId);
    }

    @Override
    public List<PackageModel> findAllByStudentIds(UserBean user, List<Long> studentIds) {

        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyList();
        }

        final int CHUNK_SIZE = AppConstants.CHUNK_SIZE;
        String baseSql = """
            SELECT DISTINCT p.*,s.student_id
            FROM packages p
            INNER JOIN student_package_mapping spm ON p.id = spm.package_id
            INNER JOIN student s ON s.student_id = spm.student_id
            WHERE p.deleted = '0'
              AND s.student_id IN (%s)
            ORDER BY p.end_date DESC
            """;

        // Collect results in insertion order while keeping them unique
        List<PackageModel> studentPackageResult = new ArrayList<>();
        for (int start = 0; start < studentIds.size(); start += CHUNK_SIZE) {
            List<Long> chunk = studentIds.subList(start, Math.min(start + CHUNK_SIZE, studentIds.size()));

            String placeholders = String.join(",", Collections.nCopies(chunk.size(), "?"));
            String sql = String.format(baseSql, placeholders);

            List<PackageModel> partial = jdbcTemplate.query(sql, new PackageMapper(), chunk.toArray());
            studentPackageResult.addAll(partial);
        }
        return studentPackageResult;
    }

     
    @Override
    public void update(PackageModel pkg) {
        String sql = "UPDATE packages SET package_name = ?, package_details = ?, amount = ?, package_for = ?, " +
                "package_type = ?, package_target_students = ?, package_mode = ?, flag = ?, package_type_name = ?, " +
                "pkg_exam_group = ?, is_archived = ?, archived_by = ?, start_date = ?, end_date = ?, " +
                "target_year = ?, show_strike_price = ?, strike_price = ?, is_testing_package = ?, updated_at = ?, " +
                "updated_by = ?, package_img_url = ?, tenant_id = ?, subscriptiontype=?, no_of_mock_exams=?,no_of_pactice_exams=?, no_of_bonus_exams=?, package_category_id=? WHERE id = ? AND deleted = '0'";
        jdbcTemplate.update(sql,
                pkg.getPackageName(),
                pkg.getPackageDetails(),
                pkg.getAmount(),
                pkg.getPackageFor(),
                pkg.getPackageType(),
                pkg.getPackageTargetStudents(),
                pkg.getPackageMode(),
                pkg.getFlag(),
                pkg.getPackageTypeName(),
                pkg.getPkgExamGroup(),
                pkg.getIsArchived(),
                pkg.getArchivedBy(),
                pkg.getStartDate(),
                pkg.getEndDate(),
                pkg.getTargetYear(),
                pkg.getShowStrikePrice(),
                pkg.getStrikePrice(),
                pkg.getIsTestingPackage(),
                pkg.getUpdatedAt(),
                pkg.getUpdatedBy(),
                pkg.getPackageImgUrl(),
                pkg.getTenantId(),
                pkg.getSubscriptiontype(),
                pkg.getNo_of_mock_exams(),
                pkg.getNo_of_pactice_exams(),
                pkg.getNo_of_bonus_exams(),
                pkg.getPackageCategoryId(),
                pkg.getId()
        		);
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE packages SET deleted = '1', deleted_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, java.time.LocalDateTime.now(), id);
    }

    @Override
    public List<PackageModel> findAll(UserBean user, String type, String targetYear) {
        StringBuilder sql = new StringBuilder("SELECT * FROM packages WHERE deleted = '0' AND tenant_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(user.getTenantId());

        // Add type filter if provided
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND package_type = ?");
            params.add(type);
        }

        // Add targetYear filter if provided
        if (targetYear != null && !targetYear.trim().isEmpty()) {
            sql.append(" AND target_year = ?");
            params.add(targetYear);
        }

        sql.append(" ORDER BY end_date DESC");

        return jdbcTemplate.query(sql.toString(), new PackageMapper(), params.toArray());
    }

    @Override
    public List<PackageModel> findSuggestedPackages(Integer classId, Integer subjectGroupId, Integer targetYear) {
        String sql = """
                SELECT DISTINCT p.*, pc.class_id
                      FROM packages p
                      INNER JOIN package_classes pc ON p.id = pc.package_id
                      WHERE p.deleted = '0'
                        AND p.is_archived = 0
                        AND p.amount > 0
                        AND CURRENT_DATE BETWEEN p.start_date AND p.end_date
                """;

        List<Object> params = new ArrayList<>();


        if (classId != null) {
            sql = sql + " AND pc.class_id = ? ";
            params.add(classId);
        }
        // Add target year filter if provided
        if (targetYear != null) {
            sql = sql + " AND p.target_year = ? ";
            params.add(targetYear.toString());
        }

        sql = sql + " ORDER BY p.package_name";

        return jdbcTemplate.query(sql.toString(), new PackageMapper(), params.toArray());
    }

}
