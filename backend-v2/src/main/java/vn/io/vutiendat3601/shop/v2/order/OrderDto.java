package vn.io.vutiendat3601.shop.v2.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(
    String trackingNumber,
    OrderStatus status,
    Integer numberOfProducts,
    BigDecimal totalProductAmount,
    BigDecimal totalProductCouponAmount,
    BigDecimal totalProductFinalAmount,
    BigDecimal vatFeeAmount,
    BigDecimal shippingFeeAmount,
    BigDecimal shippingFeeCouponAmount,
    BigDecimal finalAmount,
    String shipingFeeCouponCode,
    String shippingAddressCode,
    String customerCode,
    List<OrderItemDto> items) {}
