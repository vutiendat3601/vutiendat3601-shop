package vn.io.vutiendat3601.shop.v2.order;

import java.math.BigDecimal;

public record OrderItemDto(
    String orderTrackingNumber,
    int quantity,
    BigDecimal totalAmount,
    BigDecimal couponAmount,
    BigDecimal finalAmount,
    String productNo,
    String productName,
    String couponCode) {}
