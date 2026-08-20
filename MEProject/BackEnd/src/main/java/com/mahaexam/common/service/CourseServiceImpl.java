package com.mahaexam.common.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mahaexam.common.repo.CourseClassMappingRepository;
import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.CourseBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Course;
import com.mahaexam.common.repo.CourseRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
    private final CourseSubjectGroupMappingService  courseSubjectGroupMappingService;
	private final CourseClassMappingRepository courseClassMappingRepository;
	public CourseServiceImpl(CourseRepository courseRepository, CourseSubjectGroupMappingService courseSubjectGroupMappingService,
							 CourseClassMappingRepository courseClassMappingRepository) {
		this.courseClassMappingRepository = courseClassMappingRepository;
		this.courseRepository = courseRepository;
        this.courseSubjectGroupMappingService = courseSubjectGroupMappingService;
	}

	@Override
	public List<Course> getAllCoursesByTenant(Long tenantId) {
		return courseRepository.findAllByTenant(tenantId);
	}

	@Override
	public Course getCourseById(int id) {
        Course course = courseRepository.findById(id);
        if (course != null) {
            List<Integer> classIds = courseClassMappingRepository.findClassIdsByCourseId(course.getId());
            course.setClassIds(classIds);
        }
        return course;
	}

	@Override
    @Transactional
	public int createCourse(Course course,List<Long> subjectGroupIds) {
		if (courseRepository.existsByCourseNameAndTenantId(course.getCourseName(), course.getTenantId())) {
			throw new ValidationException("Course name already exists for this tenant.");
		}
        int courseId = courseRepository.save(course);
		if(Objects.nonNull(subjectGroupIds) && !subjectGroupIds.isEmpty()) {
			courseSubjectGroupMappingService.saveMappingsForCourse(courseId, subjectGroupIds);
		}
		courseClassMappingRepository.saveMappingsForCourse(courseId, course.getClassIds());
		return courseId;
	}

	@Override
	public int updateCourse(Course course) {
		if (courseRepository.existsByCourseNameAndTenantIdExceptId(course.getCourseName(), course.getTenantId(),
				course.getId())) {
			throw new ValidationException("Course name already exists for this tenant.");
		}
		courseClassMappingRepository.deleteMappingsForCourse(course.getId());
		courseClassMappingRepository.saveMappingsForCourse(course.getId(), course.getClassIds());
		return courseRepository.update(course);
	}

	@Override
	public int deleteCourse(int id) {
		return courseRepository.softDelete(id);
	}

	@Override
	public List<CourseBean> findAllByPackageIds(List<Integer> packageIds) {
		List<Course> courses = courseRepository.findAllByPackageIds(packageIds);
		return courses.stream().map(this::toBean).collect(Collectors.toList());
	}

    @Override
    public boolean existsByCourseNameAndTenantId(String courseName, Long tenantId) {
        return courseRepository.existsByCourseNameAndTenantId(courseName, tenantId);
    }

    @Override
    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public List<Course> findByNames(List<String> coursesNames) {
        return courseRepository.findByNames(coursesNames);
    }

    @Override
    public List<Course> getAllCoursesByTenantAndClassId(Long tenantId, Integer classId) {
        // You need to implement this in CourseRepository as well
        return courseRepository.findAllByTenantAndClassId(tenantId, classId);
    }

	public CourseBean toBean(Course course) {
		if (course == null) {
			return null;
		}
		CourseBean bean = new CourseBean();
		bean.setCourseName(course.getCourseName());
		bean.setCourseDetails(course.getCourseDetails());
		bean.setTenantId(course.getTenantId());
		bean.setUpdatedBy(course.getUpdatedBy());
		bean.setDeleted(course.getDeleted());
		bean.setPackageId(course.getPackageId());
		bean.setClassIds(course.getClassIds()); // Set class IDs to bean
		return bean;
	}

	public Course toModel(CourseBean bean) {
		if (bean == null) {
			return null;
		}
		Course course = new Course();
		course.setCourseName(bean.getCourseName());
		course.setCourseDetails(bean.getCourseDetails());
		course.setTenantId(bean.getTenantId());
		course.setUpdatedBy(bean.getUpdatedBy());
		course.setDeleted(bean.getDeleted());
		course.setPackageId(bean.getPackageId());
		course.setClassIds(bean.getClassIds()); // Set class IDs from bean
		// Fields like id, createdAt, updatedAt, deletedAt are not set as they are not
		// in CourseBean
		return course;
	}



}
