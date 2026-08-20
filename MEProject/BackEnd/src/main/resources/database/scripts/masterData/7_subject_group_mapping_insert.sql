--liquibase formatted sql
--changeset {narendra}:{id}

--- PCM
INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCM'), (select subject_id from subject where subject_name ='PHYSICS'));

INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCM'), (select subject_id from subject where subject_name ='CHEMISTRY'));

INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCM'), (select subject_id from subject where subject_name ='MATHEMATICS'));


---- PCB
INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCB'), (select subject_id from subject where subject_name ='PHYSICS'));

INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCB'), (select subject_id from subject where subject_name ='CHEMISTRY'));

INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCB'), (select subject_id from subject where subject_name ='BIOLOGY'));




---- PCMB
INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCMB'), (select subject_id from subject where subject_name ='PHYSICS'));

INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCMB'), (select subject_id from subject where subject_name ='CHEMISTRY'));

INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCMB'), (select subject_id from subject where subject_name ='MATHEMATICS'));

INSERT INTO subject_group_mapping
(group_id, subject_id)
VALUES((select group_id from subject_group where group_name ='PCMB'), (select subject_id from subject where subject_name ='BIOLOGY'));