package com.mahaexam.packagemanagment.service.payment;

import com.mahaexam.packagemanagment.bean.PaymentPayuWebhook;
import com.mahaexam.packagemanagment.bean.PaymentResponse;
import com.mahaexam.packagemanagment.bean.PayuPaymentResponse;
import com.mahaexam.payment.bean.PaymentRequest;

import java.util.Map;

public interface PaymentLinkService {

    PaymentPayuWebhook handlePayuWebhook(Map<String, String> postParams);

    PaymentResponse handlPaymentLinkCreationRequest(PaymentRequest paymentRequest);

}
