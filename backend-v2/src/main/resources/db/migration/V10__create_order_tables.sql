-- Order ----------------------------------------------------------------
CREATE TABLE business.orders (
  id bigserial NOT NULL PRIMARY KEY,
  tracking_number varchar(100) UNIQUE NOT NULL,
  "status" varchar(255) NOT NULL DEFAULT 'PENDING' CHECK ("status" IN (
    'PENDING',
    'PAID',
    'PAYMENT_FAILED',
    'PROCESSING',
    'DELIVERING',
    'DELIVERED',
    'CANCELLED',
    'RETURNED',
    'REFUNDED')
  ),
  num_of_products int NOT NULL DEFAULT 0,
  total_product_amount decimal NOT NULL DEFAULT 0,
  total_product_coupon_amount decimal NOT NULL DEFAULT 0,
  total_product_final_amount decimal NOT NULL DEFAULT 0,
  shipping_fee_amount decimal NOT NULL DEFAULT 0,
  shipping_fee_coupon_amount decimal NOT NULL DEFAULT 0,
  vat_fee_amount decimal NOT NULL DEFAULT 0,
  final_amount decimal NOT NULL DEFAULT 0,
  customer_id bigint NOT NULL,
  shipping_address_id bigint NOT NULL,
  shipping_fee_coupon_id bigint,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference customer
ALTER TABLE IF EXISTS business.orders
ADD CONSTRAINT fk_orders_customer
FOREIGN KEY (customer_id) 
REFERENCES core.customer(id);
-- Reference address
ALTER TABLE IF EXISTS business.orders
ADD CONSTRAINT fk_orders_address
FOREIGN KEY (shipping_address_id) 
REFERENCES core.address(id);
-- Reference address
ALTER TABLE IF EXISTS business.orders
ADD CONSTRAINT fk_orders_coupon
FOREIGN KEY (shipping_fee_coupon_id)
REFERENCES business.coupon(id);
-- Tracking number trigger
CREATE OR REPLACE FUNCTION business.gen_tracking_number()
RETURNS TRIGGER AS $$
DECLARE
    date_part TEXT;
    order_part TEXT;
    random_part TEXT;
    characters TEXT := 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    i INT;
BEGIN
    date_part := to_char(CURRENT_DATE, 'YYYYMMDD');
    order_part := lpad(NEW.id::TEXT, 6, '0');
    random_part := '';
    FOR i IN 1..6 LOOP
        random_part := random_part || substr(characters, floor(1 + random() * length(characters))::int, 1);
    END LOOP;
    NEW.tracking_number := date_part || '-' || order_part || '-' || random_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER gen_tracking_number_trigger
BEFORE INSERT ON business.orders
FOR EACH ROW
EXECUTE FUNCTION business.gen_tracking_number();

-- OrderItem ----------------------------------------------------------------
CREATE TABLE business.order_item (
  id bigserial NOT NULL PRIMARY KEY,
  quantity int NOT NULL DEFAULT 1,
  total_amount decimal NOT NULL DEFAULT 0,
  coupon_amount decimal NOT NULL DEFAULT 0,
  final_amount decimal NOT NULL DEFAULT 0,
  order_id bigint NOT NULL,
  product_id bigint NOT NULL,
  coupon_id bigint,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference customer
ALTER TABLE IF EXISTS business.order_item
ADD CONSTRAINT fk_order_item_orders
FOREIGN KEY (order_id) 
REFERENCES business.orders(id);
-- Reference product
ALTER TABLE IF EXISTS business.order_item
ADD CONSTRAINT fk_order_item_product
FOREIGN KEY (product_id) 
REFERENCES business.product(id);
-- Reference coupon
ALTER TABLE IF EXISTS business.order_item
ADD CONSTRAINT fk_order_item_coupon
FOREIGN KEY (coupon_id)
REFERENCES business.coupon(id);
