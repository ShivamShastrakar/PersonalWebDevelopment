package com.mahaexam.tenant.management.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mahaexam.tenant.management.model.BankAccount;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {
	private final JdbcTemplate jdbcTemplate;

	public BankAccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public BankAccount save(BankAccount bankAccount) {
		String sql = "INSERT INTO bank_account (user_id, account_number, account_name, bank_name, branch_name, "
				+ "ifsc_code, address, phone_no, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, bankAccount.getUserId());
			ps.setString(2, bankAccount.getAccountNumber());
			ps.setString(3, bankAccount.getAccountName());
			ps.setString(4, bankAccount.getBankName());
			ps.setString(5, bankAccount.getBranchName());
			ps.setString(6, bankAccount.getIfscCode());
			ps.setString(7, bankAccount.getAddress());
			ps.setString(8, bankAccount.getPhoneNo());
			ps.setString(9, bankAccount.getComments());
			return ps;
		}, keyHolder);

		// Retrieve and set the generated ID
		Number key = keyHolder.getKey();
		if (key != null) {
			bankAccount.setId(key.longValue());
		}

		return bankAccount;

	}

	@Override
	public Optional<BankAccount> findById(Long id) {
		String sql = "SELECT * FROM bank_account WHERE id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BankAccountRowMapper(), id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public List<BankAccount> findAll() {
		String sql = "SELECT * FROM bank_account";
		return jdbcTemplate.query(sql, new BankAccountRowMapper());
	}

	@Override
	public BankAccount update(BankAccount bankAccount) {
		String sql = "UPDATE bank_account SET  account_number = ?, account_name = ?, bank_name = ?, "
				+ "branch_name = ?, ifsc_code = ?, address = ?, phone_no = ?, comments = ? WHERE id = ?";

		jdbcTemplate.update(sql, bankAccount.getAccountNumber(), bankAccount.getAccountName(),
				bankAccount.getBankName(), bankAccount.getBranchName(), bankAccount.getIfscCode(),
				bankAccount.getAddress(), bankAccount.getPhoneNo(), bankAccount.getComments(), bankAccount.getId());
		return bankAccount;
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM bank_account WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public Optional<BankAccount> findByUserId(Long userId) {
        String sql = "SELECT * FROM bank_account WHERE user_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BankAccountRowMapper(), userId));
        } catch (Exception e) {
            return Optional.empty();
        }
	}
}