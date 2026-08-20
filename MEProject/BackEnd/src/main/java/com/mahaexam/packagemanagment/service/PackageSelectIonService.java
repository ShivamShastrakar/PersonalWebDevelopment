package com.mahaexam.packagemanagment.service;

import com.mahaexam.packagemanagment.bean.PaymentPayuWebhook;
import com.mahaexam.packagemanagment.bean.PaymentTransactionBean;
import com.mahaexam.packagemanagment.bean.StudentPackageSelectionSummaryBean;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PackageSelectIonService {

    PaymentTransactionBean selectPackages(StudentPackageSelectionSummaryBean packageSelectionSummaryBean);

    String handlePayuWebhook(Map<String, String> postParams);

    void handleWbHook(PaymentPayuWebhook paymentPayuWebhook, String invoiceNumber);

    /**
     * Returns payment status for the given transaction id or null if not found.
     */
    String getPaymentStatusByTransactionId(Long transactionId);
}
