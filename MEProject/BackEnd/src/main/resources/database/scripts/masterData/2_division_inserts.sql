--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO division (division_name, division_code, deleted_at, deleted, state_id) VALUES
  ('KOKAN', 5, NULL, '0', 21),
  ('KOLHAPUR', 4, NULL, '0', 21),
  ('MUMBAI', 1, NULL, '0', 21),
  ('NASIK', 3, NULL, '0', 21),
  ('PUNE', 2, NULL, '0', 21),
  ('LATUR', 7, NULL, '0', 21),
  ('NAGPUR', 9, NULL, '0', 21),
  ('CHH SAMBHAJI NAGAR', 6, NULL, '0', 21),
  ('AMRAVATI', 8, NULL, '0', 21),
  ('KARNATAKA', 0, NULL, '0', 17),
  ('SOUTH ZONE', 0, NULL, '0', 21),
  ('NORTH ZONE', 0, NULL, '0', 21),
  ('WEST ZONE', 0, NULL, '0', 21),
  ('RAIGAD ZONE', 0, NULL, '0', 21),
  ('CENTRAL ZONE', 0, NULL, '0', 21);


