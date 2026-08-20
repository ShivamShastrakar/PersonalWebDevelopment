package com.mahaexam.papertemplate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Syllabus {

	private Long id;
    private Long classId;
    private Long subjectId;
    private Long boardId;
    private String medium;
    private Integer academicYear;
    private String status;
    private Long tenantId;
    private Long createdBy;
    private Long updatedBy;
    private String name;

    // Additional attributes for joined queries
    private String boardName;
    private String className;
    private String subjectName;

    // Syllabus chapters
    private List<SyllabusChapter> chapters;

}
