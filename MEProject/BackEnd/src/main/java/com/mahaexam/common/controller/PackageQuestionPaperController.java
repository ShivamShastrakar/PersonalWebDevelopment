package com.mahaexam.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.service.PackageQuestionPaperService;
import com.mahaexam.packagemanagment.bean.PackageBean;
import com.mahaexam.packagemanagment.service.PackageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/package-question-paper")
public class PackageQuestionPaperController  extends BaseController {

    @Autowired
    private PackageQuestionPaperService service;
    
    @Autowired
    private PackageService packageService; 

    // Associate multiple question papers to a package
    @PostMapping("/{packageId}")
    public String mapQuestionPapersToPackage(
            @PathVariable Integer packageId,
            @RequestBody List<Integer> questionPaperIds) {
    	UserBean user = getUser();
        service.addQuestionPapersToPackage(packageId, questionPaperIds, user);
        Optional<PackageBean> packageBean =  packageService.getPackageById(packageId, false);
        return "Question papers mapped successfully to package" + packageBean.get().getPackageName();
    }

    // Get all question papers for a package
    @GetMapping("/package/{packageId}")
    public List<Integer> getQuestionPapersByPackage(@PathVariable Integer packageId) {
        return service.getQuestionPapersByPackage(packageId);
    }

    // Get all packages for a question paper
    @GetMapping("/question-paper/{questionPaperId}")
    public List<Integer> getPackagesByQuestionPaper(@PathVariable Integer questionPaperId) {
        return service.getPackagesByQuestionPaper(questionPaperId);
    }
}
