--liquibase formatted sql
--changeset {narendra}:79_menu_rename

INSERT INTO package_category 
(id, name, description, created_date, created_by, tenant_id)
VALUES 
(2, 'Premium', 'Premium', '2026-03-17 07:26:49', 1, (SELECT tenant_id FROM tenant ORDER BY tenant_id ASC LIMIT 1)),
(3, 'Super', 'Super', '2026-03-17 07:26:49', 1, (SELECT tenant_id FROM tenant ORDER BY tenant_id ASC LIMIT 1)),
(4, 'Supreme', 'Supreme', '2026-03-17 07:26:49', 1, (SELECT tenant_id FROM tenant ORDER BY tenant_id ASC LIMIT 1));