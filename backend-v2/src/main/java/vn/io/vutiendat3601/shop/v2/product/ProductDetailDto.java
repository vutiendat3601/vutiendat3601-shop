package vn.io.vutiendat3601.shop.v2.product;

import java.math.BigDecimal;

public record ProductDetailDto(
    String productNo,
    String sku,
    String slug,
    String name,
    String description,
    BigDecimal unitPrice,
    BigDecimal unitListedPrice,
    String thumbnail,
    Long buyedCount,
    Long likedCount,
    Boolean isActive,
    Long unitsInStock,
    Category category) {}
