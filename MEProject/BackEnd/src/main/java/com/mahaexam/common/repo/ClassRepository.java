package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.ClassesDeleteBean;
import com.mahaexam.common.model.ClassEntity;

public interface ClassRepository {
    List<ClassEntity> findAllByTenant(Long tenantId);
    ClassEntity findById(int id);
    int save(ClassEntity clazz);
    int update(ClassEntity clazz);
    int[] softDelete(ClassesDeleteBean deleteBean);
    boolean existsByClassNameAndTenantId(String className, Long tenantId);
    boolean existsByClassNameAndTenantIdExceptId(String className, Long tenantId, int excludeId);
	List<ClassEntity> findAllByPackageIds(List<Integer> packageIds);

    Optional<ClassEntity> findClassByName(String className);

    List<ClassEntity> findByBoardAndTenant(Integer boardId, Long tenantId);

}