package com.mahaexam.common.repo;

import java.util.List;

import com.mahaexam.common.model.EducationSociety;

public interface EducationSocietyRepository {

	EducationSociety findById(int id);

	List<EducationSociety> findAll();

	int save(EducationSociety society);

	int update(EducationSociety society);
	
	boolean existsBySocietyName(String societyName);

	boolean existsBySocietyNameExceptId(String societyName, int excludeId);

	void delete(Integer id);
}
