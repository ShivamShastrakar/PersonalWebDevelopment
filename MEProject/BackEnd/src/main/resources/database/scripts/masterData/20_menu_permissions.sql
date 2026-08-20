--liquibase formatted sql
--changeset {narendra}:{id}

delete from role_menu_permission where 1=1;
delete from menus where 1=1;
delete from permission where 1=1;

-- Tech Admin

-- Script for inserting into the menus table
-- Assuming table name is 'menus' based on the sample provided
INSERT INTO menus (menu_id, name, parent_id, order_index) VALUES
(1, 'Dashboard', NULL, 1),
(2, 'Configure', NULL, 2),
(3, 'System', 2, 1),
(4, 'Overview', 3, 1),
(5, 'Email', 3, 2),
(6, 'SMS', 3, 3),
(7, 'User', 3, 4),
(8, 'Whatsapp', 3, 5),
(9, 'Roles', 2, 2),
(10, 'Fees Distribution', 2, 3),
(11, 'Paper Rate', 2, 4),
(12, 'Data Upload', 2, 5),
(13, 'Student Excel', 12, 1),
(14, 'Organization', NULL, 3),
(15, 'Academics', 14, 1),
(16, 'Subjects', 15, 1),
(17, 'Chapters', 15, 2),
(18, 'Topics', 15, 3),
(19, 'Question Type', 15, 4),
(20, 'Question Difficulty Level', 15, 5),
(21, 'Question Criteria', 15, 6),
(22, 'Question Status', 15, 7),
(23, 'Application Users', 14, 2),
(24, 'Channel Partners', 23, 1),
(25, 'Network Partners', 23, 2),
(26, 'Students', 23, 3),
(27, 'Circular', 14, 3),
(28, 'Events', 14, 4),
(29, 'Events', 28, 1),
(30, 'Paper Setting Events', 28, 2),
(31, 'Entities', 14, 5),
(32, 'Boards', 31, 1),
(33, 'Classes', 31, 2),
(34, 'Categories', 31, 3),
(35, 'Parallel Reservation', 31, 4),
(36, 'Feedbacks', 31, 5),
(37, 'Levels', 14, 6),
(38, 'States', 37, 1),
(39, 'Divisions', 37, 2),
(40, 'Districts', 37, 3),
(41, 'Taluka', 37, 4),
(42, 'Education Society', 37, 5),
(43, 'Institutes', 37, 6),
(44, 'Admission Probability Institutes', 37, 7),
(45, 'Offerings', 14, 7),
(46, 'Courses', 45, 1),
(47, 'Services', 45, 2),
(48, 'Offers', 45, 3),
(49, 'Targets', 14, 8),
(50, 'Channel Partners', 49, 1),
(51, 'Staff', 49, 2),
(52, 'Institutes', 49, 3),
(53, 'Donation', NULL, 4),
(54, 'Gifts', 53, 1),
(55, 'Donate', 53, 2),
(56, 'Teachers', NULL, 5),
(57, 'MCQ', 56, 1),
(58, 'Question Curation', 57, 1),
(59, 'Setter', 57, 2),
(60, 'Operator', 57, 3),
(61, 'Moderator', 57, 4),
(62, 'Subject Expert', 57, 5),
(63, 'Subject Council Member', 57, 6),
(64, 'Incentive Distribution', 57, 7),
(65, 'MCQ Settings', 57, 8),
(66, 'Training', 56, 2),
(67, 'Subject Training', 66, 1),
(68, 'Notes', 56, 3),
(69, 'Creator', 68, 1),
(70, 'Moderator', 68, 2),
(71, 'Subject Expert', 68, 3),
(72, 'Subject council Member', 68, 4),
(73, 'Incentive Distribution', 68, 5),
(74, 'Allot to Packages', 68, 6),
(75, 'Teachers', 68, 7),
(76, 'Video Upload', 68, 8),
(77, 'Document', 68, 9),
(78, 'Lectures', 56, 4),
(79, 'Students', NULL, 6),
(80, 'All students', 79, 1),
(81, 'Students without Data', 79, 2),
(82, 'Manage Students', 79, 3),
(83, 'Manage Student', 82, 1),
(84, 'View Deleted Student', 82, 2),
(85, 'Parent', NULL, 7),
(86, 'Fees', NULL, 8),
(87, 'Wallet', 86, 1),
(88, 'Offline Settlement', 86, 2),
(89, 'Institute', 88, 1),
(90, 'Channel partner', 88, 2),
(91, 'Transactions', 86, 3),
(92, 'Exams', NULL, 9),
(93, 'Dashboard', 92, 1),
(94, 'Offline Exams', 92, 2),
(95, 'Assessment', 94, 1),
(96, 'Practice', 94, 2),
(97, 'Mocktest', 94, 3),
(98, 'Online (CBT) Exams', 92, 3),
(99, 'Assessment', 98, 1),
(100, 'Practice', 98, 2),
(101, 'Mocktest', 98, 3),
(102, 'Online Exams', 92, 4),
(103, 'Assessment', 102, 1),
(104, 'Practice', 102, 2),
(105, 'Mocktest', 102, 3),
(106, 'SOP', 92, 5),
(107, 'Merge Exam Result', 92, 6),
(108, 'Exam Management', 92, 7),
(109, 'Cut Off', 108, 1),
(110, 'Question Sorter', 92, 8),
(111, 'Question Bank', 92, 9),
(112, 'Questions', 111, 1),
(113, 'Detailed Analysis', 111, 2),
(114, 'Quick Analysis', 111, 3),
(115, 'Packages', NULL, 10),
(116, 'Overview', 115, 1),
(117, 'Packages', 115, 2),
(118, 'Your Packages', 117, 1),
(119, 'Suggested Packages', 117, 2),
(120, 'Prepare', 117, 3),
(121, 'Practice', 117, 4),
(122, 'Evaluate', 117, 5),
(123, 'Package Rules', 115, 3),
(124, 'Manage Student Packages', 115, 4),
(125, 'Doubts', NULL, 11),
(126, 'Lead', NULL, 12),
(127, 'Refer & Earn', NULL, 13),
(128, 'Report', NULL, 14),
(129, 'Administration', 128, 1),
(130, 'Academic Expertise', 129, 1),
(131, 'Accounting Report', 129, 2),
(132, 'Adoption Awaited', 129, 3),
(133, 'Adoption Completed', 129, 4),
(134, 'Committee', 129, 5),
(135, 'Districtwise Report', 129, 6),
(136, 'Donation Requests', 129, 7),
(137, 'Enrollment', 129, 8),
(138, 'Exam Attendance Report', 129, 9),
(139, 'Examwise Report', 129, 10),
(140, 'Fees Distribution', 129, 11),
(141, 'GST Report', 129, 12),
(142, 'Institute Result', 129, 13),
(143, 'Institute Result Overview', 129, 14),
(144, 'Institute Student Fee Report', 129, 15),
(145, 'MCQ Report', 129, 16),
(146, 'Network Vacancies', 129, 17),
(147, 'Settlement Report', 129, 18),
(148, 'Student Enrollment Report', 129, 19),
(149, 'Student Institute Report', 129, 20),
(150, 'Students Address Sticker Report', 129, 21),
(151, 'Students Wallet Money', 129, 22),
(152, 'Students Without Package', 129, 23),
(153, 'Subscribers Report', 129, 24),
(154, 'Targets', 129, 25),
(155, 'Transaction details', 129, 26),
(156, 'Exam', 128, 2),
(157, 'Bench Stickers', 156, 1),
(158, 'Block wise chart', 156, 2),
(159, 'Center Count', 156, 3),
(160, 'Center Incharge Card', 156, 4),
(161, 'Center Incharge list', 156, 5),
(162, 'Center Sticker', 156, 6),
(163, 'Center Sticker A4', 156, 7),
(164, 'Centre ATM List', 156, 8),
(165, 'Centre Expense allocation Sheet', 156, 9),
(166, 'Centre Incharge letter', 156, 10),
(167, 'Centre wise Student Count', 156, 11),
(168, 'Centre wise Student list', 156, 12),
(169, 'Centrerwise Cost Report', 156, 13),
(170, 'Centres Roll No.', 156, 14),
(171, 'Collegewise Student list', 156, 15),
(172, 'Hall Tickets', 156, 16),
(173, 'Principal Letter', 156, 17),
(174, 'Principal sticker', 156, 18),
(175, 'Remuneration Letter', 156, 19),
(176, 'Student and Institute', 156, 20),
(177, 'HelpDesk', NULL, 15);

