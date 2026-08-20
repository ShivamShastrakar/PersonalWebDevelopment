--liquibase formatted sql
--changeset {narendra}:{id}


INSERT INTO feature_toggles
( feature_id,feature_key,  enabled, rollout_percentage, created_at)
VALUES(1,'Phase1-menu only', 1, 100, CURRENT_TIMESTAMP);


INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(4, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(3, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(10, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(11, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(19, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(20, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(21, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(22, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(27, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(28, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(29, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(30, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(35, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(36, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(44, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(48, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(50, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(51, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(52, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(53, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(54, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(55, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(57, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(58, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(59, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(60, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(61, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(62, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(63, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(64, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(65, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(66, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(67, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(68, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(69, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(70, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(71, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(72, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(73, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(74, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(75, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(76, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(77, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(78, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(81, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(82, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(83, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(84, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(86, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(87, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(88, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(89, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(90, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(91, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(92, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(94, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(95, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(96, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(97, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(98, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(99, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(100, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(101, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(102, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(103, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(104, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(105, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(106, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(107, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(108, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(109, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(110, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(111, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(112, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(113, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(114, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(116, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(119, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(123, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(124, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(125, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(126, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(128, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(129, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(130, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(131, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(132, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(133, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(134, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(135, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(136, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(137, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(138, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(139, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(140, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(141, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(142, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(143, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(144, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(145, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(146, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(147, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(148, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(149, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(150, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(151, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(152, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(153, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(154, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(155, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(156, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(157, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(158, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(159, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(160, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(161, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(162, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(163, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(164, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(165, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(166, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(167, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(168, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(169, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(170, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(171, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(172, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(173, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(174, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(175, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(176, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(177, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(56, 1);
INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(85, 1);