package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class BankAccount {
	private Long id;
	private Long userId;
	private String accountNumber;
	private String accountName;
	private String bankName;
	private String branchName;
	private String ifscCode;
	private String address;
	private String phoneNo;
	private String comments;
}
