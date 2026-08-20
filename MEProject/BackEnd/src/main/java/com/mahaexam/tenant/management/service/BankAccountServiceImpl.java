package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mahaexam.tenant.management.model.BankAccount;
import com.mahaexam.tenant.management.repository.BankAccountRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	private final BankAccountRepository repository;

	public BankAccountServiceImpl(BankAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public BankAccount save(BankAccount bankAccount) {
		return repository.save(bankAccount);
	}

	@Override
	public Optional<BankAccount> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<BankAccount> findAll() {
		return repository.findAll();
	}

	@Override
	public BankAccount update(BankAccount bankAccount) {
		return repository.update(bankAccount);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Optional<BankAccount> findByUserId(Long userId) {
		return repository.findByUserId(userId);
	}
}
