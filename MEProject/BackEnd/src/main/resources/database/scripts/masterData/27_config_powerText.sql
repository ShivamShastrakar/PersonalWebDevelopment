--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at, deleted)
VALUES('SMS_APP_URL', '3.110.16.23/MahaExam/login', CURRENT_TIMESTAMP, '0');

update config set value = '3737444545504552534d533130301710583911' where name='SMS_API_KEY';

update config set value = 'http://bulk.powerstext.in/http-tokenkeyapi.php' where name='SMS_API_URL';

update config set value = 'TXXTOO' where name='SMS_API_SENDER';


INSERT INTO message_templates
(template_name, template_type, subject, content, status, deleted, created_at, updated_at, sms_template_id)
VALUES( 'new_registration', 'SMS', '', 'Hello %s, Thanks for registering with DEEPER Your login details: Username: %s Password: Your registered mobile number Url: %s For any queries , support@deeper.org.in Text2', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1607100000000277862');

INSERT INTO message_templates
(template_name, template_type, subject, content, status, deleted, created_at, updated_at, sms_template_id)
VALUES( 'reset_password', 'SMS', '', 'Hello %s Your password has been reset. Your new password is %s For any queries, support@deeper.org.in Text2', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1607100000000318204');

INSERT INTO message_templates
(template_name, template_type, subject, content, status, deleted, created_at, updated_at)
VALUES( 'reset_password', 'EMAIL', '', 'Hello %s Your password has been reset. Your new password is %s For any queries, support@deeper.org.in Text2', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO message_templates
(template_name, template_type, subject, content, status, deleted, created_at, updated_at)
VALUES('opt_verification', 'EMAIL', 'Your verification code” and the content should be -', 'Hi  %s,<br/>We received your request for a verification code to register on MahaExam as a sudent.<br/>Your single-use code is: %s<br/>If you didn''t request this code, you can safely ignore this email. Someone else might have typed your email address by mistake.<br/>Thanks,<br/>The MahaExam team', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
