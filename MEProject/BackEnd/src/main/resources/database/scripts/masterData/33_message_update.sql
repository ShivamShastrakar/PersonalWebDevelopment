--liquibase formatted sql
--changeset {narendra}:{id}

UPDATE message_templates
SET  content='Your OTP for registration for MahaExam is %s', updated_at=CURRENT_TIMESTAMP,
 sms_template_id='100000000361801',
 template_name='opt_verification'
WHERE template_name='new_registration' and template_type = 'sms';

UPDATE message_templates
SET  content='The OTP to reset your password for MahaExam is %s', updated_at=CURRENT_TIMESTAMP, sms_template_id='100000000361802'
WHERE template_name='reset_password' and template_type = 'sms';

UPDATE message_templates
SET content='Dear Student,<br/><br/>Welcome to MahaExam! We''re excited to have you join our platform.<br/><br/>To complete your registration, please use the verification code below:<br/><br/>Your Registration OTP: %s<br/><br/>This OTP is valid for 10 minutes only. Please enter it on the registration page to activate your account.<br/><br/><strong>Important Security Notes:</strong><br/>• Keep this OTP confidential - never share it with anyone<br/>• MahaExam staff will never ask for your OTP via phone or email<br/>• If you didn''t attempt to register, please ignore this email<br/><br/>Welcome aboard!<br/><br/>Best regards,<br/>MahaExam Team',
    updated_at=CURRENT_TIMESTAMP,
    subject='Your MahaExam Registration OTP'
WHERE template_name='opt_verification' AND template_type = 'email';

UPDATE message_templates
SET content='Dear %s,<br/><br/>We received a request to reset the password for your MahaExam account associated with this email address.<br/><br/>Your OTP for password reset is: %s<br/><br/>This OTP is valid for the next 10 minutes only. Please use it to complete your password reset process immediately.<br/><br/><strong>Security Instructions:</strong><br/>• Do not share this OTP with anyone<br/>• MahaExam staff will never ask for your OTP over phone or email<br/>• If you didn''t request this password reset, please ignore this email<br/><br/>Best regards,<br/>MahaExam Support Team',
    updated_at=CURRENT_TIMESTAMP,
    subject='Password Reset OTP - MahaExam'
WHERE template_name='reset_password' AND template_type = 'email';





