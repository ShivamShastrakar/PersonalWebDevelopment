--liquibase formatted sql
--changeset {narendra}:{id}

-- You have been registered successfully on MahaExam. Please click to {var} and use your username {var} to reset your password. -EDUVAL&templateid=1107176277191547990
INSERT INTO message_templates
( template_name, template_type, subject, content, status, deleted, created_at, updated_at, sms_template_id)
VALUES( 'user_registration_welcome_v1', 'SMS', 'Registration Successful - Welcome to MahaExam', 'You have been registered successfully on MahaExam. Please click to %s and use your username %s to reset your password. -EDUVAL', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1107176277191547990');

INSERT INTO message_templates
( template_name, template_type, subject, content, status, deleted, created_at, updated_at, sms_template_id)
VALUES( 'user_registration_welcome_v1', 'Email', 'Registration Successful - Welcome to MahaExam', 'You have been registered successfully on MahaExam. Please click to %s and use your username %s to reset your password. -EDUVAL', 'active', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '');




