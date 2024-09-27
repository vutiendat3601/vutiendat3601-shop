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
    c.id AS customer_id
  FROM core.users u
  INNER JOIN core.customer c ON u.id = c.user_id;
