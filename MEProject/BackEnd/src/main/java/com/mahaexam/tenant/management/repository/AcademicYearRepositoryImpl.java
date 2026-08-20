package com.mahaexam.tenant.management.repository;

import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.tenant.management.model.AcademicYear;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class AcademicYearRepositoryImpl implements AcademicYearRepository {

    private final JdbcTemplate jdbcTemplate;

    public AcademicYearRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AcademicYear> rowMapper = (rs, rowNum) -> AcademicYear.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .startDate(rs.getDate("start_date").toLocalDate())
            .endDate(rs.getDate("end_date").toLocalDate())
            .tenantId(rs.getLong("tenant_id"))
            .boardId(rs.getObject("board_id") != null ? rs.getInt("board_id") : null)
            .deleted(rs.getString("deleted"))
            .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
            .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
            .boardName(RepoUtil.getOptionalString( rs,"board_name"))
            .build();

    @Override
    public AcademicYear save(AcademicYear academicYear) {
        String sql = "INSERT INTO academic_year (name, start_date, end_date, tenant_id, board_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, academicYear.getName());
            ps.setDate(2, Date.valueOf(academicYear.getStartDate()));
            ps.setDate(3, Date.valueOf(academicYear.getEndDate()));
            ps.setLong(4, academicYear.getTenantId());
            if (academicYear.getBoardId() != null) {
                ps.setInt(5, academicYear.getBoardId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            return ps;
        }, keyHolder);
        academicYear.setId(keyHolder.getKey().longValue());
        return academicYear;
    }

    @Override
    public Optional<AcademicYear> findById(Long id) {
        String sql = "SELECT ay.*, b.board_name FROM academic_year ay LEFT JOIN board b ON b.id = ay.board_id WHERE ay.id = ? AND ay.deleted = '0'";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AcademicYear> findByNameAndTenantId(String name, Long tenantId) {
        String sql = "SELECT ay.*, b.board_name FROM academic_year ay LEFT JOIN board b ON b.id = ay.board_id WHERE (ay.name = ? OR ay.name LIKE CONCAT(?, '-%')) AND ay.tenant_id = ? AND ay.deleted = '0' ORDER BY ay.board_id ASC LIMIT 1";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, name, name, tenantId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AcademicYear> findByNameTenantAndBoardId(String name, Long tenantId, Integer boardId) {
        String sql = "SELECT ay.*, b.board_name FROM academic_year ay LEFT JOIN board b ON b.id = ay.board_id WHERE (ay.name = ? OR ay.name LIKE CONCAT(?, '-%')) AND ay.tenant_id = ? AND (ay.board_id = ? OR ay.board_id IS NULL) AND ay.deleted = '0' ORDER BY ay.board_id DESC LIMIT 1";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, name, name, tenantId, boardId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<AcademicYear> findAllByTenantId(Long tenantId) {
        String sql = "SELECT ay.*, b.board_name FROM academic_year ay LEFT JOIN board b ON b.id = ay.board_id WHERE ay.tenant_id = ? AND ay.deleted = '0' ORDER BY ay.start_date DESC";
        return jdbcTemplate.query(sql, rowMapper, tenantId);
    }

    @Override
    public AcademicYear update(AcademicYear academicYear) {
        String sql = "UPDATE academic_year SET name = ?, start_date = ?, end_date = ?, board_id = ? WHERE id = ? AND tenant_id = ?";
        jdbcTemplate.update(sql, academicYear.getName(), Date.valueOf(academicYear.getStartDate()), 
                Date.valueOf(academicYear.getEndDate()), academicYear.getBoardId(), academicYear.getId(), academicYear.getTenantId());
        return academicYear;
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE academic_year SET deleted = '1' WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
