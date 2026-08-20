--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO board (board_name) VALUES
	 ('HSC'),
	 ('CBSE'),
	 ('ICSE'),
	 ('IB'),
	 ('Other Board'),
	 ('NOS'),
	 ('SSC'),
	 ('NCERT'),
	 ('IBI');
	 
INSERT INTO rule_types (rule_type) VALUES
	 ('DISCOUNT'),
	 ('Additional Charges'),
	 ('Surcharges'),
	 ('Late fee'),
	 ('Fee distribution'),
	 ('Shipping charges');
	 
INSERT INTO zone (zone_name,state_id) VALUES
	 ('North',21),
	 ('West',21),
	 ('Central',21),
	 ('South',21);
