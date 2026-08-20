package com.mahaexam.tenant.management.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mahaexam.tenant.management.bean.ParentBean;
import com.mahaexam.tenant.management.model.Parent;

public class ParentConverter {

    // Convert ParentBean to Parent
    public static Parent toParent(ParentBean parentBean) {
        if (parentBean == null) {
            return null;
        }

        // Validate required fields
        if (parentBean.getFatherName() == null || parentBean.getFatherName().trim().isEmpty()) {
            throw new IllegalArgumentException("Father's name cannot be blank");
        }
        if (parentBean.getMotherName() == null || parentBean.getMotherName().trim().isEmpty()) {
            throw new IllegalArgumentException("Mother's name cannot be blank");
        }
        if (parentBean.getNumberOfSiblings() == null) {
            throw new IllegalArgumentException("Number of siblings cannot be null");
        }
        if (parentBean.getParentsYearlyIncome() == null) {
            throw new IllegalArgumentException("Parents' yearly income cannot be null");
        }

        Parent parent = Parent.builder().build();
        parent.setParentId(parentBean.getParentId());
        parent.setFatherName(parentBean.getFatherName());
        parent.setFatherMobileNumber(parentBean.getFatherMobileNumber());
        parent.setFatherOccupation(parentBean.getFatherOccupation());
        parent.setMotherName(parentBean.getMotherName());
        parent.setMotherMobileNumber(parentBean.getMotherMobileNumber());
        parent.setMotherOccupation(parentBean.getMotherOccupation());
        parent.setNumberOfSiblings(parentBean.getNumberOfSiblings());
        parent.setFirstSiblingName(parentBean.getFirstSiblingName());
        parent.setFirstSiblingStd(parentBean.getFirstSiblingStd());
        parent.setSecondSiblingName(parentBean.getSecondSiblingName());
        parent.setSecondSiblingStd(parentBean.getSecondSiblingStd());
        parent.setParentsYearlyIncome(parentBean.getParentsYearlyIncome());
        parent.setCreatedAt(parentBean.getCreatedAt());
        return parent;
    }

    // Convert Parent to ParentBean
    public static ParentBean toParentBean(Parent parent) {
        if (parent == null) {
            return null;
        }

        ParentBean parentBean = new ParentBean();
        parentBean.setParentId(parent.getParentId());
        parentBean.setFatherName(parent.getFatherName());
        parentBean.setFatherMobileNumber(parent.getFatherMobileNumber());
        parentBean.setFatherOccupation(parent.getFatherOccupation());
        parentBean.setMotherName(parent.getMotherName());
        parentBean.setMotherMobileNumber(parent.getMotherMobileNumber());
        parentBean.setMotherOccupation(parent.getMotherOccupation());
        parentBean.setNumberOfSiblings(parent.getNumberOfSiblings());
        parentBean.setFirstSiblingName(parent.getFirstSiblingName());
        parentBean.setFirstSiblingStd(parent.getFirstSiblingStd());
        parentBean.setSecondSiblingName(parent.getSecondSiblingName());
        parentBean.setSecondSiblingStd(parent.getSecondSiblingStd());
        parentBean.setParentsYearlyIncome(parent.getParentsYearlyIncome());
        parentBean.setCreatedAt(parent.getCreatedAt());
        return parentBean;
    }

    // Convert List<ParentBean> to List<Parent>
    public static List<Parent> toParentList(List<ParentBean> parentBeans) {
        if (parentBeans == null) {
            return null;
        }
        return parentBeans.stream()
                .filter(Objects::nonNull)
                .map(ParentConverter::toParent)
                .collect(Collectors.toList());
    }

    // Convert List<Parent> to List<ParentBean>
    public static List<ParentBean> toParentBeanList(List<Parent> parents) {
        if (parents == null) {
            return null;
        }
        return parents.stream()
                .filter(Objects::nonNull)
                .map(ParentConverter::toParentBean)
                .collect(Collectors.toList());
    }
}