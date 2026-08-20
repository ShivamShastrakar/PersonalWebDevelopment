package com.mahaexam.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseClassMapping {
    private Integer id;
    private Integer courseId;
    private Integer classId;
}

