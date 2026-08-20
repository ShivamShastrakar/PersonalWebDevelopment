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
public class IndirectIncomeEarningBean {
    private Long userId;
    private String userName;
    private String userFullName;
    private BigDecimal directEarning = BigDecimal.ZERO;
    private BigDecimal indirectEarning = BigDecimal.ZERO;
    private BigDecimal totalEarning = BigDecimal.ZERO;
    private List<DownLineLevelEarningBean> downlineLevels;
    private EarningSummaryBean directEarningSummary; // Include the summary bean for direct and indirect earnings
}
