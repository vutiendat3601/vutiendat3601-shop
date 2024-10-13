CREATE TABLE business.order_payment (
  id bigserial NOT NULL PRIMARY KEY,
  ref varchar(255) NOT NULL UNIQUE,
  amount decimal NOT NULL DEFAULT 0,
  "message" text NOT NULL,
  error_message text,
  method varchar(100) NOT NULL CHECK (
    "method" IN (
      'VN_PAY'
    )
  ),
  client_ip varchar(255),
  payment_url text,
  payment_url_expired_at timestamptz,
  callback_url text,
  "status" varchar(100) NOT NULL CHECK (
    "status" IN (
      'PENDING',
      'SUCCESS',
      'FAIL'
    )
  ),
  order_id int NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
