package com.mahaexam.common.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Institute {
    private int id;
    private String indexNumber;
    private String udiNumber;
    private String instituteName;
    private String instituteAddessLine1;
    private String instituteAddessLine2;
    private String place;
    private String latitude;
    private String longitude;
    private String pinCode;
    private String telephone;
    private String mobileNumber;
    private String emailId;
    private String intakeCapacity;
    private String intakeCapacity12th;
    private String staffDetails;
    private String digiInfraAvailability;
    private String batches;
    private String specialBatchStaffAvailability;
    private String seatingStrengthOfflineOnline;
    private String onOffCenterAvailability;
    private String onlineExamAvailability;
    private String offlineExamAvailability;
    private String seatingStrengthOffline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted;
    private Integer eduSocietyId;
    private Integer talukaId;
    private Integer stateId;
    private Integer districtId;
    private Integer divisionId;
    private Integer zoneId;
    private String state;
    private String district;
    private String taluka;
    private String zone;
    private String powerBackup;
    private String digitalInfrastructureAvailability;
    private String nonTeachingStaff;
    private String staffAvailabilityPhysics;
    private String staffAvailabilityChemistry;
    private String staffAvailabilityBotney;
    private String staffAvailabilityZoology;
    private String staffAvailabilityMath;
    private String distinction;
    private String totalIntake;
    private String status;
    private String tokenId;
    private boolean isDisabled;
    private String instituteDiscount;
    private String centerLevel;
    // Getters and setters...
}