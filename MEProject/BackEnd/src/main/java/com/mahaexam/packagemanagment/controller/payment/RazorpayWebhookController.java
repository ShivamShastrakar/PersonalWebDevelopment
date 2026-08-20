package com.mahaexam.packagemanagment.controller.payment;

import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.packagemanagment.bean.PaymentPayuWebhook;
import com.mahaexam.packagemanagment.service.PackageSelectIonService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Optional;

@RestController
@RequestMapping("/api/webhooks")
public class RazorpayWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(RazorpayWebhookController.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private PackageSelectIonService packageSelectIonService;

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleRazorpayWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String receivedSignature) {

        try {
            String webhookSecret = getConfigValue(ConfigService.RAZOR_PAYMENT_LINK_SALT);
            // Verify webhook signature
            if (!verifySignature(payload, receivedSignature, webhookSecret)) {
                logger.error("Invalid webhook signature");
                return ResponseEntity.status(400).body("Invalid signature");
            }

            logger.info("Webhook signature verified successfully");

            // Parse the payload
            JSONObject jsonPayload = new JSONObject(payload);
            String event = jsonPayload.getString("event");

            // Handle different events
            switch (event) {
                case "payment.authorized":
                    handlePaymentAuthorized(jsonPayload);
                    break;
                case "payment.captured":
                    handlePaymentCaptured(jsonPayload);
                    break;
                case "payment.failed":
                    handlePaymentFailed(jsonPayload);
                    break;
                case "payment_link.paid":
                    handlePaymentLinkPaid(jsonPayload);
                    break;
                case "payment_link.cancelled":
                    handlePaymentLinkCancelled(jsonPayload);
                    break;
                default:
                    logger.info("Unhandled event type: {}", event);
            }

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            logger.error("Error processing webhook", e);
            return ResponseEntity.status(500).body("Error processing webhook");
        }
    }

    private boolean verifySignature(String payload, String receivedSignature, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            StringBuilder expectedSignature = new StringBuilder();
            for (byte b : hash) {
                expectedSignature.append(String.format("%02x", b));
            }

            return expectedSignature.toString().equals(receivedSignature);
        } catch (Exception e) {
            logger.error("Error verifying signature", e);
            return false;
        }
    }

    private void handlePaymentLinkPaid(JSONObject payload) {
        // Navigate to payment_link entity
        JSONObject paymentLinkEntity = payload.getJSONObject("payload")
                .getJSONObject("payment_link")
                .getJSONObject("entity");

        // Get the reference_id you set during creation
        String referenceId = paymentLinkEntity.getString("reference_id");
        String paymentLinkId = paymentLinkEntity.getString("id");


        JSONObject payment = payload.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");

        String paymentId = payment.getString("id");
        String orderId = payment.getString("order_id");
        int amount = payment.getInt("amount");
        String status = payment.getString("status");

        logger.info("Payment Link Paid - Reference Id :{} - Link ID: {}, Payment ID: {}, Amount: {} and Status: {}",
                referenceId, paymentLinkId, paymentId, amount, status);

        PaymentPayuWebhook paymentPayuWebhook = PaymentPayuWebhook.builder().build();
        paymentPayuWebhook.setInvoiceNumber(referenceId);
        if ("paid".equalsIgnoreCase(status) || "captured".equalsIgnoreCase(status)) {
            // ✅ Payment successful - Update order status in DB
            paymentPayuWebhook.setStatus("OK");
            logger.info("Payment success for txnid/invoiceNumber: %s".formatted(referenceId));
        } else {
            // ❌ Payment failed - Update failure status
            paymentPayuWebhook.setStatus("FAIL");
            logger.warn("Payment failed for txnid/invoiceNumber: %s".formatted(referenceId));
        }
        packageSelectIonService.handleWbHook(paymentPayuWebhook, referenceId);
    }

    private void handlePaymentCaptured(JSONObject payload) {
        JSONObject payment = payload.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");

        String paymentId = payment.getString("id");
        String orderId = payment.getString("order_id");
        int amount = payment.getInt("amount");

        logger.info("Payment Captured - Payment ID: {}, Order ID: {}, Amount: {}",
                paymentId, orderId, amount);

    }

    private void handlePaymentAuthorized(JSONObject payload) {
        logger.info("Payment Authorized event received");
    }

    private void handlePaymentFailed(JSONObject payload) {
        try {
            JSONObject payloadObj = payload.optJSONObject("payload");
            if (payloadObj == null) {
                logger.warn("Webhook payload missing 'payload' object for payment.failed event");
                return;
            }
            // Log the JSON payload in pretty/indented form for easier reading in logs
            try {
                logger.info("payloadObj ===> \n{}", payloadObj.toString(2));
            } catch (Exception e) {
                // Fallback to compact string representation if pretty printing fails
                logger.info("payloadObj ===> {}", payloadObj.toString());
            }
            // Try to extract referenceId from multiple possible locations
            String referenceId = null;

            if (payloadObj.has("payment_link")) {
                JSONObject paymentLinkEntity = payloadObj.optJSONObject("payment_link").optJSONObject("entity");
                if (paymentLinkEntity != null) {
                    referenceId = paymentLinkEntity.optString("reference_id", null);
                }
            }

            // If not found, try payload.payment.entity.reference_id
            if (referenceId == null && payloadObj.has("payment")) {
                JSONObject paymentEntity = payloadObj.optJSONObject("payment").optJSONObject("entity");
                if (paymentEntity != null) {
                    referenceId = paymentEntity.optString("reference_id", null);
                    // many integrations put custom reference in 'notes' object
                    if (referenceId == null && paymentEntity.has("notes")) {
                        JSONObject notes = paymentEntity.optJSONObject("notes");
                        if (notes != null) {
                            referenceId = notes.optString("reference_id", null);
                        }
                    }
                }
            }

            // Extract some basic payment details for logging if available
            JSONObject paymentObj = payloadObj.has("payment") ? payloadObj.optJSONObject("payment") : null;
            JSONObject paymentEntity = paymentObj != null ? paymentObj.optJSONObject("entity") : null;

            String paymentId = paymentEntity != null ? paymentEntity.optString("id", "") : "";
            String orderId = paymentEntity != null ? paymentEntity.optString("order_id", "") : "";
            int amount = paymentEntity != null ? paymentEntity.optInt("amount", 0) : 0;
            String status = paymentEntity != null ? paymentEntity.optString("status", "") : "";

            logger.info("Payment Failed event - referenceId: {}, paymentId: {}, orderId: {}, amount: {}, status: {}",
                    referenceId, paymentId, orderId, amount, status);

            if (referenceId == null || referenceId.isBlank()) {
                logger.warn("Could not determine referenceId for payment.failed webhook; skipping processing");
                return;
            }

            PaymentPayuWebhook paymentPayuWebhook = PaymentPayuWebhook.builder().build();
            paymentPayuWebhook.setInvoiceNumber(referenceId);
            paymentPayuWebhook.setStatus("FAIL");
            logger.warn("Payment failed for txnid/invoiceNumber: %s".formatted(referenceId));
            packageSelectIonService.handleWbHook(paymentPayuWebhook, referenceId);
        } catch (Exception ex) {
            // Catch any unexpected parsing exceptions and log them — do not rethrow to avoid 500 for webhook
            logger.error("Error handling payment.failed webhook: {}", ex.getMessage(), ex);
        }
    }

    private void handlePaymentLinkCancelled(JSONObject payload) {
        logger.info("Payment Link Cancelled event received");
    }

    private String getConfigValue(String configKay) {
        Optional<Config> configOpt = configService.findByName(configKay);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + configKay));
        return config.getValue();
    }
}
