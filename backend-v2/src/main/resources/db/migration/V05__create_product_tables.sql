CREATE SCHEMA business;

CREATE TABLE business.category (
  id bigserial NOT NULL PRIMARY KEY,
  code varchar(255) UNIQUE NOT NULL,
  slug varchar(255) UNIQUE NOT NULL,
  "name" varchar(255) NOT NULL,
  thumbnail text NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);

CREATE TABLE business.product (
    id bigserial NOT NULL PRIMARY KEY,
    "product_no" varchar(16) NOT NULL UNIQUE,
    sku varchar(255) UNIQUE NOT NULL,
    slug varchar(1000) UNIQUE NOT NULL,
    "name" varchar(500) NOT NULL,
    description text,
    unit_price decimal NOT NULL DEFAULT 0,
    unit_listed_price decimal NOT NULL DEFAULT 0,
    thumbnail text DEFAULT NULL,    
    buyed_count bigint NOT NULL DEFAULT 0,
    tags varchar(150)[] NOT NULL DEFAULT '{}',
    liked_count bigint NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT false,
    units_in_stock bigint DEFAULT 0,
    category_id bigint NOT NULL,
    "ref" text,
    created_at timestamptz NOT NULL DEFAULT current_timestamp,
    updated_at timestamptz NOT NULL DEFAULT current_timestamp,
    created_by bigint NOT NULL DEFAULT 0,
    updated_by bigint NOT NULL DEFAULT 0
);
-- Reference category
ALTER TABLE IF EXISTS business.product
ADD CONSTRAINT fk_product_category
FOREIGN KEY (category_id) 
REFERENCES business.category(id);

CREATE TABLE business.price_history (
  id bigserial NOT NULL PRIMARY KEY,
  price decimal NOT NULL DEFAULT 0,
  product_id bigint NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference product
ALTER TABLE IF EXISTS business.price_history
ADD CONSTRAINT fk_price_history_product
FOREIGN KEY (product_id) 
REFERENCES business.product(id);
