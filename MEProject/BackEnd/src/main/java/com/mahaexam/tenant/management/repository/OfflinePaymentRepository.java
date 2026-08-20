package com.mahaexam.tenant.management.repository;

import com.mahaexam.tenant.management.model.OfflinePaymentModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OfflinePaymentRepository {

    /**
     * Save a single offline payment
     * @param offlinePayment the offline payment to save
     * @return the saved offline payment with generated ID
     */
    OfflinePaymentModel save(OfflinePaymentModel offlinePayment);

    /**
     * Save multiple offline payments
     * @param offlinePayments list of offline payments to save
     * @return list of saved offline payments
     */
    List<OfflinePaymentModel> saveAll(List<OfflinePaymentModel> offlinePayments);

    /**
     * Batch insert offline payments for better performance
     * @param offlinePayments list of offline payments to insert
     * @return array of update counts
     */
    int[] batchInsert(List<OfflinePaymentModel> offlinePayments);

    /**
     * Update an existing offline payment
     * @param offlinePayment the offline payment to update
     * @return the updated offline payment
     */
    OfflinePaymentModel update(OfflinePaymentModel offlinePayment);

    /**
     * Find offline payment by ID
     * @param id the payment ID
     * @return Optional containing the payment if found
     */
    Optional<OfflinePaymentModel> findById(Long id);

    /**
     * Find all offline payments
     * @return list of all offline payments
     */
    List<OfflinePaymentModel> findAll();

    /**
     * Find offline payments by batch ID
     * @param batchId the batch ID
     * @return list of offline payments for the batch
     */
    List<OfflinePaymentModel> findByBatchId(Long batchId);

    /**
     * Find offline payments by transaction ID
     * @param transactionId the transaction ID
     * @return list of offline payments for the transaction
     */
    List<OfflinePaymentModel> findByTransactionId(Long transactionId);

    /**
     * Find offline payments by status
     * @param status the payment status
     * @return list of offline payments with the given status
     */
    List<OfflinePaymentModel> findByStatus(String status);

    /**
     * Find offline payments by payment mode
     * @param paymentMode the payment mode (CHEQUE, CASH, etc.)
     * @return list of offline payments with the given payment mode
     */
    List<OfflinePaymentModel> findByPaymentMode(String paymentMode);

    /**
     * Find offline payments by date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of offline payments within the date range
     */
    List<OfflinePaymentModel> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find offline payments by cheque number
     * @param chequeNumber the cheque number
     * @return Optional containing the payment if found
     */
    Optional<OfflinePaymentModel> findByChequeNumber(String chequeNumber);

    /**
     * Delete offline payment by ID
     * @param id the payment ID
     */
    void deleteById(Long id);

    /**
     * Delete offline payments by batch ID
     * @param batchId the batch ID
     */
    void deleteByBatchId(Long batchId);

    /**
     * Count offline payments by batch ID
     * @param batchId the batch ID
     * @return count of payments
     */
    int countByBatchId(Long batchId);

    /**
     * Count offline payments by status
     * @param status the payment status
     * @return count of payments
     */
    int countByStatus(String status);

    /**
     * Update payment status
     * @param id the payment ID
     * @param status the new status
     * @return number of rows updated
     */
    int updateStatus(Long id, String status);

    /**
     * Batch update payment status
     * @param ids list of payment IDs
     * @param status the new status
     * @return array of update counts
     */
    int[] batchUpdateStatus(List<Long> ids, String status);
}

