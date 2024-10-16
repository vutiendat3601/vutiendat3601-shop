package vn.io.vutiendat3601.shop.v2.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import vn.io.vutiendat3601.shop.v2.common.PageDto;

@Tag(name = "Coupon")
@RequiredArgsConstructor
@RestController
@RequestMapping("v2/coupons")
public class CouponController {
  private final CouponService couponService;

  @GetMapping("shipping-fees")
  public ResponseEntity<PageDto<CouponDto>> getShippingFeeCoupons(
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "100") Integer size) {

    final PageDto<CouponDto> couponDtoPage = couponService.getShippingFeeCoupons(page, size);
    return ResponseEntity.ok(couponDtoPage);
  }
}
