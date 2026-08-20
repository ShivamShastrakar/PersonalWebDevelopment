package com.mahaexam.tenant.management.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.service.BoardService;
import com.mahaexam.common.service.ClassService;
import com.mahaexam.common.service.SubjectService;
import com.mahaexam.tenant.management.bean.AcademicExperienceBean;
import com.mahaexam.tenant.management.model.AcademicExperience;
import com.mahaexam.tenant.management.repository.AcademicExperienceRepository;
import com.mahaexam.tenant.management.repository.StudentRepository;

@Service
public class AcademicExperienceServiceImpl implements AcademicExperienceService {

    private AcademicExperienceRepository academicExperienceRepository;
	private final ApplicationUserService applicationUserService;
	private final ClassService classService;
	private final SubjectService subjectService;
	private final BoardService boardService;
	private final UserService userService;
	private final UserTenantService userTenantService;
	private final RoleService roleService;
	private final OtpAuthServiceImpl otpAuthServiceImpl;
	
	public AcademicExperienceServiceImpl(AcademicExperienceRepository academicExperienceRepository, ApplicationUserService applicationUserService,
			ClassService classService, SubjectService subjectService,
			BoardService boardService, UserService userService, UserTenantService userTenantService,
			RoleService roleService, OtpAuthServiceImpl otpAuthServiceImpl) {
		this.academicExperienceRepository = academicExperienceRepository;
		this.applicationUserService = applicationUserService;
		this.classService = classService;
		this.subjectService = subjectService;
		this.boardService = boardService;
		this.userService = userService;
		this.userTenantService = userTenantService;
		this.roleService = roleService;
		this.otpAuthServiceImpl = otpAuthServiceImpl;
	}

	@Override
    @Transactional
    public AcademicExperience createAcademicExperience(AcademicExperienceBean academicExperienceBean) {
        AcademicExperience academicExperience = mapToEntity(academicExperienceBean);
       // validateAcademicExperience(academicExperience);
        return academicExperienceRepository.createAcademicExperience(academicExperience);
    }

	private AcademicExperience mapToEntity(AcademicExperienceBean academicExperienceBean) {
		// TODO Auto-generated method stub
		 AcademicExperience academicExperience = new AcademicExperience();
		 academicExperience.setUserId(academicExperienceBean.getUserId());
		 academicExperience.setBoardId(academicExperienceBean.getBoardId());
		 academicExperience.setClassId(academicExperienceBean.getClassId());
		 academicExperience.setSubjectId(academicExperienceBean.getSubjectId());
		 academicExperience.setChapters(academicExperienceBean.getChapters());
		return academicExperience;
	}

	@Override
	public AcademicExperience updateAcademicExperience(Long academicId, AcademicExperienceBean academicExperienceBean) {
		AcademicExperience academicExperience = mapToEntity(academicExperienceBean);
		return academicExperienceRepository.updateAcademicExperience(academicId, academicExperience);
	}

	@Override
	public void deleteAcademicExperience(Long academicId) {
		academicExperienceRepository.deleteAcademicExperience(academicId);
		
	}

	@Override
	public List<AcademicExperience> getAcademicExperiencesByUserId(Long userId) {
		return academicExperienceRepository.getAcademicExperiencesByUserId(userId);
	}
}