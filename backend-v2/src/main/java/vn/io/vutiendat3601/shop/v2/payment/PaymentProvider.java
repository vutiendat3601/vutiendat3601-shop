package vn.io.vutiendat3601.shop.v2.payment;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.lang.NonNull;

public interface PaymentProvider {
  @NonNull
  String generatePaymentUrl(
      @NonNull BigDecimal amount, @NonNull String paymentRef, @NonNull Map<String, String> params);
}
