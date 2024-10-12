package vn.io.vutiendat3601.shop.v2.fee;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.auth.AuthContext;
import vn.io.vutiendat3601.shop.v2.coupon.Coupon;
import vn.io.vutiendat3601.shop.v2.coupon.CouponDao;
import vn.io.vutiendat3601.shop.v2.coupon.CouponType;
import vn.io.vutiendat3601.shop.v2.exception.InvalidException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.order.OrderItem;
import vn.io.vutiendat3601.shop.v2.product.Category;
import vn.io.vutiendat3601.shop.v2.product.Product;

@RequiredArgsConstructor
@Service
public class OnlyOneProductCouponApplier implements ProductCouponApplier {
  private final CouponDao couponDao;
  private final AuthContext authContext;

  @Override
  public void apply(@NonNull String couponCode, @NonNull OrderItem orderItem) {
    BigDecimal finalAmount = orderItem.getFinalAmount();
    final Product product = orderItem.getProduct();
    final Coupon coupon =
        couponDao
            .selectByCode(couponCode)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Coupon not found: (code=%s)".formatted(couponCode)));
    if (!checkValidCoupon(coupon) || !checkValidCouponForProduct(coupon, product)) {
      throw new InvalidException(
          "Can't apply coupon for product: (couponCode=%s, productNo=%s)"
              .formatted(coupon.getCode(), product.getProductNo()));
    }
    final BigDecimal unitPrice = product.getUnitPrice();
    final BigDecimal appliedCouponPrice =
        calculateUnitPriceAfterAppliedCoupon(
            unitPrice, coupon.getType(), coupon.getMaxAmount(), coupon.getDiscountRatio());

    final BigDecimal couponAmount = unitPrice.subtract(appliedCouponPrice);
    orderItem.setCouponAmount(couponAmount);
    finalAmount = finalAmount.subtract(couponAmount);
    orderItem.setFinalAmount(finalAmount);
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

  private BigDecimal calculateUnitPriceAfterAppliedCoupon(
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
