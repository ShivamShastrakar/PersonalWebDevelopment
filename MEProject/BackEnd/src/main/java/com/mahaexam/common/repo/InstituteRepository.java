package com.mahaexam.common.repo;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.Institute;

public interface InstituteRepository {
    int save(Institute institute);
    Institute findById(int id);
    List<Institute> findAll();
    int update(Institute institute);
    int delete(int id);
    boolean existsByInstituteName(String instituteName);
    boolean existsByInstituteNameExceptId(String instituteName, int excludeId);
	Optional<Institute> searchByIndexNumber(String indexNumber);
}