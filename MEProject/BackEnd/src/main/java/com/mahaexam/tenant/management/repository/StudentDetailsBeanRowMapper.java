package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mahaexam.common.util.RepoUtil;
import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.bean.AddressBean;
import com.mahaexam.tenant.management.bean.ParentBean;
import com.mahaexam.tenant.management.bean.StudentDetailsBean;

public class StudentDetailsBeanRowMapper implements RowMapper<StudentDetailsBean> {
    @Override
    public StudentDetailsBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentDetailsBean studentDetails = new StudentDetailsBean();

        // Fields from StudentRegistrationBean (application_user)
        studentDetails.setFirstName(rs.getString("first_name"));
        studentDetails.setLastName(rs.getString("last_name"));
        studentDetails.setMiddleName(rs.getString("middle_name"));
        studentDetails.setGender(rs.getString("gender"));
        studentDetails.setDateOfBirth(rs.getObject("date_of_birth", LocalDate.class));
        studentDetails.setAadharNumber(rs.getString("aadhar_number"));
        studentDetails.setRegisteredMobileNumber(rs.getString("registered_mobile_number"));
        studentDetails.setWhatsappNumber(rs.getString("whatsapp_number"));
        studentDetails.setEmail(rs.getString("email"));
        studentDetails.setClassId(rs.getObject("current_class_id", Integer.class));
        studentDetails.setSubjectGroupId(rs.getObject("current_subject_group_id", Integer.class));
        studentDetails.setTargetFinalExamYear(rs.getObject("target_final_exam_year", Integer.class));
        studentDetails.setUserId(rs.getObject("user_id", Long.class));
        studentDetails.setStudentReferenceId(rs.getObject("student_reference_id", Long.class));
        studentDetails.setPhotoUrl(rs.getString("photo_url"));
        studentDetails.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

        // Fields from StudentDetailsBean (student)
        studentDetails.setStudentId(rs.getObject("student_id", Long.class));
        studentDetails.setApplicationUserId(rs.getObject("id", Long.class));
        studentDetails.setCurrentClassId(rs.getObject("current_class_id", Integer.class));
        studentDetails.setCurrentSubjectGroupId(rs.getObject("current_subject_group_id", Integer.class));
        studentDetails.setUserType(RepoUtil.getOptionalString(rs,"user_type"));
        studentDetails.setClassName(rs.getString("class_name"));
        studentDetails.setGroupName(rs.getString("group_name"));
        studentDetails.setMedium(rs.getString("medium"));
        studentDetails.setSchoolName(rs.getString("school_name"));
        studentDetails.setSchoolAddress(rs.getString("school_address"));
        studentDetails.setCategory(rs.getString("category"));
        studentDetails.setInstituteName(rs.getString("institute_name"));
        studentDetails.setParallelReservation(rs.getString("parallel_reservation"));

// Address (from address table, LEFT JOIN)
        if (rs.getObject("address_id") != null) {
            AddressBean address = new AddressBean();
            address.setAddressId(rs.getObject("address_id", Long.class));
            address.setAddressText(rs.getString("address_text"));
            address.setPlace(rs.getString("place"));
            address.setPincode(rs.getString("pincode"));
            address.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            address.setState(rs.getString("state"));
            address.setTaluka(rs.getString("taluka"));
            address.setDistrict(rs.getString("district"));
            
            // Add missing ID fields
            address.setStateId(rs.getObject("state_id", Integer.class));
            address.setDistrictId(rs.getObject("district_id", Integer.class));
            address.setTalukaId(rs.getObject("taluka_id", Integer.class));
            address.setUserId(rs.getObject("user_id", Long.class));

            studentDetails.setAddress(address);
        }

        // Parent (from parent table, LEFT JOIN)
        if (rs.getObject("parent_id") != null) {
            ParentBean parent = new ParentBean();
            parent.setParentId(rs.getObject("parent_id", Long.class));
            parent.setFatherName(rs.getString("father_name"));
            parent.setFatherMobileNumber(rs.getString("father_mobile_number"));
            parent.setFatherOccupation(rs.getString("father_occupation"));
            parent.setMotherName(rs.getString("mother_name"));
            parent.setMotherMobileNumber(rs.getString("mother_mobile_number"));
            parent.setMotherOccupation(rs.getString("mother_occupation"));
            parent.setNumberOfSiblings(rs.getObject("number_of_siblings", Integer.class));
            parent.setFirstSiblingName(rs.getString("first_sibling_name"));
            parent.setFirstSiblingStd(rs.getString("first_sibling_std"));
            parent.setSecondSiblingName(rs.getString("second_sibling_name"));
            parent.setSecondSiblingStd(rs.getString("second_sibling_std"));
            parent.setParentsYearlyIncome(rs.getString("parents_yearly_income"));
            parent.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            studentDetails.setParent(parent);
        }

        return studentDetails;
    }
}