package com.mahaexam.papertemplate.bean;

import java.util.List;

import lombok.Data;

@Data
public class CreateSyllabusRequestDTO {

	private Long classId;
	private Long subjectId;
	private Long boardId;
	private String medium;
	private Integer academicYear;

	private List<SyllabusChapterDTO> chapters;

}
