package com.mahaexam.tenant.management.repository;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.tenant.management.model.ApplicationUser;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ApplicationUserRepositoryImpl implements ApplicationUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public ApplicationUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ApplicationUser save(ApplicationUser user) {
        String sql = "INSERT INTO application_user (user_id, user_type, first_name, last_name, middle_name, "
                + "gender, date_of_birth, aadhar_number, registered_mobile_number, whatsapp_number, email, "
                + "created_at, updated_at, address_id, user_parent_id, additional_commission_percent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, user.getUserId());
            ps.setString(2, user.getUserType());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getMiddleName());
            ps.setString(6, user.getGender());
            ps.setDate(7, user.getDateOfBirth() != null ? Date.valueOf(user.getDateOfBirth()) : null);
            ps.setString(8, user.getAadharNumber());
            ps.setString(9, user.getRegisteredMobileNumber());
            ps.setString(10, user.getWhatsappNumber());
            ps.setString(11, user.getEmail());
            ps.setTimestamp(12, Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(13, Timestamp.valueOf(user.getUpdatedAt()));
            if (null == user.getAddressId()) {
                ps.setNull(14, java.sql.Types.BIGINT);
            } else {
                ps.setLong(14, user.getAddressId());
            }
            if (null == user.getUserParentId()) {
                ps.setNull(15, java.sql.Types.BIGINT);
            } else {
                ps.setLong(15, user.getUserParentId());
            }
            if (null == user.getAdditionalCommissionPercent()) {
                ps.setBigDecimal(16, java.math.BigDecimal.ZERO);
            } else {
                ps.setBigDecimal(16, user.getAdditionalCommissionPercent());
            }
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            user.setId(key.longValue());
        }
        return user;
    }

    @Override
    public Optional<ApplicationUser> findById(Long id) {
        String sql = "SELECT * FROM application_user WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ApplicationUserRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ApplicationUser> findAll() {
        String sql = "SELECT * FROM application_user where deleted='0'";
        return jdbcTemplate.query(sql, new ApplicationUserRowMapper());
    }

    @Override
    public ApplicationUser update(ApplicationUser user) {
        String sql = "UPDATE application_user SET user_id = ?, user_type = ?, first_name = ?, last_name = ?, "
                + "middle_name = ?, gender = ?, date_of_birth = ?, aadhar_number = ?, "
                + "registered_mobile_number = ?, whatsapp_number = ?, email = ?, updated_at = ?, address_id = ?, photo_url = ?, user_parent_id = ?, additional_commission_percent = ? WHERE id = ?";

        jdbcTemplate.update(sql, user.getUserId(), user.getUserType(), user.getFirstName(), user.getLastName(),
                user.getMiddleName(), user.getGender(), user.getDateOfBirth(), user.getAadharNumber(),
                user.getRegisteredMobileNumber(), user.getWhatsappNumber(), user.getEmail(), user.getUpdatedAt(),
                user.getAddressId(), user.getPhotoUrl(), user.getUserParentId(), user.getAdditionalCommissionPercent(), user.getId());
        return user;
    }

    @Override
    public void deleteByUserId(Long userId) {
        String sql = "UPDATE application_user SET deleted = '1' , updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public Optional<ApplicationUser> findByUserId(Long userId) {
        String sql = """
                SELECT au.*,u.username FROM application_user au
                inner join users u on au.user_id=u.user_id  WHERE au.user_id = ?
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ApplicationUserRowMapper(), userId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ApplicationUser> findByEmailId(String emailId) {
        String sql = "SELECT * FROM application_user WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ApplicationUserRowMapper(), emailId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ApplicationUser> findByMobileNo(String mobileNo) {
        String sql = "SELECT * FROM application_user WHERE registered_mobile_number = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ApplicationUserRowMapper(), mobileNo));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ApplicationUser> findByEmailIdAndMobileNo(String emailId, String mobileNo) {
        String sql = "SELECT * FROM application_user WHERE email = ? and registered_mobile_number =?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ApplicationUserRowMapper(), emailId, mobileNo));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public ApplicationUser updateByUserId(ApplicationUser user) {
        if(Objects.isNull(user.getPhotoUrl())) {
            String sql = "UPDATE application_user SET first_name = ?, last_name = ?, "
                    + "middle_name = ?, gender = ?, date_of_birth = ?, aadhar_number = ?, "
                    + "registered_mobile_number = ?, whatsapp_number = ?, email = ?, updated_at = ?, address_id = ?, user_parent_id = ?, additional_commission_percent = ? WHERE user_id = ?";

            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(),
                    user.getMiddleName(), user.getGender(), user.getDateOfBirth(), user.getAadharNumber(),
                    user.getRegisteredMobileNumber(), user.getWhatsappNumber(), user.getEmail(), user.getUpdatedAt(),
                    user.getAddressId(), user.getUserParentId(), user.getAdditionalCommissionPercent(), user.getUserId());
        }else {
            String sql = "UPDATE application_user SET first_name = ?, last_name = ?, "
                    + "middle_name = ?, gender = ?, date_of_birth = ?, aadhar_number = ?, "
                    + "registered_mobile_number = ?, whatsapp_number = ?, email = ?, updated_at = ?, address_id = ?, photo_url = ?, user_parent_id = ?, additional_commission_percent = ? WHERE user_id = ?";

            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(),
                    user.getMiddleName(), user.getGender(), user.getDateOfBirth(), user.getAadharNumber(),
                    user.getRegisteredMobileNumber(), user.getWhatsappNumber(), user.getEmail(), user.getUpdatedAt(),
                    user.getAddressId(), user.getPhotoUrl(), user.getUserParentId(), user.getAdditionalCommissionPercent(), user.getUserId());
        }
        return user;
    }

    @Override
    public PaginatedResponse<ApplicationUser> findByUserType(Long tenantId, String userType, Boolean isDeleted, Pageable pageable) {
        // Correct offset calculation
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        String selectSQl = "SELECT au.*,u.username , (" +
                "    SELECT GROUP_CONCAT(r.name)" +
                "    FROM role r\n" +
                "    INNER JOIN user_role ur ON r.role_id = ur.role_id" +
                "    WHERE ur.user_id = au.user_id" +
                "  ) AS roleNames";
        String sql = " FROM application_user au " +
                " inner join users u on au.user_id = u.user_id " +
                " WHERE user_type = ? and u.tenant_id=? ";
        if (Objects.nonNull(isDeleted) && isDeleted.equals(true)) {
            sql = sql + " AND deleted = '1' ";
        }else{
            sql = sql + " AND deleted = '0' ";
        }

        String orderBy = " ORDER BY created_at DESC LIMIT ? OFFSET ?";
        String listSQL = selectSQl + sql + orderBy;
        List<ApplicationUser> users = jdbcTemplate.query(listSQL, new ApplicationUserRowMapper(), userType,tenantId, limit, offset);

        // Query for total count (separate for efficiency)
        String countSql = "SELECT COUNT(*) " + sql;
        long total = jdbcTemplate.queryForObject(countSql, Long.class, userType, tenantId);
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
        return PaginatedResponse.<ApplicationUser>builder().content(users)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(total)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public Integer countByUserType(Long tenantId, String userType) {
        String sql = "SELECT COUNT(*) FROM application_user au " +
                " inner join users u on au.user_id = u.user_id " +
                " WHERE user_type = ? and u.tenant_id = ? and au.deleted='0' ";
        return jdbcTemplate.queryForObject(sql, Integer.class, userType, tenantId);
    }

    @Override
    public List<ApplicationUser> findByFirstOrLastName(String searchTerm, Long tenantId, List<String> profileTypes) {
        StringBuilder sqlBuilder = new StringBuilder("""
                SELECT au.*, u.username FROM application_user au 
                INNER JOIN users u ON au.user_id = u.user_id 
                WHERE (LOWER(au.first_name) LIKE LOWER(?) OR LOWER(au.last_name) LIKE LOWER(?))
                AND au.deleted = '0' AND u.tenant_id = ?
                """);

        List<Object> params = new ArrayList<>();
        String searchPattern = "%" + searchTerm + "%";
        params.add(searchPattern);
        params.add(searchPattern);
        params.add(tenantId);

        // Add profile type filter if provided
        if (profileTypes != null && !profileTypes.isEmpty()) {
            sqlBuilder.append(" AND au.user_type IN (");
            for (int i = 0; i < profileTypes.size(); i++) {
                if (i > 0) {
                    sqlBuilder.append(",");
                }
                sqlBuilder.append("?");
                params.add(profileTypes.get(i));
            }
            sqlBuilder.append(")");
        }

        sqlBuilder.append(" ORDER BY au.first_name, au.last_name");

        return jdbcTemplate.query(sqlBuilder.toString(), new ApplicationUserRowMapper(), params.toArray());
    }
    
    @Override
    public PaginatedResponse<ApplicationUser> findAllUsersForGivenTenantId(Long tenantId, Long user_id, Pageable pageable) {
        // Correct offset calculation
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        
        String sql =
        		"FROM application_user au\r\n"
        		+ "INNER JOIN user_tenant ut \r\n"
        		+ "    ON au.user_id = ut.user_id\r\n"
        		+ "INNER JOIN user_role ur \r\n"
        		+ "    ON ur.user_id = au.user_id\r\n"
        		+ "INNER JOIN role ro \r\n"
        		+ "    ON ur.role_id = ro.role_id\r\n"
        		+ "WHERE ut.tenant_id = ? and ro.is_active =1 and ro.is_assignable =1 and au.deleted='0' and au.user_id !=?";   //limit ? offset ?

        String countSql = "SELECT COUNT(*) " + sql;
        long total = jdbcTemplate.queryForObject(countSql, Long.class, tenantId,user_id);
        sql = "SELECT au.*, ro.name AS roleNames\r\n"+ sql + " ORDER BY au.first_name, au.last_name LIMIT ? OFFSET ?";
        List<ApplicationUser> users = jdbcTemplate.query(sql, new ApplicationUserRowMapper(), tenantId, user_id, limit, offset);

        // Query for total count (separate for efficiency)
      
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
        return PaginatedResponse.<ApplicationUser>builder().content(users)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(total)
                .totalPages(totalPages)
                .build();
    }
    
    @Override
    public PaginatedResponse<ApplicationUser> getAllUsersprofilesForGivenTenantId(Long tenantId, Pageable pageable)
    {
    	int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();
        
        String sql =
        		"FROM application_user au\r\n"
        		+ "INNER JOIN user_tenant ut \r\n"
        		+ "    ON au.user_id = ut.user_id\r\n"
        		+ "INNER JOIN user_role ur \r\n"
        		+ "    ON ur.user_id = au.user_id\r\n"
        		+ "INNER JOIN role ro \r\n"
        		+ "    ON ur.role_id = ro.role_id\r\n"
        		+ "WHERE ut.tenant_id = ? and ro.is_active =1 and ro.is_assignable =1 and au.deleted='0'";   //limit ? offset ?

        String countSql = "SELECT COUNT(*) " + sql;
        long total = jdbcTemplate.queryForObject(countSql, Long.class, tenantId);
        sql = "SELECT au.*, ro.name AS roleNames\r\n"+ sql + " ORDER BY au.first_name, au.last_name LIMIT ? OFFSET ?";
        List<ApplicationUser> users = jdbcTemplate.query(sql, new ApplicationUserRowMapper(), tenantId, limit, offset);

        // Query for total count (separate for efficiency)
      
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
        return PaginatedResponse.<ApplicationUser>builder().content(users)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(total)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public void updateUserParentId(Long userParentId, Long userId) {
        String sql = "UPDATE application_user SET user_parent_id = ? , updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        jdbcTemplate.update(sql, userParentId, userId);
    }

    @Override
    public List<ApplicationUser> findByUserParentId(Long userParentId) {
        String sql = "SELECT * FROM application_user WHERE user_parent_id = ? AND deleted = '0'";
        return jdbcTemplate.query(sql, new ApplicationUserRowMapper(), userParentId);
    }
}