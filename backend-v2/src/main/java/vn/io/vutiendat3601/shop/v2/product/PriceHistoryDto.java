package vn.io.vutiendat3601.shop.v2.product;

import java.math.BigDecimal;

public record PriceHistoryDto(Long id, BigDecimal price, Long productId) {}
