--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO application_user
(user_id, user_type, first_name, last_name, middle_name, gender, registered_mobile_number, whatsapp_number, email, created_at)
VALUES( 100, 'TechAdmin', 'Tech', 'Admin', '', 'MALE',  '9890960765', '9890960765', 'narendra.chouhan129@gmail.com', CURRENT_TIMESTAMP);

INSERT INTO application_user
(user_id, user_type, first_name, last_name, middle_name, gender, registered_mobile_number, whatsapp_number, email, created_at)
VALUES( 101, 'ME-Admin', 'ME', 'Admin', '', 'MALE',  '9890960765', '9890960765', 'narendra.chouhan129@gmail.com', CURRENT_TIMESTAMP);