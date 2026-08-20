package com.mahaexam.packagemanagment.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayuPaymentResponse {
    private Integer status;
    private String message;
    private Result result;
    private String errorCode;
    private String guid;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private BigDecimal subAmount;
        private BigDecimal tax;
        private BigDecimal shippingCharge;
        private BigDecimal totalAmount;
        private String invoiceNumber;
        private String paymentLink;
        private String description;
        private Boolean active;
        private Boolean isPartialPaymentAllowed;
        private String expiryDate;
        private Udf udf;
        private Address address;
        private String emailStatus;
        private String smsStatus;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Udf {
        private String udf1;
        private String udf2;
        private String udf3;
        private String udf4;
        private String udf5;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String country;
        private String zipCode;
    }
}