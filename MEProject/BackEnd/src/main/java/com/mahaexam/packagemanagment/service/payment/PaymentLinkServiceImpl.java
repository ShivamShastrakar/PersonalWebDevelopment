package com.mahaexam.packagemanagment.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.common.constants.AppConstants;
import com.mahaexam.common.exception.ServiceException;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.common.util.StringUtil;
import com.mahaexam.packagemanagment.bean.PaymentPayuWebhook;
import com.mahaexam.packagemanagment.bean.PaymentResponse;
import com.mahaexam.packagemanagment.bean.PayuPaymentResponse;
import com.mahaexam.payment.bean.PaymentRequest;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentLinkServiceImpl implements PaymentLinkService {
    private static final Logger logger = LogManager.getLogger(PaymentLinkService.class);
    private final ObjectMapper objectMapper;
    private final ConfigService configService;

    private static final OkHttpClient client = new OkHttpClient();

    public PaymentLinkServiceImpl(ObjectMapper objectMapper,
                                  ConfigService configService) {
        this.objectMapper = objectMapper;
        this.configService = configService;
    }

    public PaymentResponse handlPaymentLinkCreationRequest(PaymentRequest paymentRequest) {
        String paymentGateway = getConfigValue(ConfigService.CONFIGURED_PAYMENT_GATEWAY);
        PaymentResponse paymentResponse = PaymentResponse.builder().invoiceNumber(paymentRequest.getInvoiceNumber()).build();
        if(AppConstants.PAYMENT_GETWAY_RAZOR_PAY.equalsIgnoreCase(paymentGateway)){
            String clientKey = getConfigValue(ConfigService.RAZOR_CLIENT_KEY);
            String clientSecret = getConfigValue(ConfigService.RAZOR_CLIENT_SECRET);
            String merchantKey = getConfigValue(ConfigService.RAZOR_MERCHANT_KEY);
            String environment = System.getProperty("spring.profiles.active");
            if (Objects.isNull(environment)) {
                environment = "Dev";
            }
            String sUrl = getConfigValue(ConfigService.RAZOR_SUCCESS_URL);
            String fUrl = getConfigValue(ConfigService.RAZOR_FAILURE_URL);
            String apiUrl = getConfigValue(ConfigService.RAZOR_PAYMENT_LINK_URL);
            String invoicePrefix = getConfigValue(ConfigService.RAZOR_INVOICE_PREFIX);
            String payuTransactionId =  StringUtil.getPayUInvoiceNumber(invoicePrefix);
            paymentRequest.setClientKey(clientKey);
            paymentRequest.setClientSecret(clientSecret);
            paymentRequest.setMerchantKey(merchantKey);
            paymentRequest.setEnvironment(environment);
            paymentRequest.setSUrl(sUrl);
            paymentRequest.setFUrl(fUrl);
            paymentRequest.setApiUrl(apiUrl);
            paymentRequest.setInvoiceNumber(payuTransactionId);
            try {
                RazorpayClient razorpayClient = new RazorpayClient(paymentRequest.getClientKey(), paymentRequest.getClientSecret());
                JSONObject paymentLinkRequest = new JSONObject();
                // Convert amount to paise (smallest currency unit) for Razorpay
                BigDecimal amount = paymentRequest.getAmount();
                paymentLinkRequest.put("amount", (amount.multiply(new BigDecimal("100"))));
                paymentLinkRequest.put("currency","INR");
                paymentLinkRequest.put("accept_partial",false);
//                paymentLinkRequest.put("first_min_partial_amount",100);
//                paymentLinkRequest.put("expire_by",1691097057);
                paymentLinkRequest.put("reference_id",paymentRequest.getInvoiceNumber());
                paymentLinkRequest.put("description",paymentRequest.getDescription());
                JSONObject customer = new JSONObject();
                customer.put("name",paymentRequest.getCustomerName());
                customer.put("contact",paymentRequest.getCustomerMobile());
                customer.put("email",paymentRequest.getCustomerEmail());
                paymentLinkRequest.put("customer",customer);
                JSONObject notify = new JSONObject();
                notify.put("sms",true);
                notify.put("email",true);
                paymentLinkRequest.put("notify",notify);
                paymentLinkRequest.put("reminder_enable",false);
//                JSONObject notes = new JSONObject();
//                notes.put("policy_name","Life Insurance Policy");
//                paymentLinkRequest.put("notes",notes);
                paymentLinkRequest.put("callback_url",sUrl);
                paymentLinkRequest.put("callback_method","get");

                PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

                String paymentUrl = payment.get("short_url").toString();
                String paymentLinkId = payment.get("id").toString();
                logger.info("Razor Pay paymentResponse: {}, paymentLinkId: {}", payment.toString(), paymentLinkId);
                paymentResponse.setPaymentLink(paymentUrl);
                paymentResponse.setPaymentLinkId(paymentLinkId);
                paymentResponse.setInvoiceNumber(payuTransactionId);
            } catch (RazorpayException e) {
                logger.error("Razorpay Payment Link Creation Failed: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }


        } else if (AppConstants.PAYMENT_GETWAY_PAYU.equalsIgnoreCase(paymentGateway)) {
            String clientId = getConfigValue(ConfigService.PAYU_CLIENT_ID);
            String clientSecret = getConfigValue(ConfigService.PAYU_CLIENT_SECRET);
            String merchantKey = getConfigValue(ConfigService.PAYU_MERCHANT_KEY);
            String environment = System.getProperty("spring.profiles.active");
            if (Objects.isNull(environment)) {
                environment = "Dev";
            }
            String sUrl = getConfigValue(ConfigService.PAYU_SUCCESS_URL);
            String fUrl = getConfigValue(ConfigService.PAYU_FAILURE_URL);
            String apiUrl = getConfigValue(ConfigService.PAYU_PAYMENT_LINK_URL);
            String authTokenUrl = getConfigValue(ConfigService.PAYU_AUTH_TOKEN_URL);
            String invoicePrefix = getConfigValue(ConfigService.PAYU_INVOICE_PREFIX);
            String payuTransactionId =  StringUtil.getPayUInvoiceNumber(invoicePrefix);

            paymentRequest.setClientId(clientId);
            paymentRequest.setClientSecret(clientSecret);
            paymentRequest.setMerchantKey(merchantKey);
            paymentRequest.setEnvironment(environment);
            paymentRequest.setSUrl(sUrl);
            paymentRequest.setFUrl(fUrl);
            paymentRequest.setApiUrl(apiUrl);
            paymentRequest.setAuthTokenUrl(authTokenUrl);
            paymentRequest.setInvoiceNumber(payuTransactionId);

            String authToken = generateAuthToken(paymentRequest);
            PayuPaymentResponse payuPaymentResponse = createPayUPaymentLink(authToken, paymentRequest);
            logger.info("paymentResponse : Message ===>" + payuPaymentResponse.getMessage());
            paymentResponse.setPaymentLink(payuPaymentResponse.getResult().getPaymentLink());
            paymentResponse.setInvoiceNumber(payuTransactionId);
        } else {
            throw new ServiceException("No Payment Gateway Configured");
        }
        return paymentResponse;
    }

    public String generateAuthToken(PaymentRequest paymentRequest) {
        String clientId = paymentRequest.getClientId();
        String clientSecret = paymentRequest.getClientSecret();

        String apiUrl = paymentRequest.getAuthTokenUrl();

        JSONObject json = new JSONObject();
        json.put("client_id", clientId);
        json.put("client_secret", clientSecret);
        json.put("grant_type", "client_credentials");
        json.put("scope", "create_payment_links");
        json.put("expires_in", 60 * 5);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder().url(apiUrl).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                throw new ServiceException("Failed to get auth token: " + responseBody);
            }
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getString("access_token");
        } catch (Exception e) {
            throw new ServiceException("Payment Link Creation Failed ", e);
        }
    }

    public String revokeAuthToken(PaymentRequest paymentRequest,String authToken) {
//        String clientId = paymentRequest.getClientId();
//        String clientSecret = paymentRequest.getClientSecret();

        String apiUrl = paymentRequest.getAuthTokenUrl();
        apiUrl = "https://uat-accounts.payu.in/revoke";

        JSONObject json = new JSONObject();
//        json.put("client_id", clientId);
//        json.put("client_secret", clientSecret);
//        json.put("grant_type", "client_credentials");
//        json.put("scope", "create_payment_links");
//        json.put("expires_in", 60 * 5);
        OkHttpClient client = new OkHttpClient();

        String clientId = paymentRequest.getClientId();
        String clientSecret = paymentRequest.getClientSecret();

        RequestBody formBody = new FormBody.Builder()
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .build();

//        Request request = new Request.Builder()
//                .url("https://uat-accounts.payu.in/revoke")
//                .post(formBody)
//                .build();




        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder().addHeader("merchantId",paymentRequest.getMerchantKey())
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","Bearer "+authToken)
                .url(apiUrl).post(formBody).build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                throw new ServiceException("Failed to get auth token: " + responseBody);
            }
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getString("access_token");
        } catch (Exception e) {
            throw new ServiceException("Payment Link Creation Failed ", e);
        }
    }

    public PayuPaymentResponse createPayUPaymentLink(String authToken, PaymentRequest paymentRequest) {

        String merchantKey = paymentRequest.getMerchantKey();
        String environment = paymentRequest.getEnvironment();
        String sUrl = paymentRequest.getSUrl();
        String fUrl = paymentRequest.getFUrl();

        PayuPaymentResponse paymentResponse = null;
        String apiUrl = paymentRequest.getApiUrl();
        JSONObject json = new JSONObject();
        json.put("subAmount", paymentRequest.getAmount());
        json.put("description", paymentRequest.getDescription());
        json.put("customerName", paymentRequest.getCustomerName());
//		json.put("firstname", "Narednra");
//		json.put("lastname", "Chpuhan");
        json.put("customerEmail", paymentRequest.getCustomerEmail());
        json.put("customerMobile", paymentRequest.getCustomerMobile());
//		json.put("email", paymentRequest.getCustomerEmail());
//		json.put("phone", paymentRequest.getCustomerMobile());
        json.put("sendEmail", true);
        json.put("sendSms", true);
        json.put("source", "API");
        json.put("surl", sUrl);
        json.put("furl", fUrl);
        json.put("udf1", environment);
        json.put("udf2", paymentRequest.getInvoiceNumber());
        json.put("udf3", paymentRequest.getBatchId());
        json.put("invoiceNumber", paymentRequest.getInvoiceNumber());
        json.put("transactionId", paymentRequest.getInvoiceNumber());

        // Add customer object
        JSONObject customer = new JSONObject();
        customer.put("name", paymentRequest.getCustomerName());
        customer.put("phone", paymentRequest.getCustomerMobile());
        customer.put("email",  paymentRequest.getCustomerEmail());

        // Add customer object into main JSON
        json.put("customer", customer);


        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        logger.info("Payment Link Request: " + json);
        Request request = new Request.Builder().url(apiUrl).post(body).addHeader("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json").addHeader("merchantId", merchantKey).build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                throw new ServiceException("Payment Link Creation Failed: " + responseBody);
            }
            logger.info("Payment Link Response: " + responseBody);
            paymentResponse = objectMapper.readValue(responseBody, PayuPaymentResponse.class);
        } catch (Exception e) {
            if(e.getMessage().contains("Token is either invalid/expired")) {
                revokeAuthToken(paymentRequest,authToken);
            }
            throw new ServiceException("Payment Link Creation Failed ", e);
        }
        return paymentResponse;
    }

    @Override
    public PaymentPayuWebhook handlePayuWebhook(Map<String, String> postParams) {
        PaymentPayuWebhook paymentPayuWebhook = PaymentPayuWebhook.builder().build();
        try {
            // Debug: print all parameters
            postParams.forEach((k, v) -> logger.info(k + " : " + v));

            String status = postParams.get("status");
            String txnid = postParams.get("txnid");
            String receivedHash = postParams.get("hash");
            if (Objects.isNull(txnid)) {
                logger.info("txnid attribute is null so geting from invoiceNumber attribute.");
                txnid = postParams.get("invoiceNumber");
            }

            logger.info("%s : %s receivedHash: %s".formatted(postParams, status, receivedHash));

            if (verifyHash(postParams, receivedHash)) {
                String environment = postParams.get("udf1"); // staging/production
//                String invoiceNumber = postParams.get("udf2");
                paymentPayuWebhook.setInvoiceNumber(txnid);

                // Extract batchId from udf3
                String batchId = postParams.get("udf3");
                paymentPayuWebhook.setBatchId(batchId);
                logger.info("environment: %s and txnid: %s".formatted(environment, txnid));
                if ("success".equalsIgnoreCase(status)) {
                    // ✅ Payment successful - Update order status in DB
                    logger.info("Payment success for txnid/invoiceNumber: %s in env: %s".formatted(txnid, environment));
                } else {
                    // ❌ Payment failed - Update failure status
                    logger.warn("Payment failed for txnid/invoiceNumber: %s".formatted(txnid));
                }
                paymentPayuWebhook.setStatus("OK");
            } else {
                paymentPayuWebhook.setStatus("FAIL");
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error", e);
        }
        return paymentPayuWebhook;
    }


    private boolean verifyHash(Map<String, String> params, String receivedHash) {
        try {
            // Fields for hash validation
            String calculatedReceivedHash = getReverseHash(params);
            return calculatedReceivedHash.equalsIgnoreCase(receivedHash);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error", e);
        }
    }

    public String getReverseHash(Map<String, String> params) {
        String key = params.get("key");
        String status = params.get("status");
        String txnid = params.get("txnid");
        if (Objects.isNull(txnid)) {
            txnid = params.get("invoiceNumber");
        }
        String amount = params.get("amount"); // Retrieve amount value from merchant's database
        String email = params.get("email");
        String productinfo = params.get("productinfo");
        String firstname = params.get("firstname");
        String udf1 = params.get("udf1");
        String udf2 = params.get("udf2");
        String udf3 = params.get("udf3");
        String udf4 = params.get("udf4");
        String udf5 = params.get("udf5");
        Optional<Config> configOpt = configService.findByName(ConfigService.PAYU_PAYMENT_LINK_SALT);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.PAYU_PAYMENT_LINK_SALT));
        String salt = config.getValue();
        String r_h = checkNull(salt) + "|" + checkNull(status) + "||||||" + checkNull(udf5) + "|" + checkNull(udf4)
                + "|" + checkNull(udf3) + "|" + checkNull(udf2) + "|" + checkNull(udf1) + "|" + checkNull(email) + "|"
                + checkNull(firstname) + "|" + checkNull(productinfo) + "|" + checkNull(amount) + "|" + checkNull(txnid)
                + "|" + checkNull(key);

        System.out.println("r_h   " + r_h);


        return calculateSHA512(r_h); // return generated reverse hash
    }

    private String checkNull(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    private String calculateSHA512(String str) {

        MessageDigest md;
        String out = "";
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(str.getBytes());
            byte[] mb = md.digest();

            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return out;

    }

    public boolean empty(String s) {
        if (s == null || s.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private String getConfigValue(String configKay) {
        Optional<Config> configOpt = configService.findByName(configKay);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + configKay));
        return config.getValue();
    }
}