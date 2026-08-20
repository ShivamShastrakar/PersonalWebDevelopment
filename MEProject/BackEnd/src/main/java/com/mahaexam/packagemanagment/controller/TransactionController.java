package com.mahaexam.packagemanagment.controller;

import com.mahaexam.packagemanagment.service.PackageSelectIonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final PackageSelectIonService packageSelectIonService;

    public TransactionController(PackageSelectIonService packageSelectIonService) {
        this.packageSelectIonService = packageSelectIonService;
    }

    @GetMapping("/status/{transactionId}")
    public ResponseEntity<Map<String, Object>> getPaymentStatus(@PathVariable("transactionId") Long transactionId) {
        String status = packageSelectIonService.getPaymentStatusByTransactionId(transactionId);
        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("transactionId", transactionId, "status", "NOT_FOUND"));
        }
        return ResponseEntity.ok(Map.of("transactionId", transactionId, "status", status));
    }
}

