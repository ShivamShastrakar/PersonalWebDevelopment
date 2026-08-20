package com.mahaexam.common.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.mahaexam.common.model.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mahaexam.common.config.SesConfig;
import com.mahaexam.common.model.EmailRequest;
import com.mahaexam.common.repo.EmailRequestRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SendRawEmailResponse;
import software.amazon.awssdk.services.ses.model.SesException;

@Service
@EnableScheduling
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final String fromEmail;
//    private final SesClient sesClient;
    private final EmailRequestRepository emailRequestRepository;
    @Autowired
    private SesClient sesClient;
    @Autowired
    private ConfigService configService;

    public EmailService(@Value("${spring.mail.from}") String fromEmail,
                       EmailRequestRepository emailRequestRepository) {
        this.fromEmail = fromEmail;
        this.emailRequestRepository = emailRequestRepository;
    }

    @Async
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void sendEmail(String to, String cc, String bcc, String subject, String body, boolean isHtml) {
        Optional<Config>  configOpt = configService.findByName(ConfigService.SMS_APP_URL);
        String appUrl = configOpt.map(Config::getValue).orElse(null);
        body = String.format(body, appUrl);
        EmailRequest emailRequest = EmailRequest.builder().build();
        emailRequest.setToAddresses(to);
        emailRequest.setCcAddresses(cc);
        emailRequest.setBccAddresses(bcc);
        emailRequest.setSubject(subject);
        emailRequest.setBody(body);
        emailRequest.setHtml(isHtml);
        emailRequest.setStatus(EmailRequest.EmailStatus.PENDING);
        emailRequestRepository.save(emailRequest);
        logger.info("Saved email request to DB for to: {}, cc: {}, bcc: {}", to, cc, bcc);
    }

    @Async
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public void sendEmailWithAttachment(String to, String cc, String bcc, String subject, String body, boolean isHtml,
                                       byte[] attachmentData, String attachmentName) {
        Optional<Config>  configOpt = configService.findByName(ConfigService.SMS_APP_URL);
        String appUrl = configOpt.map(Config::getValue).orElse(null);
        body = String.format(body, appUrl);
        EmailRequest emailRequest = EmailRequest.builder().build();
        emailRequest.setToAddresses(to);
        emailRequest.setCcAddresses(cc);
        emailRequest.setBccAddresses(bcc);
        emailRequest.setSubject(subject);
        emailRequest.setBody(body);
        emailRequest.setHtml(isHtml);
        emailRequest.setAttachmentData(attachmentData);
        emailRequest.setAttachmentName(attachmentName);
        emailRequest.setStatus(EmailRequest.EmailStatus.PENDING);
        emailRequestRepository.save(emailRequest);
        logger.info("Saved email request with attachment to DB for to: {}, cc: {}, bcc: {}", to, cc, bcc);
    }

    @Scheduled(fixedRate = 30000) // Run every 30 seconds
    public void processPendingEmails() {
        List<EmailRequest> pendingEmails = emailRequestRepository.findByStatus(EmailRequest.EmailStatus.PENDING);
        logger.info("Processing {} pending email requests", pendingEmails.size());

        for (EmailRequest emailRequest : pendingEmails) {
            try {
                // Parse email addresses
                List<String> toAddresses = parseEmailAddresses(emailRequest.getToAddresses());
                List<String> ccAddresses = parseEmailAddresses(emailRequest.getCcAddresses());
                List<String> bccAddresses = parseEmailAddresses(emailRequest.getBccAddresses());

                // Validate TO addresses
                if (toAddresses.isEmpty()) {
                    throw new IllegalArgumentException("At least one valid TO email address is required");
                }

                // Validate all email addresses
                validateEmailAddresses(toAddresses, "TO");
                validateEmailAddresses(ccAddresses, "CC");
                validateEmailAddresses(bccAddresses, "BCC");

                SendEmailRequest request = SendEmailRequest.builder()
                        .source(fromEmail)
                        .destination(Destination.builder()
                                .toAddresses(toAddresses)
                                .ccAddresses(ccAddresses)
                                .bccAddresses(bccAddresses)
                                .build())
                        .message(Message.builder().subject(Content.builder().data( emailRequest.getSubject()).build())
    							.body(Body.builder().text(Content.builder().data(emailRequest.getBody()).build())
    									.html(Content.builder().data(emailRequest.getBody()).build()).build())
    							.build())
                        .build();
/*
            	String awsAccessKeyId = System.getProperty("aws.accessKeyId"); 
        		String awsSecretAccessKey = System.getProperty("aws.secretAccessKey");
        		AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
        		Region regionObj =Region.of(region);
                try (SesClient sesClient = SesClient.builder().region(regionObj)
        				.credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build()) {
                	sesClient.sendEmail(request);
                }
                */
                sesClient.sendEmail(request);
                emailRequest.setStatus(EmailRequest.EmailStatus.SENT);
                emailRequest.setSentAt(LocalDateTime.now());
                emailRequest.setErrorMessage(null);
                logger.info("Email sent successfully to {}, cc {}, bcc {}", toAddresses, ccAddresses, bccAddresses);
            } catch (MessagingException | SesException e) {
                emailRequest.setStatus(EmailRequest.EmailStatus.FAILED);
                emailRequest.setErrorMessage(e.getMessage());
                logger.error("Failed to send email request ID {}: {}", emailRequest.getId(), e.getMessage());
            } finally {
                emailRequestRepository.save(emailRequest);
            }
        }
    }

    /**
     * Sends an email with optional attachment.
     *
     * @param from Sender email (verified in SES)
     * @param to Recipient email
     * @param subject Email subject
     * @param bodyText Email body (plain text or HTML)
     * @param attachment Optional MultipartFile for attachment
     * @throws IOException If attachment reading fails
     * @throws MessagingException If MIME construction fails
     */
    public String sendEmailWithAttachment(String from, String tos, String ccs, String bccs, String subject, String bodyText, MultipartFile attachment) 
            throws IOException, MessagingException {

        // Create a MIME session
        Session session = Session.getDefaultInstance(new Properties());
        
        List<String> toAddresses = parseEmailAddresses(tos);
        List<String> ccAddresses = parseEmailAddresses(ccs);
        List<String> bccAddresses = parseEmailAddresses(bccs);

        // Validate TO addresses
        if (toAddresses.isEmpty()) {
            throw new IllegalArgumentException("At least one valid TO email address is required");
        }

        // Validate all email addresses
        validateEmailAddresses(toAddresses, "TO");
        validateEmailAddresses(ccAddresses, "CC");
        validateEmailAddresses(bccAddresses, "BCC");


        if (toAddresses == null || toAddresses.isEmpty()) {
            throw new IllegalArgumentException("At least one TO address is required");
        }


        // Create the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        
        // Add TO recipients
        for (String to : toAddresses) {
            message.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        }
        
        // Add CC recipients (if any)
        for (String cc : ccAddresses) {
            message.addRecipient(jakarta.mail.Message.RecipientType.CC, new InternetAddress(cc));
        }
        
        // Add BCC recipients (if any)
        for (String bcc : bccAddresses) {
            message.addRecipient(jakarta.mail.Message.RecipientType.BCC, new InternetAddress(bcc));
        }
        
        message.setSubject(subject);

        
        // Create multipart message
        MimeMultipart multipart = new MimeMultipart();

        // Add body part
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(bodyText);  // Or use setContent(bodyText, "text/html") for HTML
        multipart.addBodyPart(bodyPart);

        // Add attachment if provided
        if (attachment != null && !attachment.isEmpty()) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setFileName(attachment.getOriginalFilename());
            attachmentPart.setContent(attachment.getBytes(), attachment.getContentType());
            multipart.addBodyPart(attachmentPart);
        }

        // Set the multipart content
        message.setContent(multipart);

        // Convert to raw bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        SdkBytes data = SdkBytes.fromByteArray(outputStream.toByteArray());

        // Build and send raw email request
        RawMessage rawMessage = RawMessage.builder().data(data).build();
        SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                .rawMessage(rawMessage)
                .build();

        SendRawEmailResponse response = sesClient.sendRawEmail(rawEmailRequest);
        return "Email sent successfully! Message ID: " + response.messageId();
    }
    
    private List<String> parseEmailAddresses(String emailString) {
        if (emailString == null || emailString.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(emailString.split(","))
                .map(String::trim)
                .filter(email -> !email.isEmpty())
                .collect(Collectors.toList());
    }

    private void validateEmailAddresses(List<String> addresses, String type) throws MessagingException {
        for (String email : addresses) {
            try {
                new InternetAddress(email).validate();
            } catch (MessagingException e) {
                throw new IllegalArgumentException("Invalid " + type + " email address: " + email, e);
            }
        }
    }
}