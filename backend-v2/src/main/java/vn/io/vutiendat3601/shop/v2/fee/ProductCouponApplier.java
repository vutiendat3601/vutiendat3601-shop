package vn.io.vutiendat3601.shop.v2.fee;

import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.shop.v2.order.OrderItem;

public interface ProductCouponApplier {
  void apply(@NonNull String couponCode, @NonNull OrderItem item);
}
