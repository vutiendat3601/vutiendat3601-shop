package vn.io.vutiendat3601.shop.v2.coupon;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
  @NonNull
  Optional<Coupon> findByCode(@NonNull String code);

  @NonNull
  Page<Coupon> findAllByCustomerCode(@NonNull String cusomterCode, @NonNull Pageable pageable);

  @NonNull
  Page<Coupon> findAllByCategoryCodeAndObjectType(
      @NonNull String categoryCode,
      @NonNull CouponObjectType objectType,
      @NonNull Pageable pageable);

  @NonNull
  Page<Coupon> findAllByProductProductNoAndObjectType(
      @NonNull String productNo, @NonNull CouponObjectType objectType, @NonNull Pageable pageable);

  Page<Coupon> findAllByObjectType(@NonNull CouponObjectType shippingFee, PageRequest of);
}
