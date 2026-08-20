package com.mahaexam.common.repo;

import java.util.List;

public interface PackageQuestionPaperRepository {

    int saveMapping(Integer packageId, Integer questionPaperId, Long tenantId);

    int deleteByPackageId(Integer packageId);

    List<Integer> findQuestionPapersByPackageId(Integer packageId);

    List<Integer> findPackagesByQuestionPaperId(Integer questionPaperId);
}
