package com.mahaexam.common.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.repo.PackageQuestionPaperRepository;

import java.util.List;

@Service
public class PackageQuestionPaperServiceImpl implements PackageQuestionPaperService {

    @Autowired
    private PackageQuestionPaperRepository repository;

    @Override
    public void addQuestionPapersToPackage(Integer packageId, List<Integer> questionPaperIds, UserBean user) {
        // Remove existing mappings (update scenario)
        repository.deleteByPackageId(packageId);
        Long tenantId = (user != null) ? user.getTenantId() : null;
        // Insert new mappings
        for (Integer paperId : questionPaperIds) {
            repository.saveMapping(packageId, paperId, tenantId);
        }
    }

    @Override
    public List<Integer> getQuestionPapersByPackage(Integer packageId) {
        return repository.findQuestionPapersByPackageId(packageId);
    }

    @Override
    public List<Integer> getPackagesByQuestionPaper(Integer questionPaperId) {
        return repository.findPackagesByQuestionPaperId(questionPaperId);
    }
}
