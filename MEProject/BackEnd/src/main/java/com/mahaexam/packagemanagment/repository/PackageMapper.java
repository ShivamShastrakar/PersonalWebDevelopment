package com.mahaexam.packagemanagment.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.packagemanagment.model.PackageModel;

public class PackageMapper implements RowMapper<PackageModel> {
    @Override
    public PackageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PackageModel pkg = new PackageModel();
        pkg.setId(rs.getInt("id"));
        pkg.setPackageName(rs.getString("package_name"));
        pkg.setPackageDetails(rs.getString("package_details"));
        pkg.setAmount(rs.getBigDecimal("amount"));
        pkg.setPackageFor(rs.getString("package_for"));
        pkg.setPackageType(rs.getString("package_type"));
        pkg.setPackageTargetStudents(rs.getString("package_target_students"));
        pkg.setPackageMode(rs.getString("package_mode"));
        pkg.setFlag(rs.getBoolean("flag"));
        pkg.setPackageTypeName(rs.getString("package_type_name"));
        pkg.setPkgExamGroup(RepoUtil.getOptionalInteger(rs, "pkg_exam_group"));
        pkg.setIsArchived(rs.getInt("is_archived"));
        pkg.setArchivedBy(rs.getObject("archived_by", Integer.class));
        pkg.setStartDate(rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);
        pkg.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
        pkg.setTargetYear(rs.getString("target_year"));
        pkg.setShowStrikePrice(rs.getInt("show_strike_price"));
        pkg.setStrikePrice(rs.getBigDecimal("strike_price"));
        pkg.setIsTestingPackage(rs.getInt("is_testing_package"));
        pkg.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        pkg.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        pkg.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        pkg.setDeleted(rs.getString("deleted"));
        pkg.setUpdatedBy(rs.getObject("updated_by", Integer.class));
        pkg.setPackageImgUrl(rs.getString("package_img_url"));
        pkg.setTenantId(rs.getObject("tenant_id", Long.class));
        
        // Handle optional class_id and course_id
        pkg.setClassId(RepoUtil.getOptionalInteger(rs, "class_id"));
        pkg.setCourseId(RepoUtil.getOptionalInteger(rs, "course_id"));
        pkg.setStudentId(RepoUtil.getOptionalLong(rs, "student_id"));
        pkg.setSubscriptiontype(RepoUtil.getOptionalString(rs, "subscriptiontype"));
        pkg.setNo_of_mock_exams((RepoUtil.getOptionalInteger(rs, "no_of_mock_exams")));
        pkg.setNo_of_pactice_exams((RepoUtil.getOptionalInteger(rs, "no_of_pactice_exams")));
        pkg.setNo_of_bonus_exams((RepoUtil.getOptionalInteger(rs, "no_of_bonus_exams")));
        pkg.setPackageCategoryId(RepoUtil.getOptionalInteger(rs, "package_category_id"));
        return pkg;
    }
    
   
}