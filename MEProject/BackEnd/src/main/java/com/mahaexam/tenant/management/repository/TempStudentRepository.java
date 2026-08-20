package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.TempStudent;

import java.util.List;
import java.util.Optional;

public interface TempStudentRepository {

    TempStudent save(TempStudent tempStudent);

    List<TempStudent> saveAll(List<TempStudent> tempStudents);

    int[] batchInsert(List<TempStudent> tempStudents);

    Optional<TempStudent> findById(Long id);

    List<TempStudent> findByBatchId(Long batchId);

    List<TempStudent> findByReferenceId(Long referenceId);

    List<TempStudent> findByPackageId(Integer packageId);

    List<TempStudent> findValidStudents(Long referenceId);

    List<TempStudent> findInvalidStudents(Long referenceId);

    List<TempStudent> findValidStudentsByBatchId(Long batchId);

    List<TempStudent> findInvalidStudentsByBatchId(Long batchId);

    // Fetch valid students for multiple batch IDs in a single query
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
     * @param updates List of TempStudent objects containing studentId, mobileNumber, and email
     * @return array of update counts for each update
     */
    int[] batchUpdateStudentId(List<TempStudent> updates);
}
