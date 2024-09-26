package vn.io.vutiendat3601.shop.v2.address;

import java.time.ZonedDateTime;

public record DistrictDto(
    Long id, String name, Long provinceId, ZonedDateTime createdAt, ZonedDateTime updatedAt) {}
