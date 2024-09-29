-- VAT ratio
INSERT INTO business.config ("key",number_value,value_type) VALUES
('fee.vat',10,'NUMBER');

INSERT INTO business.config ("key", json_value,value_type) VALUES
('fee.shipping','{"description":null,"defaultFee":30000,"shippingFees":[{"km":50,"unitPrice":17000,"provinceIds":[1]},{"km":200,"unitPrice":23000,"provinceIds":[1]}]}'::jsonb,'JSON');

-- Product Weight Unit
INSERT INTO business.config ("key", text_value,value_type) VALUES
('weight.unit','kg','TEXT');
