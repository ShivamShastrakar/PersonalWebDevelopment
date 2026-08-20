package com.mahaexam.common.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.common.model.Config;

@Service("smsService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class SmsService {
	
	private static final Logger LOG = LogManager.getLogger(SmsService.class);
	
	@Autowired
	private ConfigService configService;

	public String sendSms(String message, String numbers) {
		try {
			// Construct data
			String apiKeyValue = null;
			String sendSMSURL = null;
			String sender = null;
			Optional<Config> configOpt;
			if(Objects.nonNull(configService)) {
				configOpt = configService.findByName(ConfigService.SMS_API_KEY);
				apiKeyValue = configOpt.map(Config::getValue).orElse(null);
			}
			if (Objects.isNull(apiKeyValue)) {
				apiKeyValue = "qaBrKdSwW64-yHFLHJn25L9OYmwnFmgPMnqURHNu5E";
			}
			String testNumbers="917709599882";
			if(testNumbers.length()>numbers.length() && !numbers.startsWith("91")) {
				numbers="91"+numbers;
			}
			String apiKey = "apikey=" + apiKeyValue;
			// String message = "&message=" + "This is your message";
			// String sender = "&sender=" + "ENRLME";
			// String numbers = "&numbers=" + "918123456789";
			if(Objects.nonNull(configService)) {
				configOpt = configService.findByName(ConfigService.SMS_API_URL);
				sendSMSURL = configOpt.map(Config::getValue).orElse(null);
			}
//			String sendSMSURL = ApplicationPropertiesService.getPropertyStringValue(PropertyKeys.SMS_API_URL,
//					orgGroupId);
			if (Objects.isNull(sendSMSURL)) {
				sendSMSURL = "https://api.textlocal.in/send/?";
			}
			if(Objects.nonNull(configService)) {
				configOpt = configService.findByName(ConfigService.SMS_API_SENDER);
				sender = configOpt.map(Config::getValue).orElse(null);
			}
			if (Objects.isNull(sender)) {
				sender = "ENRLME";
			}
			
			
//			boolean isSmsEnabled = ApplicationPropertiesService.getPropertyBooleanValue(PropertyKeys.ENABLE_SMS,
//					orgGroupId);
			boolean isSmsEnabled = true;
			if(Objects.nonNull(configService)) {
				isSmsEnabled = configService.getBinaryBooleanConfig(ConfigService.ENABLE_SMS);
			}
			if(!isSmsEnabled) {
				LOG.info("message:"+message);
				return "SMS Messaage Logged";
			}
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL(sendSMSURL).openConnection();
			String data = apiKey +"&numbers=" + numbers + "&message=" + message + "&sender="+sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			LOG.info("data:"+data);
			LOG.info("Response:"+stringBuffer);
			return stringBuffer.toString();
		} catch (Exception e) {
//			System.out.println("Error SMS " + e);
			LOG.error("Error While Sending SMS ", e);
			return "Error " + e;
		}
	}
	
	public static void main(String[] args) {
		SmsService sendSms = new SmsService();
		//REGISTER
		String eMailBody="";
//		eMailBody="Dear User, %nThanks for Registration. %nYour PIN for login is {0}.%nPIN has also been sent to your registered email address.";
//		eMailBody = MessageFormat.format(eMailBody, "Anvika");//Candidate Name

		//Pending Fee
//		eMailBody="Dear {0}, %n%nYour fees for the {1} is pending. Please pay at the earliest. Please ignore if already paid.";
//		eMailBody = MessageFormat.format(eMailBody, "Narendra","Narendra Classes");//Candidate Name and Branch Name
//		
		//Application Fee Payment
//		eMailBody="Dear {0}%n%nYou have successfully made the payment for your application. Please visit \"My Payments\" section for the payment receipt. %n%nEnrol Me Team";
//		eMailBody = MessageFormat.format(eMailBody, "Anvika");//Candidate Name 
		
		//Application Submitted
//		eMailBody="Dear {0}%n%nYour application has been submitted via Enrol Me. You will be informed about the status changes.%n%nEnrol Me Team";
////		eMailBody = AppConstant.APP_SUBMIT_EMAIL_BODY;
//		eMailBody = MessageFormat.format(eMailBody, "Anvika");//Candidate Name 
//		
		//Program Fee Payment
//		eMailBody="Dear {0}, %n%nYou have successfully made a fee payment to {1} for your program.%n%nEnrol Me Team";
//		eMailBody = MessageFormat.format(eMailBody, "Anvika","Narendra Classes");//Candidate Name and Branch Name
		
		//Application Selected
//		eMailBody="Dear {0}, %n%nYour application has been selected. Please visit \"My Applications\" section for further action.%n%nEnrol Me Team";
//		eMailBody = MessageFormat.format(eMailBody, "Anvika");//Candidate Name
		
		//Application Rejected
//		eMailBody="Dear {0},%n%nYour application has been rejected. Please select the \"My Applications\" section for further action. %n%nEnrol Me Team";
//		eMailBody = MessageFormat.format(eMailBody, "Anvika");//Candidate Name
		
		

		//OnBoard
//		eMailBody="Dear {0}, %n%nYour admission in {1}, is successful. Please visit \"My Admissions\" section for any further action.%n%nEnrol Me Team";
//		eMailBody = MessageFormat.format(eMailBody, "Anvika","Narendra Classes");//Candidate Name and Branch Name
		
		//Reset Pin
//		eMailBody="Dear {0}, %nYour Pin Reset is successful. Your new PIN for login is {1}.PIN has also been sent to your registered email. http:// www.enrol-me.com";
//		eMailBody=AppConstant.APP_RESET_SMS_TEXT;
//		eMailBody = MessageFormat.format(eMailBody, "Anvika","1234");//Candidate Name and Branch Name
		
//		eMailBody = AppConstant.APP_REJECT_EMAIL_BODY;
//		eMailBody = "Dear {0}%n%nYour application has been submitted via Enrol Me.You will be informed about the status changes.%n%nEnrol Me Team";
//		eMailBody = "Dear User, %nThanks for Registration. %nYour PIN for login is {0}.%nPIN has also been sent to your registered email address.";
//		eMailBody = MessageFormat.format(eMailBody, "1212");//Candidate Name 
		
		//eMailBody="Dear {0},%n %n Your admission in {1}, is successful. Please visit \"My Admissions\" section for any further action.%n %nEnrol Me Team";
//		eMailBody = MessageFormat.format(eMailBody, "Narendra Classes");//Candidate Name
		
//		Admission Successful 
		
		//eMailBody=AppConstant.APP_ON_BOARD_EMAIL_BODY;
	 	
//		Pending Fee
		
		//eMailBody="Dear {0},%n %nYour fees for the {1} is pending. Please pay at the earliest. Please ignore if already paid.";
		
//		Application Fee Payment 
	//	eMailBody=AppConstant.APP_MONTHLY_FEE_EMAIL_BODY;
		
		
//		Application Submitted 
	//	eMailBody=AppConstant.APP_FEE_UPDATE_EMAIL_BODY;
//		eMailBody="Dear {0}%n %nYour revised fee for program {1} is Admission fee {2}/-, Program fee {3}/-.";
//		eMailBody="Dear {0}%n %nYour application has been submitted via Enrol Me. You will be informed about the status changes.%n %nEnrol Me Team";
	 	
//		Program Fee Payment 
//		eMailBody=AppConstant.APP_MONTHLY_FEE_EMAIL_BODY;
		
	 	
//		Application Selected 
//		eMailBody=AppConstant.APP_SELECTED_EMAIL_BODY;
		
		
//		Application Rejected 
//		eMailBody=AppConstant.APP_REJECT_EMAIL_BODY;
		
		
//		eMailBody="Dear {0},%n %nYour fees for the {1} is pending. Please pay at the earliest. Please ignore if already paid.";
//		eMailBody = MessageFormat.format(eMailBody, "Kamal Chatopadhya","Craft Tuition Afternoon","300","6000");//Candidate Name
//		eMailBody="Dear {0}%n %nYour revised Admission and Program fee for program {1} is Admision fee {2}/-, and Program fee {2}/- respectively.";
		//eMailBody = MessageFormat.format(eMailBody, "Kamal Chatopadhya","Craft Tuition Afternoon","300","6000");//Candidate Name
		
		
//		sendSms.sendSms(eMailBody, "9890960765");
		//sendSms.sendSms(eMailBody, "8788339683");
		
//		Dear {0}, %n%nKindly note, your program {1} has been suspended for today.%n%nTeam {2}
		//Dear KartikTest, %n%nKindly note, your program TestProgram has been suspended for today.%n%nTeam TestBranch
//		Dear {0}, %n%nKindly note, your program {1} has been suspended for tomorrow.%n%nTeam {2}
//		Dear {0}, %n%nKindly note, your program {1} has been suspended for the period from {2} to {3}.%n%nTeam {4}
//		
		//eMailBody="Dear User, %nThanks for Registration. %nYour User ID for login is {0}.%nLogin PIN will be sent to your registered phone and email address. ";
//		eMailBody="Dear {0}, %nYour registration process for www.enrol-me.com is pending. %nPlease call 9604188726 to complete the process.";
//		eMailBody="Dear {0}, %nWelcome to www.enrol-me.com. Biggest admission portal of India.%nPlease call 9604188726 to get started.";
//		eMailBody="Dear {0},%nYou have been added to www.enrol-me.com to enable online admission. %nWe will reach out for completing registration.";
//		eMailBody="Dear {0},%nHave you started accepting fees through www.enrol-me.com. If not then please call 9604188726. -Tracksoft";
//		eMailBody="Dear {0},%nWe are trying to reach you to complete your registration process for www.enrol-me.com. Please call 9604188726 to complete the process.";
//		eMailBody="Dear {0}%nYour association with the program {1} has been discontinued. -Tracksoft";
//		eMailBody="{0}%nTeam {1} wishes you a very Happy Birthday. -Tracksoft";
//		eMailBody="Dear {0},%n{1} has registered you or your dependent as a student.%nPlease visit www.enrol-me.com to reset your pin.%nEnrol-Me enables your admissions and fee payment. -Tracksoft";
//		eMailBody="Dear {0}, %n%nYour fees for the {1} is pending. Please pay at the earliest. Please ignore if already paid.";
//		eMailBody="Dear {0},%n %nYour fees for the {1} is pending. Please pay at the earliest. Please ignore if already paid.";
	//	eMailBody= AppConstant.APP_OFFLINE_REG_EMAIL_BODY;
//		eMailBody="{0}%nTeam {1} wishes you a very Happy Birthday. -Tracksoft";
//		eMailBody="{0}%nTeam {1} wishes you a very Happy Birthday. -Tracksoft";
//		eMailBody="Hello {0},%nAn invoice has been raised for{1}. The payment due date is {2}. Please ignore if already paid.%nEnrol me -Tracksoft";
//		eMailBody="Dear Admin, %nNew {0} organization successfully registered.%nPlease proceed with needful review & activation. -Tracksoft";
//Done		eMailBody="Dear {0}, %nCandidate has submitted a new application for the program {1}.%nPlease review the submitted application.%nEnromMe -Tracksoft";
//		eMailBody="Dear {0}, %nThe request to disburse your pending payment of Rs. {1} has been sent to the bank. %nEnrol-Me team -Tracksoft";
//		eMailBody="Dear Admin, %nNew {0} organization successfully registered.%nPlease proceed with needful review & activation. -Tracksoft";
		
//		eMailBody="Dear Admin,%nNew {0} organization successfully registered.%nPlease proceed with needful review %26 activation. -Tracksoft";
//		eMailBody="Dear User, %nThanks for Registration. %nYour User ID for login is {0}.%nLogin PIN will be sent to your registered phone and email address.";
		eMailBody="Dear {0},%nYou have been added to www.enrol-me.com to enable online admission. %nWe will reach out for completing registration.";
//		eMailBody = "Dear {0},%n%nYour application has been selected. Please visit \"My Applications\" section for further action.%n%n Enrol Me Team -Tracksoft";
//		eMailBody = "Dear {0}, %nWe are trying to reach you to complete your registration process for www.enrol-me.com. Please call 9604188726 to complete the process. -Tracksoft";
//		eMailBody = "Dear {0}%n%nYou have successfully made the payment for your application. Please visit \"My Payments\" section for the payment receipt. %n%nEnrol Me Team -Tracksoft";
//		eMailBody = "Dear {0},%n %nYour fees rupees {1} is pending for the {2} program. Please pay at the earliest. Please ignore if already paid. -Tracksoft -Tracksoft";
		
		eMailBody = MessageFormat.format(eMailBody, "TestUser","200","Test Program");//Candidate Name
		System.out.println("eMailBody===>"+eMailBody);
		sendSms.sendSms(eMailBody, "9890960765");
	}
}
