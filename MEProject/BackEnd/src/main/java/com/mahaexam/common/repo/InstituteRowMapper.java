package com.mahaexam.common.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.common.model.Institute;

public class InstituteRowMapper implements RowMapper<Institute> {
    @Override
    public Institute mapRow(ResultSet rs, int rowNum) throws SQLException {
        Institute i = new Institute();
        i.setId(rs.getInt("id"));
        i.setIndexNumber(rs.getString("index_number"));
        i.setUdiNumber(rs.getString("udi_number"));
        i.setInstituteName(rs.getString("institute_name"));
        i.setInstituteAddessLine1(rs.getString("institute_addess_line_1"));
        i.setInstituteAddessLine2(rs.getString("institute_addess_line_2"));
        i.setPlace(rs.getString("place"));
        i.setLatitude(rs.getString("latitude"));
        i.setLongitude(rs.getString("longitude"));
        i.setPinCode(rs.getString("pin_code"));
        i.setTelephone(rs.getString("telephone"));
        i.setMobileNumber(rs.getString("mobile_number"));
        i.setEmailId(rs.getString("email_id"));
        i.setIntakeCapacity(rs.getString("intake_capacity"));
        i.setIntakeCapacity12th(rs.getString("intake_capacity_12th"));
        i.setStaffDetails(rs.getString("staff_details"));
        i.setDigiInfraAvailability(rs.getString("digi_infra_availability"));
        i.setBatches(rs.getString("batches"));
        i.setSpecialBatchStaffAvailability(rs.getString("special_batch_staff_availability"));
        i.setSeatingStrengthOfflineOnline(rs.getString("seating_strength_offline_online"));
        i.setOnOffCenterAvailability(rs.getString("on_off_center_availability"));
        i.setOnlineExamAvailability(rs.getString("online_exam_availability"));
        i.setOfflineExamAvailability(rs.getString("offline_exam_availability"));
        i.setSeatingStrengthOffline(rs.getString("seating_strength_offline"));
        i.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        i.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
        i.setDeletedAt(rs.getObject("deleted_at", LocalDateTime.class));
        i.setDeleted(rs.getString("deleted"));
        i.setEduSocietyId(rs.getObject("edu_society_id", Integer.class));
        i.setTalukaId(rs.getObject("taluka_id", Integer.class));
        i.setStateId(rs.getObject("state_id", Integer.class));
        i.setDistrictId(rs.getObject("district_id", Integer.class));
        i.setDivisionId(rs.getObject("division_id", Integer.class));
        i.setZoneId(rs.getObject("zone_id", Integer.class));
        i.setPowerBackup(rs.getString("power_backup"));
        i.setDigitalInfrastructureAvailability(rs.getString("digital_infrastructure_availability"));
        i.setNonTeachingStaff(rs.getString("non_teaching_staff"));
        i.setStaffAvailabilityPhysics(rs.getString("staff_availability_physics"));
        i.setStaffAvailabilityChemistry(rs.getString("staff_availability_chemistry"));
        i.setStaffAvailabilityBotney(rs.getString("staff_availability_botney"));
        i.setStaffAvailabilityZoology(rs.getString("staff_availability_zoology"));
        i.setStaffAvailabilityMath(rs.getString("staff_availability_math"));
        i.setDistinction(rs.getString("distinction"));
        i.setTotalIntake(rs.getString("total_intake"));
        i.setStatus(rs.getString("status"));
        i.setTokenId(rs.getString("token_id"));
        i.setDisabled(rs.getBoolean("is_disabled"));
        i.setInstituteDiscount(rs.getString("institute_discount"));
        i.setCenterLevel(rs.getString("center_level"));
        i.setState(rs.getString("state_name"));
        i.setDistrict(rs.getString("district_name"));
        i.setTaluka(rs.getString("taluka_name"));
        return i;
    }
}