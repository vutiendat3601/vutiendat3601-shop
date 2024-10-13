package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import org.springframework.lang.NonNull;
import vn.io.vutiendat3601.shop.v2.payment.PaymentStatus;

public interface OrderPaymentDao {
  @NonNull
  Optional<OrderPayment> selectByRef(@NonNull String ref);

  @NonNull
  Optional<OrderPayment> selectByRefAndStatus(@NonNull String ref, @NonNull PaymentStatus status);

  void insert(@NonNull OrderPayment orderPayment);

  void update(@NonNull OrderPayment orderPayment);
}
