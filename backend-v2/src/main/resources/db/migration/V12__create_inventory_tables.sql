-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS inventory;

-- Create the transaction table
CREATE TABLE IF NOT EXISTS inventory.transaction (
  id bigserial NOT NULL PRIMARY KEY,
  product_id int NOT NULL,
  "type" varchar(100) NOT NULL CHECK (
    "type" IN (
      'IN',
      'OUT'
    )
  ),
  before_quantity int NOT NULL,
  quantity_to_stocks int NOT NULL,
  quantity int NOT NULL,
  "description" text,
  receipt_id int NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);

-- Create the receipt table
CREATE TABLE IF NOT EXISTS inventory.receipt (
  id bigserial NOT NULL PRIMARY KEY,
  receipt_number varchar(50) NOT NULL,
  signed_at timestamptz NOT NULL DEFAULT current_timestamp,
  is_signed boolean NOT NULL DEFAULT false,
  signed_by int NOT NULL,
  "type" varchar(100) NOT NULL CHECK (
     "type" IN (
      'IN',
      'OUT'
    )
  ),
  "description" text,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);

-- Create the receipt_items table with inventory_id
CREATE TABLE IF NOT EXISTS inventory.receipt_items (
  id bigserial NOT NULL PRIMARY KEY,
  receipt_id bigint NOT NULL REFERENCES inventory.receipt(id) ON DELETE CASCADE,
  product_id int NOT NULL,
  quantity int NOT NULL,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);

-- Generate receipt number trigger function
CREATE OR REPLACE FUNCTION inventory.gen_receipt_number()
RETURNS TRIGGER AS $$
DECLARE
    date_part TEXT;
    receipt_part TEXT;
    random_part TEXT;
    characters TEXT := 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    i INT;
BEGIN
    date_part := to_char(CURRENT_DATE, 'YYYYMMDD');
    receipt_part := lpad(NEW.id::TEXT, 6, '0');
    random_part := '';
    FOR i IN 1..6 LOOP
        random_part := random_part || substr(characters, floor(1 + random() * length(characters))::int, 1);
    END LOOP;
    NEW.receipt_number := date_part || '-' || receipt_part || '-' || random_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger on the receipt table
CREATE TRIGGER gen_receipt_number_trigger
BEFORE INSERT ON inventory.receipt
FOR EACH ROW
EXECUTE FUNCTION inventory.gen_receipt_number();

-- Ensure receipt_id column exists in business.product table
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name='product' 
                   AND column_name='receipt_id') THEN
        ALTER TABLE business.product ADD COLUMN receipt_id bigint;
    END IF;
END $$;

-- Ensure product_id column exists in business.product table
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name='product' 
                   AND column_name='product_id') THEN
        ALTER TABLE business.product ADD COLUMN product_id int; -- Adjust the datatype if necessary
    END IF;
END $$;

ALTER TABLE inventory.transaction
ADD CONSTRAINT fk_receipt
FOREIGN KEY (receipt_id) REFERENCES inventory.receipt(id) ON DELETE CASCADE;

ALTER TABLE inventory.receipt_items
ADD CONSTRAINT fk_product
FOREIGN KEY (product_id) REFERENCES business.product(id);

ALTER TABLE inventory.receipt_items
ADD CONSTRAINT fk_receipt
FOREIGN KEY (receipt_id) REFERENCES inventory.receipt(id) ON DELETE CASCADE;
