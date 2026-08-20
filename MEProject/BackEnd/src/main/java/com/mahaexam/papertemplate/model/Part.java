package com.mahaexam.papertemplate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Part {

    private Long id;
    private String name;
    private Boolean displayName;
    private Boolean displaySubject;
    private Integer numberOfSections;
    private Long paperTemplateId;
    private Integer subjectId;  // Changed from Subject object to subjectId

    private String subjectName; // Transient field for response
}
