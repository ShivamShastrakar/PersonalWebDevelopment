package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mahaexam.tenant.management.model.Parent;
import com.mahaexam.tenant.management.repository.ParentRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class ParentServiceImpl implements ParentService {
	private static final Logger logger = LoggerFactory.getLogger(ParentServiceImpl.class);
	private final ParentRepository parentRepository;
	private final Validator validator;

	public ParentServiceImpl(ParentRepository parentRepository, Validator validator) {
		this.parentRepository = parentRepository;
		this.validator = validator;
	}

	@Override
	public Parent saveParent(Parent parent) {
		validateParent(parent);
		return parentRepository.save(parent);
	}

	@Override
	public Optional<Parent> findParentById(Long parentId) {
		if (parentId == null) {
			logger.warn("findParentById called with null parentId");
			throw new IllegalArgumentException("Parent ID cannot be null");
		}
		return parentRepository.findById(parentId);
	}

	@Override
	public List<Parent> findAllParents() {
		return parentRepository.findAll();
	}

	@Override
	public void updateParent(Parent parent) {
		if (parent.getParentId() == null) {
			logger.warn("updateParent called with null parentId");
			throw new IllegalArgumentException("Parent ID cannot be null");
		}
		validateParent(parent);
		parentRepository.update(parent);
	}

	@Override
	public void deleteParent(Long parentId) {
		if (parentId == null) {
			logger.warn("deleteParent called with null parentId");
			throw new IllegalArgumentException("Parent ID cannot be null");
		}
		parentRepository.delete(parentId);
	}

	private void validateParent(Parent parent) {
		Set<ConstraintViolation<Parent>> violations = validator.validate(parent);
		if (!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("Validation errors: ");
			for (ConstraintViolation<Parent> violation : violations) {
				errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage())
						.append("; ");
			}
			logger.error("Validation failed for parent: {}", errorMessage);
			throw new IllegalArgumentException(errorMessage.toString());
		}
		// Additional validation for first_sibling_name and first_sibling_std if
		// number_of_siblings > 0

		if (parent.getNumberOfSiblings() > 0
				&& (parent.getFirstSiblingName() == null || parent.getFirstSiblingName().trim().isEmpty()
						|| parent.getFirstSiblingStd() == null || parent.getFirstSiblingStd().trim().isEmpty())) {
            if (parent.getNumberOfSiblings() > 1
                    && (parent.getSecondSiblingName() == null || parent.getSecondSiblingName().trim().isEmpty()
                    || parent.getSecondSiblingStd() == null || parent.getSecondSiblingStd().trim().isEmpty())) {
                logger.error("First and second sibling name and standard are required");
                throw new IllegalArgumentException(
                        "First and second sibling name and standard are required");
            }
			logger.error("First sibling name and standard are required");
			throw new IllegalArgumentException(
					"First sibling name and standard are required");
		}
	}

	@Override
	public List<Parent> findByStudentIds(List<Long> studentIds) {
		return parentRepository.findByStudentIds(studentIds);
	}

	@Override
	public Optional<Parent> findByStudentId(Long studentId) {
		return parentRepository.findByStudentId(studentId);
	}
}
