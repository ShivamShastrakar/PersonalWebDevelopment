package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.ClassBean;
import com.mahaexam.common.bean.ClassesDeleteBean;
import com.mahaexam.common.model.ClassEntity;

public interface ClassService {
    List<ClassEntity> getAllClassesByTenant(Long tenantId);
    ClassEntity getClassById(int id);
    int createClass(ClassEntity clazz);
    int updateClass(ClassEntity clazz);
    int[] deleteClass(ClassesDeleteBean deleteBean);
	List<ClassBean> findAllByPackageIds(List<Integer> packageIds);
    Optional<ClassEntity> findClassByName(String className);
}