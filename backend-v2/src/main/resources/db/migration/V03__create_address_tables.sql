CREATE TABLE common.province (
  id bigserial NOT NULL PRIMARY KEY,
  "name" varchar(255) NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);

CREATE TABLE common.district (
  id bigserial NOT NULL PRIMARY KEY,
  "name" varchar(255) NOT NULL,
  province_id bigint NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference province
ALTER TABLE IF EXISTS common.district 
ADD CONSTRAINT fk_district_province
FOREIGN KEY (province_id) 
REFERENCES common.province(id);

CREATE TABLE common.ward (
  id bigserial NOT NULL PRIMARY KEY,
  "name" varchar(255) NOT NULL,
  district_id bigint NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference district
ALTER TABLE IF EXISTS common.ward 
ADD CONSTRAINT fk_ward_district
FOREIGN KEY (district_id) 
REFERENCES common.district(id);

CREATE TABLE core.address (
  id bigserial NOT NULL PRIMARY KEY,
  code varchar(255) UNIQUE NOT NULL DEFAULT gen_random_uuid(),
  street varchar(500) NOT NULL,
  customer_id bigint NOT NULL,
  ward_id bigint NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference customer
ALTER TABLE IF EXISTS core.address 
ADD CONSTRAINT fk_address_customer
FOREIGN KEY (customer_id) 
REFERENCES core.customer(id);
-- Reference ward
ALTER TABLE IF EXISTS core.address
ADD CONSTRAINT fk_address_ward
FOREIGN KEY (ward_id) 
REFERENCES common.ward(id);

CREATE OR REPLACE VIEW core.v_address_detail
AS SELECT
    a.id,
    a.code,
    a.street,
    d.id AS district_id,
    d.name AS district_name,
    p.id AS province_id,
    p.name AS province_name,
    w.id AS ward_id,
    w.name AS ward_name,
    c.id AS customer_id,
    c.code AS customer_code,
    a.created_at,
    a.updated_at,
    a.created_by,
    a.updated_by
   FROM core.address a
      JOIN common.ward w ON a.ward_id = w.id
      JOIN common.district d ON w.district_id = d.id
      JOIN common.province p ON d.province_id = p.id
      JOIN core.customer c ON c.id = a.customer_id;

CREATE OR REPLACE VIEW core.v_ward_detail
AS SELECT
    w.id,
    w.name,
    d.id AS district_id,
    d.name AS district_name,
    p.id AS province_id,
    p.name AS province_name,
    w.created_at,
    w.updated_at,
    w.created_by,
    w.updated_by
   FROM common.ward w
      JOIN common.district d ON w.district_id = d.id
      JOIN common.province p ON d.province_id = p.id;