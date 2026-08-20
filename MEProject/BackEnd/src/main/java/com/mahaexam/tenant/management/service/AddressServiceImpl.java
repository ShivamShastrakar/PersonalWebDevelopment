package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mahaexam.tenant.management.model.Address;
import com.mahaexam.tenant.management.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	private final AddressRepository repository;

	public AddressServiceImpl(AddressRepository repository) {
		this.repository = repository;
	}

	@Override
	public Address save(Address address) {
		return repository.save(address);
	}

	@Override
	public Optional<Address> findById(Long addressId) {
		return repository.findById(addressId);
	}

	@Override
	public List<Address> findAll() {
		return repository.findAll();
	}

	@Override
	public Address update(Address address) {
		return repository.update(address);
	}

	@Override
	public void delete(Long addressId) {
		repository.delete(addressId);
	}

	@Override
	public Optional<Address> findByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public List<Address> findByUserIds(List<Long> userIds) {
		return repository.findByUserIds(userIds);
	}
}