package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.shop.v2.payment.PaymentStatus;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
  @NonNull
  Optional<OrderPayment> findByRef(@NonNull String ref);

  @NonNull
  Optional<OrderPayment> findByRefAndStatus(@NonNull String ref, @NonNull PaymentStatus status);
}
