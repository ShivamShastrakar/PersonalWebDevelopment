package com.mahaexam.packagemanagment.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PaymentResponse {
	private String invoiceNumber;
	private String merchantKey;
	private BigDecimal amount;
	private String description;
	private String customerName;
	private String customerEmail;
	private String customerMobile;
	private String paymentLink;
	 private String expiryDate;
    private String paymentLinkId;
}
