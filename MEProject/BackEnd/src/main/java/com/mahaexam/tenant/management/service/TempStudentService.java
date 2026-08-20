package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.bean.StudentDataLoadBean;
import com.mahaexam.tenant.management.model.TempStudent;

import java.util.List;
import java.util.Optional;

public interface TempStudentService {

    TempStudent save(TempStudent tempStudent);

    List<TempStudent> saveAll(List<TempStudent> tempStudents);

    int[] batchInsert(List<TempStudent> tempStudents);

    Optional<TempStudent> findById(Long id);

    List<TempStudent> findByBatchId(Long batchId);

    List<TempStudent> findByReferenceId(Long referenceId);

    List<TempStudent> findByPackageId(Integer packageId);

    List<TempStudent> findStudentsByBatchIds(Long referenceId);

    List<TempStudent> findInvalidStudents(Long referenceId);

    List<TempStudent> findValidStudentsByBatchId(Long batchId);

    List<TempStudent> findInvalidStudentsByBatchId(Long batchId);

    // Fetch valid students for multiple batch ids
    List<TempStudent> findStudentsByBatchIds(List<Long> batchIds);

    void deleteByReferenceId(Long referenceId);

    void deleteByBatchId(Long batchId);

    void deleteById(Long id);

    int countByReferenceId(Long referenceId);

    int countByBatchId(Long batchId);

    int countValidByReferenceId(Long referenceId);

    int countInvalidByReferenceId(Long referenceId);

    int countValidByBatchId(Long batchId);

    int countInvalidByBatchId(Long batchId);

    /**
     * Batch update studentId in temp_students by matching mobile_number and email
     * @param validStudentEntities List of TempStudent objects containing studentId, mobileNumber, and email
     * @return array of update counts for each update
     */
    int[] batchUpdateStudentId(List<StudentDataLoadBean> validStudentEntities);

    // Business logic methods

    /**
     * Process validation results by storing them in temp table
     */
    void storeValidationResults(Long batchId, List<TempStudent> validStudents, List<TempStudent> invalidStudents);

    /**
     * Get validation statistics for a batch
     */
    ValidationStats getValidationStats(Long batchId);

    /**
     * Convert validated temp students to registration format for final processing
     */
    List<TempStudent> getStudentsReadyForRegistration(Long batchId);

    /**
     * Clean up temp data after processing
     */
    void cleanupAfterProcessing(Long batchId);

    /**
     * Validation statistics inner class
     */
    record ValidationStats(
        int totalCount,
        int validCount,
        int invalidCount,
        double validPercentage
    ) {}
}
