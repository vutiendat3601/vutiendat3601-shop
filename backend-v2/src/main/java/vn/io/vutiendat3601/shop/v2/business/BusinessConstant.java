package vn.io.vutiendat3601.shop.v2.business;

import java.util.Set;
import vn.io.vutiendat3601.shop.v2.coupon.CouponObjectType;

public interface BusinessConstant {
  String KEY_VAT_FEE = "fee.vat";
  String KEY_SHIPPING_FEE = "fee.shipping";
  String KEY_SHIPPING_FEE_DEFAULT = "fee.shipping.default";
  String KEY_WEIGHT_UNIT = "weight.unit";


  Set<CouponObjectType> PRODUCT_AVAILABLE_COUPON_TYPES =
      Set.of(CouponObjectType.PRODUCT, CouponObjectType.CATEGORY, CouponObjectType.CUSTOMER);
}
