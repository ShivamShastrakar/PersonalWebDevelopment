--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO question_type (que_type,created_by,created_at,updated_by,updated_at,deleted_at) VALUES
	 ('Theoretical',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL),
	 ('Mathematical',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL);


INSERT INTO question_difficulty_level (`level`,alias_name,created_by,created_at,updated_by,updated_at,deleted_at) VALUES
	 ('L-1','L1',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL),
	 ('L-2','L2',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL),
	 ('L-3','L3',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL);

INSERT INTO question_criteria (criteria,alias_name,created_by,created_at,updated_by,updated_at,deleted_at) VALUES
	 ('Understanding','U',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL),
	 ('Skill','S',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL),
	 ('Knowledge','K',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL),
	 ('Application','A',NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL);


INSERT INTO question_status (status_name,created_by,created_at,updated_by,updated_at,deleted_at) VALUES
	 ('Setter',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('Operator',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('Moderator',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('Confirm',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('Waiting',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('Not selected',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('Subject Expert',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('SCM',NULL,'2020-10-30 19:57:30.0',NULL,NULL,NULL),
	 ('Reject',18,'2020-10-31 14:59:14.0',NULL,NULL,NULL),
	 ('Duplicated',18,'2020-10-31 14:59:45.0',NULL,NULL,NULL);
INSERT INTO question_status (status_name,created_by,created_at,updated_by,updated_at,deleted_at) VALUES
	 ('Wrong Answer',18,'2020-11-03 18:21:20.0',NULL,NULL,NULL),
	 ('Curator',NULL,'2021-03-12 15:56:18.0',NULL,NULL,NULL);
