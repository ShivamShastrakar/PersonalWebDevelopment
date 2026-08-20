package com.mahaexam.packagemanagment.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeRuleBean {
    private Integer id;
    private String ruleName;
    private Integer ruleTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal amount;
    private LocalDateTime dueDate;
    private Integer packageId;
    private Integer instituteId;
    private Integer talukaId;
    private Integer districtId;
    private Integer stateId;
    private Integer divisionId;
    private String amountType;
    private String ruleCode;
    private Long roleId;
    private Integer incentiveCap;
    private String rulesAmount;
    private String packageType;
    private Integer quantity;
    private String parentPackageIds;
    private Long tenantId;
}