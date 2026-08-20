package com.mahaexam.common.service;

import com.mahaexam.common.model.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("powersTextSmsService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class PowersTextSmsService {

    private static final Logger logger = LogManager.getLogger(PowersTextSmsService.class);
    @Autowired
    private ConfigService configService;


    public String sendSms(String templateId, String message, String numbers) {
        String senderId = "TXXTOO";//"TEXTTO";//"TXXTOO";//"TEXTTO";
        String appUrl = "3.110.16.23/MahaExam/login";
        String uri = null;
        String authenticKey = "3737444545504552534d533130301710583911";

        Optional<Config> configOpt;
        boolean isSmsEnabled = true;
        if (Objects.nonNull(configService)) {
            isSmsEnabled = configService.getBinaryBooleanConfig(ConfigService.ENABLE_SMS);
        }
        if (isSmsEnabled) {
            if (Objects.nonNull(configService)) {
                configOpt = configService.findByName(ConfigService.SMS_API_KEY);
                authenticKey = configOpt.map(Config::getValue).orElse(null);
            }
            if (Objects.nonNull(configService)) {
                configOpt = configService.findByName(ConfigService.SMS_API_URL);
                uri = configOpt.map(Config::getValue).orElse(null);
            }
            if (Objects.nonNull(configService)) {
                configOpt = configService.findByName(ConfigService.SMS_API_SENDER);
                senderId = configOpt.map(Config::getValue).orElse(null);
            }
            if (Objects.nonNull(configService)) {
                configOpt = configService.findByName(ConfigService.SMS_APP_URL);
                appUrl = configOpt.map(Config::getValue).orElse(null);
            }
            message = String.format(message, appUrl);
            Map<String, String> data = new HashMap<>();
            data.put("authentic-key", authenticKey);
            data.put("senderid", senderId);
            data.put("route", "1");
            data.put("number", numbers);
            data.put("message", message);
            data.put("templateid", templateId);

            String formData = data.entrySet().stream()
                    .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                            URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));

            try {
                // Create a custom SSLContext that trusts all certificates (for testing only)
                SSLContext sslContext = SSLContext.getInstance("TLS");
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                                // Trust all client certificates (no validation)
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                                // Trust all server certificates (no validation)
                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }
                        }
                };
                sslContext.init(null, trustAllCerts, new SecureRandom());

                // Create HttpClient with custom SSL context
                HttpClient client = HttpClient.newBuilder()
                        .sslContext(sslContext)
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .build();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString(formData))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                // Log or handle response
                logger.info("Response: {}", responseBody);
                return responseBody;
            } catch (Exception e) {
                // Detailed error logging
                logger.error("Error sending SMS: ", e);
                return "error" + e;
            }

        }
        return null;
    }

    public void sendSmsToNewRegisteredStudents(String mobileNumber, String studentName, String username, String type, String newPassword) {
        String senderId = "MAHEXM";//"TEXTTO";//"TXXTOO";//"TEXTTO";
        String templateId = "1107176225503269275";
        String appUrl = "mahaexam.org.in";
        String newMsg = "The OTP to complete your registration on MahaExam is %s To login click %s -EDUVAL";

        //The OTP to complete your registration on MahaExam is 036207 To login click mahaexam.org.in -EDUVAL
        newMsg = "The OTP to complete your registration on MahaExam is 326054 To login click mahaexam.org.in -EDUVAL";

        //You have been registered successfully on MahaExam. Please click to %s and use your username %s to reset your password. -EDUVAL
        // newMsg = String.format("The OTP to complete your registration on MahaExam is $s To login click %s -EDUVAL",
        //                studentName, username, appUrl);
        //The OTP to complete your registration on MahaExam is {var} To login click {var} -EDUVAL&templateid=1107176225503269275

        //http://sms1.powerstext.in/http-tokenkeyapi.php?authentic-key=383645647576616c3130301760434267&senderid=MAHEXM&route=1&number=8169571998&message=
        // The OTP to complete your registration on MahaExam is {var} To login click {var} -EDUVAL&templateid=1107176225503269275

        /*
        UPDATE message_templates
SET  content='Your OTP for registration for MahaExam is %s', updated_at=CURRENT_TIMESTAMP,
 sms_template_id='100000000361801',
 template_name='opt_verification'
WHERE template_name='new_registration' and template_type = 'sms';
         */
        newMsg="The OTP to reset your password on MahaExam is {var} Do Not Share With Anyone -EDUVAL";

        templateId = "1107176225555040970";
        newMsg = String.format(newMsg,
                 username, appUrl);
        System.out.println("newMsg==>"+newMsg);

        String uri = "http://bulk.powerstext.in/http-tokenkeyapi.php";

        String authentic_key = "383645647576616c3130301760434267";
        Map<String, String> data = new HashMap<>();
        data.put("authentic-key", authentic_key);
        data.put("senderid", senderId);
        data.put("route", "1");
        data.put("number", mobileNumber);
        data.put("message", newMsg);
        data.put("templateid", templateId);
//        data.put("senderid", authentic)
//        data.put("apirequest", "Text");


//        data.put("format", "JSON");


        String formData = data.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        try {
            // Create a custom SSLContext that trusts all certificates (for testing only)
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                            // Trust all client certificates (no validation)
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                            // Trust all server certificates (no validation)
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };
            sslContext.init(null, trustAllCerts, new SecureRandom());

            // Create HttpClient with custom SSL context
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formData))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Log or handle response
            System.out.println("Response: " + responseBody);
        } catch (Exception e) {
            // Detailed error logging
            System.err.println("Error sending SMS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String mobileNumber = "9890960765";
        String studentName = "Narendra";
        String username = "036207";
        String type = "new_registration";//"send_login_details";
        String newPassword = "0000";
        PowersTextSmsService powersTextSmsService = new PowersTextSmsService();
        powersTextSmsService.sendSmsToNewRegisteredStudents(mobileNumber, newPassword, username, type, newPassword);


    }
}