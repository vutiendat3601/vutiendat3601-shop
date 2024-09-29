CREATE TABLE business.coupon (
  id bigserial NOT NULL PRIMARY KEY,
  code varchar(255) UNIQUE NOT NULL,
  "description" text,
  discount_ratio decimal NOT NULL DEFAULT 0,
  max_amount decimal NOT NULL DEFAULT 0,
  expired_at timestamptz NOT NULL,
  quantity int NOT NULL,
  object_type varchar(100) NOT NULL CHECK (
    object_type IN (
      'PRODUCT',
      'CATEGORY',
      'CUSTOMER',
      'SHIPPING_FEE'
    )
  ),
  "type" varchar(100) NOT NULL CHECK (
    "type" IN (
      'RATIO',
      'AMOUNT'
    )
  ),
  category_id bigint,
  product_id bigint,
  customer_id bigint,
  created_at timestamptz NOT NULL DEFAULT current_timestamp,
  updated_at timestamptz NOT NULL DEFAULT current_timestamp,
  created_by bigint NOT NULL DEFAULT 0,
  updated_by bigint NOT NULL DEFAULT 0
);
-- Reference customer
ALTER TABLE IF EXISTS business.coupon
ADD CONSTRAINT fk_coupon_category
FOREIGN KEY (category_id) 
REFERENCES business.category(id);
-- Reference product
ALTER TABLE IF EXISTS business.coupon
ADD CONSTRAINT fk_coupon_product
FOREIGN KEY (product_id) 
REFERENCES business.product(id);
-- Reference customer
ALTER TABLE IF EXISTS business.coupon
ADD CONSTRAINT fk_coupon_customer
FOREIGN KEY (customer_id)
REFERENCES core.customer(id);
