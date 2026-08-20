package com.mahaexam.exam.controller;

import java.util.List;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.QuestionPaperRequestDTO;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.exam.service.QuestionPaperService;

@RestController
@RequestMapping("/api/question-papers")
public class QuestionPaperController extends BaseController {

    private final QuestionPaperService questionPaperService;

    public QuestionPaperController(QuestionPaperService questionPaperService) {
        this.questionPaperService = questionPaperService;
    }

    @PostMapping
    public QuestionPaperResponseDTO createQuestionPaper(
            @RequestBody QuestionPaperRequestDTO request) {
        UserBean user = getUser();
        return questionPaperService.createQuestionPaper(request, user);
    }

    @GetMapping("/{id}")
    public QuestionPaperResponseDTO getQuestionPaper(
            @PathVariable Long id) {
        return questionPaperService.getQuestionPaperById(id,true, 0, 0);//Fetch all  without questions
    }

    @GetMapping("/{id}/exam")
    public QuestionPaperResponseDTO getQuestionPaperForExam(
            @PathVariable Long id) {
        return questionPaperService.getQuestionPaperHierarchyById(id, true, getUser());
    }

    @GetMapping
    public List<QuestionPaperResponseDTO> getAllQuestionPapers(
            @RequestParam(required = false) Long boardId,
            @RequestParam(required = false) Integer classId) {
        UserBean user = getUser();
        if (user != null && user.getTenantId() != null) {
            if (boardId != null || classId != null) {
                return questionPaperService.getAllQuestionPapersByTenantAndFilter(user.getTenantId(), boardId, classId);
            }
            return questionPaperService.getAllQuestionPapersByTenant(user.getTenantId());
        }
        return questionPaperService.getAllQuestionPapers();
    }
    
    @GetMapping("/class/{classId}")
    public List<QuestionPaperResponseDTO> getQuestionPapersByClass(@PathVariable Integer classId) {
        return questionPaperService.getQuestionPapersByClass(classId);
    }


    @PatchMapping("/{id}/status")
    public void updateStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {

        questionPaperService.updateQuestionPaperStatus(id, active);
    }
}
