package vn.io.vutiendat3601.shop.v2.address;

import java.time.ZonedDateTime;

public record WardDto(
    Long id, String name, Long districtId, ZonedDateTime createdAt, ZonedDateTime updatedAt) {}
