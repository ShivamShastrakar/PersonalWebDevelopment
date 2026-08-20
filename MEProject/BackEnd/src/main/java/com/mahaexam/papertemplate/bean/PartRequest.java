package com.mahaexam.papertemplate.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.mahaexam.common.model.Section;
import com.mahaexam.papertemplate.model.Part;
import com.mahaexam.question.model.QuestionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PartRequest {
    private Part part;
    private List<Section> sections;
}