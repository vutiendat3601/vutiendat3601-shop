package vn.io.vutiendat3601.shop.v2.payment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.shop.v2.order.OrderPayment;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
  @NonNull
  Optional<OrderPayment> findByRef(@NonNull String ref);
}
