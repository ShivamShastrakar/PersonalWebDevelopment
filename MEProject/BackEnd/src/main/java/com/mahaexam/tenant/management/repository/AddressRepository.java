package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Address;

public interface AddressRepository {
	Address save(Address address);

	Optional<Address> findById(Long addressId);

	List<Address> findAll();

	Address update(Address address);

	void delete(Long addressId);

	public Optional<Address> findByUserId(Long userId);

	List<Address> findByUserIds(List<Long> userIds);
}