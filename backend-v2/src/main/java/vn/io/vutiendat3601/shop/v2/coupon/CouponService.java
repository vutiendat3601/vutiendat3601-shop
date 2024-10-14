package vn.io.vutiendat3601.shop.v2.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

// import vn.io.vutiendat3601.shop.v2.auth.AuthContext;

@RequiredArgsConstructor
@Service
public class CouponService {
  private final CouponDao couponDao;
  private final CouponDtoMapper couponDtoMapper;

  public PageDto<CouponDto> getAvailableCouponByCategoryCode(
      @NonNull String categoryCode, int page, int size) {
    page--;
    final Page<Coupon> couponPage = couponDao.selectAllByCategoryCode(categoryCode, page, size);
    return PageDto.of(couponPage).map(couponDtoMapper);
  }

  public PageDto<CouponDto> getAvailableCouponByProductNo(
      @NonNull String productNo, int page, int size) {
    page--;
    final Page<Coupon> couponPage = couponDao.selectAllByProductNo(productNo, page, size);
    return PageDto.of(couponPage).map(couponDtoMapper);
  }

  public PageDto<CouponDto> getShippingFeeCoupons(Integer page, Integer size) {
    page--;
    final Page<Coupon> couponPage =
        couponDao.selectAllByObjectType(CouponObjectType.SHIPPING_FEE, page, size);
    return PageDto.of(couponPage).map(couponDtoMapper);
  }
}
