package vn.io.vutiendat3601.shop.v2.coupon;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record CouponDto(
    String code,
    String name,
    String description,
    double discountRatio,
    BigDecimal maxAmount,
    ZonedDateTime expiredAt,
    String categoryCode,
    String productNo,
    String customerCode,
    CouponType type,
    CouponObjectType objectType) {}
