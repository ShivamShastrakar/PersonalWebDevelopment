package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.stereotype.Service;

import com.mahaexam.common.model.SubjectGroup;
import com.mahaexam.common.repo.SubjectGroupRepository;

@Service
public class SubjectGroupServiceImpl implements SubjectGroupService {

	private final SubjectGroupRepository subjectGroupRepository;

	public SubjectGroupServiceImpl(SubjectGroupRepository subjectGroupRepository) {
		this.subjectGroupRepository = subjectGroupRepository;
	}

	@Override
	public List<SubjectGroup> getAllGroupsByTenant(Long tenantId) {
		return subjectGroupRepository.findAllByTenant(tenantId);
	}

	@Override
	public SubjectGroup getGroupById(int id) {
		return subjectGroupRepository.findById(id);
	}

	@Override
	public int createGroup(SubjectGroup group) {
		if (subjectGroupRepository.existsByGroupNameAndTenantId(group.getGroupName(), group.getTenantId())) {
			throw new ValidationException("Group name already exists for this tenant.");
		}
		return subjectGroupRepository.save(group);
	}

	@Override
	public int updateGroup(SubjectGroup group) {
		if (subjectGroupRepository.existsByGroupNameAndTenantIdExceptId(group.getGroupName(), group.getTenantId(), group.getGroupId())) {
			throw new ValidationException("Group name already exists for this tenant.");
		}
		return subjectGroupRepository.update(group);
	}

	@Override
	public int deleteGroup(int id) {
		return subjectGroupRepository.softDelete(id);
	}

    @Override
    public Optional<SubjectGroup> findGroupByName(String subjectGroupName) {
        return subjectGroupRepository.findGroupByName(subjectGroupName);
    }
}