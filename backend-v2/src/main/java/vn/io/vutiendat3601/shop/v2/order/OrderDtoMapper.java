package vn.io.vutiendat3601.shop.v2.order;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderDtoMapper implements Function<Order, OrderDto> {

  @Override
  public OrderDto apply(Order order) {
    Assert.notNull(order, "order argument must not be null");
    Assert.notNull(order.getCustomer(), "customer atrribute of order argument must not be null");
    final List<OrderItemDto> itemDtos =
        order.getItems().stream()
            .map(
                oi ->
                    new OrderItemDto(
                        order.getTrackingNumber(),
                        oi.getQuantity(),
                        oi.getTotalAmount(),
                        oi.getCouponAmount(),
                        oi.getFinalAmount(),
                        oi.getProduct().getProductNo(),
                        oi.getProduct().getName(),
                        Objects.nonNull(oi.getCoupon()) ? oi.getCoupon().getCode() : null))
            .toList();
    final String shippingFeeCouponCode =
        Objects.nonNull(order.getShipingFeeCoupon()) ? order.getShipingFeeCoupon().getCode() : null;
    final String shippingAddresCode =
        Objects.nonNull(order.getShippingAddress()) ? order.getShippingAddress().getCode() : null;
    final String customerCode =
        Objects.nonNull(order.getCustomer()) ? order.getCustomer().getCode() : null;
    return new OrderDto(
        order.getTrackingNumber(),
        order.getStatus(),
        order.getNumberOfProducts(),
        order.getTotalProductAmount(),
        order.getTotalProductCouponAmount(),
        order.getTotalProductFinalAmount(),
        order.getVatFeeAmount(),
        order.getShippingFeeAmount(),
        order.getShippingFeeCouponAmount(),
        order.getFinalAmount(),
        shippingFeeCouponCode,
        shippingAddresCode,
        customerCode,
        itemDtos);
  }
}
