package com.mahaexam.common.bean;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectGroupMappingBean {
    private int groupId;
    private int subjectId;
}