package com.mahaexam.tenant.management.service.bulkservice;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadProcessor<T> {
    List<T> readExcelToEntities(String filePath) throws Exception;
    List<T> readExcelToEntitiesV1(MultipartFile file) throws IOException;
    List<T> readExcelToEntitiesV1(byte[] fileBytes) throws IOException;
    ValidationResult<T> validateEntities(List<?> entities);
    void insertValidEntities(List<?> validEntities, boolean withPayment);
    String generateErrorFile(List<?> invalidEntities, Long batchId) throws Exception;
    String getSupportedClass();
}
