package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassesDeleteBean {
    private List<Integer> idsToDelete;
}