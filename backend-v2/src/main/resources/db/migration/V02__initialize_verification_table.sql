CREATE SCHEMA common;

CREATE TABLE common.verification (
  id bigserial NOT NULL PRIMARY KEY,
  user_id bigint NOT NULL,
  code varchar(255) UNIQUE NOT NULL,
  expired_at timestamptz NOT NULL,
  "type" varchar(255) check (type in (
    'LOGIN_CODE',
    'PASSWORD_RESET',
    'EMAIL_VERIFICATION',
    'PHONE_VERIFICATION')
  ),
  is_disabled boolean NOT NULL DEFAULT false,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 1,
  updated_by bigint NOT NULL DEFAULT 1
);
-- Reference users
ALTER TABLE IF EXISTS common.verification 
ADD CONSTRAINT fk_verification_user
FOREIGN KEY (user_id) 
REFERENCES core.users(id);
