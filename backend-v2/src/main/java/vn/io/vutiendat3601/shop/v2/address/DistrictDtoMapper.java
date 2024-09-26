package vn.io.vutiendat3601.shop.v2.address;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DistrictDtoMapper implements Function<District, DistrictDto> {

  @Override
  public DistrictDto apply(District dist) {
    Assert.notNull(dist, "dist argument must not be null");
    final Province province = dist.getProvince();
    return new DistrictDto(
        dist.getId(),
        dist.getName(),
        province == null ? null : province.getId(),
        dist.getCreatedAt(),
        dist.getUpdatedAt());
  }
}
