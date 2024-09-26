package vn.io.vutiendat3601.shop.v2.address;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class WardDtoMapper implements Function<Ward, WardDto> {

  @Override
  public WardDto apply(Ward ward) {
    Assert.notNull(ward, "ward argument must not be null");
    final District dist = ward.getDistrict();
    return new WardDto(
        ward.getId(),
        ward.getName(),
        dist == null ? null : dist.getId(),
        ward.getCreatedAt(),
        ward.getUpdatedAt());
  }
}
