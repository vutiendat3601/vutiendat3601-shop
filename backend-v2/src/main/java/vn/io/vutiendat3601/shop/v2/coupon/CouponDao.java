package vn.io.vutiendat3601.shop.v2.coupon;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface CouponDao {
  @NonNull
  Optional<Coupon> selectByCode(@NonNull String code);

  @NonNull
  Page<Coupon> selectAllByCustomerCode(@NonNull String customerCode, int page, int size);

  @NonNull
  Page<Coupon> selectAllByCategoryCode(@NonNull String categoryCode, int page, int size);

  @NonNull
  Page<Coupon> selectAllByProductNo(@NonNull String productNo, int page, int size);
}
