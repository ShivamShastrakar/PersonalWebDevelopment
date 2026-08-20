package com.mahaexam.packagemanagment.service;

import com.mahaexam.packagemanagment.bean.StudentPackageSelectionBean;
import com.mahaexam.packagemanagment.bean.StudentPackageSelectionSummaryBean;
import com.mahaexam.packagemanagment.model.StudentPackageSelection;
import com.mahaexam.packagemanagment.model.StudentPackageSelectionSummary;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentPackageSelectionConverter {

    // Convert StudentPackageSelection to StudentPackageSelectionBean
    public static StudentPackageSelectionBean toBean(StudentPackageSelection selection) {
        if (selection == null) {
            return null;
        }
        StudentPackageSelectionBean bean = new StudentPackageSelectionBean();
        bean.setSelectionId(selection.getSelectionId());
        bean.setPackageId(selection.getPackageId());
        bean.setStudentId(selection.getStudentId());
        bean.setSelectionSummaryId(selection.getSelectionSummaryId());
        bean.setAmount(selection.getAmount());
        bean.setSelectedAt(selection.getSelectedAt());
        return bean;
    }

    // Convert StudentPackageSelectionBean to StudentPackageSelection
    public static StudentPackageSelection toEntity(StudentPackageSelectionBean bean) {
        if (bean == null) {
            return null;
        }
        StudentPackageSelection selection = new StudentPackageSelection();
        selection.setSelectionId(bean.getSelectionId());
        selection.setPackageId(bean.getPackageId());
        selection.setStudentId(bean.getStudentId());
        selection.setSelectionSummaryId(bean.getSelectionSummaryId());
        selection.setAmount(bean.getAmount());
        selection.setSelectedAt(bean.getSelectedAt());
        return selection;
    }

    // Convert StudentPackageSelectionSummary to StudentPackageSelectionSummaryBean
    public static StudentPackageSelectionSummaryBean toBean(StudentPackageSelectionSummary summary) {
        if (summary == null) {
            return null;
        }
        StudentPackageSelectionSummaryBean bean = new StudentPackageSelectionSummaryBean();
        bean.setSelectionSummaryId(summary.getSelectionSummaryId());
        bean.setStudentId(summary.getStudentId());
        bean.setTotalAmount(summary.getTotalAmount());
        bean.setSelectedAt(summary.getSelectedAt());
        bean.setStatus(summary.getStatus());
        // Convert selections to beans, or set empty list if selections are null
        List<StudentPackageSelection> selections = summary.getPackageSelections();
        List<StudentPackageSelectionBean> selectionBeans = selections != null
                ? selections.stream().map(StudentPackageSelectionConverter::toBean).collect(Collectors.toList())
                : Collections.emptyList();
        bean.setPackageSelectionBeans(selectionBeans);
        return bean;
    }

    // Convert StudentPackageSelectionSummaryBean to StudentPackageSelectionSummary
    public static StudentPackageSelectionSummary toEntity(StudentPackageSelectionSummaryBean bean) {
        if (bean == null) {
            return null;
        }
        StudentPackageSelectionSummary summary = new StudentPackageSelectionSummary();
        summary.setSelectionSummaryId(bean.getSelectionSummaryId());
        summary.setStudentId(bean.getStudentId());
        summary.setTotalAmount(bean.getTotalAmount());
        summary.setSelectedAt(bean.getSelectedAt());
        summary.setStatus(bean.getStatus());
        List<StudentPackageSelectionBean> packageSelectionBeans = bean.getPackageSelectionBeans();
        List<StudentPackageSelection> selections = packageSelectionBeans != null
                ? packageSelectionBeans.stream().map(StudentPackageSelectionConverter::toEntity).collect(Collectors.toList())
                : Collections.emptyList();
        summary.setPackageSelections(selections);
        return summary;
    }
}