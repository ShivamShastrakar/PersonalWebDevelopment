package com.mahaexam.tenant.management.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserEarningTransactionsDtls {
    private Long id;
    private Long referralUserId;
    private Long studentId;
    private Integer studentPackageId;
    private Long commisionConfigId;
    private Integer commisionType;
    private Long levelOrderId;
    private BigDecimal earnedAmount;
    private LocalDate earnedDate;
    private Long eligibleCommisionSlab;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
