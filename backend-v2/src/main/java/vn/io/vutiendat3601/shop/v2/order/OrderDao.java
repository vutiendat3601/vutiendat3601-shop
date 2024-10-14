package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface OrderDao {
  long insert(@NonNull Order order);

  @NonNull
  Optional<Order> selectByTrackingNumber(@NonNull String trackingNumber);

  @NonNull
  Optional<Order> selectById(long id);

  @NonNull
  Optional<Order> selectByTrackingNumberAndCustomerCode(
      @NonNull String trackingNumber, @NonNull String customerCode);
}
