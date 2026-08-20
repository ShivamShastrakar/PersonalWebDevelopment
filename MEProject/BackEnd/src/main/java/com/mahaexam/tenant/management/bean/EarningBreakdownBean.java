package com.mahaexam.tenant.management.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EarningBreakdownBean {
    private String packageType;
    private int referrals;
    private java.math.BigDecimal totalPackageAmount = BigDecimal.ZERO;
    private java.math.BigDecimal commissionAmount = BigDecimal.ZERO;
    private String appliedSlab; // human readable slab info
    private java.math.BigDecimal totalAdditionalCommissionAmount = BigDecimal.ZERO;
    private java.math.BigDecimal totalPackageAmountForAllPackages = BigDecimal.ZERO;
}
