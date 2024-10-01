package vn.io.vutiendat3601.shop.v2.order;

import jakarta.validation.constraints.NotNull;

public record CreateOrderItemDto(
    @NotNull String productNo, @NotNull Integer quantity, String couponCode) {}
