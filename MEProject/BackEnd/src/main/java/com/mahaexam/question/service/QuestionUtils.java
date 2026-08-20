package com.mahaexam.question.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.exam.service.QuestionPaperServiceImpl;
import com.mahaexam.question.model.QuestionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;


public class QuestionUtils {
    private static final Logger log = LoggerFactory.getLogger(QuestionUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private QuestionUtils() {
        // Private constructor to prevent instantiation
    }
    public static void setPrefixImageUlr(QuestionEntity q, String cloudFrontDomain) {
        if(Objects.isNull(q)){
            return;
        }
        if(Objects.nonNull(q.getQuestionImageUrl())){
            q.setQuestionImageUrl(cloudFrontDomain + q.getQuestionImageUrl());
        }
        if(Objects.nonNull(q.getAnswerExplanationImageUrl())){
            q.setAnswerExplanationImageUrl(cloudFrontDomain +  q.getAnswerExplanationImageUrl());
        }
        String qOptions = q.getOptions();
        if(Objects.nonNull(qOptions)){
            try {
                Map<String, Object> optionsMap = objectMapper.readValue(qOptions, new TypeReference<Map<String, Object>>() {});
                for (Map.Entry<String, Object> entry : optionsMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.toLowerCase().contains("imageurl")
                            && value instanceof String
                            && !((String) value).isBlank()) {
                        optionsMap.put(key, cloudFrontDomain +  value);
                    }
                }
                q.setOptions(objectMapper.writeValueAsString(optionsMap));
            } catch (JsonProcessingException e) {
                log.error("Error parsing options JSON for question ID {}: {}", q.getId(), e.getMessage());
            }
        }
    }
}
