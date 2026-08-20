package com.mahaexam.packagemanagment.service;

import com.mahaexam.packagemanagment.bean.PaymentTransactionBean;
import com.mahaexam.packagemanagment.model.PaymentTransaction;
import org.springframework.stereotype.Component;

public class PaymentTransactionConverter {

    // Convert PaymentTransaction to PaymentTransactionBean
    public static PaymentTransactionBean toBean(PaymentTransaction entity) {
        if (entity == null) {
            return null;
        }
        PaymentTransactionBean bean = PaymentTransactionBean.builder().build();
        bean.setTransactionId(entity.getTransactionId());
        bean.setSelectionSummaryId(entity.getSelectionSummaryId());
        bean.setPayuTransactionId(entity.getPayuTransactionId());
        bean.setTotalAmount(entity.getTotalAmount());
        bean.setPaymentStatus(entity.getPaymentStatus());
        bean.setPaymentLink(entity.getPaymentLink());
        bean.setCreatedAt(entity.getCreatedAt());
        bean.setUpdatedAt(entity.getUpdatedAt());
        return bean;
    }

    // Convert PaymentTransactionBean to PaymentTransaction
    public static PaymentTransaction toEntity(PaymentTransactionBean bean) {
        if (bean == null) {
            return null;
        }
        PaymentTransaction entity = PaymentTransaction.builder().build();
        entity.setTransactionId(bean.getTransactionId());
        entity.setSelectionSummaryId(bean.getSelectionSummaryId());
        entity.setPayuTransactionId(bean.getPayuTransactionId());
        entity.setTotalAmount(bean.getTotalAmount());
        entity.setPaymentStatus(bean.getPaymentStatus());
        entity.setPaymentLink(bean.getPaymentLink());
        entity.setCreatedAt(bean.getCreatedAt());
        entity.setUpdatedAt(bean.getUpdatedAt());
        return entity;
    }
}