-- Script for inserting into the permission table
INSERT INTO permission (permission_id, name, description, created_at, `type`) values 
(1, 'View', 'View', CURRENT_TIMESTAMP, 'Page'),
(2, 'Search', 'Search', CURRENT_TIMESTAMP, 'Page'),
(3, 'Filters', 'Filters', CURRENT_TIMESTAMP, 'Page'),
(4, 'Excel', 'Excel', CURRENT_TIMESTAMP, 'Page'),
(5, 'PDF', 'PDF', CURRENT_TIMESTAMP, 'Page'),
(6, 'Add', 'Add', CURRENT_TIMESTAMP, 'Page'),
(7, 'DummyView', 'DummyView', CURRENT_TIMESTAMP, 'Page'),
(8, 'Edit', 'Edit', CURRENT_TIMESTAMP, 'Page'),
(9, 'Delete', 'Delete', CURRENT_TIMESTAMP, 'Page'),
(10, 'Send Mess', 'Send Mess', CURRENT_TIMESTAMP, 'Page'),
(11, 'MCQ Access', 'MCQ Access', CURRENT_TIMESTAMP, 'Page'),
(12, 'Import Q', 'Import Q', CURRENT_TIMESTAMP, 'Page'),
(13, 'Notes History', 'Notes History', CURRENT_TIMESTAMP, 'Page'),
(14, 'View Feedback', 'View Feedback', CURRENT_TIMESTAMP, 'Page'),
(15, 'Switch Package', 'Switch Package', CURRENT_TIMESTAMP, 'Page'),
(16, 'Switch Class', 'Switch Class', CURRENT_TIMESTAMP, 'Page'),
(17, 'Reset Pwd', 'Reset Pwd', CURRENT_TIMESTAMP, 'Page'),
(18, 'Send Login Details', 'Send Login Details', CURRENT_TIMESTAMP, 'Page'),
(19, 'Add package', 'Add package', CURRENT_TIMESTAMP, 'Page'),
(20, 'Update Status', 'Update Status', CURRENT_TIMESTAMP, 'Page'),
(21, 'Impersonate', 'Impersonate', CURRENT_TIMESTAMP, 'Page'),
(22, 'Download', 'Download', CURRENT_TIMESTAMP, 'Page');

