package com.mahaexam.tenant.management.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfflinePayment {
    private Long Id;
    private Long batchId;
    private Long transactionId;
    private BigDecimal amount;
    private String paymentMode;
    private LocalDate paymentDate;
    private String remarks;

    // Cheque-specific fields (used only if paymentMode = CHEQUE)
    private String chequeNumber;
    private String bankName;
    private LocalDate chequeDate;

    // Cash-specific fields (used only if paymentMode = CASH)
    private String receivedBy;

    private String status; // e.g., PENDING, COMPLETED, FAILED

}
