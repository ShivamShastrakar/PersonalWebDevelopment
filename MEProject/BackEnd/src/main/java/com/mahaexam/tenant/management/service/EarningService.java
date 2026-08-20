package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.bean.EarningSummaryBean;
import com.mahaexam.tenant.management.bean.IndirectIncomeEarningBean;

public interface EarningService {
    EarningSummaryBean computeEarningSummary(Long userId, Long tenantId);
    
    IndirectIncomeEarningBean computeIndirectEarning(Long userId,Long tenantId);
}
