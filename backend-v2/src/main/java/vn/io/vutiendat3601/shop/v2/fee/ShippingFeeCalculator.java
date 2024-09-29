package vn.io.vutiendat3601.shop.v2.fee;

import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.shop.v2.order.Order;

public interface ShippingFeeCalculator {
  void calculate(@NonNull Order order);
}
