package vn.io.vutiendat3601.shop.v2.coupon;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
  @NonNull
  Optional<Coupon> findByCode(@NonNull String code);
}
