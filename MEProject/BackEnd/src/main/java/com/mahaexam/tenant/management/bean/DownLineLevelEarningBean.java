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
public class DownLineLevelEarningBean {
    private Integer levelNumber; // 1, 2, 3, or 4
    private String levelName; // "Level 1", "Level 2", etc.
    private int totalDownlineUsers;
    private int totalReferralsAcrossLevel;
    private BigDecimal totalEarningForLevel = BigDecimal.ZERO;
    private List<IndirectEarningDetailBean> userEarnings;
    private java.math.BigDecimal totalAdditionalCommissionAmount = BigDecimal.ZERO;
    private java.math.BigDecimal totalPackageAmountForAllPackages = BigDecimal.ZERO;
}