-- Script for inserting into role_menu_permission table
-- Based on the attached Excel for Tech Admin, all 177 menus have all 22 permissions enabled (value=1) for each.
-- This would result in 3894 rows. To avoid a massive single INSERT statement, we use a stored procedure to loop over the menus and insert the mappings.
-- Batch 1: Menus 1-50
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 1, m.menu_id, p.permission_id
FROM menus m
CROSS JOIN permission p
WHERE m.menu_id BETWEEN 1 AND 50
AND p.permission_id BETWEEN 1 AND 22;

-- Batch 2: Menus 51-100
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 1, m.menu_id, p.permission_id
FROM menus m
CROSS JOIN permission p
WHERE m.menu_id BETWEEN 51 AND 100
AND p.permission_id BETWEEN 1 AND 22;

-- Batch 3: Menus 101-150
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 1, m.menu_id, p.permission_id
FROM menus m
CROSS JOIN permission p
WHERE m.menu_id BETWEEN 101 AND 150
AND p.permission_id BETWEEN 1 AND 22;

-- Batch 4: Menus 151-177
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 1, m.menu_id, p.permission_id
FROM menus m
CROSS JOIN permission p
WHERE m.menu_id BETWEEN 151 AND 177
AND p.permission_id BETWEEN 1 AND 22;

-- Admin

-- Script for inserting into role_menu_permission table
-- Based on the attached Excel for Admin (role_id=2), permissions are granted only where the value is 1 for each menu-permission combination.
-- Permissions are mapped as follows (id 1 to 22):
-- 1: View, 2: Search, 3: Filters, 4: Excel, 5: PDF, 6: Add, 7: View (detail), 8: Edit, 9: Delete, 10: Send Mess,
-- 11: MCQ Access, 12: Import Q, 13: Notes History, 14: View Feedback, 15: Switch Package, 16: Switch Class,
-- 17: Reset Pwd, 18: Send Login Details, 19: Add package, 20: Update Status, 21: Impersonate, 22: Download
-- Assuming menus are inserted with menu_id from 1 to 177 as per the hierarchy in the Excel and previous scripts.
-- To handle the variable permissions efficiently, we use a stored procedure with explicit inserts for unique patterns and loops for repetitive groups.


INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 1, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 2, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 3, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES 
  (2, 4, 1), (2, 4, 8), (2, 4, 9),
  (2, 5, 1), (2, 5, 8), (2, 5, 9),
  (2, 6, 1), (2, 6, 8), (2, 6, 9),
  (2, 7, 1), (2, 7, 8), (2, 7, 9),
  (2, 8, 1), (2, 8, 8), (2, 8, 9);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 9, 1), (2, 9, 4), (2, 9, 5), (2, 9, 6), (2, 9, 8), (2, 9, 9), (2, 9, 10), (2, 9, 21);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 10, 1), (2, 10, 4), (2, 10, 5);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 11, 1), (2, 11, 2), (2, 11, 3), (2, 11, 6), (2, 11, 7), (2, 11, 8);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 12, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 13, 1), (2, 13, 4), (2, 13, 5), (2, 13, 22);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 14, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 15, 1);

-- Batch for Subjects (16) to Question Status (22): permissions 1-9
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 16 AND 22) m
CROSS JOIN (SELECT permission_id FROM permission WHERE permission_id BETWEEN 1 AND 9) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 23, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 24, 1), (2, 24, 2), (2, 24, 3), (2, 24, 4), (2, 24, 5),
  (2, 24, 6), (2, 24, 7), (2, 24, 8), (2, 24, 9), (2, 24, 10),
  (2, 24, 17), (2, 24, 18), (2, 24, 21);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 25, 1), (2, 25, 2), (2, 25, 3), (2, 25, 4), (2, 25, 5),
  (2, 25, 6), (2, 25, 7), (2, 25, 8), (2, 25, 9), (2, 25, 10),
  (2, 25, 11), (2, 25, 12), (2, 25, 17), (2, 25, 18), (2, 25, 21);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 26, 1), (2, 26, 2), (2, 26, 3), (2, 26, 4), (2, 26, 5),
  (2, 26, 6), (2, 26, 7), (2, 26, 8), (2, 26, 9), (2, 26, 10),
  (2, 26, 17), (2, 26, 18), (2, 26, 19), (2, 26, 21);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 27, 1), (2, 27, 2), (2, 27, 3), (2, 27, 6), (2, 27, 7), (2, 27, 8), (2, 27, 9);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 28, 1);

-- Batch for Events (29) to Paper Setting Events (30): permissions 1,2,3,6,7,8,9
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 29 AND 30) m
CROSS JOIN (SELECT permission_id FROM (SELECT 1 AS permission_id UNION SELECT 2 UNION SELECT 3 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS p) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 31, 1);

