package com.mahaexam.tenant.management.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelPartnerUpdate {
    private Integer id;
    private Long userId;
    private String name;
    private String imageUrl;
    private String userType;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private LocalDate dateOfBirth;

    private String registeredMobileNumber;
    private String whatsappNumber;
    private String email;

    private Long addressId;
    private String addressText;
    private Integer stateId;
    private Integer districtId;
    private Integer talukaId;
    private String pinCode;
    private String place;

    private String companyName;
    private String businessType;
    private String panNumber;
    private String tanNumber;
    private String gstNumber;

    private Integer businessExpYears;
    private String serviceType;
    private Integer deeperAssociationYears;

    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;

    private BigDecimal additionalCommissionPercent;

}
