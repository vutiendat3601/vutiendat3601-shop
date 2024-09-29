package vn.io.vutiendat3601.shop.v2.coupon;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface CouponDao {
  @NonNull
  Optional<Coupon> selectByCode(@NonNull String code);
}