-- Batch for Boards (32) to Feedbacks (36): permissions 1,2,3,6,7,8,9
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 32 AND 36) m
CROSS JOIN (SELECT permission_id FROM (SELECT 1 AS permission_id UNION SELECT 2 UNION SELECT 3 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS p) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 37, 1);

-- Batch for States (38) to Admission Probability Institutes (44): permissions 1,2,3,6,7,8,9
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 38 AND 44) m
CROSS JOIN (SELECT permission_id FROM (SELECT 1 AS permission_id UNION SELECT 2 UNION SELECT 3 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS p) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 45, 1);

-- Batch for Courses (46) to Offers (48): permissions 1-9
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 46 AND 48) m
CROSS JOIN (SELECT permission_id FROM permission WHERE permission_id BETWEEN 1 AND 9) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 49, 1);

-- Batch for Channel Partners (50) to Institutes (52): permissions 1-6,8,9,10,21
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 50 AND 52) m
CROSS JOIN (SELECT permission_id FROM (SELECT 1 AS permission_id UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 21) AS p) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 53, 1);

-- Batch for Gifts (54), Donate (55): permissions 1-6
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 54 AND 55) m
CROSS JOIN (SELECT permission_id FROM permission WHERE permission_id BETWEEN 1 AND 6) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 56, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 57, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 58, 12);

-- Batch for Setter (59) to Incentive Distribution (64): permissions 1-7
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 59 AND 64) m
CROSS JOIN (SELECT permission_id FROM permission WHERE permission_id BETWEEN 1 AND 7) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 65, 12);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 66, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 67, 1), (2, 67, 2), (2, 67, 3), (2, 67, 4), (2, 67, 5), (2, 67, 6),
  (2, 67, 8), (2, 67, 9), (2, 67, 14);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 68, 1);

-- Batch for Creator (69) to Subject council Member (72): permissions 1-8,13,19,20
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 69 AND 72) m
CROSS JOIN (SELECT permission_id FROM (SELECT permission_id FROM permission WHERE permission_id BETWEEN 1 AND 8 UNION SELECT 13 UNION SELECT 19 UNION SELECT 20) AS p) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 73, 1), (2, 73, 2),
  (2, 74, 1), (2, 74, 2),
  (2, 75, 1), (2, 75, 2);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 76, 1), (2, 76, 2), (2, 76, 6), (2, 76, 19), (2, 76, 20), (2, 76, 22),
  (2, 77, 1), (2, 77, 2), (2, 77, 6), (2, 77, 19), (2, 77, 20), (2, 77, 22);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 78, 1), (2, 78, 2), (2, 78, 3), (2, 78, 4), (2, 78, 6),
  (2, 78, 7), (2, 78, 8), (2, 78, 9), (2, 78, 22);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 79, 1), (2, 79, 2), (2, 79, 3), (2, 79, 4), (2, 79, 5), (2, 79, 6), (2, 79, 7), (2, 79, 8), (2, 79, 9), (2, 79, 10),
  (2, 79, 15), (2, 79, 16), (2, 79, 17), (2, 79, 18), (2, 79, 19), (2, 79, 21),
  (2, 80, 1), (2, 80, 2), (2, 80, 3), (2, 80, 4), (2, 80, 5), (2, 80, 6), (2, 80, 7), (2, 80, 8), (2, 80, 9), (2, 80, 10),
  (2, 80, 15), (2, 80, 16), (2, 80, 17), (2, 80, 18), (2, 80, 19), (2, 80, 21);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 81, 1), (2, 81, 2), (2, 81, 3), (2, 81, 4), (2, 81, 5),
  (2, 81, 7), (2, 81, 8), (2, 81, 9), (2, 81, 10),
  (2, 81, 15), (2, 81, 16), (2, 81, 17), (2, 81, 18), (2, 81, 19), (2, 81, 21);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 82, 1), (2, 82, 2), (2, 82, 3), (2, 82, 4), (2, 82, 5), (2, 82, 6), (2, 82, 7), (2, 82, 8), (2, 82, 9), (2, 82, 10),
  (2, 82, 17), (2, 82, 18), (2, 82, 19), (2, 82, 21),
  (2, 83, 1), (2, 83, 2), (2, 83, 3), (2, 83, 4), (2, 83, 5), (2, 83, 6), (2, 83, 7), (2, 83, 8), (2, 83, 9), (2, 83, 10),
  (2, 83, 17), (2, 83, 18), (2, 83, 19), (2, 83, 21);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 84, 1), (2, 84, 2), (2, 84, 3), (2, 84, 4), (2, 84, 5), (2, 84, 6), (2, 84, 7), (2, 84, 8), (2, 84, 9), (2, 84, 10),
  (2, 84, 15), (2, 84, 16), (2, 84, 17), (2, 84, 18), (2, 84, 19), (2, 84, 21);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 86, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 88, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 89, 1), (2, 89, 2), (2, 89, 3),
  (2, 90, 1), (2, 90, 2), (2, 90, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 92, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 94, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 95, 1), (2, 95, 2), (2, 95, 3),
  (2, 96, 1), (2, 96, 2), (2, 96, 3),
  (2, 97, 1), (2, 97, 2), (2, 97, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 98, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 99, 1), (2, 99, 2), (2, 99, 3),
  (2, 100, 1), (2, 100, 2), (2, 100, 3),
  (2, 101, 1), (2, 101, 2), (2, 101, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 102, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 103, 1), (2, 103, 2), (2, 103, 3),
  (2, 104, 1), (2, 104, 2), (2, 104, 3),
  (2, 105, 1), (2, 105, 2), (2, 105, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 106, 1), (2, 106, 2), (2, 106, 3),
  (2, 107, 1), (2, 107, 2), (2, 107, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 108, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 109, 1), (2, 109, 2), (2, 109, 3),
  (2, 110, 1), (2, 110, 2), (2, 110, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 111, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 113, 1), (2, 113, 2), (2, 113, 3),
  (2, 114, 1), (2, 114, 2), (2, 114, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 115, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 116, 1), (2, 116, 2), (2, 116, 3);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 117, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 120, 1), (2, 120, 2), (2, 120, 3),
  (2, 121, 1), (2, 121, 2), (2, 121, 3),
  (2, 122, 1), (2, 122, 2), (2, 122, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 123, 1), (2, 123, 2), (2, 123, 3),
  (2, 124, 1), (2, 124, 2), (2, 124, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 125, 1), (2, 125, 2), (2, 125, 3);

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 128, 1);
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 129, 1);

