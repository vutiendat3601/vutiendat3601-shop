package vn.io.vutiendat3601.shop.v2.product;

import java.math.BigDecimal;

public record ProductDto(
    Long id,
    String sku,
    String name,
    String description,
    BigDecimal unitPrice,
    BigDecimal unitListedPrice,
    String imageUrl,
    Long buyedCount,
    String[] tags,
    Long likedCount,
    Boolean isActive,
    Long unitsInStock) {}
