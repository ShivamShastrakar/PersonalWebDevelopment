package com.mahaexam.common.service;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.model.Config;

public interface ConfigService {

	String SMS_API_KEY = "SMS_API_KEY";

	String SMS_API_URL = "SMS_API_URL";

	String SMS_API_SENDER = "SMS_API_SENDER";

    String SMS_APP_URL = "SMS_APP_URL";
	
	String ENABLE_EMAIL = "ENABLE_EMAIL";
	
    String ENABLE_SMS = "ENABLE_SMS";
	
	String AZ_S3_BUCKET_NAME	= "AZ_S3_BUCKET_NAME";
	String AZ_S3_BUCKET_PACKAGE_IMG_FOLDER	= "AZ_S3_BUCKET_PACKAGE_IMG_FOLDER";
	String AZ_S3_BUCKET_PHOTO_IMG_FOLDER	= "AZ_S3_BUCKET_PHOTO_IMG_FOLDER";
    String AZ_S3_BUCKET_BULK_UPLOAD_FOLDER	= "AZ_S3_BUCKET_BULK_UPLOAD_FOLDER";

    String PAYU_CLIENT_ID = "PAYU_CLIENT_ID";

    String PAYU_CLIENT_SECRET = "PAYU_CLIENT_SECRET";

    String PAYU_MERCHANT_KEY = "PAYU_MERCHANT_KEY";

    String PAYU_SUCCESS_URL = "PAYU_SUCCESS_URL";

    String PAYU_FAILURE_URL="PAYU_FAILURE_URL";

    String PAYU_PAYMENT_LINK_URL = "PAYU_PAYMENT_LINK_URL";

    String PAYU_PAYMENT_LINK_SALT = "PAYU_PAYMENT_LINK_SALT";
    String PAYU_AUTH_TOKEN_URL = "PAYU_AUTH_TOKEN_URL";
    String APP_RESET_EMAIL_BODY= "Dear {0}, %nYour Pin Reset is successful. Your new PIN for login is {1}.PIN has also been sent to your registered mobile number. http:// www.enrol-me.com<br/><br/>Enrol Me Team";

    String PAYU_INVOICE_PREFIX = "PAYU_INVOICE_PREFIX";

    String CLOUD_FRONT_URL = "CLOUD_FRONT_URL";

    String STUDY_MATERIAL  = "STUDY_MATERIAL";

    String DEFAULT_SMS = "DEFAULT_SMS";

    String CONFIGURED_PAYMENT_GATEWAY = "CONFIGURED_PAYMENT_GATEWAY";

    String DEFAULT_PACKAGE = "DEFAULT_PACKAGE";

    String RAZOR_CLIENT_KEY = "RAZOR_CLIENT_KEY";
    String RAZOR_CLIENT_SECRET = "RAZOR_CLIENT_SECRET";
    String RAZOR_FAILURE_URL = "RAZOR_FAILURE_URL";
    String RAZOR_INVOICE_PREFIX = "RAZOR_INVOICE_PREFIX";
    String RAZOR_MERCHANT_KEY = "RAZOR_MERCHANT_KEY";
    String RAZOR_PAYMENT_LINK_SALT = "RAZOR_PAYMENT_LINK_SALT";
    String RAZOR_PAYMENT_LINK_URL = "RAZOR_PAYMENT_LINK_URL";
    String RAZOR_SUCCESS_URL = "RAZOR_SUCCESS_URL";


	Config save(Config config);

	Optional<Config> findByName(String name);
	
	Boolean getBinaryBooleanConfig(String name);

	List<Config> findAllActive();

	List<Config> findAllDeleted();

	Config update(Config config);

	void softDelete(String name);
}
