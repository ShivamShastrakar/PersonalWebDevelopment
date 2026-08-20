package com.mahaexam.tenant.management.repository;

import java.util.List;

import com.mahaexam.tenant.management.model.AcademicExperience;

public interface AcademicExperienceRepository {
	
	AcademicExperience createAcademicExperience(AcademicExperience academicExperience);
    AcademicExperience updateAcademicExperience(Long academicId, AcademicExperience academicExperience);
    void deleteAcademicExperience(Long academicId);
    List<AcademicExperience> getAcademicExperiencesByUserId(Long userId);


}
