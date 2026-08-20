package com.mahaexam.tenant.management.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EarningSummaryBean {
    private Long channelPartnerId;
    private int totalReferrals;
    private BigDecimal totalEarning = BigDecimal.ZERO;
    private List<EarningBreakdownBean> breakdown;
}
