CREATE TABLE business.config (
  id bigserial PRIMARY KEY NOT NULL,
  "key" varchar(500) UNIQUE NOT NULL,
  text_value text,
  boolean_value boolean,
  number_value decimal,
  json_value jsonb,
  value_type varchar(100) NOT NULL DEFAULT 'TEXT' CHECK (
    value_type IN (
      'TEXT',
      'NUMBER',
      'BOOLEAN',
      'JSON'
    )
  ),
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