-- Batch for Administration reports (130 to 155): permissions 1-5
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 130 AND 155) m
CROSS JOIN (SELECT permission_id FROM permission WHERE permission_id BETWEEN 1 AND 5) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (2, 156, 1);

-- Batch for Exam reports (157 to 176): permissions 1-5
INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id)
SELECT 2, m.menu_id, p.permission_id
FROM (SELECT menu_id FROM menus WHERE menu_id BETWEEN 157 AND 176) m
CROSS JOIN (SELECT permission_id FROM permission WHERE permission_id BETWEEN 1 AND 5) p;

INSERT IGNORE INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 177, 1), (2, 177, 2), (2, 177, 3), (2, 177, 4), (2, 177, 5), (2, 177, 6), (2, 177, 8), (2, 177, 9);


-- Student
-- Script for inserting into role_menu_permission table
-- Based on the attached Excel for Student (role_id=3), permissions are granted only where the value is 1 for each menu-permission combination.
-- Permissions are mapped as follows (id 1 to 22):
-- 1: View, 2: Search, 3: Filters, 4: Excel, 5: PDF, 6: Add, 7: View (detail), 8: Edit, 9: Delete, 10: Send Mess,
-- 11: MCQ Access, 12: Import Q, 13: Notes History, 14: View Feedback, 15: Switch Package, 16: Switch Class,
-- 17: Reset Pwd, 18: Send Login Details, 19: Add package, 20: Update Status, 21: Impersonate, 22: Download
-- Assuming menus have been inserted with menu_id from 1 to 177 as per the hierarchy in previous scripts and name matching.
-- Only menus with at least one permission=1 are included. Menus with all 0s are skipped (no access).
-- Cut Off is mapped to menu_id 109 based on name match, despite hierarchy difference in Excel.


CREATE PROCEDURE add_student_permissions()
BEGIN
  -- Dashboard (1): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 1, 1);

  -- Fees (86): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 86, 1);

  -- Transactions (91): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (3, 91, 1), (3, 91, 2), (3, 91, 3), (3, 91, 4), (3, 91, 5);

  -- Exams (92): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 92, 1);

  -- Offline Exams (94): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 94, 1);

  -- Assessment (95): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 95, 1), (3, 95, 2), (3, 95, 3);

  -- Practice (96): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 96, 1), (3, 96, 2), (3, 96, 3);

  -- Mocktest (97): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 97, 1), (3, 97, 2), (3, 97, 3);

  -- Online (CBT) Exams (98): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 98, 1);

  -- Assessment (99): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 99, 1), (3, 99, 2), (3, 99, 3);

  -- Practice (100): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 100, 1), (3, 100, 2), (3, 100, 3);

  -- Mocktest (101): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 101, 1), (3, 101, 2), (3, 101, 3);

  -- Online Exams (102): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 102, 1);

  -- Assessment (103): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 103, 1), (3, 103, 2), (3, 103, 3);

  -- Practice (104): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 104, 1), (3, 104, 2), (3, 104, 3);

  -- Mocktest (105): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 105, 1), (3, 105, 2), (3, 105, 3);

  -- Cut Off (109): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 109, 1), (3, 109, 2), (3, 109, 3);

  -- Question Sorter (110): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 110, 1), (3, 110, 2), (3, 110, 3);

  -- Question Bank (111): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 111, 1);

  -- Detailed Analysis (113): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 113, 1), (3, 113, 2), (3, 113, 3);

  -- Quick Analysis (114): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 114, 1), (3, 114, 2), (3, 114, 3);

  -- Packages (115): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 115, 1);

  -- Overview (116): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 116, 1), (3, 116, 2), (3, 116, 3);

  -- Packages (117): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 117, 1);

  -- Doubts (125): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (3, 125, 1), (3, 125, 2), (3, 125, 3);

  -- HelpDesk (177): View, Search, Filters, Excel, PDF, Add, Edit, Delete
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (3, 177, 1), (3, 177, 2), (3, 177, 3), (3, 177, 4), (3, 177, 5),
  (3, 177, 6), (3, 177, 8), (3, 177, 9);
