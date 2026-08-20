package com.mahaexam.papertemplate.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.mahaexam.common.model.Chapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SyllabusChapter {
	
	   private Long id;
	    private Long syllabusId;
	    private String chapterName;
	    private Long chapterId;
	    private Integer numberOfQuestions;
	    private Integer marks;
	    private BigDecimal coveragePercentage;

}
