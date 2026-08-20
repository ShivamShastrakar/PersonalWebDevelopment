package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.TempStudent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TempStudentRowMapper implements RowMapper<TempStudent> {

    @Override
    public TempStudent mapRow(ResultSet rs, int rowNum) throws SQLException {
        TempStudent tempStudent = new TempStudent();

        tempStudent.setId(rs.getLong("id"));
        tempStudent.setBatchId(rs.getLong("batch_id"));
        tempStudent.setLastName(rs.getString("last_name"));
        tempStudent.setFirstName(rs.getString("first_name"));
        tempStudent.setMiddleName(rs.getString("middle_name"));
        tempStudent.setAdharNo(rs.getString("adhar_no"));
        tempStudent.setMobileNumber(rs.getString("mobile_number"));
        tempStudent.setEmail(rs.getString("email"));
        tempStudent.setClassName(rs.getString("class_name"));

        Integer classId = rs.getInt("class_id");
        tempStudent.setClassId(rs.wasNull() ? null : classId);

        tempStudent.setExamGroup(rs.getString("exam_group"));
        tempStudent.setCourses(rs.getString("courses"));
        tempStudent.setCourseIds(rs.getString("course_ids"));

        Integer subjectGroupId = rs.getInt("subject_group_id");
        tempStudent.setSubjectGroupId(rs.wasNull() ? null : subjectGroupId);

        Integer targetYear = rs.getInt("target_final_exam_year");
        tempStudent.setTargetFinalExamYear(rs.wasNull() ? null : targetYear);

        Integer packageId = rs.getInt("package_id");
        tempStudent.setPackageId(rs.wasNull() ? null : packageId);

        Long referenceId = rs.getLong("reference_id");
        tempStudent.setReferenceId(rs.wasNull() ? null : referenceId);

        tempStudent.setErrorMessage(rs.getString("error_message"));

        if (rs.getTimestamp("created_at") != null) {
            tempStudent.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }

        if (rs.getTimestamp("updated_at") != null) {
            tempStudent.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        tempStudent.setMedium(rs.getString("medium"));

        tempStudent.setStudentId(rs.getLong("student_id"));
        return tempStudent;
    }
}
