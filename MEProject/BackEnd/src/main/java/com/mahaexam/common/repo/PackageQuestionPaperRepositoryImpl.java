package com.mahaexam.common.repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PackageQuestionPaperRepositoryImpl implements PackageQuestionPaperRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_MAPPING =
            "INSERT INTO package_question_paper_mapping (package_id, question_paper_id,tenant_id) VALUES (?, ?, ?)";

    private static final String DELETE_BY_PACKAGE =
            "DELETE FROM package_question_paper_mapping WHERE package_id = ?";

    private static final String FIND_PAPERS_BY_PACKAGE =
            "SELECT question_paper_id FROM package_question_paper_mapping WHERE package_id = ?";

    private static final String FIND_PACKAGES_BY_PAPER =
            "SELECT package_id FROM package_question_paper_mapping WHERE question_paper_id = ?";

    @Override
    public int saveMapping(Integer packageId, Integer questionPaperId, Long tenantId) {
        return jdbcTemplate.update(INSERT_MAPPING, packageId, questionPaperId, tenantId);
    }

    @Override
    public int deleteByPackageId(Integer packageId) {
        return jdbcTemplate.update(DELETE_BY_PACKAGE, packageId);
    }

    @Override
    public List<Integer> findQuestionPapersByPackageId(Integer packageId) {
        return jdbcTemplate.queryForList(FIND_PAPERS_BY_PACKAGE, Integer.class, packageId);
    }

    @Override
    public List<Integer> findPackagesByQuestionPaperId(Integer questionPaperId) {
        return jdbcTemplate.queryForList(FIND_PACKAGES_BY_PAPER, Integer.class, questionPaperId);
    }
}
