--liquibase formatted sql
--changeset {narendra}:{id}

UPDATE message_templates
SET  content='The OTP to reset your password on MahaExam is {var} Do Not Share With Anyone -EDUVAL', updated_at=CURRENT_TIMESTAMP, sms_template_id='1107176225555040970'
WHERE template_name='reset_password' and template_type = 'sms';
