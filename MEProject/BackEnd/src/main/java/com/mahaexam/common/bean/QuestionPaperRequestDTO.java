package com.mahaexam.common.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mahaexam.common.config.FlexibleLocalDateTimeDeserializer;
import lombok.Data;


@Data
public class QuestionPaperRequestDTO {

	private Long id;
    private String questionPaperName;
    private List<QuestionPaperTemplateRequestDTO> paperTemplates;
    private String academicYear;
    private String status;

    @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    private String description;
    private LocalDateTime createdAt;
    private QuestionPaperMetaData metaData;
    // getters & setters
}
