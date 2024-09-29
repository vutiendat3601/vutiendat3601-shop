package vn.io.vutiendat3601.shop.v2.order;

import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderDtoMapper implements Function<Order, OrderDto> {

  @Override
  public OrderDto apply(Order order) {
    Assert.notNull(order, "order argument must not be null");
    Assert.notNull(order.getCustomer(), "customer atrribute of order argument must not be null");
    return new OrderDto(order.getId());
  }
}
