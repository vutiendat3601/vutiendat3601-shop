package vn.io.vutiendat3601.shop.v2.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
    String productNo,
    String sku,
    String name,
    String description,
    BigDecimal unitPrice,
    BigDecimal unitListedPrice,
    String thumbnail,
    Long buyedCount,
    List<String> tags,
    Long likedCount,
    Boolean isActive,
    Integer unitsInStock,
    String categoryCode) {}
