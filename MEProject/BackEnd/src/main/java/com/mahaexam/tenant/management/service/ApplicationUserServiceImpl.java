package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mahaexam.tenant.management.model.ApplicationUser;
import com.mahaexam.tenant.management.repository.ApplicationUserRepository;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
	private final ApplicationUserRepository repository;

	public ApplicationUserServiceImpl(ApplicationUserRepository repository) {
		this.repository = repository;
	}

	@Override
	public ApplicationUser save(ApplicationUser user) {
		return repository.save(user);
	}

	@Override
	public Optional<ApplicationUser> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<ApplicationUser> findAll() {
		return repository.findAll();
	}

	@Override
	public ApplicationUser update(ApplicationUser user) {
		return repository.update(user);
	}

	@Override
	public Optional<ApplicationUser> findByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public Optional<ApplicationUser> findByEmailId(String emailId) {
		return repository.findByEmailId(emailId);
	}

    @Override
    public Optional<ApplicationUser> findByMobileNo(String mobileNo) {
        return repository.findByMobileNo(mobileNo);
    }

    @Override
    public List<ApplicationUser> findByFirstOrLastName(String name, Long tenantId, List<String> profileTypes) {
        return repository.findByFirstOrLastName(name, tenantId, profileTypes);
    }

	@Override
	public Optional<ApplicationUser> findByEmailIdAndMobileNo(String emailId, String mobileNo) {
		return repository.findByEmailIdAndMobileNo(emailId, mobileNo);
	}
	
	@Override
	public ApplicationUser updateByUserId(ApplicationUser user) {
		return repository.updateByUserId(user);
	}
}