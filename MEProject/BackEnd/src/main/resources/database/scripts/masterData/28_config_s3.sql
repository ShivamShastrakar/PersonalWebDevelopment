--liquibase formatted sql
--changeset {narendra}:{id}

UPDATE  config set value ='eduval-other' where name ='AZ_S3_BUCKET_NAME';

INSERT INTO config
(name, value, created_at,  deleted)
VALUES('STUDY_MATERIAL', 'eduvel-study-material', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at,  deleted)
VALUES('CLOUD_FRONT_URL', 'http://dnpqb8e27pyzw.cloudfront.net/', CURRENT_TIMESTAMP, '0');