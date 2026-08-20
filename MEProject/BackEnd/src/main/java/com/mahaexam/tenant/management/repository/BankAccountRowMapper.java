package com.mahaexam.tenant.management.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mahaexam.tenant.management.model.BankAccount;

public class BankAccountRowMapper implements RowMapper<BankAccount> {
	@Override
	public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setId(rs.getLong("id"));
		bankAccount.setUserId(rs.getLong("user_id"));
		bankAccount.setAccountNumber(rs.getString("account_number"));
		bankAccount.setAccountName(rs.getString("account_name"));
		bankAccount.setBankName(rs.getString("bank_name"));
		bankAccount.setBranchName(rs.getString("branch_name"));
		bankAccount.setIfscCode(rs.getString("ifsc_code"));
		bankAccount.setAddress(rs.getString("address"));
		bankAccount.setPhoneNo(rs.getString("phone_no"));
		bankAccount.setComments(rs.getString("comments"));
		return bankAccount;
	}
}