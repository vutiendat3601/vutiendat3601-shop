package vn.io.vutiendat3601.shop.v2.coupon;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CouponJpaDataAccessService implements CouponDao {
  private final CouponRepository couponRepo;

  @Override
  @NonNull
  public Optional<Coupon> selectByCode(@NonNull String code) {
    return couponRepo.findByCode(code);
  }
}
