package com.mahaexam.tenant.management.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mahaexam.packagemanagment.bean.PackageBean;
import com.mahaexam.tenant.management.model.StudentCourse;
import com.mahaexam.tenant.management.model.StudentSubjectGroup;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDetailsBean extends StudentRegistrationBean {
	
    private Long studentId;
    private Long applicationUserId;
    private Integer currentClassId;
    private Integer currentSubjectGroupId;
    private Long studentReferenceId;
    private String className;
    private String groupName;
    private AddressBean address;
    private ParentBean parent;
    
    List<StudentCourse> studentCourses;
    List<StudentSubjectGroup> studentSubjectGroups;
    
    private String photoImg;
    private String photoUrl;

    List<PackageBean> studentPackages;

    private java.time.LocalDateTime createdAt;
    private Integer boardId;
    private String schoolName;
    private String schoolAddress;
    private String category;
    private String instituteName;
    private String parallelReservation;

}