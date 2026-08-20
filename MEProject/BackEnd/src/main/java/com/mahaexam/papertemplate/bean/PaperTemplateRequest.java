package com.mahaexam.papertemplate.bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.mahaexam.papertemplate.model.PaperTemplate;
import com.mahaexam.question.model.QuestionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaperTemplateRequest {
	    private PaperTemplate paperTemplate;
	    private List<PartRequest> parts;
}
