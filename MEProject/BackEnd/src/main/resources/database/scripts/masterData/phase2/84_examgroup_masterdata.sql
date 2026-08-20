--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO mahaexam.exam_group_dtls
(name, description)
VALUES('Scholarship', 'Scholarship Exams'),
('PCM', 'PCM'),
('PCB', 'PCB'),
('PCMB', 'PCMB');