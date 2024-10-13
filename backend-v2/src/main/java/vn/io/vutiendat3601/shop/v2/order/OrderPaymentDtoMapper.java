package vn.io.vutiendat3601.shop.v2.order;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderPaymentDtoMapper implements Function<OrderPayment, OrderPaymentDto> {

  @Override
  public OrderPaymentDto apply(OrderPayment orderPayment) {
    Assert.notNull(orderPayment, "orderPayment argument must not be null");
    final Order order = orderPayment.getOrder();
    return new OrderPaymentDto(
        order.getTrackingNumber(),
        orderPayment.getRef(),
        orderPayment.getAmount(),
        orderPayment.getStatus(),
        orderPayment.getMessage(),
        orderPayment.getErrorMessage(),
        orderPayment.getPaymentUrl(),
        orderPayment.getPaymentUrlExpiredAt(),
        orderPayment.getCallbackUrl());
  }
}
