package com.mahaexam.tenant.management.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndirectEarningDetailBean {
    private Long userId;
    private String userName;
    private String userFullName;
    private int totalReferrals;
    private BigDecimal totalEarning = BigDecimal.ZERO;
    private List<EarningBreakdownBean> breakdown;
    private java.math.BigDecimal totalAdditionalCommissionAmount = BigDecimal.ZERO;
    private java.math.BigDecimal totalPackageAmountForAllPackages = BigDecimal.ZERO;
}
