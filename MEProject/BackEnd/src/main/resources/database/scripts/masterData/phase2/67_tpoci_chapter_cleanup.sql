--liquibase formatted sql
--changeset {narendra}:{id}

START TRANSACTION;

 DELETE from questions where 1=1;
 DELETE from chapter_board_class_mapping where chapter_id in(select chapter_id from chapters where created_date <='2025-11-18')
 and board_id  = (select id from board where board_name='CBSE');;
 DELETE FROM topics where  created_at<='2025-11-18';
 DELETE FROM chapters where created_date <='2025-11-18';
 DELETE from subject_board_class_mapping
 where board_id  = (select id from board where board_name='CBSE');
 DELETE from board where board_name='CBSE';

  DELETE from  chapter_board_class_mapping where 1=1;

 INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id)
 SELECT
     c.id,
     c.class_id,
     c.board_id
 FROM chapters c
 WHERE c.class_id IS NOT NULL
   AND c.board_id IS NOT NULL;
COMMIT;
