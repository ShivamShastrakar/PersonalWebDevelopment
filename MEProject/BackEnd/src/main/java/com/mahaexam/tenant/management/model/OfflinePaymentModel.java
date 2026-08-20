package com.mahaexam.tenant.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfflinePaymentModel {

    private Long id;
    private BigDecimal amount;
    private String paymentMode;
    private LocalDate paymentDate;
    private String remarks;

    // Cheque fields
    private String chequeNumber;
    private String bankName;
    private LocalDate chequeDate;

    // Cash fields
    private String receivedBy;

    private Long batchId;
    private Long transactionId;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

