package com.mahaexam.tenant.management.repository;

import java.util.List;
import java.util.Optional;

import com.mahaexam.tenant.management.model.Parent;

public interface ParentRepository {
    Parent save(Parent parent);
    Optional<Parent> findById(Long parentId);
    List<Parent> findAll();
    void update(Parent parent);
    void delete(Long parentId);
	List<Parent> findByStudentIds(List<Long> studentIds);
	Optional<Parent> findByStudentId(Long studentId);
}
