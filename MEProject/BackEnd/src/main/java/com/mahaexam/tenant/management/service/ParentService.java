package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Parent;

public interface ParentService {
	Parent saveParent(Parent parent);

	Optional<Parent> findParentById(Long parentId);

	List<Parent> findAllParents();

	void updateParent(Parent parent);

	void deleteParent(Long parentId);

	List<Parent> findByStudentIds(List<Long> studentIds);

	Optional<Parent> findByStudentId(Long studentId);
}