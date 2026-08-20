package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class ChannelPartner {
    private Long partnerId;
    private Long userId;
    private String companyName;
    private String businessType;
    private String panNumber;
    private String tanNumber;
    private String gstNumber;
    private Integer businessExpYears;
    private String serviceType;
    private Integer deeperAssociationYears;
    private Long parentPartnerId;
}
