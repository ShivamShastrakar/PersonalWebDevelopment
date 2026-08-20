package com.mahaexam.packagemanagment.repository;


import com.mahaexam.packagemanagment.model.StudentPackageSelectionSummary;

public interface StudentPackageSelectionSummaryRepository {
    StudentPackageSelectionSummary save(StudentPackageSelectionSummary summary);
    void updateStatus(Long selectionSummaryId, String status);
}