END ;

-- Call the procedure to execute the inserts
CALL add_student_permissions();

-- Optional: Drop the procedure after use
DROP PROCEDURE IF EXISTS add_student_permissions;


-- Channel Partner
-- Script for inserting into role_menu_permission table
-- Based on the attached Excel for Channel Partner (role_id=4), permissions are granted only where the value is 1 for each menu-permission combination.
-- Permissions are mapped as follows (id 1 to 22):
-- 1: View, 2: Search, 3: Filters, 4: Excel, 5: PDF, 6: Add, 7: View (detail), 8: Edit, 9: Delete, 10: Send Mess,
-- 11: MCQ Access, 12: Import Q, 13: Notes History, 14: View Feedback, 15: Switch Package, 16: Switch Class,
-- 17: Reset Pwd, 18: Send Login Details, 19: Add package, 20: Update Status, 21: Impersonate, 22: Download
-- Assuming menus have been inserted with menu_id from 1 to 177 as per the hierarchy in previous scripts and name matching.
-- Only menus with at least one permission=1 are included. Menus with all 0s are skipped (no access).
-- Menu names are mapped to ids based on hierarchy and names (e.g., Dashboard=1, Students=79, All students=80, Exams=92, etc.).


CREATE PROCEDURE add_channel_partner_permissions()
BEGIN
  -- Dashboard (1): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 1, 1);

  -- Students (79): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Delete, Send Mess, Switch Package, Switch Class, Reset Pwd, Send Login Details, Add package, Impersonate
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (4, 79, 1), (4, 79, 2), (4, 79, 3), (4, 79, 4), (4, 79, 5),
  (4, 79, 6), (4, 79, 7), (4, 79, 8), (4, 79, 9), (4, 79, 10),
  (4, 79, 15), (4, 79, 16), (4, 79, 17), (4, 79, 18), (4, 79, 19), (4, 79, 21);

  -- All students (80): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Delete, Send Mess, Add package
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (4, 80, 1), (4, 80, 2), (4, 80, 3), (4, 80, 4), (4, 80, 5),
  (4, 80, 6), (4, 80, 7), (4, 80, 8), (4, 80, 9), (4, 80, 10), (4, 80, 19);

  -- Exams (92): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 92, 1);

  -- Offline Exams (94): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 94, 1);

  -- Assessment (95): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 95, 1), (4, 95, 2), (4, 95, 3);

  -- Practice (96): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 96, 1), (4, 96, 2), (4, 96, 3);

  -- Mocktest (97): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 97, 1), (4, 97, 2), (4, 97, 3);

  -- Online (CBT) Exams (98): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 98, 1);

  -- Assessment (99): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 99, 1), (4, 99, 2), (4, 99, 3);

  -- Practice (100): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 100, 1), (4, 100, 2), (4, 100, 3);

  -- Mocktest (101): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 101, 1), (4, 101, 2), (4, 101, 3);

  -- Online Exams (102): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 102, 1);

  -- Assessment (103): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 103, 1), (4, 103, 2), (4, 103, 3);

  -- Practice (104): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 104, 1), (4, 104, 2), (4, 104, 3);

  -- Mocktest (105): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 105, 1), (4, 105, 2), (4, 105, 3);

  -- Report (128): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 128, 1);

  -- Administration (129): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 129, 1);

  -- Districtwise Report (135): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 135, 1), (4, 135, 2), (4, 135, 3), (4, 135, 4), (4, 135, 5);

  -- Enrollment (137): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 137, 1), (4, 137, 2), (4, 137, 3), (4, 137, 4), (4, 137, 5);

  -- Exam Attendance Report (138): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 138, 1), (4, 138, 2), (4, 138, 3), (4, 138, 4), (4, 138, 5);

  -- Examwise Report (139): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 139, 1), (4, 139, 2), (4, 139, 3), (4, 139, 4), (4, 139, 5);

  -- Settlement Report (147): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 147, 1), (4, 147, 2), (4, 147, 3), (4, 147, 4), (4, 147, 5);

  -- Student Enrollment Report (148): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 148, 1), (4, 148, 2), (4, 148, 3), (4, 148, 4), (4, 148, 5);

  -- Student Institute Report (149): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 149, 1), (4, 149, 2), (4, 149, 3), (4, 149, 4), (4, 149, 5);

  -- Targets (154): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 154, 1), (4, 154, 2), (4, 154, 3), (4, 154, 4), (4, 154, 5);

  -- HelpDesk (177): View, Search, Filters, Excel, PDF, Add, Edit, Delete
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (4, 177, 1), (4, 177, 2), (4, 177, 3), (4, 177, 4), (4, 177, 5),
  (4, 177, 6), (4, 177, 8), (4, 177, 9);
END ;

-- Call the procedure to execute the inserts
CALL add_channel_partner_permissions();

-- Optional: Drop the procedure after use
DROP PROCEDURE IF EXISTS add_channel_partner_permissions;

