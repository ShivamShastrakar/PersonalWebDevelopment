package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class Address {
    private Long addressId;
    private Long userId;
    private String addressText;
    private Integer stateId;
    private Integer districtId;
    private Integer talukaId;
    private String place;
    private String pincode;
    private String country;
    private java.time.LocalDateTime createdAt;

    private String state;
    private String district;
    private String taluka;
}
