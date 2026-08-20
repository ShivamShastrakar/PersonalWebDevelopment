package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.OfflinePaymentModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OfflinePaymentService {

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
     * Find offline payment by cheque number
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

    /**
     * Approve a payment
     * @param id the payment ID
     * @return updated payment
     */
    OfflinePaymentModel approvePayment(Long id);

    /**
     * Approve all payments for a batch and return an example updated payment
     * @param batchId the batch ID
     * @return an updated payment model (first from the batch)
     */
    void approvePaymentByBatchId(Long batchId);

    /**
     * Reject a payment
     * @param id the payment ID
     * @param remarks rejection reason
     * @return updated payment
     */
    OfflinePaymentModel rejectPayment(Long id, String remarks);

    /**
     * Verify a payment
     * @param id the payment ID
     * @return updated payment
     */
    OfflinePaymentModel verifyPayment(Long id);

    /**
     * Mark cheque as cleared
     * @param id the payment ID
     * @return updated payment
     */
    OfflinePaymentModel markChequeCleared(Long id);

    /**
     * Mark cheque as bounced
     * @param id the payment ID
     * @param remarks bounce reason
     * @return updated payment
     */
    OfflinePaymentModel markChequeBounced(Long id, String remarks);

    /**
     * Get payment statistics for a batch
     * @param batchId the batch ID
     * @return payment statistics
     */
    PaymentStats getPaymentStatsByBatch(Long batchId);

    /**
     * Get payment statistics by date range
     * @param startDate the start date
     * @param endDate the end date
     * @return payment statistics
     */
    PaymentStats getPaymentStatsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Inner class for payment statistics
     */
    class PaymentStats {
        private final int totalCount;
        private final int pendingCount;
        private final int approvedCount;
        private final int rejectedCount;
        private final int verifiedCount;
        private final java.math.BigDecimal totalAmount;
        private final java.math.BigDecimal approvedAmount;

        public PaymentStats(int totalCount, int pendingCount, int approvedCount, int rejectedCount,
                          int verifiedCount, java.math.BigDecimal totalAmount, java.math.BigDecimal approvedAmount) {
            this.totalCount = totalCount;
            this.pendingCount = pendingCount;
            this.approvedCount = approvedCount;
            this.rejectedCount = rejectedCount;
            this.verifiedCount = verifiedCount;
            this.totalAmount = totalAmount;
            this.approvedAmount = approvedAmount;
        }

        public int getTotalCount() { return totalCount; }
        public int getPendingCount() { return pendingCount; }
        public int getApprovedCount() { return approvedCount; }
        public int getRejectedCount() { return rejectedCount; }
        public int getVerifiedCount() { return verifiedCount; }
        public java.math.BigDecimal getTotalAmount() { return totalAmount; }
        public java.math.BigDecimal getApprovedAmount() { return approvedAmount; }
    }
}
