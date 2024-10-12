CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA core;

CREATE TABLE core.users (
  id bigserial NOT NULL PRIMARY KEY,
  username varchar(255) UNIQUE NOT NULL,
  email varchar(255) UNIQUE NOT NULL,
  phone varchar(255) UNIQUE,
  is_deleted boolean NOT NULL DEFAULT false,
  is_verified boolean NOT NULL DEFAULT false,
  hashed_password varchar(255),
  authorities varchar(255)[] NOT NULL DEFAULT '{}',
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- INSERT INTO core.users
-- (id, is_verified, display_name, email, hashed_password, phone, created_at, updated_at)
-- VALUES(nextval('users_id_seq'::regclass), true, 'root', 'vutien.dat.work@gmail.com', null, null, current_timestamp, current_timestamp);

CREATE TABLE core.customer (
  id bigserial NOT NULL PRIMARY KEY,
  user_id bigint UNIQUE NOT NULL,
  code varchar(255) UNIQUE NOT NULL DEFAULT gen_random_uuid(),
  "name" varchar(255) NOT NULL,
  phones varchar(32)[] NOT NULL DEFAULT '{}',
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference users
ALTER TABLE IF EXISTS core.customer 
ADD CONSTRAINT fk_customer_users
FOREIGN KEY (user_id)
REFERENCES core.users(id);

CREATE OR REPLACE VIEW core.v_user_detail
AS SELECT u.id,
    u.username,
    u.email,
    u.phone,
    u.is_deleted,
    u.is_verified,
    u.hashed_password,
    u.authorities,
    u.created_at,
    u.updated_at,
    u.created_by,
    u.updated_by,
    c.name,
    c.code AS customer_code
  FROM core.users u
  INNER JOIN core.customer c ON u.id = c.user_id;

INSERT INTO core.users
(id, username, email, phone, is_deleted, is_verified, hashed_password, authorities, created_at, updated_at, created_by, updated_by)
VALUES(0, 'root', 'root@shopsinhvien.io.vn', '0898993601', false, true, '', '{ADMIN}'::character varying[], CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 0);

INSERT INTO core.customer
(id, user_id, code, "name", phones, created_at, updated_at, created_by, updated_by)
VALUES(0, 0, '2c0e3d09-8dd1-4d26-adbe-0310cf513d2d', 'root', '{0898993601}'::character varying[], CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 0);