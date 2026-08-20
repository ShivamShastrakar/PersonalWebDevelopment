package com.mahaexam.tenant.management.repository;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.tenant.management.model.UploadBatch;

import java.util.List;
import java.util.Optional;

public interface UploadBatchRepository {
    UploadBatch save(UploadBatch batch);
    Optional<UploadBatch> findById(Long id);
    List<UploadBatch> findAllOrderedByUploadTimeDesc(UserBean userBean);
    Long findBtachIdByInvoiceNumber(String invoiceNumber);
}
