package com.mahaexam.tenant.management.service;

import java.util.List;

import com.mahaexam.tenant.management.bean.AcademicExperienceBean;
import com.mahaexam.tenant.management.model.AcademicExperience;

public interface AcademicExperienceService {
	AcademicExperience createAcademicExperience(AcademicExperienceBean academicExperienceDTO);
    AcademicExperience updateAcademicExperience(Long academicId, AcademicExperienceBean academicExperienceDTO);
    void deleteAcademicExperience(Long academicId);
    List<AcademicExperience> getAcademicExperiencesByUserId(Long userId);
    
}
