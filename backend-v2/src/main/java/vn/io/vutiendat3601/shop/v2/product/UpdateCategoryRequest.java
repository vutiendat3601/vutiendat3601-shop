package vn.io.vutiendat3601.shop.v2.product;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(
    @NotBlank String code,
    @NotBlank String slug,
    @NotBlank String name,
    @NotBlank String thumbnail) {}
