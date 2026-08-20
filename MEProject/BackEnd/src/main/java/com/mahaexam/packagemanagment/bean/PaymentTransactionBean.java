package com.mahaexam.packagemanagment.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentTransactionBean {
    private Long transactionId;
    private Long selectionSummaryId;
    private String payuTransactionId;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String paymentLink;
    private String paymentLinkId;  // New nullable field
    private String remark;         // New nullable field
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
