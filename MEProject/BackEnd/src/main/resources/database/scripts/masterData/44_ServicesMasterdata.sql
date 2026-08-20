--liquibase formatted sql
--changeset {narendra}:{id}

delete from services;

insert into services (id,service_name, service_details, service_type) values(1,'Online Test','Online Test for students',"Exam");
insert into services (id,service_name, service_details, service_type) values(2,'Online Learning','Online Learning for students',"Document");