package vn.io.vutiendat3601.shop.v2.order;

import java.util.Optional;
import org.springframework.lang.NonNull;

public interface OrderPaymentDao {
  @NonNull
  Optional<OrderPayment> selectByRef(@NonNull String ref);

  void insert(@NonNull OrderPayment orderPayment);
}
