package com.mahaexam.packagemanagment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    private Long transactionId;
    private Long selectionSummaryId;
    private Long batchId;
    private String payuTransactionId;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String paymentLink;
    private String paymentLinkId;  // New nullable field
    private String remark;         // New nullable field
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
