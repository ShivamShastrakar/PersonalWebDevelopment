--liquibase formatted sql
--changeset {narendra}:{id}


INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('AC Member', 'AC Member', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Taluka Coordinator', 'Taluka Coordinator', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('GC Member', 'GC Member', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Channel Partner', 'Channel Partner', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Institute', 'Institute', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Teacher', 'Teacher', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Student', 'Student', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Brand Loyalty', 'Brand Loyalty', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Fixed Deposit', 'Fixed Deposit', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Magazine', 'Magazine', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Rural Education', 'Rural Education', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Scholarships', 'Scholarships', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO `role`
(name, description, is_active, is_assignable, created_at)
VALUES('Principal', 'Principal', 1, 1, CURRENT_TIMESTAMP);
