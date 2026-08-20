package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.bean.StudentDataLoadBean;
import com.mahaexam.tenant.management.model.TempStudent;
import com.mahaexam.tenant.management.repository.TempStudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TempStudentServiceImpl implements TempStudentService {

    private final TempStudentRepository tempStudentRepository;

    public TempStudentServiceImpl(TempStudentRepository tempStudentRepository) {
        this.tempStudentRepository = tempStudentRepository;
    }

    @Override
    public TempStudent save(TempStudent tempStudent) {
        return tempStudentRepository.save(tempStudent);
    }

    @Override
    public List<TempStudent> saveAll(List<TempStudent> tempStudents) {
        return tempStudentRepository.saveAll(tempStudents);
    }

    @Override
    public int[] batchInsert(List<TempStudent> tempStudents) {
        return tempStudentRepository.batchInsert(tempStudents);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TempStudent> findById(Long id) {
        return tempStudentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findByBatchId(Long batchId) {
        return tempStudentRepository.findByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findByReferenceId(Long referenceId) {
        return tempStudentRepository.findByReferenceId(referenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findByPackageId(Integer packageId) {
        return tempStudentRepository.findByPackageId(packageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findStudentsByBatchIds(Long referenceId) {
        return tempStudentRepository.findValidStudents(referenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findInvalidStudents(Long referenceId) {
        return tempStudentRepository.findInvalidStudents(referenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findValidStudentsByBatchId(Long batchId) {
        return tempStudentRepository.findValidStudentsByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findInvalidStudentsByBatchId(Long batchId) {
        return tempStudentRepository.findInvalidStudentsByBatchId(batchId);
    }

    @Override
    public void deleteByReferenceId(Long referenceId) {
        tempStudentRepository.deleteByReferenceId(referenceId);
    }

    @Override
    public void deleteByBatchId(Long batchId) {
        tempStudentRepository.deleteByBatchId(batchId);
    }

    @Override
    public void deleteById(Long id) {
        tempStudentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int countByReferenceId(Long referenceId) {
        return tempStudentRepository.countByReferenceId(referenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public int countByBatchId(Long batchId) {
        return tempStudentRepository.countByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public int countValidByReferenceId(Long referenceId) {
        return tempStudentRepository.countValidByReferenceId(referenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public int countInvalidByReferenceId(Long referenceId) {
        return tempStudentRepository.countInvalidByReferenceId(referenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public int countValidByBatchId(Long batchId) {
        return tempStudentRepository.countValidByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public int countInvalidByBatchId(Long batchId) {
        return tempStudentRepository.countInvalidByBatchId(batchId);
    }

    @Override
    public int[] batchUpdateStudentId(List<StudentDataLoadBean> validStudentEntities ) {
        List<TempStudent> updates = validStudentEntities.stream().map(bean -> {
            TempStudent tempStudent = new TempStudent();
            tempStudent.setStudentId(bean.getStudentId());
            tempStudent.setMobileNumber(bean.getMobileNumber());
            tempStudent.setEmail(bean.getEmail());
            tempStudent.setBatchId(bean.getBatchId());
            return tempStudent;
        }).toList();
        return tempStudentRepository.batchUpdateStudentId(updates);
    }

    @Override
    public void storeValidationResults(Long batchId, List<TempStudent> validStudents, List<TempStudent> invalidStudents) {
        // Set batch ID for all students
        validStudents.forEach(student -> student.setBatchId(batchId));
        invalidStudents.forEach(student -> student.setBatchId(batchId));

        // Combine all students for batch insert
        List<TempStudent> allStudents = new java.util.ArrayList<>(validStudents);
        allStudents.addAll(invalidStudents);

        // Use batch insert for performance
        batchInsert(allStudents);
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationStats getValidationStats(Long batchId) {
        int totalCount = countByBatchId(batchId);
        int validCount = countValidByBatchId(batchId);
        int invalidCount = countInvalidByBatchId(batchId);

        double validPercentage = totalCount > 0 ? (double) validCount / totalCount * 100 : 0.0;

        return new ValidationStats(totalCount, validCount, invalidCount, validPercentage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> getStudentsReadyForRegistration(Long batchId) {
        return findValidStudentsByBatchId(batchId);
    }

    @Override
    public void cleanupAfterProcessing(Long batchId) {
        deleteByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TempStudent> findStudentsByBatchIds(List<Long> batchIds) {
        if (batchIds == null || batchIds.isEmpty()) return List.of();
        // Delegate to repository single-query implementation
        return tempStudentRepository.findStudentsByBatchIds(batchIds);
    }
}
