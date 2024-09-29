package vn.io.vutiendat3601.shop.v2.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UpdateProductRequest(
    @NotBlank String sku,
    @NotBlank String slug,
    @NotBlank String name,
    @NotBlank String description,
    @Min(value = 0) BigDecimal unitPrice,
    @NotBlank String thumbnail,
    Boolean isActive,
    @NotNull Long categoryId) {}
