package com.mahaexam.tenant.management.controller;

import java.util.List;

import com.mahaexam.tenant.management.bean.CountBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.PaginatedResponse;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.tenant.management.bean.StudentDetailsBean;
import com.mahaexam.tenant.management.bean.StudentRegistrationBean;
import com.mahaexam.tenant.management.bean.StudentSerchBean;
import com.mahaexam.tenant.management.model.Student;
import com.mahaexam.tenant.management.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentRegistrationController extends BaseController{
	private static final Logger logger = LogManager.getLogger(StudentRegistrationController.class);
	private final StudentService studentService;

	public StudentRegistrationController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping("/register")
	public ResponseEntity<SuccessResponseBean> registerStudent(@Valid @RequestBody StudentRegistrationBean registrationDTO) {
		try {
			studentService.registerStudent(registrationDTO,true,true,false);
			return ResponseEntity.status(HttpStatus.OK)
					.body(SuccessResponseBean.builder().status("success").message(
							"Student registered successfully with user ID: " + registrationDTO.getUserId())
							.build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	 // 1. Get all students by referral user ID
    @GetMapping("/referralstudent/{studentReferralId}")
    public ResponseEntity<List<Student>> getAllStudentsByReferralId(
            @PathVariable Long studentReferralId) {
    	UserBean userbean = getUser();
        List<Student> students = studentService.getAllStudentsByRefferalUsreId(userbean.getUserId());
        if (students.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(students);
    }

    // 2. Get student count referred by given user ID
        @GetMapping("/referral-count/{studentReferralId}")
    public ResponseEntity<CountBean> getStudentCountByReferralId(
            @PathVariable Long studentReferralId) {
    	UserBean userbean = getUser();
    	Integer count = studentService.getStudentCountRefferedByGivenUserId(userbean.getUserId());
        return ResponseEntity.ok(CountBean.builder().count(count).build());
    }

    // 3. Get total student count
    @GetMapping("/studentscount")
    public ResponseEntity<Integer> getTotalStudentCount() {
        UserBean userbean = getUser();
        Integer totalCount = studentService.getAllStudentsCount(userbean);
        return ResponseEntity.ok(totalCount);
    }
    @GetMapping("/findAllStudents")
    public ResponseEntity<PaginatedResponse<StudentDetailsBean>> findAllStudents() {
    	UserBean userbean = getUser();
//        List<Student> students = studentService.getAllStudentsByRefferalUsreId(userbean.getTenantId());
    	StudentSerchBean studentSearchBean = new StudentSerchBean();
    	PaginatedResponse<StudentDetailsBean> students = studentService.search(userbean, studentSearchBean);
       
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}