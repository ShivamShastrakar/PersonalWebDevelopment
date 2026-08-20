package com.mahaexam.payment.bean;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequest {
    private String invoiceNumber;
    private BigDecimal amount;
    private String description;
    private String customerName;
    private String customerEmail;
    private String customerMobile;

    //For Razor Pay
    private String clientKey;
    //For PayU
    private String clientId;

    private String clientSecret;
    private String merchantKey;
    private String environment;
    private String sUrl;
    private String fUrl;
    private String apiUrl;

    //For PayU
    private String authTokenUrl;

    private String batchId;
}
