--liquibase formatted sql
--changeset {narendra}:{id}

delete from role_menu_permission where 1=1;
delete from menus where 1=1;
delete from permission where 1=1;



-- 1. INSERTS FOR menu table
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (1, 'Configure', NULL, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (2, 'System', 1, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (3, 'Overview', 2, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (4, 'Email', 2, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (5, 'SMS', 2, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (6, 'User', 2, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (7, 'Whatsapp', 2, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (8, 'Roles', 1, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (9, 'Fees Distribution', 1, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (10, 'Paper Rate', 1, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (11, 'Data Upload', 1, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (12, 'Student Excel', 11, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (13, 'Organization', NULL, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (14, 'Academics', 13, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (15, 'Subjects', 14, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (16, 'Chapters', 14, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (17, 'Topics', 14, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (18, 'Question Type', 14, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (19, 'Question Diffculty Level', 14, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (20, 'Question Criteria', 14, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (21, 'Question Status', 14, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (22, 'Application Users', 13, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (23, 'Channel Partners', 22, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (24, 'Network Partners', 22, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (25, 'Students', 22, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (26, 'Circular', 13, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (27, 'Events', 13, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (28, 'Events', 27, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (29, 'Paper Setting Events', 27, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (30, 'Entities', 13, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (31, 'Boards', 30, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (32, 'Classes', 30, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (33, 'Categories', 30, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (34, 'Parallel Reservation', 30, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (35, 'Feedbacks', 30, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (36, 'Levels', 13, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (37, 'States', 36, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (38, 'Divisions', 36, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (39, 'Districts', 36, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (40, 'Taluka', 36, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (41, 'Education Society', 36, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (42, 'Institutes', 36, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (43, 'Admission Probability Institutes', 36, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (44, 'Offerings', 13, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (45, 'Courses', 44, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (46, 'Services', 44, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (47, 'Offers', 44, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (48, 'Targets', 13, 8);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (49, 'Channel Partners', 48, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (50, 'Staff', 48, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (51, 'Institutes', 48, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (52, 'Donation', NULL, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (53, 'Gifts', 52, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (54, 'Donate', 52, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (55, 'Teachers', NULL, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (56, 'MCQ', 55, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (57, 'Question Curation', 56, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (58, 'Setter', 56, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (59, 'Operator', 56, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (60, 'Moderator', 56, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (61, 'Subject Expert', 56, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (62, 'Subject Council Member', 56, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (63, 'Incentive Distribution', 56, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (64, 'MCQ Settings', 56, 8);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (65, 'Training', 55, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (66, 'Subject Training', 65, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (67, 'Notes', 55, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (68, 'Creator', 67, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (69, 'Moderator', 67, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (70, 'Subject Expert', 67, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (71, 'Subject council Member', 67, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (72, 'Incentive Distribution', 67, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (73, 'Allot to Packages', 67, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (74, 'Teachers', 67, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (75, 'Video Upload', 67, 8);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (76, 'Document', 67, 9);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (77, 'Lectures', 55, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (78, 'Students', NULL, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (79, 'All students', 78, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (80, 'Students without Data', 78, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (81, 'Manage Students', 78, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (82, 'Manage Student', 81, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (83, 'View Deleted Student', 81, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (84, 'Fees', NULL, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (85, 'Offline Settlement', 84, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (86, 'Institute', 85, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (87, 'Channel partner', 85, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (88, 'Exams', NULL, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (89, 'Offline Exams', 88, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (90, 'Assessment', 89, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (91, 'Practice', 89, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (92, 'Mocktest', 89, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (93, 'Online (CBT) Exams', 88, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (94, 'Assessment', 93, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (95, 'Practice', 93, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (96, 'Mocktest', 93, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (97, 'Online Exams', 88, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (98, 'Assessment', 97, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (99, 'Practice', 97, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (100, 'Mocktest', 97, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (101, 'SOP', 88, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (102, 'Merge Exam Result', 88, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (103, 'Exam Management', 88, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (104, 'Cut Off', 103, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (105, 'Question Sorter', 88, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (106, 'Question Bank', 88, 8);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (107, 'Detailed Analysis', 106, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (108, 'Quick Analysis', 106, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (109, 'Packages', NULL, 8);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (110, 'Overview', 109, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (111, 'Packages', 109, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (112, 'Prepare', 111, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (113, 'Practice', 111, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (114, 'Evalute', 111, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (115, 'Package Rules', 109, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (116, 'Manage Student Packages', 109, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (117, 'Doubts', NULL, 9);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (118, 'Lead', NULL, 10);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (119, 'Report', NULL, 11);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (120, 'Administration', 119, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (121, 'Academic Expertise', 120, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (122, 'Accounting Report', 120, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (123, 'Committee', 120, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (124, 'Enrollment', 120, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (125, 'Student Enrollment Report', 120, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (126, 'Districtwise Report', 120, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (127, 'GST Report', 120, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (128, 'MCQ Report', 120, 8);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (129, 'Settlement Report', 120, 9);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (130, 'Subscribers Report', 120, 10);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (131, 'Students Address Sticker Report', 120, 11);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (132, 'Targets', 120, 12);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (133, 'Students Wallet Money', 120, 13);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (134, 'Examwise Report', 120, 14);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (135, 'Exam Attendance Report', 120, 15);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (136, 'Institute Student Fee Report', 120, 16);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (137, 'Students Without Package', 120, 17);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (138, 'Institute Result Overview', 120, 18);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (139, 'Institute Result', 120, 19);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (140, 'Network Vacancies', 120, 20);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (141, 'Donation Requests', 120, 21);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (142, 'Adoption Awaited', 120, 22);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (143, 'Adoption Completed', 120, 23);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (144, 'Fees Distribution', 120, 24);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (145, 'Transaction details', 120, 25);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (146, 'Exam', 119, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (147, 'Bench Stickers', 146, 1);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (148, 'Hall Tickets', 146, 2);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (149, 'Block wise chart', 146, 3);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (150, 'Center Count', 146, 4);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (151, 'Centre wise Student Count', 146, 5);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (152, 'Centre wise Student list', 146, 6);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (153, 'Centres Roll No.', 146, 7);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (154, 'Center Incharge list', 146, 8);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (155, 'Center Incharge Card', 146, 9);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (156, 'Centre Expense allocation Sheet', 146, 10);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (157, 'Centre ATM List', 146, 11);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (158, 'Center Sticker', 146, 12);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (159, 'Center Sticker A4', 146, 13);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (160, 'Centre Incharge letter', 146, 14);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (161, 'Collegewise Student list', 146, 15);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (162, 'Princpal sticker', 146, 16);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (163, 'Remuneration Letter', 146, 17);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (164, 'Principal Letter', 146, 18);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (165, 'Centrerwise Cost Report', 146, 19);
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES (166, 'Student and Institute', 146, 20);

-- 2. INSERTS FOR permission table (unique names from Excel columns F-Z, ignoring Phase)
INSERT INTO permission (permission_id,name,description,created_at,`type`) VALUES
	 (1,'View','View','2025-07-19 02:42:00.0','Page'),
	 (2,'Search','Search','2025-07-19 02:42:00.0','Page'),
	 (3,'Filters','Filters','2025-07-19 02:42:00.0','Page'),
	 (4,'Excel','Excel','2025-07-19 02:42:00.0','Page'),
	 (5,'PDF','PDF','2025-07-19 02:42:00.0','Page'),
	 (6,'Add','Add','2025-07-19 02:42:00.0','Page'),
	 (7,'View Detail','View Detail','2025-07-19 02:42:00.0','Row'),
	 (8,'Edit','Edit','2025-07-19 02:42:00.0','Row'),
	 (9,'Delete','Send Message','2025-07-19 02:42:00.0','Row'),
	 (10,'Send Message','Send Mess','2025-07-19 02:42:00.0','Row');
INSERT INTO permission (permission_id,name,description,created_at,`type`) VALUES
	 (11,'Update MCQ Acess','Update MCQ Acess','2025-07-19 02:42:00.0','Row'),
	 (12,'Import Question','Import Question','2025-07-19 02:42:00.0','Row'),
	 (13,'Notes History','Notes History','2025-07-19 02:42:00.0','Row'),
	 (14,'View Feedback','View Feedback','2025-07-19 02:42:00.0','Row'),
	 (15,'Package Switch','Switch Package','2025-07-19 02:42:00.0','Row'),
	 (16,'Class Switch','Switch Class','2025-07-19 02:42:00.0','Row'),
	 (17,'Reset Pasward','Reset Pwd','2025-07-19 02:42:00.0','Row'),
	 (18,'Send Login Details','Send Login Details','2025-07-19 02:42:00.0','Row'),
	 (19,'Add package','Add package','2025-07-19 02:42:00.0','Row'),
	 (20,'Update Status','Update Status','2025-07-19 02:42:00.0','Row');
INSERT INTO permission (permission_id,name,description,created_at,`type`) VALUES
	 (21,'Impersonate','Impersonate','2025-07-19 02:42:00.0','Row'),
	 (22,'Download','Download','2025-07-19 02:42:00.0','Row');


-- 3. INSERTS FOR role_menu_permission table (only where Excel value=1 for role_id=2)
-- Configure (menu_id=1): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 1, 1);
-- System (menu_id=2): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 2, 1);
-- Overview (menu_id=3): View (Page)=1, Edit=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 3, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 3, 8);
-- Email (menu_id=4): View (Page)=1, Edit=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 4, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 4, 8);
-- SMS (menu_id=5): View (Page)=1, Edit=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 5, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 5, 8);
-- User (menu_id=6): View (Page)=1, Edit=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 6, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 6, 8);
-- Whatsapp (menu_id=7): View (Page)=1, Edit=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 7, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 7, 8);
-- Roles (menu_id=8): View (Page)=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 8, 20);
-- Fees Distribution (menu_id=9): View (Page)=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 9, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 9, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 9, 5);
-- Paper Rate (menu_id=10): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 10, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 10, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 10, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 10, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 10, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 10, 8);
-- Data Upload (menu_id=11): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 11, 1);
-- Student Excel (menu_id=12): View (Page)=1, Excel=1, PDF=1, Download=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 12, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 12, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 12, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 12, 22);
-- Organization (menu_id=13): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 13, 1);
-- Academics (menu_id=14): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 14, 1);
-- Subjects (menu_id=15): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 9);
-- Chapters (menu_id=16): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 16, 9);
-- Topics (menu_id=17): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 17, 9);
-- Question Type (menu_id=18): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 18, 9);
-- Question Diffculty Level (menu_id=19): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 19, 9);
-- Question Criteria (menu_id=20): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 20, 9);
-- Question Status (menu_id=21): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 21, 9);
-- Application Users (menu_id=22): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 22, 1);
-- Channel Partners (menu_id=23): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Switch Class=1, Reset Pwd=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 20);
-- Network Partners (menu_id=24): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Send Mess=1, MCQ Acess=1, Switch Class=1, Reset Pwd=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 11);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 24, 20);
-- Students (menu_id=25): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Send Mess=1, Switch Class=1, Reset Pwd=1, Send Login Details=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 18);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 25, 20);
-- Circular (menu_id=26): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 26, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 26, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 26, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 26, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 26, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 26, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 26, 9);
-- Events (menu_id=27): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 27, 1);
-- Events (menu_id=28): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 9);
-- Paper Setting Events (menu_id=29): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 29, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 29, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 29, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 29, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 29, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 29, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 29, 9);
-- Entities (menu_id=30): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 30, 1);
-- Boards (menu_id=31): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 9);
-- Classes (menu_id=32): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 32, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 32, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 32, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 32, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 32, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 32, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 32, 9);
-- Categories (menu_id=33): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 33, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 33, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 33, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 33, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 33, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 33, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 33, 9);
-- Parallel Reservation (menu_id=34): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 34, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 34, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 34, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 34, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 34, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 34, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 34, 9);
-- Feedbacks (menu_id=35): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 35, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 35, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 35, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 35, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 35, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 35, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 35, 9);
-- Levels (menu_id=36): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 36, 1);
-- States (menu_id=37): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 9);
-- Divisions (menu_id=38): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 38, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 38, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 38, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 38, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 38, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 38, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 38, 9);
-- Districts (menu_id=39): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 39, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 39, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 39, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 39, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 39, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 39, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 39, 9);
-- Taluka (menu_id=40): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 40, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 40, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 40, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 40, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 40, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 40, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 40, 9);
-- Education Society (menu_id=41): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 41, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 41, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 41, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 41, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 41, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 41, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 41, 9);
-- Institutes (menu_id=42): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 42, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 42, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 42, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 42, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 42, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 42, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 42, 9);
-- Admission Probability Institutes (menu_id=43): View (Page)=1, Search=1, Filters=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 43, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 43, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 43, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 43, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 43, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 43, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 43, 9);
-- Offerings (menu_id=44): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 44, 1);
-- Courses (menu_id=45): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 9);
-- Services (menu_id=46): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 46, 9);
-- Offers (menu_id=47): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 47, 9);
-- Targets (menu_id=48): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 48, 1);
-- Channel Partners (menu_id=49): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, Edit=1, Delete=1, Send Mess=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 20);
-- Staff (menu_id=50): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, Edit=1, Delete=1, Send Mess=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 50, 20);
-- Institutes (menu_id=51): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, Edit=1, Delete=1, Send Mess=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 51, 20);
-- Donation (menu_id=52): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 52, 1);
-- Gifts (menu_id=53): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 53, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 53, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 53, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 53, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 53, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 53, 6);
-- Donate (menu_id=54): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 54, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 54, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 54, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 54, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 54, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 54, 6);
-- Teachers (menu_id=55): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 55, 1);
-- MCQ (menu_id=56): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 56, 1);
-- Question Curation (menu_id=57): Import Q=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 57, 12);
-- Setter (menu_id=58): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 7);
-- Operator (menu_id=59): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 59, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 59, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 59, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 59, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 59, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 59, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 59, 7);
-- Moderator (menu_id=60): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 60, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 60, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 60, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 60, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 60, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 60, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 60, 7);
-- Subject Expert (menu_id=61): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 61, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 61, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 61, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 61, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 61, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 61, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 61, 7);
-- Subject Council Member (menu_id=62): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 62, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 62, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 62, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 62, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 62, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 62, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 62, 7);
-- Incentive Distribution (menu_id=63): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 63, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 63, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 63, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 63, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 63, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 63, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 63, 7);
-- MCQ Settings (menu_id=64): Import Q=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 64, 12);
-- Training (menu_id=65): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 65, 1);
-- Subject Training (menu_id=66): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Edit=1, Delete=1, Notes History=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 13);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 20);
-- Notes (menu_id=67): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 67, 1);
-- Creator (menu_id=68): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Notes History=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 13);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 20);
-- Moderator (menu_id=69): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Notes History=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 13);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 69, 20);
-- Subject Expert (menu_id=70): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Notes History=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 13);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 70, 20);
-- Subject council Member (menu_id=71): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Notes History=1, Add package=1, Update Status=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 13);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 71, 20);
-- Incentive Distribution (menu_id=72): View (Page)=1, Search=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 72, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 72, 2);
-- Allot to Packages (menu_id=73): View (Page)=1, Search=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 73, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 73, 2);
-- Teachers (menu_id=74): View (Page)=1, Search=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 74, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 74, 2);
-- Video Upload (menu_id=75): View (Page)=1, Search=1, Add=1, Add package=1, Update Status=1, Download=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 75, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 75, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 75, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 75, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 75, 20);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 75, 22);
-- Document (menu_id=76): View (Page)=1, Search=1, Add=1, Add package=1, Update Status=1, Download=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 76, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 76, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 76, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 76, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 76, 20);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 76, 22);
-- Lectures (menu_id=77): View (Page)=1, Search=1, Filters=1, Excel=1, Add=1, View (Item)=1, Edit=1, Delete=1, Download=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 77, 22);
-- All students (menu_id=79): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Send Mess=1, Switch Package=1, Switch Class=1, Reset Pwd=1, Send Login Details=1, Add package=1, Impersonate=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 15);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 18);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 79, 21);
-- Students without Data (menu_id=80): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, View (Item)=1, Edit=1, Delete=1, Send Mess=1, Switch Package=1, Switch Class=1, Reset Pwd=1, Send Login Details=1, Add package=1, Impersonate=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 15);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 18);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 80, 21);
-- Manage Students (menu_id=81): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Send Mess=1, Switch Class=1, Reset Pwd=1, Send Login Details=1, Add package=1, Impersonate=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 18);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 81, 21);
-- Manage Student (menu_id=82): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Send Mess=1, Switch Class=1, Reset Pwd=1, Send Login Details=1, Add package=1, Impersonate=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 18);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 82, 21);
-- View Deleted Student (menu_id=83): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1, Add=1, View (Item)=1, Edit=1, Delete=1, Send Mess=1, Switch Package=1, Switch Class=1, Reset Pwd=1, Send Login Details=1, Add package=1, Impersonate=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 5);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 6);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 7);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 8);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 9);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 10);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 15);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 16);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 17);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 18);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 19);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 83, 21);
-- Fees (menu_id=84): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 84, 1);
-- Offline Settlement (menu_id=85): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 85, 1);
-- Institute (menu_id=86): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 86, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 86, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 86, 3);
-- Channel partner (menu_id=87): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 87, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 87, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 87, 3);
-- Exams (menu_id=88): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 88, 1);
-- Offline Exams (menu_id=89): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 89, 1);
-- Assessment (menu_id=90): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 90, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 90, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 90, 3);
-- Practice (menu_id=91): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 91, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 91, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 91, 3);
-- Mocktest (menu_id=92): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 92, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 92, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 92, 3);
-- Online (CBT) Exams (menu_id=93): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 93, 1);
-- Assessment (menu_id=94): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 94, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 94, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 94, 3);
-- Practice (menu_id=95): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 95, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 95, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 95, 3);
-- Mocktest (menu_id=96): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 96, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 96, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 96, 3);
-- Online Exams (menu_id=97): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 97, 1);
-- Assessment (menu_id=98): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 98, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 98, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 98, 3);
-- Practice (menu_id=99): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 99, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 99, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 99, 3);
-- Mocktest (menu_id=100): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 100, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 100, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 100, 3);
-- SOP (menu_id=101): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 101, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 101, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 101, 3);
-- Merge Exam Result (menu_id=102): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 102, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 102, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 102, 3);
-- Exam Management (menu_id=103): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 103, 1);
-- Cut Off (menu_id=104): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 104, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 104, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 104, 3);
-- Question Sorter (menu_id=105): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 105, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 105, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 105, 3);
-- Question Bank (menu_id=106): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 106, 1);
-- Detailed Analysis (menu_id=107): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 107, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 107, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 107, 3);
-- Quick Analysis (menu_id=108): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 108, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 108, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 108, 3);
-- Packages (menu_id=109): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 109, 1);
-- Overview (menu_id=110): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 110, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 110, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 110, 3);
-- Packages (menu_id=111): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 111, 1);
-- Prepare (menu_id=112): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 112, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 112, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 112, 3);
-- Practice (menu_id=113): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 113, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 113, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 113, 3);
-- Evalute (menu_id=114): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 114, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 114, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 114, 3);
-- Package Rules (menu_id=115): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 115, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 115, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 115, 3);
-- Manage Student Packages (menu_id=116): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 116, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 116, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 116, 3);
-- Doubts (menu_id=117): View (Page)=1, Search=1, Filters=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 117, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 117, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 117, 3);
-- Lead (menu_id=118): No permissions with 1 (all 0)
-- (No inserts for menu_id=118)
-- Report (menu_id=119): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 119, 1);
-- Administration (menu_id=120): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 120, 1);
-- Academic Expertise (menu_id=121): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 121, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 121, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 121, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 121, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 121, 5);
-- Accounting Report (menu_id=122): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 122, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 122, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 122, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 122, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 122, 5);
-- Committee (menu_id=123): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 123, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 123, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 123, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 123, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 123, 5);
-- Enrollment (menu_id=124): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 124, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 124, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 124, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 124, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 124, 5);
-- Student Enrollment Report (menu_id=125): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 125, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 125, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 125, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 125, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 125, 5);
-- Districtwise Report (menu_id=126): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 126, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 126, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 126, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 126, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 126, 5);
-- GST Report (menu_id=127): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 127, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 127, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 127, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 127, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 127, 5);
-- MCQ Report (menu_id=128): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 128, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 128, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 128, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 128, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 128, 5);
-- Settlement Report (menu_id=129): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 129, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 129, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 129, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 129, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 129, 5);
-- Subscribers Report (menu_id=130): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 130, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 130, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 130, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 130, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 130, 5);
-- Students Address Sticker Report (menu_id=131): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 131, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 131, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 131, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 131, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 131, 5);
-- Targets (menu_id=132): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 132, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 132, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 132, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 132, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 132, 5);
-- Students Wallet Money (menu_id=133): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 133, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 133, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 133, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 133, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 133, 5);
-- Examwise Report (menu_id=134): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 134, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 134, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 134, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 134, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 134, 5);
-- Exam Attendance Report (menu_id=135): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 135, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 135, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 135, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 135, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 135, 5);
-- Institute Student Fee Report (menu_id=136): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 136, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 136, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 136, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 136, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 136, 5);
-- Students Without Package (menu_id=137): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 137, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 137, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 137, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 137, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 137, 5);
-- Institute Result Overview (menu_id=138): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 138, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 138, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 138, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 138, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 138, 5);
-- Institute Result (menu_id=139): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 139, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 139, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 139, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 139, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 139, 5);
-- Network Vacancies (menu_id=140): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 140, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 140, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 140, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 140, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 140, 5);
-- Donation Requests (menu_id=141): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 141, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 141, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 141, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 141, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 141, 5);
-- Adoption Awaited (menu_id=142): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 142, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 142, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 142, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 142, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 142, 5);
-- Adoption Completed (menu_id=143): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 143, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 143, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 143, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 143, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 143, 5);
-- Fees Distribution (menu_id=144): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 144, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 144, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 144, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 144, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 144, 5);
-- Transaction details (menu_id=145): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 145, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 145, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 145, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 145, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 145, 5);
-- Exam (menu_id=146): View (Page)=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 146, 1);
-- Bench Stickers (menu_id=147): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 147, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 147, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 147, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 147, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 147, 5);
-- Hall Tickets (menu_id=148): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 148, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 148, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 148, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 148, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 148, 5);
-- Block wise chart (menu_id=149): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 149, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 149, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 149, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 149, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 149, 5);
-- Center Count (menu_id=150): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 150, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 150, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 150, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 150, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 150, 5);
-- Centre wise Student Count (menu_id=151): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 151, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 151, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 151, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 151, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 151, 5);
-- Centre wise Student list (menu_id=152): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 152, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 152, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 152, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 152, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 152, 5);
-- Cent res Roll No. (menu_id=153): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 153, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 153, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 153, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 153, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 153, 5);
-- Center Incharge list (menu_id=154): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 154, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 154, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 154, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 154, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 154, 5);
-- Center Incharge Card (menu_id=155): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 155, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 155, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 155, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 155, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 155, 5);
-- Centre Expense allocation Sheet (menu_id=156): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 156, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 156, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 156, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 156, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 156, 5);
-- Centre ATM List (menu_id=157): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 157, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 157, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 157, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 157, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 157, 5);
-- Center Sticker (menu_id=158): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 158, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 158, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 158, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 158, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 158, 5);
-- Center Sticker A4 (menu_id=159): View (Page)=1, Search=1, Filters=1, Excel=1, PDF=1
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 159, 1);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 159, 2);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 159, 3);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 159, 4);
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 159, 5);