-- Institute Representative

-- Script for inserting into role_menu_permission table
-- Based on the attached Excel for Institute Representative (role_id=5), permissions are granted only where the value is 1 for each menu-permission combination.
-- Permissions are mapped as follows (id 1 to 22):
-- 1: View, 2: Search, 3: Filters, 4: Excel, 5: PDF, 6: Add, 7: View (detail), 8: Edit, 9: Delete, 10: Send Mess,
-- 11: MCQ Access, 12: Import Q, 13: Notes History, 14: View Feedback, 15: Switch Package, 16: Switch Class,
-- 17: Reset Pwd, 18: Send Login Details, 19: Add package, 20: Update Status, 21: Impersonate, 22: Download
-- Assuming menus have been inserted with menu_id from 1 to 177 as per the hierarchy in previous scripts and name matching.
-- Only menus with at least one permission=1 are included. Menus with all 0s are skipped (no access).
-- Menu names are mapped to ids based on hierarchy and names (e.g., Dashboard=1, Teachers=56, MCQ=57, Question Curation=58, Setter=59, Incentive Distribution=64, MCQ Settings=65, Training=66, Subject Training=67, Notes=68, Creator=69, Moderator=70, Subject Expert=71, Subject council Member=72, Incentive Distribution (Notes)=73, Allot to Packages=74, Teachers (Notes)=75, Video Upload=76, Document=77, Lectures=78, Students=79, All students=80, Fees=86, Transactions=91, Exams=92, Dashboard (Exams)=93, Offline Exams=94, Assessment (Offline)=95, Practice (Offline)=96, Mocktest (Offline)=97, Online (CBT) Exams=98, Assessment (CBT)=99, Practice (CBT)=100, Mocktest (CBT)=101, Online Exams=102, Assessment (Online)=103, Practice (Online)=104, Mocktest (Online)=105, Report=128, Administration=129, Districtwise Report=135, Donation Requests=136, Enrollment=137, Exam Attendance Report=138, Examwise Report=139, Student Enrollment Report=148, Targets=154).


