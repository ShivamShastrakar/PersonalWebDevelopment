package com.mahaexam.packagemanagment.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequest {
	@NotBlank(message = "Invoice number is mandatory")
	private String invoiceNumber;

	@NotNull(message = "Amount is mandatory")
	@Positive(message = "Amount must be a positive value")
	private BigDecimal amount;

	@NotBlank(message = "Description is mandatory")
	private String description;

	@NotBlank(message = "Customer name is mandatory")
	private String customerName;

	@NotBlank(message = "Customer email is mandatory")
	private String customerEmail;

	@NotBlank(message = "Customer mobile is mandatory")
	private String customerMobile;
}
