CREATE TABLE verification (
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
ALTER TABLE IF EXISTS verification 
ADD CONSTRAINT fk_verification_user
FOREIGN KEY (user_id) 
REFERENCES users(id);
