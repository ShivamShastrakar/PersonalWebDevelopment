package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.StudentPackageSelection;

import java.util.List;

public interface StudentPackageSelectionRepository {
    StudentPackageSelection save(StudentPackageSelection selection);

    List<StudentPackageSelection> save(List<StudentPackageSelection> selections);

    ;

    List<StudentPackageSelection> findBySelectionSummaryId(Long selectionSummaryId);

    StudentPackageSelection findById(Long selectionId);

    List<StudentPackageSelection> findByInvoiceNumber(String invoiceNumber);
}