CREATE PROCEDURE add_institute_representative_permissions()
BEGIN
  -- Dashboard (1): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 1, 1);

  -- Teachers (56): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 56, 1);

  -- MCQ (57): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 57, 1);

  -- Question Curation (58): Import Q
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 58, 12);

  -- Setter (59): View, Search, Filters, Excel, PDF, Add, View (detail)
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 59, 1), (5, 59, 2), (5, 59, 3), (5, 59, 4), (5, 59, 5), (5, 59, 6), (5, 59, 7);

  -- Incentive Distribution (64): View, Search, Filters, Excel, PDF, Add, View (detail)
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 64, 1), (5, 64, 2), (5, 64, 3), (5, 64, 4), (5, 64, 5), (5, 64, 6), (5, 64, 7);

  -- MCQ Settings (65): Import Q
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 65, 12);

  -- Training (66): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 66, 1);

  -- Subject Training (67): View, Search, Filters, Excel, PDF, Add, Edit, Delete, View Feedback
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 67, 1), (5, 67, 2), (5, 67, 3), (5, 67, 4), (5, 67, 5), (5, 67, 6), (5, 67, 8), (5, 67, 9), (5, 67, 14);

  -- Notes (68): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 68, 1);

  -- Creator (69): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Notes History, Add package, Update Status
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 69, 1), (5, 69, 2), (5, 69, 3), (5, 69, 4), (5, 69, 5), (5, 69, 6), (5, 69, 7), (5, 69, 8), (5, 69, 13), (5, 69, 19), (5, 69, 20);

  -- Moderator (70): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Notes History, Add package, Update Status
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 70, 1), (5, 70, 2), (5, 70, 3), (5, 70, 4), (5, 70, 5), (5, 70, 6), (5, 70, 7), (5, 70, 8), (5, 70, 13), (5, 70, 19), (5, 70, 20);

  -- Subject Expert (71): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Notes History, Add package, Update Status
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 71, 1), (5, 71, 2), (5, 71, 3), (5, 71, 4), (5, 71, 5), (5, 71, 6), (5, 71, 7), (5, 71, 8), (5, 71, 13), (5, 71, 19), (5, 71, 20);

  -- Subject council Member (72): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Notes History, Add package, Update Status
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 72, 1), (5, 72, 2), (5, 72, 3), (5, 72, 4), (5, 72, 5), (5, 72, 6), (5, 72, 7), (5, 72, 8), (5, 72, 13), (5, 72, 19), (5, 72, 20);

  -- Incentive Distribution (73): View, Search
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 73, 1), (5, 73, 2);

  -- Allot to Packages (74): View, Search
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 74, 1), (5, 74, 2);

  -- Teachers (75): View, Search
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 75, 1), (5, 75, 2);

  -- Video Upload (76): View, Search, Add, Add package, Update Status, Download
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 76, 1), (5, 76, 2), (5, 76, 6), (5, 76, 19), (5, 76, 20), (5, 76, 22);

  -- Document (77): View, Search, Add, Add package, Update Status, Download
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 77, 1), (5, 77, 2), (5, 77, 6), (5, 77, 19), (5, 77, 20), (5, 77, 22);

  -- Lectures (78): View, Search, Filters, Excel, Add, View (detail), Edit, Delete, Download
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 78, 1), (5, 78, 2), (5, 78, 3), (5, 78, 4), (5, 78, 6), (5, 78, 7), (5, 78, 8), (5, 78, 9), (5, 78, 22);

  -- Students (79): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Delete, Send Mess, Switch Package, Switch Class, Reset Pwd, Send Login Details, Add package, Impersonate
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 79, 1), (5, 79, 2), (5, 79, 3), (5, 79, 4), (5, 79, 5), (5, 79, 6), (5, 79, 7), (5, 79, 8), (5, 79, 9), (5, 79, 10),
  (5, 79, 15), (5, 79, 16), (5, 79, 17), (5, 79, 18), (5, 79, 19), (5, 79, 21);

  -- All students (80): View, Search, Filters, Excel, PDF, Add, View (detail), Edit, Delete, Send Mess, Switch Package, Switch Class, Reset Pwd, Send Login Details, Add package, Impersonate
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 80, 1), (5, 80, 2), (5, 80, 3), (5, 80, 4), (5, 80, 5), (5, 80, 6), (5, 80, 7), (5, 80, 8), (5, 80, 9), (5, 80, 10),
  (5, 80, 15), (5, 80, 16), (5, 80, 17), (5, 80, 18), (5, 80, 19), (5, 80, 21);

  -- Fees (86): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 86, 1);

  -- Exams (92): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 92, 1);

  -- Offline Exams (94): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 94, 1);

  -- Assessment (95): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 95, 1), (5, 95, 2), (5, 95, 3);

  -- Practice (96): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 96, 1), (5, 96, 2), (5, 96, 3);

  -- Mocktest (97): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 97, 1), (5, 97, 2), (5, 97, 3);

  -- Online (CBT) Exams (98): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 98, 1);

  -- Assessment (99): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 99, 1), (5, 99, 2), (5, 99, 3);

  -- Practice (100): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 100, 1), (5, 100, 2), (5, 100, 3);

  -- Mocktest (101): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 101, 1), (5, 101, 2), (5, 101, 3);

  -- Online Exams (102): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 102, 1);

  -- Assessment (103): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 103, 1), (5, 103, 2), (5, 103, 3);

  -- Practice (104): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 104, 1), (5, 104, 2), (5, 104, 3);

  -- Mocktest (105): View, Search, Filters
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 105, 1), (5, 105, 2), (5, 105, 3);

  -- Report (128): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 128, 1);

  -- Administration (129): View
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 129, 1);

  -- Districtwise Report (135): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 135, 1), (5, 135, 2), (5, 135, 3), (5, 135, 4), (5, 135, 5);

  -- Donation Requests (136): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 136, 1), (5, 136, 2), (5, 136, 3), (5, 136, 4), (5, 136, 5);

  -- Enrollment (137): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 137, 1), (5, 137, 2), (5, 137, 3), (5, 137, 4), (5, 137, 5);

  -- Exam Attendance Report (138): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 138, 1), (5, 138, 2), (5, 138, 3), (5, 138, 4), (5, 138, 5);

  -- Examwise Report (139): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 139, 1), (5, 139, 2), (5, 139, 3), (5, 139, 4), (5, 139, 5);

  -- Student Enrollment Report (148): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 148, 1), (5, 148, 2), (5, 148, 3), (5, 148, 4), (5, 148, 5);

  -- Targets (154): View, Search, Filters, Excel, PDF
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 154, 1), (5, 154, 2), (5, 154, 3), (5, 154, 4), (5, 154, 5);
END;

-- Call the procedure to execute the inserts
CALL add_institute_representative_permissions();

-- Optional: Drop the procedure after use
DROP PROCEDURE IF EXISTS add_institute_representative_permissions;


delete from role_menu_permission where permission_id=7;
delete from permission where  permission_id=7;


-- To update menu paths
UPDATE menus m
LEFT JOIN menus m2 ON m.parent_id = m2.menu_id
LEFT JOIN menus m3 ON m2.parent_id = m3.menu_id
LEFT JOIN (
    SELECT parent_id, COUNT(*) AS child_count
    FROM menus
    GROUP BY parent_id
) children ON m.menu_id = children.parent_id
SET m.path = 
    CASE
        -- Set path to NULL if the menu has children (child_count > 0) or is top-level (parent_id IS NULL)
        WHEN children.child_count > 0 OR m.parent_id IS NULL THEN NULL
        -- Sub-menu without children (parent_id is not null, m2.parent_id is null)
        WHEN m2.parent_id IS NULL AND m.parent_id IS NOT NULL THEN
            CONCAT(
                LOWER(REPLACE(m2.name, ' ', '-')), '/',
                LOWER(REPLACE(m.name, ' ', '-'))
            )
        -- Sub-sub-menu without children (m2.parent_id is not null)
        ELSE
            CONCAT(
                LOWER(REPLACE(m3.name, ' ', '-')), '/',
                LOWER(REPLACE(m2.name, ' ', '-')), '/',
                LOWER(REPLACE(m.name, ' ', '-'))
            )
    end
   where 1=1;
