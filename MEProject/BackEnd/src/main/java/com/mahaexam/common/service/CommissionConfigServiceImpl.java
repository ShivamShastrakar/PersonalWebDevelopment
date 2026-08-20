package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.model.CommissionConfigRequest;
import com.mahaexam.common.repo.CommissionConfigRepository;

@Service
public class CommissionConfigServiceImpl implements CommissionConfigService {
	private final CommissionConfigRepository repository;

    public CommissionConfigServiceImpl(CommissionConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public CommissionConfigRequest create(CommissionConfigRequest request) {
        if (request.getPackageType() == null) {
            request.setPackageType("Test");
        }
        validateRequest(request);

        Long configId = repository.insertConfig(request);
        repository.insertSlabs(configId, request.getSlabs());

        return repository.findById(configId)
                .orElseThrow(() -> new IllegalStateException("Config not found after insert"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public CommissionConfigRequest update(Long id, CommissionConfigRequest request) {
        request.setId(id);
        if (request.getPackageType() == null) {
            request.setPackageType("Test");
        }
        validateRequest(request);

        repository.updateConfig(request);
        repository.deleteSlabsByConfigId(id);
        repository.insertSlabs(id, request.getSlabs());

        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Config not found: " + id));
    }

    public List<CommissionConfigRequest> list(Integer hierarchyLevelId,
                                               String packageType,
                                               Boolean active) {
        return repository.findConfigs(hierarchyLevelId, packageType, active);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void deactivate(Long id) {
        CommissionConfigRequest req = new CommissionConfigRequest();
        repository.findById(id).ifPresent(existing -> {
            req.setId(existing.getId());
            req.setHierarchyLevelId(existing.getHierarchyLevelId());
            req.setPackageType(existing.getPackageType());
            req.setCommissionType(existing.getCommissionType());
            req.setPackageCategoryId(existing.getPackageCategoryId());
            req.setExamGroupId(existing.getExamGroupId());
            req.setActive(false);
            repository.updateConfig(req);
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commission config not found for id: " + id));
        // Delete child slabs first, then the parent config
        repository.deleteSlabsByConfigId(id);
        repository.deleteConfigById(id);
    }

    private void validateRequest(CommissionConfigRequest req) {
        // same rule set as described earlier:
        // - non-null role, package, commissionType
        // - slabs not empty
        // - every slab has valid range and exactly one of percentage/amount
        // - no overlapping slabs
        // etc.
    }

    @Override
    public Optional<CommissionConfigRequest> getCommissionSlabsByRoleId(Integer roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public Optional<CommissionConfigRequest> getCommissionSlabsByHierarchyLevelId(Long hierarchyLevelId) {
        return repository.findByHierarchyLevelId(hierarchyLevelId);
    }

}
