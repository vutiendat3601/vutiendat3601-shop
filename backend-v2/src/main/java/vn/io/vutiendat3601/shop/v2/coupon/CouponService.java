package vn.io.vutiendat3601.shop.v2.coupon;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.auth.AuthContext;
import vn.io.vutiendat3601.shop.v2.exception.InvalidException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.product.Category;
import vn.io.vutiendat3601.shop.v2.product.Product;
import vn.io.vutiendat3601.shop.v2.product.ProductDao;

@RequiredArgsConstructor
@Service
public class CouponService {
  private final CouponDao couponDao;
  private final ProductDao productDao;
  private final AuthContext authContext;

  @NonNull
  public BigDecimal applyCouponForProductUnitPrice(
      @NonNull String code, @NonNull String productNo) {
    final Coupon coupon =
        couponDao
            .selectByCode(code)
            .orElseThrow(
                () -> new ResourceNotFoundException("Coupon not found: (code=%s)".formatted(code)));

    final Product product =
        productDao
            .selectByProductNoAndIsActiveTrue(productNo)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Product not found: (id=%d)".formatted(productNo)));
    if (!checkValidCoupon(coupon) || !checkValidCouponForProduct(coupon, product)) {
      throw new InvalidException(
          "Can't apply coupon for product: (couponCode=%s, productNo=%s)"
              .formatted(coupon.getCode(), product.getProductNo()));
    }
    return calculatePriceAfterAppliedCoupon(
        product.getUnitPrice(), coupon.getType(), coupon.getMaxAmount(), coupon.getDiscountRatio());
  }

  private boolean checkValidCoupon(@NonNull Coupon coupon) {
    return coupon.getExpiredAt().isAfter(ZonedDateTime.now(ZoneOffset.UTC));
  }

  private boolean checkValidCouponForProduct(@NonNull Coupon coupon, @NonNull Product product) {
    // The coupon amount must be less than the Product unit price
    if (coupon.getType().equals(CouponType.AMOUNT)) {
      final BigDecimal couponAmount = coupon.getMaxAmount();
      final BigDecimal productUnitPrice = product.getUnitPrice();
      if (couponAmount.compareTo(productUnitPrice) > 0) {
        return false;
      }
    }
    switch (coupon.getObjectType()) {
      case CATEGORY: // Check product must belong to the category of coupon
        final Category couponCategory = coupon.getCategory();
        final Category productCategory = product.getCategory();
        return couponCategory.getId().equals(productCategory.getId());
      case CUSTOMER: // Check current user must own the coupon
        final String customerCode = authContext.getUser().customerCode();
        final String couponCustomerCode = coupon.getCustomer().getCode();
        return couponCustomerCode.equals(customerCode);
      default:
        return false;
    }
  }

  private BigDecimal calculatePriceAfterAppliedCoupon(
      @NonNull BigDecimal originalPrice,
      @NonNull CouponType couponType,
      @NonNull BigDecimal couponMaxAmount,
      @NonNull Double couponRatio) {
    switch (couponType) {
      case AMOUNT:
        return originalPrice.subtract(couponMaxAmount);
      case RATIO:
        BigDecimal discountAmount = originalPrice.multiply(new BigDecimal(couponRatio));
        discountAmount =
            discountAmount.compareTo(couponMaxAmount) < 0 ? discountAmount : couponMaxAmount;
        return originalPrice.subtract(discountAmount);
      default:
        return originalPrice;
    }
  }
}
