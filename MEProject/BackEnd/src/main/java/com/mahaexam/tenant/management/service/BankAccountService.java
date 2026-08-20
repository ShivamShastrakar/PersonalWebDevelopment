package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.BankAccount;

public interface BankAccountService {
	BankAccount save(BankAccount bankAccount);

	Optional<BankAccount> findById(Long id);

	List<BankAccount> findAll();

	BankAccount update(BankAccount bankAccount);

	void delete(Long id);

    Optional<BankAccount> findByUserId(Long userId);
}