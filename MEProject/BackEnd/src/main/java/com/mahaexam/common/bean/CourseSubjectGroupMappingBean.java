package com.mahaexam.common.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseSubjectGroupMappingBean {
    private int courseId;
    private List<Long> subjectGroupIds;

}
