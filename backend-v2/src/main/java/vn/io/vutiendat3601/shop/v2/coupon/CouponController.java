package vn.io.vutiendat3601.shop.v2.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v2/coupons")
public class CouponController {
  private final CouponService couponService;
}
