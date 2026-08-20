package com.mahaexam.tenant.management.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentJourneyBean {
    private String groupName;
    private Integer totalExams;
    private Integer completedExams;
    private Double completionRate;
}
