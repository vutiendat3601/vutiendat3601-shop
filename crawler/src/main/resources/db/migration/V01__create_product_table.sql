CREATE SCHEMA IF NOT EXISTS bussiness;

-- DROP TABLE IF EXISTS bussiness.product;

CREATE TABLE bussiness.product (
    id bigserial NOT NULL PRIMARY KEY,
    sku varchar(255) UNIQUE NOT NULL,
    slug varchar(1000) UNIQUE NOT NULL,
    "name" varchar(500) NOT NULL,
    description text,
    unit_price DECIMAL NOT NULL DEFAULT 0,
    unit_listed_price DECIMAL NOT NULL DEFAULT 0,
    thumbnail text DEFAULT NULL,    
    buyed_count bigint NOT NULL DEFAULT 0,
    tags varchar(150)[] NOT NULL DEFAULT ARRAY[]::varchar(150)[],
    liked_count bigint NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT true,
    units_in_stock bigint DEFAULT NULL,
    category_id bigint NOT NULL,
    "ref" text,
    created_at timestamptz NOT NULL DEFAULT current_timestamp,
    updated_at timestamptz NOT NULL DEFAULT current_timestamp,
    created_by bigint NOT NULL DEFAULT 0,
    updated_by bigint NOT NULL DEFAULT 0
);
