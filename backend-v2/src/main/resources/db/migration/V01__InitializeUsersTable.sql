CREATE TABLE users (
  id bigserial NOT NULL PRIMARY KEY,
  display_name varchar(255) NOT NULL,
  email varchar(255) UNIQUE NOT NULL,
  phone varchar(255) UNIQUE,
  is_verified boolean NOT NULL DEFAULT false,
  hashed_password varchar(255),
  roles varchar(255)[] NOT NULL DEFAULT '{}',
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 1,
  updated_by bigint NOT NULL DEFAULT 1
);
INSERT INTO users
(id, is_verified, display_name, email, hashed_password, phone, created_at, updated_at)
VALUES(nextval('users_id_seq'::regclass), true, 'root', 'vutien.dat.work@gmail.com', null, null, current_timestamp, current_timestamp);
