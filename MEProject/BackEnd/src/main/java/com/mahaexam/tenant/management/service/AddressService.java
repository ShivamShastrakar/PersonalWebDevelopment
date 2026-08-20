package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Address;

public interface AddressService {
	Address save(Address address);

	Optional<Address> findById(Long addressId);

	List<Address> findAll();

	Address update(Address address);

	void delete(Long addressId);

	Optional<Address> findByUserId(Long userId);

	List<Address> findByUserIds(List<Long> userIds);
}