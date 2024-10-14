package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface OrderRepository extends JpaRepository<Order, Long> {
  @NonNull
  Optional<Order> findByTrackingNumber(@NonNull String trackingNumber);

  @NonNull
  Optional<Order> findByTrackingNumberAndCustomerCode(
      @NonNull String trackingNumber, @NonNull String customerCode);
}
