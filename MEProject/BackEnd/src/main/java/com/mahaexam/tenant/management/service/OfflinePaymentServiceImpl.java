package com.mahaexam.tenant.management.service;

import com.mahaexam.tenant.management.model.OfflinePaymentModel;
import com.mahaexam.tenant.management.repository.OfflinePaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfflinePaymentServiceImpl implements OfflinePaymentService {

    private static final Logger logger = LoggerFactory.getLogger(OfflinePaymentServiceImpl.class);

    private final OfflinePaymentRepository offlinePaymentRepository;

    public OfflinePaymentServiceImpl(OfflinePaymentRepository offlinePaymentRepository) {
        this.offlinePaymentRepository = offlinePaymentRepository;
    }

    @Override
    public OfflinePaymentModel save(OfflinePaymentModel offlinePayment) {
        logger.info("Saving offline payment with amount: {}, mode: {}",
                    offlinePayment.getAmount(), offlinePayment.getPaymentMode());
        return offlinePaymentRepository.save(offlinePayment);
    }

    @Override
    public List<OfflinePaymentModel> saveAll(List<OfflinePaymentModel> offlinePayments) {
        logger.info("Saving {} offline payments", offlinePayments.size());
        return offlinePaymentRepository.saveAll(offlinePayments);
    }

    @Override
    public int[] batchInsert(List<OfflinePaymentModel> offlinePayments) {
        logger.info("Batch inserting {} offline payments", offlinePayments.size());
        return offlinePaymentRepository.batchInsert(offlinePayments);
    }

    @Override
    public OfflinePaymentModel update(OfflinePaymentModel offlinePayment) {
        logger.info("Updating offline payment with ID: {}", offlinePayment.getId());
        return offlinePaymentRepository.update(offlinePayment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfflinePaymentModel> findById(Long id) {
        return offlinePaymentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfflinePaymentModel> findAll() {
        return offlinePaymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfflinePaymentModel> findByBatchId(Long batchId) {
        return offlinePaymentRepository.findByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfflinePaymentModel> findByTransactionId(Long transactionId) {
        return offlinePaymentRepository.findByTransactionId(transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfflinePaymentModel> findByStatus(String status) {
        return offlinePaymentRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfflinePaymentModel> findByPaymentMode(String paymentMode) {
        return offlinePaymentRepository.findByPaymentMode(paymentMode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfflinePaymentModel> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        return offlinePaymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfflinePaymentModel> findByChequeNumber(String chequeNumber) {
        return offlinePaymentRepository.findByChequeNumber(chequeNumber);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting offline payment with ID: {}", id);
        offlinePaymentRepository.deleteById(id);
    }

    @Override
    public void deleteByBatchId(Long batchId) {
        logger.info("Deleting offline payments for batch ID: {}", batchId);
        offlinePaymentRepository.deleteByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public int countByBatchId(Long batchId) {
        return offlinePaymentRepository.countByBatchId(batchId);
    }

    @Override
    @Transactional(readOnly = true)
    public int countByStatus(String status) {
        return offlinePaymentRepository.countByStatus(status);
    }

    @Override
    public int updateStatus(Long id, String status) {
        logger.info("Updating status to {} for payment ID: {}", status, id);
        return offlinePaymentRepository.updateStatus(id, status);
    }

    @Override
    public int[] batchUpdateStatus(List<Long> ids, String status) {
        logger.info("Batch updating status to {} for {} payments", status, ids.size());
        return offlinePaymentRepository.batchUpdateStatus(ids, status);
    }

    @Override
    public OfflinePaymentModel approvePayment(Long id) {
        logger.info("Approving payment with ID: {}", id);
        Optional<OfflinePaymentModel> optionalPayment = findById(id);

        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + id);
        }

        OfflinePaymentModel payment = optionalPayment.get();
        payment.setStatus("APPROVED");
        return update(payment);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void approvePaymentByBatchId(Long batchId) {
        logger.info("Approving payments for batch ID: {}", batchId);

        List<OfflinePaymentModel> payments = findByBatchId(batchId);
        if (payments == null || payments.isEmpty()) {
            throw new IllegalArgumentException("No payments found for batch ID: " + batchId);
        }

        List<Long> ids = payments.stream()
                                 .map(OfflinePaymentModel::getId)
                                 .filter(Objects::nonNull)
                                 .collect(Collectors.toList());

        if (!ids.isEmpty()) {
            offlinePaymentRepository.batchUpdateStatus(ids, "APPROVED");
        }
    }

    @Override
    public OfflinePaymentModel rejectPayment(Long id, String remarks) {
        logger.info("Rejecting payment with ID: {}, reason: {}", id, remarks);
        Optional<OfflinePaymentModel> optionalPayment = findById(id);

        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + id);
        }

        OfflinePaymentModel payment = optionalPayment.get();
        payment.setStatus("REJECTED");
        payment.setRemarks(remarks);
        return update(payment);
    }

    @Override
    public OfflinePaymentModel verifyPayment(Long id) {
        logger.info("Verifying payment with ID: {}", id);
        Optional<OfflinePaymentModel> optionalPayment = findById(id);

        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + id);
        }

        OfflinePaymentModel payment = optionalPayment.get();
        payment.setStatus("VERIFIED");
        return update(payment);
    }

    @Override
    public OfflinePaymentModel markChequeCleared(Long id) {
        logger.info("Marking cheque as cleared for payment ID: {}", id);
        Optional<OfflinePaymentModel> optionalPayment = findById(id);

        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + id);
        }

        OfflinePaymentModel payment = optionalPayment.get();

        if (!"CHEQUE".equalsIgnoreCase(payment.getPaymentMode())) {
            throw new IllegalStateException("Payment is not a cheque payment");
        }

//        payment.setStatus("CLEARED");
        payment.setStatus("APPROVED");
        return update(payment);
    }

    @Override
    public OfflinePaymentModel markChequeBounced(Long id, String remarks) {
        logger.info("Marking cheque as bounced for payment ID: {}, reason: {}", id, remarks);
        Optional<OfflinePaymentModel> optionalPayment = findById(id);

        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + id);
        }

        OfflinePaymentModel payment = optionalPayment.get();

        if (!"CHEQUE".equalsIgnoreCase(payment.getPaymentMode())) {
            throw new IllegalStateException("Payment is not a cheque payment");
        }

        payment.setStatus("BOUNCED");
        payment.setRemarks(remarks);
        return update(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentStats getPaymentStatsByBatch(Long batchId) {
        logger.info("Getting payment statistics for batch ID: {}", batchId);
        List<OfflinePaymentModel> payments = findByBatchId(batchId);
        return calculateStats(payments);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentStats getPaymentStatsByDateRange(LocalDate startDate, LocalDate endDate) {
        logger.info("Getting payment statistics from {} to {}", startDate, endDate);
        List<OfflinePaymentModel> payments = findByPaymentDateBetween(startDate, endDate);
        return calculateStats(payments);
    }

    /**
     * Helper method to calculate payment statistics
     */
    private PaymentStats calculateStats(List<OfflinePaymentModel> payments) {
        int totalCount = payments.size();
        int pendingCount = 0;
        int approvedCount = 0;
        int rejectedCount = 0;
        int verifiedCount = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal approvedAmount = BigDecimal.ZERO;

        for (OfflinePaymentModel payment : payments) {
            totalAmount = totalAmount.add(payment.getAmount());

            String status = payment.getStatus();
            if (status != null) {
                switch (status.toUpperCase()) {
                    case "PENDING":
                        pendingCount++;
                        break;
                    case "APPROVED":
                        approvedCount++;
                        approvedAmount = approvedAmount.add(payment.getAmount());
                        break;
                    case "REJECTED":
                        rejectedCount++;
                        break;
                    case "VERIFIED":
                        verifiedCount++;
                        break;
                    case "CLEARED":
                        approvedCount++;
                        approvedAmount = approvedAmount.add(payment.getAmount());
                        break;
                }
            }
        }

        return new PaymentStats(totalCount, pendingCount, approvedCount, rejectedCount,
                              verifiedCount, totalAmount, approvedAmount);
    }
}

