package vn.io.vutiendat3601.shop.v2.coupon;

import java.util.Objects;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CouponDtoMapper implements Function<Coupon, CouponDto> {
  @Override
  public CouponDto apply(Coupon coupon) {
    Assert.notNull(coupon, "coupon argument must not be null");
    final String productNo =
        Objects.nonNull(coupon.getProduct()) ? coupon.getProduct().getProductNo() : null;
    final String customerCode =
        Objects.nonNull(coupon.getCustomer()) ? coupon.getCustomer().getCode() : null;
    final String categoryCode =
        Objects.nonNull(coupon.getCategory()) ? coupon.getCategory().getCode() : null;
    return new CouponDto(
        coupon.getCode(),
        coupon.getName(),
        coupon.getDescription(),
        coupon.getDiscountRatio(),
        coupon.getMaxAmount(),
        coupon.getExpiredAt(),
        categoryCode,
        productNo,
        customerCode,
        coupon.getType(),
        coupon.getObjectType());
  }
}
