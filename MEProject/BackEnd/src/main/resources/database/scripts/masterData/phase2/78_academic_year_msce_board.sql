--liquibase formatted sql
--changeset {narendra}:{id}

SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

INSERT INTO academic_year
( name, start_date, end_date, tenant_id,board_id, deleted, created_at, updated_at)
VALUES( '2025-26', '2025-03-15', '2026-04-30', 101,@board_msce_id, '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);