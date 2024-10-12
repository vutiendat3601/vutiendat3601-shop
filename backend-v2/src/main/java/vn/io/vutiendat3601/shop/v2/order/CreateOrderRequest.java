package vn.io.vutiendat3601.shop.v2.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderRequest(
    @NotNull String addressCode,
    String shippingFeeCouponCode,
    @NotNull @NotEmpty List<CreateOrderItemDto> items) {}
