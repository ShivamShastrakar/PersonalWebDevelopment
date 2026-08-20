package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.util.RepoUtil;
import com.mahaexam.tenant.management.model.Parent;

public class ParentRowMapper implements RowMapper<Parent> {
    @Override
    public Parent mapRow(ResultSet rs, int rowNum) throws SQLException {
        Parent parent =  Parent.builder().build();
        parent.setParentId(rs.getLong("parent_id"));
        parent.setFatherName(rs.getString("father_name"));
        parent.setFatherMobileNumber(rs.getString("father_mobile_number"));
        parent.setFatherOccupation(rs.getString("father_occupation"));
        parent.setMotherName(rs.getString("mother_name"));
        parent.setMotherMobileNumber(rs.getString("mother_mobile_number"));
        parent.setMotherOccupation(rs.getString("mother_occupation"));
        parent.setNumberOfSiblings(rs.getInt("number_of_siblings"));
        parent.setFirstSiblingName(rs.getString("first_sibling_name"));
        parent.setFirstSiblingStd(rs.getString("first_sibling_std"));
        parent.setSecondSiblingName(rs.getString("second_sibling_name"));
        parent.setSecondSiblingStd(rs.getString("second_sibling_std"));
        parent.setParentsYearlyIncome(rs.getString("parents_yearly_income"));
        parent.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        parent.setStudentId(RepoUtil.getOptionalLong(rs, "student_id"));
        
        return parent;
    }
}