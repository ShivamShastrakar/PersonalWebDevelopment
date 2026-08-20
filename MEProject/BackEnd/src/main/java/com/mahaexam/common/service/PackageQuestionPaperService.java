package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.bean.UserBean;

public interface PackageQuestionPaperService {

    void addQuestionPapersToPackage(Integer packageId, List<Integer> questionPaperIds,UserBean User);

    List<Integer> getQuestionPapersByPackage(Integer packageId);

    List<Integer> getPackagesByQuestionPaper(Integer questionPaperId);
}
