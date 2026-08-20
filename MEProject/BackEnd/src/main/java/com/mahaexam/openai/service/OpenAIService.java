package com.mahaexam.openai.service;

import com.mahaexam.openai.model.QuestionMetadata;
import com.mahaexam.openai.model.QuestionSet;

public interface OpenAIService {
    String sendPrompt(String prompt);
    String sendPrompt(String prompt, int maxCompletionTokens);
    public QuestionSet createQuestionSet(String openAIResponse, QuestionMetadata metadata);
}
