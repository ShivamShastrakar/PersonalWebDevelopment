package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mahaexam.common.bean.ClassesDeleteBean;
import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.ClassBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.repo.ClassRepository;

@Service
public class ClassServiceImpl implements ClassService {

	private final ClassRepository classRepository;

	public ClassServiceImpl(ClassRepository classRepository) {
		this.classRepository = classRepository;
	}

	@Override
	public List<ClassEntity> getAllClassesByTenant(Long tenantId) {
		return classRepository.findAllByTenant(tenantId);
	}

	@Override
	public ClassEntity getClassById(int id) {
		return classRepository.findById(id);
	}

	@Override
	public int createClass(ClassEntity clazz) {
		if (classRepository.existsByClassNameAndTenantId(clazz.getClassName(), clazz.getTenantId())) {
			throw new ValidationException("Class name already exists.");
		}
		return classRepository.save(clazz);
	}

	@Override
	public int updateClass(ClassEntity clazz) {
		if (classRepository.existsByClassNameAndTenantIdExceptId(clazz.getClassName(), clazz.getTenantId(),
				clazz.getId())) {
			throw new ValidationException("Class name already exists.");
		}
		return classRepository.update(clazz);
	}

	@Override
	public int[] deleteClass(ClassesDeleteBean deleteBean) {
		return classRepository.softDelete(deleteBean);
	}

	@Override
	public List<ClassBean> findAllByPackageIds(List<Integer> packageIds) {
		List<ClassEntity> classEntities = classRepository.findAllByPackageIds(packageIds);
		return classEntities.stream().map(this::toBean).collect(Collectors.toList());
	}

    @Override
    public Optional<ClassEntity> findClassByName(String className) {
        return classRepository.findClassByName(className);
    }

    public ClassBean toBean(ClassEntity entity) {
		if (entity == null) {
			return null;
		}
		ClassBean bean = new ClassBean();
		bean.setId(entity.getId());
		bean.setClassName(entity.getClassName());
		bean.setTenantId(entity.getTenantId());
		bean.setDeleted(entity.getDeleted());
		bean.setPackageId(entity.getPackageId());
		return bean;
	}

	public ClassEntity toEntity(ClassBean bean) {
		if (bean == null) {
			return null;
		}
		ClassEntity entity = new ClassEntity();
		entity.setId(bean.getId());
		entity.setClassName(bean.getClassName());
		entity.setTenantId(bean.getTenantId());
		entity.setDeleted(bean.getDeleted());
		entity.setPackageId(bean.getPackageId());
		// Fields like id, createdAt, updatedAt, deletedAt are not set as they are not
		// in ClassBean
		return entity;
	}
}
