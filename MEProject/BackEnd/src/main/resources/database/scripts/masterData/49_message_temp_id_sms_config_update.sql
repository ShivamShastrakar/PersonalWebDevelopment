--liquibase formatted sql
--changeset {narendra}:{id}

UPDATE message_templates
SET sms_template_id='1107176225503269275'
WHERE template_name='opt_verification' AND template_type = 'sms';

update config set value='383645647576616c3130301760434267' where name='SMS_API_KEY';
update config set value='mahaexam.org.in' where name='SMS_APP_URL';
