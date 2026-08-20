package com.mahaexam.packagemanagment.controller.payment;

import com.mahaexam.packagemanagment.service.PackageSelectIonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payu/payments")
public class PayuPaymentController {


    private final PackageSelectIonService packageSelectIonService;

    public PayuPaymentController(PackageSelectIonService packageSelectIonService) {
        this.packageSelectIonService = packageSelectIonService;
    }

    @PostMapping(value = "/payu-webhook", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String handlePayuWebhook(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> postParams = new HashMap<>();

        parameterMap.forEach((key, value) -> postParams.put(key, value[0]));
        return packageSelectIonService.handlePayuWebhook(postParams);
    }

}
