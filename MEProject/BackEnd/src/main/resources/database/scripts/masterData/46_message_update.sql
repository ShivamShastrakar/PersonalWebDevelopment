--liquibase formatted sql
--changeset {narendra}:{id}


UPDATE message_templates
SET content='Dear User,<br/><br/>Welcome to MahaExam! We''re excited to have you join our platform.<br/><br/>To complete your registration, please use the verification code below:<br/><br/>Your Registration OTP: %s<br/><br/>This OTP is valid for 10 minutes only. Please enter it on the registration page to activate your account.<br/><br/><strong>Important Security Notes:</strong><br/>• Keep this OTP confidential - never share it with anyone<br/>• MahaExam staff will never ask for your OTP via phone or email<br/>• If you didn''t attempt to register, please ignore this email<br/><br/>Welcome aboard!<br/><br/>Best regards,<br/>MahaExam Team',
    updated_at=CURRENT_TIMESTAMP,
    subject='Your MahaExam Registration OTP'
WHERE template_name='opt_verification' AND template_type = 'email';

UPDATE message_templates
SET content='The OTP to complete your registration on MahaExam is %s To login click %s -EDUVAL',
    updated_at=CURRENT_TIMESTAMP,
    subject='Your MahaExam Registration OTP',
    sms_template_id='1107176225555040970'
WHERE template_name='opt_verification' AND template_type = 'sms';


INSERT INTO message_templates
( template_name, template_type, subject, content, status, deleted, created_at, updated_at, sms_template_id)
VALUES( 'user_registration_welcome', 'Email', 'Registration Successful - Welcome to MahaExam', 'Dear %s,<br/>Welcome to MahaExam! We''re excited to have you join our platform.<br/>You are successfully registered to MahaExam with user name - "%s"<br/>Welcome aboard!<br/>Best regards,<br/>MahaExam Team', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '');

-- Welcome to MahaExam. Your registration is successful. Your username is {var} To login click {var} -EDUVAL&templateid=1107176225507504239
INSERT INTO message_templates
( template_name, template_type, subject, content, status, deleted, created_at, updated_at, sms_template_id)
VALUES( 'user_registration_welcome', 'SMS', 'Registration Successful - Welcome to MahaExam', 'Welcome to MahaExam. Your registration is successful. Your username is %s To login click %s -EDUVAL', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1107176225507504239');




