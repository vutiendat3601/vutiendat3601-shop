package vn.io.vutiendat3601.shop.v2.coupon;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  @Override
  @NonNull
  public Page<Coupon> selectAllByCustomerCode(@NonNull String customerCode, int page, int size) {
    return couponRepo.findAllByCustomerCode(customerCode, PageRequest.of(page, size));
  }

  @Override
  @NonNull
  public Page<Coupon> selectAllByCategoryCode(@NonNull String categoryCode, int page, int size) {
    return couponRepo.findAllByCategoryCodeAndObjectType(
        categoryCode, CouponObjectType.CATEGORY, PageRequest.of(page, size));
  }

  @Override
  @NonNull
  public Page<Coupon> selectAllByProductNo(@NonNull String productNo, int page, int size) {
    return couponRepo.findAllByProductProductNoAndObjectType(
        productNo, CouponObjectType.PRODUCT, PageRequest.of(page, size));
  }

  @Override
  @NonNull
  public Page<Coupon> selectAllByObjectType(
      @NonNull CouponObjectType objectType, int page, int size) {
    return couponRepo.findAllByObjectType(
        CouponObjectType.SHIPPING_FEE, PageRequest.of(page, size));
  }
}
