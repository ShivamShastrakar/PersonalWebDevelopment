--liquibase formatted sql
--changeset {narendra}:{id}


ALTER TABLE application_user
ADD COLUMN address_id bigint(20),
ADD COLUMN photo_url VARCHAR(255);

-- Add foreign key constraint for address_id
ALTER TABLE application_user
ADD CONSTRAINT fk_application_user_address
FOREIGN KEY (address_id) REFERENCES address(address_id)
ON DELETE SET NULL ON UPDATE CASCADE;

