package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpcomingExamResponse {
    private Long questionPaperId;
    private String questionPaperName;
    private Integer totalDuration;       // minutes
    private Integer examTotalMarks;
    private Integer examTotalQuestions;
    private LocalDateTime startDate;     // exam window open date
    private LocalDateTime endDate;
}
