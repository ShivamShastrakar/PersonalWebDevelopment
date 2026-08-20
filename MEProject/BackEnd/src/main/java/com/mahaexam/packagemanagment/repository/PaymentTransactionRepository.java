package com.mahaexam.packagemanagment.repository;

import com.mahaexam.packagemanagment.model.PaymentTransaction;

public interface PaymentTransactionRepository {
    PaymentTransaction save(PaymentTransaction transaction);

    void updatePaymentStatus(String payuTransactionId, String status);

    /**
     * Returns payment_status for the given transaction_id or null if not found.
     */
    String getPaymentStatusByTransactionId(Long transactionId);
}