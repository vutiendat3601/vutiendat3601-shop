INSERT INTO business.coupon
(id, code, "name", description, discount_ratio, max_amount, expired_at, quantity, object_type, "type", category_id, product_id, customer_id, created_at, updated_at, created_by, updated_by) 
VALUES
(nextval('business.coupon_id_seq'), 'TrangDiem1024', 'Giảm 50K cho mặt hàng trang điểm tháng 10', NULL, 0, 50000, '2024-10-31 00:23:43.705', 200, 'CATEGORY', 'RATIO', 3, NULL, NULL, '2024-10-11 21:19:49.561', '2024-10-11 21:19:49.561', 0, 0),
(nextval('business.coupon_id_seq'), 'Sach1024', 'Giảm 20K cho mặt hàng sách', NULL, 0, 20000, '2024-10-31 00:23:43.705', 200, 'CATEGORY', 'AMOUNT', 2, NULL, NULL, '2024-09-29 23:23:43.714', '2024-09-29 23:23:43.714', 0, 0),
(nextval('business.coupon_id_seq'), 'PKDT1024', 'Phụ kiện điện tử Giảm giá 30%', NULL, 30, 60000, '2024-10-31 00:23:43.705', 200, 'CATEGORY', 'RATIO', 1, NULL, NULL, '2024-10-11 21:14:18.327', '2024-10-11 21:14:18.327', 0, 0),
(nextval('business.coupon_id_seq'), 'MAQP1024', 'Máy ảnh - quay phim giảm giá 100K', NULL, 0, 100000, '2024-10-31 00:23:43.705', 200, 'CATEGORY', 'AMOUNT', 8, NULL, NULL, '2024-10-11 21:19:49.570', '2024-10-11 21:19:49.570', 0, 0),
(nextval('business.coupon_id_seq'), 'Energizer50K', 'Sạc dự phòng Energizer QE10013PQ giảm 50K', NULL, 0, 50000, '2024-10-31 00:23:43.705', 200, 'PRODUCT', 'AMOUNT', NULL, 1, NULL, '2024-10-11 21:19:49.570', '2024-10-11 21:19:49.570', 0, 0),
(nextval('business.coupon_id_seq'), 'LogitechB10050K', 'Chuột Có Dây Logitech B100 Giảm 20K', NULL, 0, 50000, '2024-10-31 00:23:43.705', 200, 'PRODUCT', 'AMOUNT', NULL, 6, NULL, '2024-10-11 21:19:49.570', '2024-10-11 21:19:49.570', 0, 0),
(nextval('business.coupon_id_seq'), 'FREESHIP15K', 'Giảm 15K phí vận chuyển', NULL, 0, 15000, '2024-10-31 00:23:43.705', 200, 'SHIPPING_FEE', 'AMOUNT', NULL, 6, NULL, '2024-10-11 21:19:49.570', '2024-10-11 21:19:49.570', 0, 0)
;
