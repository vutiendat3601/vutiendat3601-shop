CREATE SCHEMA bussiness;

CREATE TABLE bussiness.category (
  id bigserial NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);

CREATE TABLE bussiness.product (
    id bigserial NOT NULL PRIMARY KEY,
    sku varchar(255) UNIQUE NOT NULL,
    name varchar(255) NOT NULL,
    description text,
    unit_price DECIMAL NOT NULL DEFAULT 0,
    unit_listed_price DECIMAL NOT NULL DEFAULT 0,
    image VARCHAR(255) DEFAULT NULL,    
    buyed_count bigint NOT NULL DEFAULT 0,
    tags varchar(32)[] NOT NULL DEFAULT ARRAY[]::varchar(32)[],
    liked_count bigint NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT true,
    units_in_stock bigint DEFAULT NULL,
    category_id bigint NOT NULL,
    created_at timestamptz NOT NULL DEFAULT current_timestamp,
    updated_at timestamptz NOT NULL DEFAULT current_timestamp,
    created_by bigint NOT NULL DEFAULT 0,
    updated_by bigint NOT NULL DEFAULT 0
);
-- Reference category
ALTER TABLE IF EXISTS bussiness.product
ADD CONSTRAINT fk_product_category
FOREIGN KEY (category_id) 
REFERENCES bussiness.category(id);

CREATE TABLE bussiness.price_history (
  id bigserial NOT NULL PRIMARY KEY,
  price DECIMAL NOT NULL DEFAULT 0,
  product_id bigint NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference product
ALTER TABLE IF EXISTS bussiness.price_history
ADD CONSTRAINT fk_price_history_product
FOREIGN KEY (product_id) 
REFERENCES bussiness.product(id);
