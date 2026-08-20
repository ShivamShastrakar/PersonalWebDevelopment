package com.mahaexam.packagemanagment.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentPayuWebhook {
    private String status;
    private String invoiceNumber;
    private String batchId;
}
